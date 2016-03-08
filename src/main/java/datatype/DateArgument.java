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

import config.Constants;

/**
 * @author pouria
 */
public class DateArgument implements IArgument {

    int year;
    int month;
    int day;

    public DateArgument(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public String dump() {
        StringBuilder sb = new StringBuilder(year + "");
        String mo = (month < 10) ? ("0" + month) : ("" + month);
        String d = (day < 10) ? ("0" + day) : ("" + day);
        sb.append(Constants.ARG_DATETIME_DUMP_DELIM).append(mo);
        sb.append(Constants.ARG_DATETIME_DUMP_DELIM).append(d);
        return sb.toString();
    }

    @Override
    public TypeTag getTag() {
        return TypeTag.DATE;
    }

    @Override
    public String admPrint() {
        String mo = (month < 10) ? ("0" + month) : ("" + month);
        String d = (day < 10) ? ("0" + day) : ("" + day);
        StringBuffer sb = new StringBuffer("date(\"");
        sb.append(year).append("-");
        sb.append(mo).append("-");
        sb.append(d).append("\")");
        return sb.toString();
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}