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
package structure;

import java.util.ArrayList;

/**
 * @author pouria
 */
public class AqlUpdate extends Update {

    private String dvName;
    private String dsName;
    private String keyName; //for delete (set to null for insert)
    private ArrayList<String> updates; //full adm records to insert, or primary keys to delete

    public AqlUpdate(String dv, String ds, String key, UpdateTag t) {
        super(t);
        setMetadata(dv, ds, key);
        updates = new ArrayList<String>();
    }

    public void addUpdate(String u) {
        updates.add(u);
    }

    public void reset() {
        updates.clear();
    }

    public String printAqlStatement() {
        switch (tag) {
            case INSERT:
                return printInsert();
            case DELETE:
                return printDelete();
        }
        System.err.println("Unknow Update Type in AqlUpdate " + tag.toString());
        return null;
    }

    private void setMetadata(String dv, String ds, String key) {
        dvName = dv;
        dsName = ds;
        keyName = key;
    }

    private String printInsert() {
        StringBuilder sb = new StringBuilder("use dataverse ");
        sb.append(dvName).append(";\n");
        sb.append("insert into dataset ").append(dsName).append("(\n").append("for $t in [");
        for (int i = 0; i < updates.size() - 1; i++) {
            sb.append(updates.get(i)).append(",");
        }
        sb.append(updates.get(updates.size() - 1));
        sb.append("]\nreturn $t);");
        return sb.toString();
    }

    private String printDelete() {
        StringBuilder sb = new StringBuilder("use dataverse ");
        sb.append(dvName).append(";\n");
        sb.append("delete $t from dataset ").append(dsName).append("\n");
        sb.append("where");
        for (int i = 0; i < updates.size() - 1; i++) {
            sb.append(" $t.").append(keyName).append(" = int64(\"").append(updates.get(i)).append("\") or"); //currently we only delete int fields (no proper adm print)
        }
        sb.append(" $t.").append(keyName).append(" = int64(\"").append(updates.get(updates.size() - 1)).append("\");");
        return sb.toString();
    }
}
