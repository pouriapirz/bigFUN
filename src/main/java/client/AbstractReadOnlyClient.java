package client;

public abstract class AbstractReadOnlyClient extends AbstractClient {

    protected boolean execQuery;
    protected AbstractReadOnlyClientUtility clUtil;

    public AbstractReadOnlyClient() {
    }

    protected abstract void setClientUtil(String qIxFile, String qGenConfigFile, String statsFile, int ignore,
            String qSeqFile, String resultsFile);

    protected abstract void initReadOnlyWorkloadGen(long seed, long maxUsrId);

    public void generateReport() {
        clUtil.generateReport();
    }

    public void setExecQuery(boolean b) {
        execQuery = b;
        if (!execQuery) {
            System.out.println("User requested to skip actual query execution.");
        }
    }

    public void setDumpResults(boolean b) {
        clUtil.setDumpResults(b);
    }
}
