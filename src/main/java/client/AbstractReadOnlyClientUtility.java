package client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import config.Constants;
import structure.Pair;;

public abstract class AbstractReadOnlyClientUtility extends AbstractClientUtility {

    public String qIndexFile;
    public ArrayList<Pair> qvids;
    public ArrayList<String> dumpDir;
    protected boolean dumpResults = false;

    public abstract void executeQuery(int qid, int vid, String qBody);

    public AbstractReadOnlyClientUtility(String qIxFile, String statsFile, int ignore, String qSeqFile,
            String dumpDirFile, String resultsFile) {
        super(statsFile, resultsFile, ignore);
        this.qIndexFile = qIxFile;
        loadQSeqFile(qSeqFile);
        if (dumpDirFile != null) {
            loadDumpDirPaths(dumpDirFile);
        }
    }

    private void loadDumpDirPaths(String dumpDirPaths) {
        if (dumpDir == null) {
            this.dumpDir = new ArrayList<String>();
        }
        this.dumpDir.clear();
        try {
            BufferedReader in = new BufferedReader(new FileReader(dumpDirPaths));
            String str;
            while ((str = in.readLine()) != null) {
                if (str.startsWith(Constants.COMMENT_TAG) || (str.length() == 0)) { //ignore comment lines
                    continue;
                }
                dumpDir.add(str.trim());
            }
            in.close();
        } catch (IOException e) {
            System.err.println("Error in loading dump directories in AsterixClient for Random Workloads !");
            e.printStackTrace();
        }
    }

    private void loadQSeqFile(String qSeqFile) {
        if (qvids == null) {
            this.qvids = new ArrayList<Pair>();
        }
        this.qvids.clear();
        try {
            BufferedReader in = new BufferedReader(new FileReader(qSeqFile));
            String str;
            while ((str = in.readLine()) != null) {
                if (str.startsWith(Constants.COMMENT_TAG) || (str.length() == 0)) { //ignore comment lines
                    continue;
                }
                StringTokenizer st = new StringTokenizer(str, Constants.QSEQ_FILE_DELIM);
                int qIdentifier = Integer.parseInt(st.nextToken());
                int version = 0; //default version
                if (st.hasMoreTokens()) {
                    version = Integer.parseInt(st.nextToken());
                }
                qvids.add(new Pair(qIdentifier, version));
            }
            in.close();
        } catch (IOException e) {
            System.err.println("Error in loading queries sequence in AsterixClient for Random Workloads !");
            e.printStackTrace();
        }
    }

    protected void setDumpResults(boolean b) {
        this.dumpResults = b;
    }
}