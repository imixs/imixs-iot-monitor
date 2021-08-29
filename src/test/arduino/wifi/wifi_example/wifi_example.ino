/*
  WiFi Example

  This example code:

  https://docs.arduino.cc/tutorials/nano-rp2040-connect/rp2040-01-technical-reference#wi-fi

  https://elektro.turanis.de/html/prj365/index.html

  For HTTPS see:

  https://techtutorialsx.com/2017/11/18/esp32-arduino-https-get-request/
  
*/

#include <WiFiNINA.h>

// WiFi settings:
const char* SSID     = "xx";
const char* PASSWORD = "xx";


const char* TS_HOST    = "www.imixs.org";

WiFiClient client;

// the setup function runs once when you press reset or power the board
void setup() {

  // initialize
  Serial.println("Starting Wifi Example");
}

// the loop function runs over and over again forever
void loop() {

  Serial.println("Starte looop to WiFi...");
  connectWiFi();

  if (client.connect(TS_HOST, 443)) {
    Serial.println("Connected ");

    client.println("GET /sub_jee.html HTTPS/1.0");
    client.println();

    // wait for data to be available
    unsigned long timeout = millis();
    while (client.available() == 0) {
      if (millis() - timeout > 5000) {
        Serial.println(">>> Client Timeout !");
        client.stop();
        delay(60000);
        return;
      }
    }

    // Read all the lines of the reply from server and print them to Serial
    Serial.println("Respond:");
    while (client.available()) {
      String line = client.readStringUntil('\r');
      Serial.print(line);
    }

    Serial.println();
    Serial.println("closing connection");
  } else {
    Serial.println("Connection to server failed!");
  }


  client.stop();
  WiFi.end();
  // Exit the loop
  exit(0);  //The 0 is required to prevent compile error.

}



void connectWiFi()
{
  if (WiFi.status() == WL_CONNECTED) {
    return;
  }

  Serial.print("Connecting to WiFi...");
  WiFi.begin(SSID, PASSWORD);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("[connected]");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}
