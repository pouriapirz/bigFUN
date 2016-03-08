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
