package asterixUpdateClient;

import structure.AqlUpdate;
import structure.Update;
import structure.UpdateTag;
import workloadGenerator.AbstractUpdateWorkloadGenerator;

public class AsterixUpdateWorkloadGenerator extends AbstractUpdateWorkloadGenerator {

    private String dvName;
    private String dsName;
    private String keyName; //for delete (set to null for insert)
    UpdateTag upTag;

    public AsterixUpdateWorkloadGenerator(String dv, String ds, String key, UpdateTag tag) {
        super();
        this.dvName = dv;
        this.dsName = ds;
        this.keyName = key;
        this.upTag = tag;
    }

    @Override
    protected void modifySingleUpdate(Update update, String line) {
        ((AqlUpdate) update).addUpdate(line);
    }

    @Override
    protected Update generateNewUpdate() {
        return (new AqlUpdate(dvName, dsName, keyName, upTag));
    }
}
