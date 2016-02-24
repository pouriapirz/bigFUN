package datatype;

/**
 * @author pouria
 */
public class LongArgument implements IArgument {

    private long value;

    public LongArgument(long v) {
        this.value = v;
    }

    @Override
    public String dump() {
        return value + "";
    }

    @Override
    public String admPrint() {
        return "int64(\"" + value + "\")";
    }

    @Override
    public TypeTag getTag() {
        return TypeTag.LONG;
    }

    public long getValue() {
        return value;
    }
}