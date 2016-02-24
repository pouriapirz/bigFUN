package config;

import datatype.DoubleArgument;

public class Constants {
    //Delimiters
    public static final String ARG_TAG_IN_Q_TEMPLATE_FILE = "@PARAM";
    public static final String DUMPED_QUERY_ARG_DELIM = ","; //RandomWorkloadGenerator uses this delim to separate arg-tag and arg-val in dumps
    public static final String QGEN_CONFIG_FILE_DELIM = ","; //RandomQueryGeneratorConfig uses this delim to distinguish between param-name and param-val in its reference config file
    public static final String QSEQ_FILE_DELIM = ","; //Delimiter for QId and VId
    public static final String QPARAM_FILE_DELIM = ","; //QueryParamSetting file uses this delim
    public static final String CLIENT_CONFIG_FILE_DELIM = ","; //param-names and their values are separated using this delimiter on lines of a client config file
    public static final String ARG_DATETIME_DUMP_DELIM = "-"; //various fields of a dateTime arg is separated with this delim when dumped in generic format

    public static final String COMMENT_TAG = "#"; //ignore lines starting with this char

    //QGen Param Tags in generator config File
    public static final String START_DATE = "START_DATE";
    public static final String END_DATE = "END_DATE";
    public static final String MAX_FBU_ID = "MAX_FBU_ID";
    public static final String FBU_SCAN_LEN = "FBU_SCAN_LEN";
    public static final String QPARAM_SETTINGS_FILE = "QUERY_PARAMS_FILE";

    //Clients related
    //general
    public static final String CLIENT_TYPE = "clientType";
    public static final String ITERATIONS = "iterations";
    public static final String IGNORE = "ignore-rounds"; //how many initial rounds to ignore when calculating avgerage time

    public static final String EXECUTE_QUERY = "ExecQ";
    public static final String UPDATE_OPR_TYPE_TAG = "operation";
    public static final String INSERT_OPR_TYPE = "insert";
    public static final String DELETE_OPR_TYPE = "delete";
    public static final String UPDATE_BATCH_SIZE = "batch-size";
    public static final String UPDATE_LIMIT = "limit"; //how many batches of update to run (you may want early termination)
    public static final String QDUMP_FILE_NAME_PRFX = "q";
    public static final String QDUMP_FILE_ID_VER_SEPARATOR = "_";
    public static final String Q_IX_FILE = "qix_file"; //Queries' templates
    public static final String STATS_FILE = "stats_file";
    public static final String WARMUP_STATS_FILE = "warmup_stats_file"; //File to dump stats for the warmup phase
    public static final String Q_SEQ_FILE = "qseq_file"; //Sequence of queries during execution (one iteration/within one directory)
    public static final String DUMP_DIR_FILE = "dump_dirs_file"; //List of directories to-dump/for-a-dumped workload
    public static final String Q_GEN_CONFIG_FILE = "qgen_config_file"; //Configuration file for a random query generator
    public static final String RESULTS_DUMP_FILE = "res_dump_file"; //File that contains returned query results, if desired
    public static final String UPDATES_FILE = "updates_file"; //File that contains update workload
    public static final String WARMUP_FILE = "warmup_file"; //File that contains update workload, to be run as the warm-up phase
    public static final int INSERT_QIX = 18;
    public static final int DELETE_QIX = 19;
    //Asterix - Common among clients
    public static final String CC_URL = "cc";
    public static final String ASTX_DV_NAME = "dataverse";
    //Asterix - Read-Only Client
    public static final String ASTX_FIXED_CLIENT_TAG = "AstxFixed"; //used as tag in client config file
    public static final String ASTX_RANDOM_CLIENT_TAG = "AstxRandom"; //used as tag in client config file
    public static final String ASTX_DUMP_RESULTS = "DumpResults"; //whether or not to write full returned results
    public static final String ASTX_PARSE_RESULTS = "ParseResults"; //whether or not get results count from returned json
    //Asterix - Update Client
    public static final String ASTX_UPDATE_CLIENT_TAG = "AstxUpdate";
    public static final String ASTX_DS_NAME = "dataset";
    public static final String ASTX_KEY_NAME = "keyAtt"; //PK of dataset where delete operations are gonna be executed on
    //Datastore related
    public static final int ASTX_REST_PORT = 19002;
    //QStat Codes
    public static final int INVALID_QID = -1;
    public static final int INVALID_RS = -1;
    public static final long INVALID_TIME = -1;
    public static final long INVALID_STAT = -1;
    //Q-Params (used for query generation in Spatial & Text Search)
    public static final String[] FREQ_KW = { "Surface", "Nexus" };
    public static final String[] MEDIUM_KW = { "Galaxy", "Kindle" };
    public static final String[] RARE_KW = { "Lumia", "XPeria" };
    public static final int MIN_LAT = 26;
    public static final int MAX_LAT = 47;
    public static final int MIN_LON = 68;
    public static final int MAX_LON = 96;
    public static DoubleArgument SMALL_RADIUS = new DoubleArgument(0.01);
    public static DoubleArgument MEDIUM_RADIUS = new DoubleArgument(0.1);
    public static DoubleArgument LARGE_RADIUS = new DoubleArgument(1.0);
    public static final String DEFAULT_START_DATE = "2005-01-01-00-00-00";
    public static final String DEFAULT_END_DATE = "2014-05-29-23-59-59";
    public static final long DEFAULT_MAX_FB_USR_ID = 1000000L;
}
