/*
 * Copyright by The Regents of the University of California
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * you may obtain a copy of the License from
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package config;

import asterixReadOnlyClient.AsterixClientReadOnlyWorkload;
import asterixUpdateClient.AsterixClientUpdateWorkload;
import client.AbstractReadOnlyClient;
import client.AbstractUpdateClient;
import structure.UpdateTag;

public class AsterixClientConfig extends AbstractClientConfig {

    public AsterixClientConfig(String clientConfigFile) {
        super(clientConfigFile);
    }

    public AbstractReadOnlyClient readReadOnlyClientConfig(String bigFunHomePath) {
        String cc = (String) getParamValue(Constants.CC_URL);
        String dvName = (String) getParamValue(Constants.ASTX_DV_NAME);
        int iter = (int) getParamValue(Constants.ITERATIONS);

        String qIxFile = bigFunHomePath + "/files/" + Constants.Q_IX_FILE_NAME;
        String qGenConfigFile = bigFunHomePath + "/files/" + Constants.Q_GEN_CONFIG_FILE_NAME;
        String workloadFile = bigFunHomePath + "/files/" + Constants.WORKLOAD_FILE_NAME;

        String statsFile = bigFunHomePath + "/files/output/" + Constants.STATS_FILE_NAME;
        if (isParamSet(Constants.STATS_FILE)) {
            statsFile = (String) getParamValue(Constants.STATS_FILE);
        }

        long seed = Constants.DEFAULT_SEED;
        if (isParamSet(Constants.SEED)) {
            Object value = getParamValue(Constants.SEED);
            if (value instanceof Long) {
                seed = (long) value;
            } else if (value instanceof Integer) {
                seed = ((Integer) value).longValue();
            } else {
                System.err.println("WARNING: Invalid Seed value in " + Constants.BIGFUN_CONFIG_FILE_NAME
                        + " . Using default seed value for the generator.");
            }

        }

        long maxUserId = Constants.DEFAULT_MAX_GBOOK_USR_ID;
        if (isParamSet(Constants.MAX_GBOOK_USR_ID)) {
            Object value = getParamValue(Constants.MAX_GBOOK_USR_ID);
            if (value instanceof Long) {
                maxUserId = (long) value;
            } else if (value instanceof Integer) {
                maxUserId = ((Integer) value).longValue();
            } else {
                System.err.println("WARNING: Invalid " + Constants.MAX_GBOOK_USR_ID + " value in "
                        + Constants.BIGFUN_CONFIG_FILE_NAME + " . Using the default value for the generator.");
            }
        }

        int ignore = -1;
        if (isParamSet(Constants.IGNORE)) {
            ignore = (int) getParamValue(Constants.IGNORE);
        }

        boolean qExec = true;
        if (isParamSet(Constants.EXECUTE_QUERY)) {
            qExec = (boolean) getParamValue(Constants.EXECUTE_QUERY);
        }

        boolean dumpResults = false;
        String resultsFile = null;
        if (isParamSet(Constants.ASTX_DUMP_RESULTS)) {
            dumpResults = (boolean) getParamValue(Constants.ASTX_DUMP_RESULTS);
            resultsFile = (String) getParamValue(Constants.RESULTS_DUMP_FILE);
        }

        AsterixClientReadOnlyWorkload rClient = new AsterixClientReadOnlyWorkload(cc, dvName, iter, qGenConfigFile,
                qIxFile, statsFile, ignore, workloadFile, /*dumpDirFile,*/ resultsFile, seed, maxUserId);

        rClient.setExecQuery(qExec);
        rClient.setDumpResults(dumpResults);
        return rClient;
    }

    @Override
    public AbstractUpdateClient readUpdateClientConfig(String bigFunHomePath) {
        String cc = (String) getParamValue(Constants.CC_URL);
        String oprType = (String) getParamValue(Constants.UPDATE_OPR_TYPE_TAG);

        String updatesFile = (String) getParamValue(Constants.UPDATES_FILE);
        String statsFile = bigFunHomePath + "/files/output/" + Constants.STATS_FILE_NAME;
        if (isParamSet(Constants.STATS_FILE)) {
            statsFile = (String) getParamValue(Constants.STATS_FILE);
        }

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

        return new AsterixClientUpdateWorkload(cc, dvName, dsName, keyName, upTag, batchSize, limit, updatesFile,
                statsFile, ignore);
    }
}
