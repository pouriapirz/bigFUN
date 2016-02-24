package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import structure.StatsCollector;

public abstract class AbstractClientUtility {

    protected PrintWriter resPw;
    protected StatsCollector sc;

    public AbstractClientUtility(String statsFile, String resultsFile, int ignore) {
        this.sc = new StatsCollector(statsFile, ignore);
        if (resultsFile != null) {
            try {
                resPw = new PrintWriter(new File(resultsFile));
            } catch (FileNotFoundException e) {
                System.err.println("Problem in creating writer for query results dump !");
                e.printStackTrace();
            }
        }
    }

    public abstract void init();

    public abstract void terminate();

    protected void updateStat(int qid, int vid, long rspTime) {
        sc.updateStat(qid, vid, rspTime);
    }

    public void generateReport() {
        sc.report();
    }

}
