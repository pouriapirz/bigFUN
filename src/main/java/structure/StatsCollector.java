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
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();
            String currentTime = df.format(dateobj);
            StringBuffer tsb = new StringBuffer(currentTime);
            tsb.append("\n\nResponse Times (in ms per iteration)\n");
            StringBuffer avgsb = new StringBuffer("Avg Times (first " + ignore + " round(s) excluded)\n");

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
            pw.println(tsb.toString());
            pw.println("\n");
            if (avgsb != null) {
                pw.println(avgsb.toString());
                pw.println("\n");
            }
            pw.close();

        } catch (IOException e) {
            System.err.println("Problem in creating report in StatsCollector !");
            e.printStackTrace();
        }
    }
}
