package datatype;

/**
 * @author pouria
 */
public class DoubleArgument implements IArgument {

    private double value;

    public DoubleArgument(double v) {
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
        return TypeTag.DOUBLE;
    }

    public double getValue() {
        return value;
    }
}