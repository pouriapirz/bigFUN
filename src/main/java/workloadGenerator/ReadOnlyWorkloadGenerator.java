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

import java.io.IOException;
import java.util.ArrayList;

import config.RandomQueryGeneratorConfig;
import datatype.IArgument;
import queryGenerator.RandomQueryGenerator;
import structure.Query;

public class ReadOnlyWorkloadGenerator extends AbstractReadOnlyWorkloadGenerator {

    final RandomQueryGenerator rqGen;

    public ReadOnlyWorkloadGenerator(String qIndexFile, String qGenConfigFile, long seed, long maxUsrId) {
        super();
        try {
            loadQueryTemplate(qIndexFile);
        } catch (IOException e) {
            System.err.println("Error in loading qIndexFile in RandomWorkloadGenerator");
            e.printStackTrace();
        }
        rqGen = new RandomQueryGenerator(seed, maxUsrId);
        try {
            RandomQueryGeneratorConfig.configure(rqGen, qGenConfigFile);
        } catch (IOException e) {
            System.err.println("Error in configuring RandomQueryGenerator");
            e.printStackTrace();
        }
    }

    public Query nextQuery(int qid, int vid) {
        ArrayList<IArgument> args = rqGen.nextQuery(qid, vid);
        Query q = qIxToQuery.get(qid);
        if (q == null) {
            System.err.println("Query " + qid + " can not be found !");
            return null;
        }
        q.reset(args);
        return q;
    }
}
