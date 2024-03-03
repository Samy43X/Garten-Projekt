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
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;

import ch.qos.logback.core.boolex.Matcher;


public class MQTTtoSQLite {
//	private static final String DATABASE_URL = "jdbc:sqlite:garten.db";

	public MQTTtoSQLite(Jdbi jdbi){
		MQTTtoSQLite.jdbi = jdbi;
	}
	
	
		private static Jdbi jdbi;
        final String broker = "tcp://192.168.2.60:1883";
        final String clientId = "JavaClient";
        final String topic = "feuchtigkeitsmessung";
		public Object creator;
		MqttClient client;
		int messageCount = 0;

        public void verbindenMitMQTT() {
        try (MqttClient client = new MqttClient(broker, clientId)) {
        	this.client = client;
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.connect(connOpts);
            System.out.println("Connected to MQTT broker");

            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost: " + cause.toString());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws MqttException {
                    saveMessage(topic, new String(message.getPayload()));
//                    messageCount++;
//
//                    if (messageCount >= 10) {
//                        new Thread(() -> {
//                            try {
//                                client.disconnect();
//                                System.out.println("Disconnected from the broker after receiving 10 messages.");
//                            } catch (MqttException e) {
//                                e.printStackTrace();
//                            }
//                        }).start();
//                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            client.subscribe(topic);
            System.out.println("Subscribed to topic: " + topic);

        } catch (MqttException me) {
            System.out.println("Reason: " + me.getReasonCode());
            me.printStackTrace();
        }
    }

        public void trenneVonMQTT() {
            if (client != null && client.isConnected()) {
                try {
                    client.disconnect();
                    System.out.println("Disconnected from MQTT broker");
                } catch (MqttException e) {
                    System.err.println("Error disconnecting from MQTT broker: " + e.getMessage());
                }
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


}
