package workloadGenerator;

import java.io.IOException;
import java.util.ArrayList;

import config.RandomQueryGeneratorConfig;
import datatype.IArgument;
import queryGenerator.RandomQueryGenerator;
import structure.Query;

public class ReadOnlyWorkloadGenerator extends AbstractReadOnlyWorkloadGenerator {

    final RandomQueryGenerator rqGen;

    public ReadOnlyWorkloadGenerator(String qIndexFile, String qGenConfigFile, long seed, long maxUsrId) {
        super();
        try {
            loadQueryTemplate(qIndexFile);
        } catch (IOException e) {
            System.err.println("Error in loading qIndexFile in RandomWorkloadGenerator");
            e.printStackTrace();
        }
        rqGen = new RandomQueryGenerator(seed, maxUsrId);
        try {
            RandomQueryGeneratorConfig.configure(rqGen, qGenConfigFile);
        } catch (IOException e) {
            System.err.println("Error in configuring RandomQueryGenerator");
            e.printStackTrace();
        }
    }

    public Query nextQuery(int qid, int vid) {
        ArrayList<IArgument> args = rqGen.nextQuery(qid, vid);
        Query q = qIxToQuery.get(qid);
        if (q == null) {
            System.err.println("Query " + qid + " can not be found !");
            return null;
        }
        q.reset(args);
        return q;
    }
}
