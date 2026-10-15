char ssid[] = SECERT_SSID;
char pass[] = SECERT_PASSWORD;

void connectToWifi() {
  while (WiFi.begin(ssid, pass) != WL_CONNECTED) {
    Serial.println(".");
    delay(10000);
  }
}