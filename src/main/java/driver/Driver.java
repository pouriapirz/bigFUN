package driver;

import client.AbstractClient;
import config.AbstractClientConfig;
import config.AsterixClientConfig;
import config.Constants;

public class Driver {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Correct Usage:\n");
            System.out.println("\t[0]: BigFUN home has to be set to a valid path.");
            return;
        }

        String bigFunHome = args[0];
        bigFunHome = bigFunHome.replaceAll("/$", "");
        String clientConfigFile = bigFunHome + "/conf/bigfun-conf.json";
        AbstractClientConfig clientConfig = new AsterixClientConfig(clientConfigFile);
        clientConfig.parseConfigFile();
        if (!clientConfig.isParamSet(Constants.CLIENT_TYPE)) {
            System.err.println("The Client Type is not set to a valid value in the config file.");
            return;
        }
        String clientTypeTag = (String) clientConfig.getParamValue(Constants.CLIENT_TYPE);
        AbstractClient client = null;
        switch (clientTypeTag) {
            case Constants.ASTX_RANDOM_CLIENT_TAG:
                //case Constants.ASTX_FIXED_CLIENT_TAG:
                client = clientConfig.readReadOnlyClientConfig(bigFunHome);
                break;
            case Constants.ASTX_UPDATE_CLIENT_TAG:
                client = clientConfig.readUpdateClientConfig(bigFunHome);
                break;

            default:
                System.err.println("Unknown/Invalid client type:\t" + clientTypeTag);
        }

        client.execute();
        client.generateReport();
        System.out.println("\nBigFUN Benchmark is done.\n");
    }

}
