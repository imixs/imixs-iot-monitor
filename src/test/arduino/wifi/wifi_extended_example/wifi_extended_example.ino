/*

This example creates a client object that connects to a web server via SSL.

To test the programm make sure that you have uploaded the root SSL certificates
for the web server you try to connect to your Arduino nano. To upload a new 
Certificate use the WiFiNINA Firmware/Certificate Updater form Tools.

It is compatible with the methods normally related to plain

connections, like client.connect(host, port).

Lnital version by Arturo Guadalupi - revision November 2015
Latest version by ralph.soika@imixs.com - revision September 2021 
*/

#include <SPI.h>
#include <WiFiNINA.h>
#include "arduino_secrets.h"

///////please enter your sensitive data in the Secret file arduino_secrets.h
char ssid[] = SECRET_SSID;   // #define SECRET_SSID "your_network_SSID"
char pass[] = SECRET_PASS;   // #define SECRET_PASS "your_network_password"
// your network password (use for WPA, or use as key for WEP)
int keyIndex = 0;            // your network key Index number (needed only for WEP)

int status = WL_IDLE_STATUS;
// if you don't want to use DNS (and reduce your sketch size)
// use the numeric IP instead of the name for the server:
//IPAddress server(10,0,0,10); 
char server[] = "www.imixs.org";   

WiFiClient client;

void setup() {

  //Initialize serial and wait for port to open:
  Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }

  // =====================================
  // check for the WiFi module:
  // =====================================
  if (WiFi.status() == WL_NO_MODULE) {
    Serial.print("Communication with WiFi module failed! WiFi Status=WL_NO_MODULE");
    // don't continue
    while (true);
  }

  // =====================================
  // check firmware version...
  // =====================================
  Serial.println("====== WiFi Firmware ==========");
  Serial.print("Current Version: ");
  Serial.println(WiFi.firmwareVersion());
  Serial.print("Latest available Version: ");
  Serial.println(WIFI_FIRMWARE_LATEST_VERSION);
  Serial.println("=============================");

  // attempt to connect to WiFi network:
  connectWiFi();
  printWiFiStatus();

  Serial.println("\nStarting connection to server...");
  Serial.println(server);
  
  // if you get a connection, report back via serial:

  if (client.connectSSL(server, 443)) {
    Serial.println("connected to server");
    // Make a HTTP request:
    client.println("GET /sub_jee.html HTTP/1.1");
    client.println("Host: www.imixs.org");
    client.println("Connection: close");
    client.println();
  } else {
    Serial.println("connected to server failed!");
  }
}

void loop() {

  // if there are incoming bytes available
  // from the server, read them and print them:
  while (client.available()) {
    char c = client.read();
    Serial.write(c);
  }

  // if the server's disconnected, stop the client:
  if (!client.connected()) {
    Serial.println();
    Serial.println("disconnecting from server.");
    client.stop();

    // Don't forget to disconnect the wifi!
    Serial.println("disconnecting wifi...");
    WiFi.disconnect();
    Serial.println("====== Finished ==========");
    // do nothing forevermore:
    while (true);
  }
}


/**
 * Connects the WiFi adapter
 */
void connectWiFi()
{
  if (WiFi.status() == WL_CONNECTED) {
    Serial.print("WiFi is already connected.");
    return;
  }

  Serial.print("Connecting to WiFi...");
  WiFi.begin(ssid, pass);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }
  Serial.println("\nWiFi Connection = OK");

}


/**
 * Prints the wifi status information
 */
void printWiFiStatus() {

  // print the SSID of the network you're attached to:
  Serial.println("====== WiFi Status ==========");
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());
  // print your board's IP address:
  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);
  // print the received signal strength:
  long rssi = WiFi.RSSI();
  Serial.print("signal strength (RSSI):");
  Serial.print(rssi);
  Serial.println(" dBm");
  Serial.println("=============================");

}
