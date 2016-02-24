package client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;

import structure.StatsCollector;
import structure.Update;
import workloadGenerator.AbstractUpdateWorkloadGenerator;

public abstract class AbstractUpdateClientUtility extends AbstractClientUtility {

    int limit; //how many batches to run (in case of early termination request) - if negative it processes all the file
    int batchSize;
    protected String updatesFile;
    AbstractUpdateWorkloadGenerator uwg;

    String warmupUpdatesFile; //if no warmup is needed, simply leave as null
    protected StatsCollector warmupSc;

    public AbstractUpdateClientUtility(int batchSize, int limit, AbstractUpdateWorkloadGenerator uwg,
            String updatesFile, String statsFile, int ignore, String resultsFile, String warmupUpdatesFile,
            String warmupStatsFile) {
        super(statsFile, resultsFile, ignore);
        this.batchSize = batchSize;
        this.limit = limit;
        this.updatesFile = updatesFile;
        this.uwg = uwg;
        //Added for the doing and tracking warm-up phase
        this.warmupUpdatesFile = warmupUpdatesFile;
        warmupSc = needWarmup() ? new StatsCollector(warmupStatsFile, -1) : null;
    }

    protected abstract void executeUpdate(int qid, Update update, boolean isWarmup);

    protected void updateStat(int qid, int vid, long rspTime, boolean isWarmup) {
        if (isWarmup) {
            warmupSc.updateStat(qid, vid, rspTime);
        } else {
            sc.updateStat(qid, vid, rspTime);
        }
    }

    public void generateReport() {
        sc.report();
        if (needWarmup()) {
            warmupSc.report();
        }
    }

    public void processUpdates(int qid, boolean isWarmup) {
        try {
            BufferedReader in = null;
            if (isWarmup) {
                in = new BufferedReader(new FileReader(warmupUpdatesFile));
            } else {
                in = new BufferedReader(new FileReader(updatesFile));
            }

            int totalBatchCounter = 0;
            int currentBatchSize = 0;
            StringBuffer sb = null;
            String str;
            while ((limit < 0 || totalBatchCounter < limit) && ((str = in.readLine()) != null)) {
                if (sb == null) {
                    sb = new StringBuffer();
                }
                sb.append(str).append("\n");
                if ((++currentBatchSize) == batchSize) { //we have read enough to form the next batch
                    StringReader sr = new StringReader(sb.toString());
                    runOneBatch(qid, sr, isWarmup);
                    sb = null;
                    currentBatchSize = 0;
                    totalBatchCounter++;
                }
            }

            if (currentBatchSize > 0) { //Last set of updates, whose size is less than batch size
                StringReader sr = new StringReader(sb.toString());
                runOneBatch(qid, sr, isWarmup);
            }

            in.close();
        } catch (Exception e) {
            System.err.println("Problem in processing updates in Update Client Utility");
            e.printStackTrace();
        }
    }

    private void runOneBatch(int qid, Reader r, boolean isWarmup) {
        uwg.resetUpdatesInput(r);
        Update nextUpdate = uwg.getNextUpdate();
        executeUpdate(qid, nextUpdate, isWarmup);
    }

    public boolean needWarmup() {
        return !(this.warmupUpdatesFile == null);
    }

    public abstract void resetTraceCounters(); //To reset trace-only stats after warmup phase

}
