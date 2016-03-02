package asterixUpdateClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import client.AbstractUpdateClientUtility;
import config.Constants;
import structure.AqlUpdate;
import structure.Update;
import workloadGenerator.AbstractUpdateWorkloadGenerator;

public class AsterixUpdateClientUtility extends AbstractUpdateClientUtility {

    final int TRACE_PACE = 5000;

    String ccUrl;
    DefaultHttpClient httpclient;
    HttpPost httpPost;
    int counter = 0; //for trace only

    public AsterixUpdateClientUtility(String cc, int batchSize, int limit, AbstractUpdateWorkloadGenerator uwg,
            String updatesFile, String statsFile, int ignore) {
        super(batchSize, limit, uwg, updatesFile, statsFile, ignore);
        this.ccUrl = cc;
    }

    @Override
    public void init() {
        httpclient = new DefaultHttpClient();
        httpPost = new HttpPost(getUpdateUrl());
    }

    @Override
    public void terminate() {
        httpclient.getConnectionManager().shutdown();
    }

    @Override
    public void executeUpdate(int qid, Update update) {
        long rspTime = Constants.INVALID_TIME;
        String updateBody = null;
        HttpResponse response;
        try {
            updateBody = ((AqlUpdate) update).printAqlStatement();
            httpPost.setEntity(new StringEntity(updateBody));

            long s = System.currentTimeMillis();
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            EntityUtils.consume(entity);
            long e = System.currentTimeMillis();
            rspTime = (e - s);
        } catch (Exception e) {
            System.err.println("Problem in running update " + qid + " against Asterixdb !");
            updateStat(qid, 0, Constants.INVALID_TIME);
            return;
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            System.err.println("Update " + qid + " against Asterixdb returned http error code");
            rspTime = Constants.INVALID_TIME;
        }
        updateStat(qid, 0, rspTime);

        if (++counter % TRACE_PACE == 0) {
            System.out
                    .println(counter + " Updates done - last one took\t" + rspTime + " ms\tStatus-Code\t" + statusCode);
        }

    }

    private String getUpdateUrl() {
        return ("http://" + ccUrl + ":" + Constants.ASTX_AQL_REST_API_PORT + "/update");
    }

    @Override
    public void resetTraceCounters() {
        counter = 0;
    }
}
