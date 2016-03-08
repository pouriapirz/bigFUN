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
package queryGenerator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import config.Constants;
import datatype.ArgumentParser;
import datatype.DateTimeArgument;
import datatype.DoubleArgument;
import datatype.IArgument;
import datatype.LongArgument;
import datatype.StringArgument;

public class RandomQueryGenerator {

    DateTimeArgument START_DATE;
    DateTimeArgument END_DATE;
    long MAX_FB_USR_ID;

    Random rand;
    long seed;
    ArrayList<IArgument> args;

    QueryParamSetting qps;

    public RandomQueryGenerator(long seed, long maxUsrId) {
        this.rand = new Random(seed);
        setStartDate(ArgumentParser.parseDateTime(Constants.DEFAULT_START_DATE));
        setEndDate(ArgumentParser.parseDateTime(Constants.DEFAULT_END_DATE));
        setMaxFbuId(maxUsrId);
        this.args = new ArrayList<IArgument>();
    }

    public ArrayList<IArgument> nextQuery(int qIx, int vIx) {
        args.clear();
        switch (qIx) {

            case 101: //PK lookup
                nextQ101();
                break;
            case 102: //PK range scan
                nextQ102(qIx, vIx);
                break;
            case 103: //non-unique temporal attribute range scan
                nextQ103(qIx, vIx);
                break;
            case 104: //e-quantification
                nextQ104(qIx, vIx);
                break;
            case 105: //u-quantification 
                nextQ105(qIx, vIx);
                break;
            case 106: //global aggregation
                nextQ106(qIx, vIx);
                break;
            case 107: //grp and aggregation
                nextQ107(qIx, vIx);
                break;
            case 108: //top-k
                nextQ108(qIx, vIx);
                break;
            case 109: //spatial selection
                nextQ109(vIx);
                break;
            case 1010: //exact text search
                nextQ1010(vIx);
                break;
            case 1011: //text similarity search
                nextQ1011(vIx);
                break;
            case 1012: //equi-join
            case 2012: //equi-IX-join
                nextQ1012(qIx, vIx);
                break;
            case 1013: //left-Outer-Join
            case 2013: //left-Outer-IX-Join
                nextQ1013(qIx, vIx);
                break;
            case 1014: //join and grp
            case 2014: //IX-join and grp
                nextQ1014(qIx, vIx);
                break;
            case 1015: //join and top-k
            case 2015: //IX-join and top-k
                nextQ1015(qIx, vIx);
                break;
            case 1016: //spatial Join
                nextQ1016(qIx, vIx);
                break;
            default:
                return null;
        }
        return (new ArrayList<IArgument>(args));
    }

    private void nextQ101() {
        LongArgument k = randomLongArg(MAX_FB_USR_ID);
        args.add(k);
    }

    private void nextQ102(int qid, int vid) {
        int len = qps.getParam(qid, vid).get(0);
        LongArgument s = randomLongArg(MAX_FB_USR_ID - len);
        LongArgument e = new LongArgument(s.getValue() + len);
        args.add(s);
        args.add(e);
    }

    private void nextQ103(int qid, int vid) {
        DateTimeArgument s = randomDateTime(START_DATE, END_DATE);
        int shift = qps.getParam(qid, vid).get(0);
        DateTimeArgument e = shift(s, shift);
        args.add(s);
        args.add(e);
    }

    private void nextQ104(int qid, int vid) {
        DateTimeArgument s = randomDateTime(START_DATE, END_DATE);
        int shift = qps.getParam(qid, vid).get(0);
        DateTimeArgument e = shift(s, shift);
        args.add(s);
        args.add(e);
    }

    private void nextQ105(int qid, int vid) {
        DateTimeArgument s = randomDateTime(START_DATE, END_DATE);
        int shift = qps.getParam(qid, vid).get(0);
        DateTimeArgument e = shift(s, shift);
        args.add(s);
        args.add(e);
    }

    private void nextQ106(int qid, int vid) {
        DateTimeArgument s = randomDateTime(START_DATE, END_DATE);
        int shift = qps.getParam(qid, vid).get(0);
        DateTimeArgument e = shift(s, shift);
        args.add(s);
        args.add(e);
    }

    private void nextQ107(int qid, int vid) {
        DateTimeArgument s = randomDateTime(START_DATE, END_DATE);
        int shift = qps.getParam(qid, vid).get(0);
        DateTimeArgument e = shift(s, shift);
        args.add(s);
        args.add(e);
    }

    private void nextQ108(int qid, int vid) {
        DateTimeArgument s = randomDateTime(START_DATE, END_DATE);
        int shift = qps.getParam(qid, vid).get(0);
        DateTimeArgument e = shift(s, shift);
        args.add(s);
        args.add(e);
    }

    private void nextQ109(int vid) {
        double[] location = getRandomLocation();
        DoubleArgument radius = getRadius(vid);
        args.add(new DoubleArgument(location[0]));
        args.add(new DoubleArgument(location[1]));
        args.add(radius);
    }

    private void nextQ1010(int vid) {
        args.add(getKeyword(vid));
    }

    private void nextQ1011(int vid) {
        args.add(getKeyword(vid));
    }

    private void nextQ1014(int qid, int vid) {
        DateTimeArgument s = randomDateTime(START_DATE, END_DATE);
        ArrayList<Integer> p = qps.getParam(qid, vid);
        DateTimeArgument e = shift(s, p.get(0));
        DateTimeArgument s2 = randomDateTime(START_DATE, END_DATE);
        DateTimeArgument e2 = shift(s2, p.get(1));
        args.add(s);
        args.add(e);
        args.add(s2);
        args.add(e2);
    }

    private void nextQ1015(int qid, int vid) {
        DateTimeArgument s = randomDateTime(START_DATE, END_DATE);
        ArrayList<Integer> p = qps.getParam(qid, vid);
        DateTimeArgument e = shift(s, p.get(0));
        DateTimeArgument s2 = randomDateTime(START_DATE, END_DATE);
        DateTimeArgument e2 = shift(s2, p.get(1));
        args.add(s);
        args.add(e);
        args.add(s2);
        args.add(e2);
    }

    private void nextQ1012(int qid, int vid) {
        DateTimeArgument s1 = randomDateTime(START_DATE, END_DATE);
        ArrayList<Integer> p = qps.getParam(qid, vid);
        DateTimeArgument e1 = shift(s1, p.get(0));
        DateTimeArgument s2 = randomDateTime(START_DATE, END_DATE);
        DateTimeArgument e2 = shift(s2, p.get(1));

        args.add(s1);
        args.add(e1);
        args.add(s2);
        args.add(e2);
    }

    private void nextQ1013(int qid, int vid) {
        DateTimeArgument s1 = randomDateTime(START_DATE, END_DATE);
        ArrayList<Integer> p = qps.getParam(qid, vid);
        DateTimeArgument e1 = shift(s1, p.get(0));
        DateTimeArgument s2 = randomDateTime(START_DATE, END_DATE);
        DateTimeArgument e2 = shift(s2, p.get(1));

        args.add(s1);
        args.add(e1);
        args.add(s2);
        args.add(e2);
    }

    private void nextQ1016(int qid, int vid) {
        DateTimeArgument s = randomDateTime(START_DATE, END_DATE);
        int shift = qps.getParam(qid, vid).get(0);
        DateTimeArgument e = shift(s, shift);
        DoubleArgument r = getRadius(vid);
        args.add(s);
        args.add(e);
        args.add(r);
    }

    //Utility Methods
    public void setStartDate(DateTimeArgument sd) {
        this.START_DATE = sd;
    }

    public void setEndDate(DateTimeArgument ed) {
        this.END_DATE = ed;
    }

    public void setMaxFbuId(long max) {
        this.MAX_FB_USR_ID = max;
    }

    public void setQParamSetting(QueryParamSetting qps) {
        this.qps = qps;
    }

    private LongArgument randomLongArg(long max) {
        return new LongArgument(generateRandomLong(1, max));
    }

    private long generateRandomLong(long x, long y) {
        return (x + ((long) (rand.nextDouble() * (y - x))));
    }

    private DateTimeArgument randomDateTime(DateTimeArgument minDate, DateTimeArgument maxDate) {
        int yDiff = (maxDate.year - minDate.year) + 1;
        int year = rand.nextInt(yDiff) + minDate.year;
        int month;
        int day;
        if (year == maxDate.year) {
            month = rand.nextInt(maxDate.month) + 1;
            if (month == maxDate.month) {
                day = rand.nextInt(maxDate.day) + 1;
            } else {
                day = rand.nextInt(28) + 1;
            }
        } else {
            month = rand.nextInt(12) + 1;
            day = rand.nextInt(28) + 1;
        }

        int hour = rand.nextInt(24);
        int min = rand.nextInt(58);
        int sec = rand.nextInt(58);

        return new DateTimeArgument(year, month, day, hour, min, sec);
    }

    private DateTimeArgument shift(DateTimeArgument orig, int amount) {
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(orig.convertToMillis());
        c.add(Calendar.SECOND, amount);
        return new DateTimeArgument(c);
    }

    private double[] getRandomLocation() {
        int latMajor = Constants.MIN_LAT + rand.nextInt(Constants.MAX_LAT - Constants.MIN_LAT);
        int latMinor = rand.nextInt(10000);
        double latitude = latMajor + ((double) latMinor) / 10000;

        int longMajor = Constants.MIN_LON + rand.nextInt(Constants.MAX_LON - Constants.MIN_LON);
        int longMinor = rand.nextInt(10000);
        double longitude = longMajor + ((double) longMinor) / 10000;

        return new double[] { latitude, longitude };
    }

    private DoubleArgument getRadius(int ver) {
        switch (ver) {
            case 1:
                return Constants.SMALL_RADIUS;
            case 2:
                return Constants.MEDIUM_RADIUS;
            case 3:
                return Constants.LARGE_RADIUS;
            default:
                System.err.println(
                        "Invalid query version for spatial query, query version can be 1 (short), 2 (medium) or 3 (long)");
        }
        return null;
    }

    private StringArgument getKeyword(int ver) {
        switch (ver) {
            case 1:
                return getRareKW();
            case 2:
                return getMediumKW();
            case 3:
                return getFrquentKW();
            default:
                System.err.println(
                        "Invalid query version for Text search query, query version can be 1 (rare), 2 (medium) or 3 (frequent)");
        }
        return null;
    }

    private StringArgument getFrquentKW() {
        int ix = rand.nextInt(Constants.FREQ_KW.length);
        return new StringArgument(Constants.FREQ_KW[ix]);
    }

    private StringArgument getMediumKW() {
        int ix = rand.nextInt(Constants.MEDIUM_KW.length);
        return new StringArgument(Constants.MEDIUM_KW[ix]);
    }

    private StringArgument getRareKW() {
        int ix = rand.nextInt(Constants.RARE_KW.length);
        return new StringArgument(Constants.RARE_KW[ix]);
    }
}
