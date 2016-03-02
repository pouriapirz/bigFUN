package config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import queryGenerator.QueryParamSetting;
import queryGenerator.RandomQueryGenerator;

/**
 * Goes through a config file and configures a RandomQueryGenerator accordingly
 * 
 * @author pouria
 */
public class RandomQueryGeneratorConfig {

    public static void configure(RandomQueryGenerator rqGen, String configFile) throws IOException {
        rqGen.setQParamSetting(parseQParamSettings(configFile));
    }

    //Each line should have the following format
    //[qid],[vid],[Int1],[Int2],...,[Intn]
    private static QueryParamSetting parseQParamSettings(String inputFilePath) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(inputFilePath));
        QueryParamSetting qps = new QueryParamSetting();
        String str;
        while ((str = in.readLine()) != null) {
            if (str.startsWith(Constants.COMMENT_TAG) || (str.length() == 0)) { //ignore comment lines
                continue;
            }
            StringTokenizer st = new StringTokenizer(str, Constants.QPARAM_FILE_DELIM);
            int qid = Integer.parseInt(st.nextToken());
            int vid = Integer.parseInt(st.nextToken());
            ArrayList<Integer> pList = new ArrayList<Integer>();
            while (st.hasMoreTokens()) {
                pList.add(Integer.parseInt(st.nextToken()));
            }
            qps.addParamSetting(qid, vid, pList);
        }
        in.close();
        return qps;
    }
}
