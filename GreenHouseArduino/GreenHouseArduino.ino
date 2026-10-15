#include "WiFiS3.h"
#include "settings.h"
#include "wifi.h"

void setup() {
  // put your setup code here, to run once:

  Serial.begin(9600);

  Serial.println("Ansluter till WIFI....");
  
  connectToWifi();

  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());
}

void loop() {
  
}
