package workloadGenerator;

import structure.AqlUpdate;
import structure.Update;
import structure.UpdateTag;

public class UpdateWorkloadGenerator extends AbstractUpdateWorkloadGenerator {

    private String dvName;
    private String dsName;
    private String keyName; //for delete (set to null for insert)
    UpdateTag upTag;

    public UpdateWorkloadGenerator(String dv, String ds, String key, UpdateTag tag) {
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
