package config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import com.fasterxml.jackson.databind.ObjectMapper;

import datatype.ArgumentParser;
import queryGenerator.QueryParamSetting;
import queryGenerator.RandomQueryGenerator;

/**
 * Goes through a config file and configures a RandomQueryGenerator accordingly
 * 
 * @author pouria
 */
public class RandomQueryGeneratorConfig {

    public static void configure(RandomQueryGenerator rqGen, String configFile) throws IOException {
        Map<String, Object> params = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            params = mapper.readValue(new File(configFile), Map.class);
        } catch (Exception e) {
            System.err.println("Problem in parsing the JSON query generator config file.");
            e.printStackTrace();
        }

        if (params.containsKey(Constants.START_DATE)) {
            String stDate = (String) params.get(Constants.START_DATE);
            rqGen.setStartDate(ArgumentParser.parseDateTime(stDate));
        } else {
            rqGen.setStartDate(ArgumentParser.parseDateTime(Constants.DEFAULT_START_DATE));
            System.err.println("WARNING: Could not find START_DATE in qGenConfig - Using the default START_DATE.");
        }

        if (params.containsKey(Constants.END_DATE)) {
            String enDate = (String) params.get(Constants.END_DATE);
            rqGen.setEndDate(ArgumentParser.parseDateTime(enDate));
        } else {
            rqGen.setEndDate(ArgumentParser.parseDateTime(Constants.DEFAULT_END_DATE));
            System.err.println("WARNING: Could not find END_DATE in qGenConfig - Using the default END_DATE.");
        }

        if (params.containsKey(Constants.MAX_FBU_ID)) {
            long maxUId = (long) params.get(Constants.MAX_FBU_ID);
            rqGen.setMaxFbuId(maxUId);
        } else {
            rqGen.setMaxFbuId(Constants.DEFAULT_MAX_FB_USR_ID);
            System.err.println("WARNING: Could not find MAX USER ID in qGenConfig - Using the default MAX USER ID.");
        }

        if (params.containsKey(Constants.QPARAM_SETTINGS_FILE)) {
            String qParamsFile = (String) params.get(Constants.QPARAM_SETTINGS_FILE);
            rqGen.setQParamSetting(parseQParamSettings(qParamsFile));
        } else {
            System.err.println(
                    "ERROR: No QUERY_PARAMS_FILE is specified in qGenConfig file - The query generation can not proceed !");
        }
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
