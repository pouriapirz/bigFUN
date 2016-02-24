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
