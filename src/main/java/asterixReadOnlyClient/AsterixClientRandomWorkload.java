package asterixReadOnlyClient;

import client.AbstractReadOnlyClient;
import config.Constants;
import structure.Pair;
import structure.Query;
import workloadGenerator.RandomReadOnlyWorkloadGenerator;

public class AsterixClientRandomWorkload extends AbstractReadOnlyClient {
    String ccUrl;
    String dvName;
    int iterations;
    String qGenConfigFile;
    RandomReadOnlyWorkloadGenerator rwg;

    public AsterixClientRandomWorkload(String cc, String dvName, int iter, String qGenConfigFile, String qIxFile,
            String statsFile, int ignore, String qSeqFile, String resDumpFile) {
        this(cc, dvName, iter, qGenConfigFile, qIxFile, statsFile, ignore, qSeqFile, null, resDumpFile);
    }

    public AsterixClientRandomWorkload(String cc, String dvName, int iter, String qGenConfigFile, String qIxFile,
            String statsFile, int ignore, String qSeqFile, String dumpDirFile, String resDumpFile) {
        super();
        this.ccUrl = cc;
        this.dvName = dvName;
        this.iterations = iter;
        this.qGenConfigFile = qGenConfigFile;
        setClientUtil(qIxFile, statsFile, ignore, qSeqFile, dumpDirFile, resDumpFile);
        clUtil.init();
        initReadOnlyWorkloadGen();
        execQuery = true;
    }

    @Override
    protected void initReadOnlyWorkloadGen() {
        this.rwg = new RandomReadOnlyWorkloadGenerator(clUtil.qIndexFile, qGenConfigFile);
    }

    @Override
    public void execute() {
        //Make sure dumping setting (if desired) is consistent with execution
        if ((clUtil.dumpDir != null) && (clUtil.dumpDir.size() < iterations)) {
            System.err.println("Mismatch in Dump Directory Paths and Iterations");
            return;
        }

        long iteration_start = 0l;
        long iteration_end = 0l;

        for (int i = 0; i < iterations; i++) {
            System.out.println("Asterix Client - Random Workload - Starting Iteration " + i);
            iteration_start = System.currentTimeMillis();
            String dir = (clUtil.dumpDir != null) ? clUtil.dumpDir.get(i) : null;
            for (Pair qvPair : clUtil.qvids) {
                String qDumpFile = null;
                int qid = qvPair.getQId();
                int vid = qvPair.getVId();
                if (dir != null) {
                    qDumpFile = dir + "/" + Constants.QDUMP_FILE_NAME_PRFX + qid + Constants.QDUMP_FILE_ID_VER_SEPARATOR
                            + vid;
                }
                Query q = rwg.nextQuery(qid, vid, qDumpFile);
                if (q == null) {
                    continue; //do not break, if one query is not found
                }
                if (execQuery) {
                    clUtil.executeQuery(qid, vid, q.aqlPrint(dvName));
                }
            }
            iteration_end = System.currentTimeMillis();
            System.out.println("\nTotal iteration time:\t" + (iteration_end - iteration_start) + " ms");
        }
        clUtil.terminate();
    }

    @Override
    public void setClientUtil(String qIxFile, String statsFile, int ignore, String qSeqFile, String dumpDirFile,
            String resultsFile) {
        clUtil = new AsterixReadOnlyClientUtility(ccUrl, qIxFile, statsFile, ignore, qSeqFile, dumpDirFile,
                resultsFile);

    }
}
