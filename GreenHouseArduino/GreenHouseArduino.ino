#include <ArduinoHttpClient.h>
#include <WiFiS3.h>
#include <WiFiSSLClient.h>
#include <ArduinoJson.h>
#include "settings.h"
#include "wifi.h"
#include "moisture.h"
#include "https.h"

void setup() {
  // put your setup code here, to run once:

  Serial.begin(9600);

  Serial.println("Ansluter till WIFI....");
  
  connectToWifi();

  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  delay(1000);
}

void loop() {
  JsonDocument doc;
  doc = readMoistureSensor();

  postFunc(doc);

  delay(10000);
}


