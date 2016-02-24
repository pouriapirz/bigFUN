package workloadGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import config.Constants;
import datatype.ArgumentParser;
import datatype.IArgument;
import datatype.TypeTag;
import structure.Pair;
import structure.Query;

public class FixedReadOnlyWorkloadGenerator extends ReadOnlyWorkloadGenerator {

    HashMap<Pair, List<IArgument>> qIxToQArgs;

    public FixedReadOnlyWorkloadGenerator(String qIndexFile) {
        super();
        qIxToQArgs = new HashMap<Pair, List<IArgument>>();
        try {
            if (qIndexFile != null) {
                loadQueryTemplate(qIndexFile);
            }
        } catch (IOException e) {
            System.err.println("Error in loading qIndexFile in FixedWorkloadGenerator");
            e.printStackTrace();
        }
    }

    public Query nextQuery(Pair qv) {
        Query q = qIxToQuery.get(qv.getQId());
        if (q == null) {
            System.err.println("Query " + qv.getQId() + " Version " + qv.getVId() + " can not be found !");
            return null;
        }
        List<IArgument> args = getQueryArgs(qv);
        if (args == null) {
            System.err.println("No arguments found for query " + qv.toString());
            return null;
        }
        q.reset(args);
        return q;
    }

    public List<IArgument> getQueryArgs(Pair qv) {
        List<IArgument> args = qIxToQArgs.get(qv);
        return args;
    }

    public void load(String dumpDir) {
        qIxToQArgs.clear();
        ArrayList<File> queries = getFiles(dumpDir);
        try {
            for (File f : queries) {
                BufferedReader in = new BufferedReader(new FileReader(f));
                String str = in.readLine();
                int qid = Integer.parseInt(str); //First line of dumped file should be qid
                str = in.readLine(); //Second line of dumped file should be vid
                int vid = Integer.parseInt(str);
                Pair p = new Pair(qid, vid);
                if (qIxToQArgs.containsKey(p)) {
                    System.err.println(
                            "Query with id " + qid + " and version " + vid + " already exists in fixed workload");
                    continue;
                }
                ArrayList<IArgument> args = new ArrayList<IArgument>();
                while ((str = in.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(str, Constants.DUMPED_QUERY_ARG_DELIM);
                    String tag = st.nextToken();
                    String arg = st.nextToken();
                    args.add(parseArg(tag, arg));
                }
                qIxToQArgs.put(p, args);
                in.close();
            }
        } catch (Exception e) {
            System.err.println("Problem in loading fixed workload from directory " + dumpDir);
            e.printStackTrace();
        }
    }

    private IArgument parseArg(String tag, String arg) {
        if (tag.equals(TypeTag.DATETIME.toString())) {
            return ArgumentParser.parseDateTime(arg);
        } else if (tag.equals(TypeTag.INTEGER.toString())) {
            return ArgumentParser.parseInt(arg);
        } else if (tag.equals(TypeTag.STRING.toString())) {
            return ArgumentParser.parseString(arg);
        } else if (tag.equals(TypeTag.LONG.toString())) {
            return ArgumentParser.parseLong(arg);
        } else if (tag.equals(TypeTag.DOUBLE.toString())) {
            return ArgumentParser.parseDouble(arg);
        }
        System.err.println("Unknow Type Tag" + tag);
        return null;
    }

    private ArrayList<File> getFiles(String dirPath) {
        File folder = new File(dirPath);
        File[] listOfFiles = folder.listFiles();
        ArrayList<File> listOfQueries = new ArrayList<File>();
        for (File f : listOfFiles) {
            String fileName = f.getName();
            if (!fileName.startsWith(".")) {
                listOfQueries.add(f);
            }
        }
        return listOfQueries;
    }
}
