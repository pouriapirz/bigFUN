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
package asterixReadOnlyClient;

import client.AbstractReadOnlyClient;
import structure.Pair;
import structure.Query;
import workloadGenerator.ReadOnlyWorkloadGenerator;

public class AsterixClientReadOnlyWorkload extends AbstractReadOnlyClient {

    final String ccUrl;
    final String dvName;
    final int iterations;
    ReadOnlyWorkloadGenerator rwg;

    public AsterixClientReadOnlyWorkload(String cc, String dvName, int iter, String qGenConfigFile, String qIxFile,
            String statsFile, int ignore, String qSeqFile, String resDumpFile, long seed, long maxUsrId) {
        super();
        this.ccUrl = cc;
        this.dvName = dvName;
        this.iterations = iter;
        setClientUtil(qIxFile, qGenConfigFile, statsFile, ignore, qSeqFile, resDumpFile);
        clUtil.init();
        initReadOnlyWorkloadGen(seed, maxUsrId);
        execQuery = true;
    }

    @Override
    protected void initReadOnlyWorkloadGen(long seed, long maxUsrId) {
        this.rwg = new ReadOnlyWorkloadGenerator(clUtil.getQIxFile(), clUtil.getQGenConfigFile(), seed, maxUsrId);
    }

    @Override
    public void execute() {
        long iteration_start = 0l;
        long iteration_end = 0l;

        for (int i = 0; i < iterations; i++) {
            System.out.println("\nAsterixDB Client - Read-Only Workload - Starting Iteration " + i);
            iteration_start = System.currentTimeMillis();
            for (Pair qvPair : clUtil.qvids) {
                int qid = qvPair.getQId();
                int vid = qvPair.getVId();
                Query q = rwg.nextQuery(qid, vid);
                if (q == null) {
                    continue; //do not break, if one query is not found
                }
                if (execQuery) {
                    clUtil.executeQuery(qid, vid, q.aqlPrint(dvName));
                }
            }
            iteration_end = System.currentTimeMillis();
            System.out.println("Total time for iteration " + i + " :\t" + (iteration_end - iteration_start) + " ms\n");
        }
        clUtil.terminate();
    }

    @Override
    public void setClientUtil(String qIxFile, String qGenConfigFile, String statsFile, int ignore, String qSeqFile,
            String resultsFile) {
        clUtil = new AsterixReadOnlyClientUtility(ccUrl, qIxFile, qGenConfigFile, statsFile, ignore, qSeqFile,
                resultsFile);
    }
}
