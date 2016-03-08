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
