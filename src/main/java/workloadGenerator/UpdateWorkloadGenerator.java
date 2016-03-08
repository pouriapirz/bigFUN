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
package workloadGenerator;

import structure.AqlUpdate;
import structure.Update;
import structure.UpdateTag;

public class UpdateWorkloadGenerator extends AbstractUpdateWorkloadGenerator {

    private String dvName;
    private String dsName;
    private String keyName; //for delete (set to null for insert)
    UpdateTag upTag;

    public UpdateWorkloadGenerator(String dv, String ds, String key, UpdateTag tag) {
        super();
        this.dvName = dv;
        this.dsName = ds;
        this.keyName = key;
        this.upTag = tag;
    }

    @Override
    protected void modifySingleUpdate(Update update, String line) {
        ((AqlUpdate) update).addUpdate(line);
    }

    @Override
    protected Update generateNewUpdate() {
        return (new AqlUpdate(dvName, dsName, keyName, upTag));
    }
}
