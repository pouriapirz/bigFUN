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
package queryGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class QueryParamSetting {
    private HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> map;

    public QueryParamSetting() {
        this.map = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
    }

    public void addParamSetting(int qid, int vid, ArrayList<Integer> params) {
        HashMap<Integer, ArrayList<Integer>> m = map.get(qid);
        if (m == null) {
            m = new HashMap<Integer, ArrayList<Integer>>();
        }
        m.put(vid, params);
        map.put(qid, m);
    }

    public ArrayList<Integer> getParam(int qid, int vid) {
        if (!map.containsKey(qid)) {
            System.err.println("No query parameter(s) is provided for q" + qid);
            return null;
        }
        return map.get(qid).get(vid);
    }

    public String print() {
        StringBuffer sb = new StringBuffer();
        Set<Integer> qs = map.keySet();
        for (int q : qs) {
            HashMap<Integer, ArrayList<Integer>> vsm = map.get(q);
            Set<Integer> vs = vsm.keySet();
            for (int v : vs) {
                ArrayList<Integer> ps = vsm.get(v);
                sb.append("(").append(q).append(", ").append(v).append("): ");
                for (int p : ps) {
                    sb.append(p).append(",");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
