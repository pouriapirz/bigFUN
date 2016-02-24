package client;

public abstract class AbstractUpdateClient extends AbstractClient {

    protected int batchSize;
    protected int limit;
    protected AbstractUpdateClientUtility clUtil;

    public AbstractUpdateClient() {
    }

    protected abstract void setClientUtil(int batchSize, int limit, String updatesFile, String statsFile, int ignore,
            String resultsFile);

    protected abstract void initUpdateWorkloadGen();

    public void generateReport() {
        clUtil.generateReport();
    }
}
