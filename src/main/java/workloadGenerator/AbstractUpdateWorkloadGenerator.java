package workloadGenerator;

import java.io.BufferedReader;
import java.io.Reader;

import structure.Update;

public abstract class AbstractUpdateWorkloadGenerator {

    Reader updateReader;

    public AbstractUpdateWorkloadGenerator() {
    }

    protected abstract Update generateNewUpdate();

    protected abstract void modifySingleUpdate(Update update, String line);

    public void resetUpdatesInput(Reader upr) {
        updateReader = upr;
    }

    public Update getNextUpdate() {
        if (updateReader == null) {
            System.err.println(
                    "Update generator does not have (or already consumed) its update reader.\nYou need to reset it !");
            return null;
        }
        Update upd = generateNewUpdate();
        try {
            BufferedReader in = new BufferedReader(updateReader);
            String str;
            while ((str = in.readLine()) != null) {
                modifySingleUpdate(upd, str);
            }
            in.close();
        } catch (Exception e) {
            System.err.println("problem in update generation from updates reader ");
            e.printStackTrace();
        }
        updateReader = null;
        return upd;
    }
}
