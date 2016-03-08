/*
 * Copyright by The Regents of the University of California
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * you may obtain a copy of the License from
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    URIBuilder roBuilder;

    public AsterixReadOnlyClientUtility(String cc, String qIxFile, String qGenConfigFile, String statsFile, int ignore,
            String qSeqFile, String resultsFile) {
        super(qIxFile, qGenConfigFile, statsFile, ignore, qSeqFile, resultsFile);
        this.ccUrl = cc;
    }

    @Override
    public void init() {
        httpclient = new DefaultHttpClient();
        httpGet = new HttpGet();
        try {
            roBuilder = new URIBuilder("http://" + ccUrl + ":" + Constants.ASTX_AQL_REST_API_PORT + "/query");
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
            EntityUtils.consume(entity);
            long e = System.currentTimeMillis();

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
        System.out.println("Q" + qid + " version " + vid + "\t" + rspTime); //trace the progress

    }
}
