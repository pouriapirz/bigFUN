package config;

import java.io.File;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import client.AbstractReadOnlyClient;
import client.AbstractUpdateClient;

public abstract class AbstractClientConfig {
    private String clientConfigFile;
    private Map<String, Object> params;

    public AbstractClientConfig(String clientConfigFile) {
        this.clientConfigFile = clientConfigFile;
    }

    public abstract AbstractReadOnlyClient readReadOnlyClientConfig();

    public abstract AbstractUpdateClient readUpdateClientConfig();

    public void parseConfigFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            params = mapper.readValue(new File(clientConfigFile), Map.class);
        } catch (Exception e) {
            System.err.println("Problem in parsing the JSON config file.");
            e.printStackTrace();
        }
    }

    public boolean isParamSet(String paramName) {
        return params.containsKey(paramName);
    }

    public Object getParamValue(String paramName) {
        return params.get(paramName);
    }

    /*
    protected HashMap<String, String> parse(){	//TODO You may wanna return Java Properties
    	clientTypeTag = null;	//reset
    	HashMap<String, String> params = new HashMap<String, String>();
    	try {
    		int lineCounter = 0;	//tracking lines for potential error reporting
    		BufferedReader in = new BufferedReader(new FileReader(clientConfigFile));
    		String header = in.readLine();
    		lineCounter++;
    		while(header.startsWith(Constants.COMMENT_TAG) || (header.length() == 0)){	//ignore comment lines
    			header = in.readLine();	
    			lineCounter++;
    			continue;
    		}
    		clientTypeTag = new String(header);	//first meaningful line of file should be client type tag
    		String str;
    		while ((str = in.readLine()) != null) {
    			lineCounter++;
    			if(str.startsWith(Constants.COMMENT_TAG) || (str.length() == 0)){	//ignore comment lines
    				continue;
    			}
    			StringTokenizer st = new StringTokenizer(str.trim(), Constants.CLIENT_CONFIG_FILE_DELIM);
    			if(st.countTokens() < 2){
    				System.err.println("WARNING for file \'"+clientConfigFile+"\'\nline "+lineCounter+" with length "+str.length()+" seems to be invalid:\n"+str);
    				System.err.println("config parameters have the generic form of a Key,Value pair (one or both of them could be missed in this case - Remember an empty line (with just new line or any white space chars) is NOT valid)");
    			}
    			params.put(st.nextToken(), st.nextToken());
    		}
    		in.close();
    	} catch (IOException e) {
    		System.err.println("Problem in reading and configuring asterix client !");
    		e.printStackTrace();
    	}
    	return params;
    }
    */
}
