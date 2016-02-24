package structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import datatype.IArgument;

/**
 * @author pouria
 */
public class Query {

    private List<String> segments;
    private List<IArgument> args;

    public Query(List<String> segments) {
        this.segments = new ArrayList<String>();
        for (String s : segments) {
            this.segments.add(s);
        }
        this.args = new ArrayList<IArgument>();
    }

    public String aqlPrint(String dataverse) {
        Iterator<String> segIt = segments.iterator();
        Iterator<IArgument> argIt = args.iterator();

        StringBuilder st = new StringBuilder("use dataverse " + dataverse + ";\n");
        while (segIt.hasNext()) {
            st.append(segIt.next()).append(" ");
            if (argIt.hasNext()) {
                st.append(argIt.next().admPrint()).append(" ");
            }
        }
        if (argIt.hasNext()) {
            st.append(" ").append(argIt.next().admPrint());
        }
        return st.toString();
    }

    public void reset(List<IArgument> a) {
        args.clear();
        for (IArgument arg : a) {
            args.add(arg);
        }
    }

    public List<IArgument> getArguments() {
        return this.args;
    }
}