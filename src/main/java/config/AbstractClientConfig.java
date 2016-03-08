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
