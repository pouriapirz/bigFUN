package config;

import asterixReadOnlyClient.AsterixClientFixedWorkload;
import asterixReadOnlyClient.AsterixClientRandomWorkload;
import asterixUpdateClient.AsterixClientUpdateWorkload;
import client.AbstractReadOnlyClient;
import client.AbstractUpdateClient;
import structure.UpdateTag;

public class AsterixClientConfig extends AbstractClientConfig {

    public AsterixClientConfig(String clientConfigFile) {
        super(clientConfigFile);
    }

    public AbstractReadOnlyClient readReadOnlyClientConfig() {
        //common params 
        String clientTypeTag = (String) getParamValue(Constants.CLIENT_TYPE);
        String cc = (String) getParamValue(Constants.CC_URL);
        String qIxFile = (String) getParamValue(Constants.Q_IX_FILE);
        String statsFile = (String) getParamValue(Constants.STATS_FILE);
        String qSeqFile = (String) getParamValue(Constants.Q_SEQ_FILE);
        String dumpDirFile = (String) getParamValue(Constants.DUMP_DIR_FILE);
        String resultsFile = (String) getParamValue(Constants.RESULTS_DUMP_FILE);
        String dvName = (String) getParamValue(Constants.ASTX_DV_NAME);

        int ignore = -1;
        if (isParamSet(Constants.IGNORE)) {
            ignore = (int) getParamValue(Constants.IGNORE);
        }

        boolean qExec = true;
        if (isParamSet(Constants.EXECUTE_QUERY)) {
            qExec = (boolean) getParamValue(Constants.EXECUTE_QUERY);
        }

        boolean dumpResults = false;
        if (isParamSet(Constants.ASTX_DUMP_RESULTS)) {
            dumpResults = (boolean) getParamValue(Constants.ASTX_DUMP_RESULTS);
        }

        switch (clientTypeTag) {
            case Constants.ASTX_RANDOM_CLIENT_TAG:
                int iter = (int) getParamValue(Constants.ITERATIONS);
                String qGenConfigFile = (String) getParamValue(Constants.Q_GEN_CONFIG_FILE);
                AsterixClientRandomWorkload rClient = new AsterixClientRandomWorkload(cc, dvName, iter, qGenConfigFile,
                        qIxFile, statsFile, ignore, qSeqFile, dumpDirFile, resultsFile);

                rClient.setExecQuery(qExec);
                rClient.setDumpResults(dumpResults);
                return rClient;

            case Constants.ASTX_FIXED_CLIENT_TAG:
                AsterixClientFixedWorkload fClient = new AsterixClientFixedWorkload(cc, dvName, qIxFile, statsFile,
                        ignore, qSeqFile, dumpDirFile, resultsFile);

                fClient.setExecQuery(qExec);
                fClient.setDumpResults(dumpResults);
                return fClient;

            default:
                System.err.println("Unknown asterix client type " + clientTypeTag);
                System.err.println("Valid client types are:\n");
                System.err.println("\t" + Constants.ASTX_RANDOM_CLIENT_TAG);
                System.err.println("\t" + Constants.ASTX_FIXED_CLIENT_TAG);
        }

        return null;
    }

    @Override
    public AbstractUpdateClient readUpdateClientConfig() {
        String cc = (String) getParamValue(Constants.CC_URL);
        String oprType = (String) getParamValue(Constants.UPDATE_OPR_TYPE_TAG);
        String statsFile = (String) getParamValue(Constants.STATS_FILE);
        String resultsFile = (String) getParamValue(Constants.RESULTS_DUMP_FILE);
        String updatesFile = (String) getParamValue(Constants.UPDATES_FILE);
        String dvName = (String) getParamValue(Constants.ASTX_DV_NAME);
        String dsName = (String) getParamValue(Constants.ASTX_DS_NAME);
        String keyName = (String) getParamValue(Constants.ASTX_KEY_NAME);
        int batchSize = (int) getParamValue(Constants.UPDATE_BATCH_SIZE);

        int limit = -1;
        if (isParamSet(Constants.UPDATE_LIMIT)) {
            limit = (int) getParamValue(Constants.UPDATE_LIMIT);
        }

        int ignore = -1;
        if (isParamSet(Constants.IGNORE)) {
            ignore = (int) getParamValue(Constants.IGNORE);
        }

        UpdateTag upTag = null;
        if (oprType.equals(Constants.INSERT_OPR_TYPE)) {
            upTag = UpdateTag.INSERT;
        } else if (oprType.equals(Constants.DELETE_OPR_TYPE)) {
            upTag = UpdateTag.DELETE;
        } else {
            System.err.println("Unknow Data Manipulation Operation for AsterixDB - " + oprType);
            System.err.println("You can only run " + Constants.INSERT_OPR_TYPE + " and " + Constants.DELETE_OPR_TYPE
                    + " against AsterixDB");
            return null;
        }

        String warmupUpdatesFile = (String) getParamValue(Constants.WARMUP_FILE);
        String warmupStatsFile = (String) getParamValue(Constants.WARMUP_STATS_FILE);

        return new AsterixClientUpdateWorkload(cc, dvName, dsName, keyName, upTag, batchSize, limit, updatesFile,
                statsFile, ignore, resultsFile, warmupUpdatesFile, warmupStatsFile);
    }
}
