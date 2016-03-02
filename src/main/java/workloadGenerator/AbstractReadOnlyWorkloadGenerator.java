package workloadGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import config.Constants;
import structure.Query;

public abstract class AbstractReadOnlyWorkloadGenerator {

    HashMap<Integer, Query> qIxToQuery;

    public AbstractReadOnlyWorkloadGenerator() {
        this.qIxToQuery = new HashMap<Integer, Query>();
    }

    protected void loadQueryTemplate(String qIndexFile) throws IOException {
        qIxToQuery.clear();
        Map<String, String> qIxToPath;
        ObjectMapper mapper = new ObjectMapper();
        qIxToPath = mapper.readValue(new File(qIndexFile), Map.class);
        for (String id : qIxToPath.keySet()) {
            int qId = Integer.parseInt(id);
            String segFile = (String) qIxToPath.get(id);
            addQuery(segFile, qId);
        }
    }

    private void addQuery(String segmentsFile, int ix) throws IOException {
        if (qIxToQuery.containsKey(ix)) {
            System.err.println("ERROR - Query Already Exists for Index " + ix);
            return;
        }

        BufferedReader in = new BufferedReader(new FileReader(segmentsFile));
        ArrayList<String> segments = new ArrayList<String>();
        String str;
        String thisSeg = "";
        while ((str = in.readLine()) != null) {
            if (str.trim().equals(Constants.ARG_TAG_IN_Q_TEMPLATE_FILE)) {
                segments.add(thisSeg);
                thisSeg = "";
            } else {
                thisSeg += str + "\n";
            }
        }
        in.close();
        if (thisSeg.length() > 0) {
            segments.add(thisSeg);
        }
        qIxToQuery.put(ix, new Query(segments));
    }
}
