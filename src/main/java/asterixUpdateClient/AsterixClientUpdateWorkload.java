package asterixUpdateClient;

import client.AbstractUpdateClient;
import config.Constants;
import structure.UpdateTag;
import workloadGenerator.AbstractUpdateWorkloadGenerator;

public class AsterixClientUpdateWorkload extends AbstractUpdateClient {
    String ccUrl;
    String dvName;
    String dsName;
    String keyName;
    UpdateTag upTag;

    AbstractUpdateWorkloadGenerator uwg;

    String warmupUpdatesFile;
    String warmupStatsFile;

    public AsterixClientUpdateWorkload(String cc, String dvName, String dsName, String keyName, UpdateTag upTag,
            int batchSize, int limit, String updatesFile, String statsFile, int ignore, String resultsFile,
            String warmupUpdatesFile, String warmupStatsFile) {
        super();
        this.ccUrl = cc;
        this.dvName = dvName;
        this.dsName = dsName;
        this.keyName = keyName;
        this.upTag = upTag;
        this.warmupUpdatesFile = warmupUpdatesFile;
        this.warmupStatsFile = warmupStatsFile;
        initUpdateWorkloadGen();
        setClientUtil(batchSize, limit, updatesFile, statsFile, ignore, resultsFile);
        clUtil.init();
    }

    @Override
    protected void setClientUtil(int batchSize, int limit, String updatesFile, String statsFile, int ignore,
            String resultsFile) {
        clUtil = new AsterixUpdateClientUtility(ccUrl, batchSize, limit, uwg, updatesFile, statsFile, ignore,
                resultsFile, warmupUpdatesFile, warmupStatsFile);
    }

    @Override
    protected void initUpdateWorkloadGen() {
        this.uwg = new AsterixUpdateWorkloadGenerator(dvName, dsName, keyName, upTag);
    }

    @Override
    public void execute() {
        int qid = (upTag == UpdateTag.INSERT) ? Constants.INSERT_QIX : Constants.DELETE_QIX;
        if (clUtil.needWarmup()) {
            System.out.println("Warmup Phase is about to start.");
            clUtil.processUpdates(qid, true);
            System.out.println("Warmup Phase is finished.\nSwitching to stats-collection phase.");
            clUtil.resetTraceCounters();
        }
        clUtil.processUpdates(qid, false);
        clUtil.terminate();
    }

}
