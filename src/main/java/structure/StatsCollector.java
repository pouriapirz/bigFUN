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
package structure;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * @author pouria
 */
public class StatsCollector {

    int ignore; //number of beginning iteration to ignore in average report
    HashMap<Pair, QueryStat> qvToStat;
    String statsFile; //the file to eventually dump final results into
    int counter; //for tracing purpose only

    public StatsCollector(String statsFile, int ignore) {
        this.qvToStat = new HashMap<Pair, QueryStat>();
        this.statsFile = statsFile;
        this.ignore = ignore;
        this.counter = 0;
    }

    public void reset() {
        qvToStat.clear();
    }

    public void updateStat(int qid, int vid, long time) {
        Pair p = new Pair(qid, vid);
        if (!qvToStat.containsKey(p)) {
            qvToStat.put(p, new QueryStat(qid));
        }
        qvToStat.get(p).addStat(time);
    }

    public void report() {
        partialReport(0, counter, statsFile);
    }

    private void partialReport(int startRound, int endRound, String fileName) {
        try {
            PrintWriter pw = new PrintWriter(new File(fileName));
            if (startRound != 0) {
                ignore = -1;
            }
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
            Date dateobj = new Date();
            String currentTime = df.format(dateobj);
            StringBuffer tsb = new StringBuffer();
            tsb.append("Response Times (in ms per iteration)\n");
            StringBuffer avgsb = new StringBuffer(currentTime);
            avgsb.append("\n\nAvg Times (first " + ignore + " round(s) excluded)\n");
            Set<Pair> keys = qvToStat.keySet();
            Pair[] qvs = keys.toArray(new Pair[keys.size()]);
            Arrays.sort(qvs);
            for (Pair p : qvs) {
                QueryStat qs = qvToStat.get(p);
                tsb.append("Q").append(p.toString()).append("\t").append(qs.getTimes()).append("\n");
                double partialAvg = -1;
                if (avgsb != null) {
                    partialAvg = qs.getAverageRT(ignore);
                    avgsb.append("Q").append(p.toString()).append("\t").append(partialAvg).append("\n");
                }
            }
            if (avgsb != null) {
                pw.println(avgsb.toString());
                pw.println("\n");
            }
            pw.println(tsb.toString());
            pw.close();

        } catch (IOException e) {
            System.err.println("Problem in creating report in StatsCollector !");
            e.printStackTrace();
        }
    }
}
