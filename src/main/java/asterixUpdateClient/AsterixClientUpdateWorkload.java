package asterixUpdateClient;

import client.AbstractUpdateClient;
import config.Constants;
import structure.UpdateTag;
import workloadGenerator.AbstractUpdateWorkloadGenerator;
import workloadGenerator.UpdateWorkloadGenerator;

public class AsterixClientUpdateWorkload extends AbstractUpdateClient {
    String ccUrl;
    String dvName;
    String dsName;
    String keyName;
    UpdateTag upTag;

    AbstractUpdateWorkloadGenerator uwg;

    public AsterixClientUpdateWorkload(String cc, String dvName, String dsName, String keyName, UpdateTag upTag,
            int batchSize, int limit, String updatesFile, String statsFile, int ignore) {
        super();
        this.ccUrl = cc;
        this.dvName = dvName;
        this.dsName = dsName;
        this.keyName = keyName;
        this.upTag = upTag;
        initUpdateWorkloadGen();
        setClientUtil(batchSize, limit, updatesFile, statsFile, ignore);
        clUtil.init();
    }

    @Override
    protected void setClientUtil(int batchSize, int limit, String updatesFile, String statsFile, int ignore) {
        clUtil = new AsterixUpdateClientUtility(ccUrl, batchSize, limit, uwg, updatesFile, statsFile, ignore);
    }

    @Override
    protected void initUpdateWorkloadGen() {
        this.uwg = new UpdateWorkloadGenerator(dvName, dsName, keyName, upTag);
    }

    @Override
    public void execute() {
        int qid = (upTag == UpdateTag.INSERT) ? Constants.INSERT_QIX : Constants.DELETE_QIX;
        clUtil.processUpdates(qid, false);
        clUtil.terminate();
    }

}
