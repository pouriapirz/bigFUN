package asterixReadOnlyClient;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import client.AbstractReadOnlyClientUtility;
import config.Constants;

public class AsterixReadOnlyClientUtility extends AbstractReadOnlyClientUtility {

    String ccUrl;
    DefaultHttpClient httpclient;
    HttpGet httpGet;
    URIBuilder roBuilder; //ReadOnly Queries

    public AsterixReadOnlyClientUtility(String cc, String qIxFile, String statsFile, int ignore, String qSeqFile,
            String dumpDirFile, String resultsFile) {
        super(qIxFile, statsFile, ignore, qSeqFile, dumpDirFile, resultsFile);
        this.ccUrl = cc;
    }

    @Override
    public void init() {
        httpclient = new DefaultHttpClient();
        httpGet = new HttpGet();
        try {
            roBuilder = new URIBuilder("http://" + ccUrl + ":" + Constants.ASTX_REST_PORT + "/query");
        } catch (URISyntaxException e) {
            System.err.println("Problem in initializing Read-Only URI Builder");
            e.printStackTrace();
        }
    }

    @Override
    public void terminate() {
        if (resPw != null) {
            resPw.close();
        }
        httpclient.getConnectionManager().shutdown();
    }

    @Override
    public void executeQuery(int qid, int vid, String qBody) {
        String content = null;
        long rspTime = Constants.INVALID_TIME;
        try {
            roBuilder.setParameter("query", qBody);
            URI uri = roBuilder.build();
            httpGet.setURI(uri);

            long s = System.currentTimeMillis();
            HttpResponse response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);
            long e = System.currentTimeMillis();
            EntityUtils.consume(entity);

            rspTime = (e - s);

        } catch (Exception ex) {
            System.err.println("Problem in read-only query execution against Asterix");
            ex.printStackTrace();
            updateStat(qid, vid, Constants.INVALID_TIME);
            return;
        }
        updateStat(qid, vid, rspTime);
        if (resPw != null) {
            resPw.println(qid);
            resPw.println("Ver " + vid);
            resPw.println(qBody + "\n");
            if (dumpResults) {
                resPw.println(content + "\n");
            }
        }
        //Prints it on the screen to trace the progress
        System.out.println("Q" + qid + " version " + vid + "\t" + rspTime);

    }
}
