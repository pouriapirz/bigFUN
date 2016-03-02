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

    public abstract AbstractReadOnlyClient readReadOnlyClientConfig(String bigFunHomePath);

    public abstract AbstractUpdateClient readUpdateClientConfig(String bigFunHomePath);

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

    /**
     * Added for debug/trace purposes only
     * 
     * @return List all params and their values in the current config
     */
    public String printConfig() {
        StringBuilder sb = new StringBuilder();
        for (String s : params.keySet()) {
            sb.append(s).append(":\t").append(getParamValue(s).toString()).append("\n");
        }
        return sb.toString();
    }
}
