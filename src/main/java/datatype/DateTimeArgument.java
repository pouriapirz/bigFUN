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
package datatype;

import java.util.Calendar;
import java.util.GregorianCalendar;

import config.Constants;

/**
 * @author pouria
 */
public class DateTimeArgument implements IArgument {

    public int year;
    public int month;
    public int day;
    public int hour;
    public int min;
    public int sec;

    public DateTimeArgument(int year, int month, int day, int hour, int min, int sec) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }

    public DateTimeArgument(Calendar c) {
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH) + 1;
        this.day = c.get(Calendar.DAY_OF_MONTH);
        this.hour = c.get(Calendar.HOUR_OF_DAY);
        this.min = c.get(Calendar.MINUTE);
        this.sec = c.get(Calendar.SECOND);
    }

    @Override
    public String dump() {
        StringBuilder sb = new StringBuilder(year + "");
        String mo = (month < 10) ? ("0" + month) : ("" + month);
        String d = (day < 10) ? ("0" + day) : ("" + day);
        String h = (hour < 10) ? ("0" + hour) : ("" + hour);
        String mi = (min < 10) ? ("0" + min) : ("" + min);
        String s = (sec < 10) ? ("0" + sec) : ("" + sec);
        sb.append(Constants.ARG_DATETIME_DUMP_DELIM).append(mo);
        sb.append(Constants.ARG_DATETIME_DUMP_DELIM).append(d);
        sb.append(Constants.ARG_DATETIME_DUMP_DELIM).append(h);
        sb.append(Constants.ARG_DATETIME_DUMP_DELIM).append(mi);
        sb.append(Constants.ARG_DATETIME_DUMP_DELIM).append(s);
        return sb.toString();
    }

    @Override
    public TypeTag getTag() {
        return TypeTag.DATETIME;
    }

    @Override
    public String admPrint() {
        String mo = (month < 10) ? ("0" + month) : ("" + month);
        String d = (day < 10) ? ("0" + day) : ("" + day);
        String h = (hour < 10) ? ("0" + hour) : ("" + hour);
        String mi = (min < 10) ? ("0" + min) : ("" + min);
        String s = (sec < 10) ? ("0" + sec) : ("" + sec);
        StringBuffer sb = new StringBuffer("datetime('");
        sb.append(year).append("-");
        sb.append(mo).append("-");
        sb.append(d);
        sb.append("T");
        sb.append(h).append(":");
        sb.append(mi).append(":");
        sb.append(s).append("')");
        return sb.toString();
    }

    public GregorianCalendar getGCalendar() {
        return new GregorianCalendar(year, (month - 1), day, hour, min, sec);
    }

    public long convertToMillis() {
        Calendar c = getGCalendar();
        return c.getTimeInMillis();
    }

    public void rest(int year, int month, int day, int hour, int min, int sec) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }
}