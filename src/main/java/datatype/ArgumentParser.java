package datatype;

import java.util.StringTokenizer;

import config.Constants;

/**
 * Parses an argument from a string which had been created with dump format
 * 
 * @author pouria
 */
public class ArgumentParser {
    public static DateTimeArgument parseDateTime(String arg) {
        StringTokenizer st = new StringTokenizer(arg, Constants.ARG_DATETIME_DUMP_DELIM);
        int argY = Integer.parseInt(st.nextToken());
        int argMo = Integer.parseInt(st.nextToken());
        int argD = Integer.parseInt(st.nextToken());
        int argH = Integer.parseInt(st.nextToken());
        int argMi = Integer.parseInt(st.nextToken());
        int argSec = Integer.parseInt(st.nextToken());
        return new DateTimeArgument(argY, argMo, argD, argH, argMi, argSec);
    }

    public static IntArgument parseInt(String arg) {
        int val = Integer.parseInt(arg);
        return new IntArgument(val);
    }

    public static StringArgument parseString(String arg) {
        return new StringArgument(arg.substring(1, (arg.length() - 1)));
    }

    public static LongArgument parseLong(String arg) {
        long val = Long.parseLong(arg);
        return new LongArgument(val);
    }

    public static DoubleArgument parseDouble(String arg) {
        double val = Double.parseDouble(arg);
        return new DoubleArgument(val);
    }
}