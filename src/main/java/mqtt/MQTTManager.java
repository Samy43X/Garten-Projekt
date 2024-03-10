package mqtt;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.jdbi.v3.core.Jdbi;

import db.DatabaseManager;


public class MQTTManager {
//	private static final String DATABASE_URL = "jdbc:sqlite:garten.db";

	public MQTTManager(){
	}
	
		private static MQTTManager instance;
		private static Jdbi jdbi = DatabaseManager.getJdbi();
        //final String broker = "tcp://192.168.2.60:1883";
        String brokerUrl; // = "tcp://192.168.178.51:1883";
        String clientId;  //= "JavaClient";
        private boolean isConnected = false;
        final String topic = "feuchtigkeitsmessung";
		public Object creator;
		MqttClient client;
		int messageCount = 0;
		
		
		public static synchronized MQTTManager getInstance() {
	        if (instance == null) {
	            instance = new MQTTManager();
	        }
	        return instance;
	    }
		
		public void connect(String broker, String clientId) {
	        try {
	            client = new MqttClient(broker, clientId, new MemoryPersistence());
	            MqttConnectOptions connOpts = new MqttConnectOptions();
	            connOpts.setCleanSession(true);
	            System.out.println("Connecting to broker: " + broker);
	            client.connect(connOpts);
	            System.out.println("Connected");
	            isConnected = true;
	            
	            client.setCallback(new MqttCallback() {
	                @Override
	                public void connectionLost(Throwable cause) {
	                    System.out.println("Connection lost: " + cause.toString());
	                }

	                @Override
	                public void messageArrived(String topic, MqttMessage message) throws MqttException {
	                    saveMessage(topic, new String(message.getPayload()));
	                    /*messageCount++;

	                    if (messageCount >= 10) {
	                        new Thread(() -> {
	                            try {
	                                client.disconnect();
	                                System.out.println("Disconnected from the broker after receiving 10 messages.");
	                            } catch (MqttException e) {
	                                e.printStackTrace();
	                            }
	                        }).start();
	                    }*/
	                }

	                @Override
	                public void deliveryComplete(IMqttDeliveryToken token) {
	                }
	            });
	        } catch (MqttException me) {
	        	System.out.println("Reason: " + me.getReasonCode());
	            me.printStackTrace();
	        }
	        
	        
	    }


        
        public void connectMQTT() {
            try {
                if (this.client != null) {
                    if (this.client.isConnected()) {
                        this.client.disconnect();
                    }
                    this.client.close();
                }
                this.client = new MqttClient(brokerUrl, clientId);
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                this.client.connect(connOpts);

                // Konfigurieren Sie hier Ihren Client weiter, z.B. das Setzen von Callbacks
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }

        public void disconnectMQTT() {
            try {
                if (this.client != null && this.client.isConnected()) {
                    this.client.disconnect();
                }
            } catch (MqttException e) {
            	System.err.println("Error disconnecting from MQTT broker: " + e.getMessage());
            }
        }
       

    private static void saveMessage(String topic, String message) {
    	int unix = unixZeitAusgeben(message);
    	int feuchtigkeit = feuchtigkeitAusgeben(message);
    	String uhrzeit = zeitBerechnenAusUnix(unix);
    	String art = pflanzenartAusgeben(message);
    	
    	jdbi.useTransaction(handle -> {
    	    // Zuerst überprüfen, ob die Art bereits existiert, und wenn nicht, einfügen.
    	    boolean artExists = handle.createQuery("SELECT count(*) FROM pflanzen WHERE art = :art")
    	                              .bind("art", art)
    	                              .mapTo(Integer.class)
    	                              .findFirst().orElse(null) > 0;

    	    if (!artExists) {
    	        handle.execute("INSERT INTO pflanzen (art) VALUES (?)", art);
    	    }
    	    Integer pflanzenId = handle.createQuery("SELECT id FROM pflanzen WHERE art = :art")
                    .bind("art", art)
                    .mapTo(Integer.class)
                    .findFirst().orElse(null); 
    	
    	    // Nachrichtendaten einfügen
    	    
    	    if (pflanzenId != null) {
    	    handle.execute("INSERT INTO messungen (zeitangabe, unixzeit, feuchtigkeit, pflanzenId) VALUES (?, ?, ?, ?)",
    	                   uhrzeit, unix, feuchtigkeit, pflanzenId);
    	    }
    	    else {
    	    	handle.execute("INSERT INTO messungen (zeitangabe, unixzeit, feuchtigkeit) VALUES (?, ?, ?)",
 	                   uhrzeit, unix, feuchtigkeit);
    	    
    	    }
    	});
    	

        System.out.println("Message saved to SQLite database using JDBI");
    }
    
    private static String zeitBerechnenAusUnix(long unixzeit) {
    	Instant instant = Instant.ofEpochSecond(unixzeit);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Europe/Berlin"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return zonedDateTime.format(formatter);
    	
    }
    
    private static int unixZeitAusgeben(String message) {
    	Pattern pattern = Pattern.compile("Uhrzeit: (\\d+)");
        java.util.regex.Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
          	
    }
    
    private static int feuchtigkeitAusgeben(String message) {
    	Pattern pattern = Pattern.compile("Feuchtigkeitsmessung: (\\d+)");
        java.util.regex.Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1; 	
    }
    
    private static String pflanzenartAusgeben(String message) {
    	Pattern pattern = Pattern.compile("^(.*?) / Feuchtigkeitsmessung: \\d+%, Uhrzeit: \\d+");
        java.util.regex.Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
        	System.out.println("POW"+matcher.group(1)+"POWS");
            return matcher.group(1); 
        }
        return null; 
    }
    
    public MqttClient getClient() {
		return client;
	}


	public void setClient(MqttClient client) {
		this.client = client;
	}

	public boolean isConnected() {
		return isConnected;
	}

	

}
