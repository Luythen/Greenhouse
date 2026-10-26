char API_KEY[] = SECRET_API_KEY;
char serverAddress[] = SECRET_SERVER_IP;
int port = PORT;

WiFiSSLClient wifi;
HttpClient client = HttpClient(wifi, serverAddress, port);

void postFunc (JsonDocument doc) {
  //https://arduinojson.org
  String output;
  serializeJson(doc, output);

  client.beginRequest();
  client.post("/soil/add");

  client.sendHeader("Content-Type", "application/json");
  client.sendHeader("Content-Length", output.length());
  client.sendHeader("X-API-KEY", API_KEY);

  client.beginBody();
  client.print(output);
  client.endRequest();

  int statusCode = client.responseStatusCode();
  String res = client.responseBody();

  Serial.println(statusCode);
  Serial.println(res);

  client.stop();
}