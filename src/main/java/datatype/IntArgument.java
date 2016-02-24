package datatype;

/**
 * @author pouria
 */
public class IntArgument implements IArgument {

    private int value;

    public IntArgument(int v) {
        this.value = v;
    }

    @Override
    public String dump() {
        return value + "";
    }

    @Override
    public String admPrint() {
        return value + "";
    }

    @Override
    public TypeTag getTag() {
        return TypeTag.INTEGER;
    }

    public int getValue() {
        return value;
    }
}