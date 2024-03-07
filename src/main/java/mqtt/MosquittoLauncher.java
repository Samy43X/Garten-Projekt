package mqtt;

import java.io.IOException;

public class MosquittoLauncher {
	
	public MosquittoLauncher(String mosquittoPath, String configFilePath) {
		startMosquittoBroker(mosquittoPath, configFilePath);
	}
	
	

    public static void startMosquittoBroker(String mosquittoPath, String configFilePath) {
        try {
            ProcessBuilder builder = new ProcessBuilder(mosquittoPath, "-c", configFilePath);
            builder.start(); 
            System.out.println("Mosquitto Broker gestartet mit Konfiguration: " + configFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Fehler beim Starten des Mosquitto Brokers.");
        }
    }

}

