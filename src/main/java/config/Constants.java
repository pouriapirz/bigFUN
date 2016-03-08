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
package config;

import datatype.DoubleArgument;

public class Constants {

    public static final String ARG_TAG_IN_Q_TEMPLATE_FILE = "@PARAM";
    public static final String QSEQ_FILE_DELIM = ",";
    public static final String QPARAM_FILE_DELIM = ",";
    public static final String ARG_DATETIME_DUMP_DELIM = "-";
    public static final String COMMENT_TAG = "#";
    public static final String SEED = "seed";
    public static final String MAX_GBOOK_USR_ID = "max_gleambookUser_id";
    public static final String CLIENT_TYPE = "clientType";
    public static final String ITERATIONS = "iterations";
    public static final String IGNORE = "ignore_rounds";
    public static final String EXECUTE_QUERY = "execute";
    public static final String UPDATE_OPR_TYPE_TAG = "operation";
    public static final String INSERT_OPR_TYPE = "insert";
    public static final String DELETE_OPR_TYPE = "delete";
    public static final String UPDATE_BATCH_SIZE = "batch_size";
    public static final String UPDATE_LIMIT = "limit";
    public static final String STATS_FILE = "stats_file";
    public static final String RESULTS_DUMP_FILE = "res_dump_file";
    public static final String UPDATES_FILE = "updates_file";
    public static final int INSERT_QIX = 18;
    public static final int DELETE_QIX = 19;
    public static final String CC_URL = "cc";
    public static final String ASTX_DV_NAME = "dataverse";
    public static final String ASTX_RANDOM_CLIENT_TAG = "AsterixdbReadOnly";
    public static final String ASTX_DUMP_RESULTS = "dumpResults";
    public static final String ASTX_UPDATE_CLIENT_TAG = "AsterixdbUpdate";
    public static final String ASTX_DS_NAME = "dataset";
    public static final String ASTX_KEY_NAME = "primary_key";
    public static final int ASTX_AQL_REST_API_PORT = 19002;
    public static final int INVALID_QID = -1;
    public static final long INVALID_TIME = -1;
    public static final String[] FREQ_KW = { "Surface", "Nexus" };
    public static final String[] MEDIUM_KW = { "Galaxy", "Kindle" };
    public static final String[] RARE_KW = { "Lumia", "XPeria" };
    public static final int MIN_LAT = 26;
    public static final int MAX_LAT = 47;
    public static final int MIN_LON = 68;
    public static final int MAX_LON = 96;
    public static final DoubleArgument SMALL_RADIUS = new DoubleArgument(0.01);
    public static final DoubleArgument MEDIUM_RADIUS = new DoubleArgument(0.1);
    public static final DoubleArgument LARGE_RADIUS = new DoubleArgument(1.0);
    public static final String DEFAULT_START_DATE = "2005-01-01-00-00-00";
    public static final String DEFAULT_END_DATE = "2014-05-29-23-59-59";
    public static final long DEFAULT_MAX_GBOOK_USR_ID = 10000L;
    public static final long DEFAULT_SEED = 10L;
    public static final String BIGFUN_CONFIG_FILE_NAME = "bigfun-conf.json";
    public static final String Q_IX_FILE_NAME = "query-index.json";
    public static final String WORKLOAD_FILE_NAME = "workload.txt";
    public static final String STATS_FILE_NAME = "stats.txt";
    public static final String Q_GEN_CONFIG_FILE_NAME = "query-params.txt";
}
