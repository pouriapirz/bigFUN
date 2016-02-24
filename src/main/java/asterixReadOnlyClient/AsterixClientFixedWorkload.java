package asterixReadOnlyClient;

import client.AbstractReadOnlyClient;
import structure.Pair;
import structure.Query;
import workloadGenerator.FixedReadOnlyWorkloadGenerator;

public class AsterixClientFixedWorkload extends AbstractReadOnlyClient {

    String ccUrl;
    String dvName;
    FixedReadOnlyWorkloadGenerator fwg;

    public AsterixClientFixedWorkload(String cc, String dvName, String qIxFile, String statsFile, int ignore,
            String qSeqFile, String dumpDirFile, String resDumpFile) {
        super();
        this.ccUrl = cc;
        this.dvName = dvName;
        setClientUtil(qIxFile, statsFile, ignore, qSeqFile, dumpDirFile, resDumpFile);
        clUtil.init();
        initReadOnlyWorkloadGen();
        execQuery = true;
    }

    public void setExecQuery(boolean b) {
        execQuery = b;
        if (!execQuery) {
            System.out.println("User requested to skip actual query execution in AsterixFixedClient");
        }
    }

    @Override
    public void setClientUtil(String qIxFile, String statsFile, int ignore, String qSeqFile, String dumpDirFile,
            String resultsFile) {
        clUtil = new AsterixReadOnlyClientUtility(ccUrl, qIxFile, statsFile, ignore, qSeqFile, dumpDirFile,
                resultsFile);
    }

    @Override
    protected void initReadOnlyWorkloadGen() {
        this.fwg = new FixedReadOnlyWorkloadGenerator(clUtil.qIndexFile);
    }

    @Override
    public void execute() {
        int round = 0; //for execution trace
        for (String dir : clUtil.dumpDir) {
            long start = System.currentTimeMillis();
            System.out.println("Asterix Client - Fixed Workload - Starting round " + (round++));
            fwg.load(dir);
            //for(int qid : clUtil.qids){
            for (Pair qvid : clUtil.qvids) {
                int qid = qvid.getQId();
                int vid = qvid.getVId();
                Query q = fwg.nextQuery(qvid);
                if (q == null) {
                    continue; //do not break, if one query is not found
                }
                if (execQuery) {
                    clUtil.executeQuery(qid, vid, q.aqlPrint(dvName));
                } else {
                    System.out.println("Query " + qid + " - " + vid + "\n" + q.aqlPrint(dvName));
                }
            }
            long end = System.currentTimeMillis();
            long duration = end - start;
            System.out.println("Total time of last round:\t" + duration);
        }
        clUtil.terminate();
    }
}
