package workloadGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import config.Constants;
import config.RandomQueryGeneratorConfig;
import datatype.IArgument;
import queryGenerator.RandomQueryGenerator;
import structure.Query;

public class RandomReadOnlyWorkloadGenerator extends ReadOnlyWorkloadGenerator {

    RandomQueryGenerator rqGen;

    public RandomReadOnlyWorkloadGenerator(String qIndexFile, String qGenConfigFile) {
        super();
        try {
            loadQueryTemplate(qIndexFile);
        } catch (IOException e) {
            System.err.println("Error in loading qIndexFile in RandomWorkloadGenerator");
            e.printStackTrace();
        }
        rqGen = new RandomQueryGenerator();
        try {
            RandomQueryGeneratorConfig.configure(rqGen, qGenConfigFile);
        } catch (IOException e) {
            System.err.println("Error in configuring RandomQueryGenerator");
            e.printStackTrace();
        }
    }

    public Query nextQuery(int qid, int vid) { //No dump desired
        return nextQuery(qid, vid, null);
    }

    public Query nextQuery(int qid, int vid, String dumpFile) {
        ArrayList<IArgument> args = rqGen.nextQuery(qid, vid);
        Query q = qIxToQuery.get(qid);
        if (q == null) {
            System.err.println("Query " + qid + " can not be found !");
            return null;
        }
        q.reset(args);
        if (dumpFile != null) {
            dumpQuery(qid, vid, args, dumpFile);
        }
        return q;
    }

    private void dumpQuery(int qid, int vid, List<IArgument> args, String dumpFile) {
        try {
            PrintWriter pw = new PrintWriter(new File(dumpFile));
            pw.println(qid);
            pw.println(vid);
            Iterator<IArgument> argIt = args.iterator();
            while (argIt.hasNext()) {
                IArgument arg = argIt.next();
                StringBuffer sb = new StringBuffer(arg.getTag().toString());
                sb.append(Constants.DUMPED_QUERY_ARG_DELIM);
                sb.append(arg.dump());
                pw.println(sb.toString());
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error in dumping query ");
            e.printStackTrace();
        }

    }
}
