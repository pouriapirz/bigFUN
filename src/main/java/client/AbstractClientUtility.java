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
package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import structure.StatsCollector;

public abstract class AbstractClientUtility {

    protected PrintWriter resPw;
    protected StatsCollector sc;

    public AbstractClientUtility(String statsFile, String resultsFile, int ignore) {
        this.sc = new StatsCollector(statsFile, ignore);
        if (resultsFile != null) {
            try {
                resPw = new PrintWriter(new File(resultsFile));
            } catch (FileNotFoundException e) {
                System.err.println("Problem in creating writer for query results dump !");
                e.printStackTrace();
            }
        }
    }

    public abstract void init();

    public abstract void terminate();

    protected void updateStat(int qid, int vid, long rspTime) {
        sc.updateStat(qid, vid, rspTime);
    }

    public void generateReport() {
        sc.report();
    }

}
