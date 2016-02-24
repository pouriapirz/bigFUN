package datatype;

/**
 * @author pouria
 */
public class StringArgument implements IArgument {

    private String value;

    public StringArgument(String s) {
        this.value = s;
    }

    @Override
    public String dump() {
        return "\"" + value + "\"";
    }

    @Override
    public TypeTag getTag() {
        return TypeTag.STRING;
    }

    @Override
    public String admPrint() {
        return "\"" + value + "\"";
    }

    public String getValue() {
        return value;
    }
}