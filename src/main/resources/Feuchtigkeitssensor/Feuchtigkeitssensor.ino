#include "WiFi.h"
#include <PubSubClient.h>
#include <time.h> 

const int sensorPin = 34;  // Ändern Sie A0 entsprechend Ihrem Anschluss
const int minVal = 2600;
const int maxVal = 1010;
const long  gmtOffset_sec = 3600; 
const int   daylightOffset_sec = 7200;
const char* pflanzenart = "Palme";
unsigned long nextHourTimestamp = 0; 

const char* ssid = "MagentaWLAN-H3OK";
const char* password = "84870265238123251727";
byte mac[] = { 0xC8, 0xF0, 0x9E, 0x52, 0x84, 0xD8 };
const char* ntpServer = "pool.ntp.org";

// MQTT Server Daten
const char* mqtt_server = "192.168.2.60";
const int mqtt_port = 1883; // Standard-MQTT-Port
WiFiClient espClient;
PubSubClient client(espClient);

// IP-Adresse des Arduino (muss für dein Netzwerk angepasst werden)
IPAddress ip(192, 168, 2, 62);
// IP-Adresse des Servers, an den du senden möchtest
IPAddress server(192, 168, 2, 60);


unsigned long calculateDelayToNextHour() {
  struct tm timeinfo;
  if(!getLocalTime(&timeinfo)){
    Serial.println("Failed to obtain time");
    return 60 * 60 * 1000; // Standardmäßig 1 Stunde warten, wenn Zeit nicht abgerufen werden kann
  }
  
  // Berechnen, wie viele Sekunden bis zur nächsten vollen Stunde verbleiben
  int secondsToNextHour = (59 - timeinfo.tm_min) * 60 + (60 - timeinfo.tm_sec);
  Serial.println(secondsToNextHour);
  return secondsToNextHour * 1000; // Rückgabe in Millisekunden
}


void setup_wifi(){
  
  Serial.println("Verbinde mit WLAN...");
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("Mit WLAN verbunden!");
  Serial.print("IP-Adresse: ");
  Serial.println(WiFi.localIP());
}

void reconnect() {
  while (!client.connected()) {
    Serial.print("Versuche, eine MQTT-Verbindung herzustellen...");
    if (client.connect("ArduinoGarten")) {
      Serial.println("verbunden");
    } 
    else {
      Serial.print("fehlgeschlagen, rc=");
      Serial.print(client.state());
      Serial.println(" versuche es in 5 Sekunden erneut");
      delay(5000);
      Serial.println(WiFi.localIP());
    }
  }
}

void httpConnect(){
  /* if (client.connect(server, 80)) {
    client.print("GET /speichereWert?wert=");
    client.print(feuchtigkeitswert);
    client.println(" HTTP/1.1");
    client.println("Host: 192.168.1.100");
    client.println("Connection: close");
    client.println(); // HTTP-Header mit einer Leerzeile beenden
  }
  if (client.connected()) {
    client.stop(); // Verbindung schließen
  }

  delay(60000); // Daten alle 60 Sekunden senden*/
}

int feuchtigkeitsmessung() {
    int messungSumme = 0; 

    for (int i = 0; i < 3; i++) {
        int sensorValue = analogRead(sensorPin);
        int percentage = map(sensorValue, minVal, maxVal, 0, 100);
        messungSumme += constrain(percentage, 0, 100);
        delay(3000); 
    }

    int durchschnitt = messungSumme / 3;
    return durchschnitt;
}

void publishMessage(char *msgBuffer, size_t bufferSize) {
  time_t jetzt;
  time(&jetzt); // Aktuelle Zeit holen
  struct tm *zeitStruktur = localtime(&jetzt);
  
  // Abrunden auf die letzte volle Stunde
  zeitStruktur->tm_min = 0;
  zeitStruktur->tm_sec = 0;
  time_t abgerundeteZeit = mktime(zeitStruktur);

  //static time_t letzteMessungsStunde = 0;
  //if (zeitStruktur->tm_hour != localtime(&letzteMessungsStunde)->tm_hour) {
    int feuchtigkeit = feuchtigkeitsmessung(); 
    snprintf(msgBuffer, bufferSize, "%s / Feuchtigkeitsmessung: %d %%, Uhrzeit: %ld", pflanzenart, feuchtigkeit, (long)abgerundeteZeit);
    client.publish("feuchtigkeitsmessung", msgBuffer); 
   // letzteMessungsStunde = jetzt;
//}
}


void setup() {
  Serial.begin(9600);
  setup_wifi();
  configTime(gmtOffset_sec, daylightOffset_sec, ntpServer);
  while (time(nullptr) < 86400) {
    Serial.print(".");
    delay(1000);
  }
  Serial.println("Zeit gesetzt.");
  client.setServer(mqtt_server, mqtt_port);
}



void loop() {
    
 
  if (!client.connected()) {
    reconnect();
  }
  client.loop();

  unsigned long currentMillis = millis();
//if (currentMillis >= nextHourTimestamp) {
  char msgBuffer[100]; 
  publishMessage(msgBuffer, sizeof(msgBuffer)); 

  unsigned long delayToNextHour = calculateDelayToNextHour();
  nextHourTimestamp = currentMillis + delayToNextHour;
//}
  }

  



