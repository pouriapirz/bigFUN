package structure;

import java.util.ArrayList;
import java.util.Iterator;

import config.Constants;

/**
 * @author pouria
 */
public class QueryStat {

    int qid;
    ArrayList<Long> times;
    double totalTime;
    double totalCount;

    public QueryStat() {
        this(Constants.INVALID_QID);
    }

    public QueryStat(int qId) {
        qid = qId;
        times = new ArrayList<Long>();
        totalTime = totalCount = 0;
    }

    public void reset(double lastAvg, double lastRSSize, double lastCount) {
        times.clear();
        if (lastAvg >= 0) {
            totalTime += (lastAvg * lastCount);
            totalCount += lastCount;
        }
    }

    public void setQid(int qId) {
        qid = qId;
    }

    public void addStat(long time) {
        times.add(time);
    }

    public String getTimes() {
        StringBuffer sb = new StringBuffer();
        for (long t : times) {
            sb.append(t + "\t");
        }
        return sb.toString();
    }

    public int getTotalSize() {
        return times.size();
    }

    public double getAverageRT(int ignore) {
        if (ignore >= times.size()) {
            return Constants.INVALID_TIME;
        }
        double sum = 0;
        double count = 0;
        int skip = 0;
        Iterator<Long> it = times.iterator();
        while (it.hasNext()) {
            double d = it.next();
            if ((++skip) > ignore) {
                sum += d;
                count++;
            }
        }
        return sum / count;
    }
}
