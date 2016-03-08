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
package client;

public abstract class AbstractReadOnlyClient extends AbstractClient {

    protected boolean execQuery;
    protected AbstractReadOnlyClientUtility clUtil;

    public AbstractReadOnlyClient() {
    }

    protected abstract void setClientUtil(String qIxFile, String qGenConfigFile, String statsFile, int ignore,
            String qSeqFile, String resultsFile);

    protected abstract void initReadOnlyWorkloadGen(long seed, long maxUsrId);

    public void generateReport() {
        clUtil.generateReport();
    }

    public void setExecQuery(boolean b) {
        execQuery = b;
        if (!execQuery) {
            System.out.println("User requested to skip actual query execution.");
        }
    }

    public void setDumpResults(boolean b) {
        clUtil.setDumpResults(b);
    }
}
