package workloadGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import config.Constants;
import structure.Query;

public abstract class ReadOnlyWorkloadGenerator {
    HashMap<Integer, Query> qIxToQuery;

    public ReadOnlyWorkloadGenerator() {
        this.qIxToQuery = new HashMap<Integer, Query>();
    }

    protected void loadQueryTemplate(String qIndexFile) throws IOException {
        qIxToQuery.clear();
        BufferedReader in = new BufferedReader(new FileReader(qIndexFile));
        String str;
        while ((str = in.readLine()) != null) {
            if (str.startsWith(Constants.COMMENT_TAG) || (str.length() == 0)) { //ignore empty/comment lines
                continue;
            }
            StringTokenizer st = new StringTokenizer(str.trim(), " ");
            int qId = Integer.parseInt(st.nextToken());
            String segFile = st.nextToken();
            addQuery(segFile, qId);
        }
        in.close();
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
