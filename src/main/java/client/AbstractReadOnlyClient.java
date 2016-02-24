package client;

public abstract class AbstractReadOnlyClient extends AbstractClient {

    protected boolean execQuery;
    protected AbstractReadOnlyClientUtility clUtil;

    public AbstractReadOnlyClient() {
    }

    protected abstract void setClientUtil(String qIxFile, String statsFile, int ignore, String qSeqFile,
            String dumpDirFile, String resultsFile);

    protected abstract void initReadOnlyWorkloadGen();

    public void generateReport() {
        clUtil.generateReport();
    }

    public void setExecQuery(boolean b) {
        execQuery = b;
        if (!execQuery) {
            System.out.println("User requested to skip actual query execution in AsterixFixedClient");
        }
    }

    public void setDumpResults(boolean b) {
        clUtil.setDumpResults(b);
    }
}
