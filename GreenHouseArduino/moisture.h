const int AIR_VALUE = 510;
const int WATER_VALUE = 250;

const int PINS[] = {A0,A1};
const int PIN_COUNT = sizeof(PINS) / sizeof(PINS[0]);

const int LOOP_DELAY = 10;
const int PIN_LOOP_DELAY = 1000;

//https://arduinojson.org
JsonDocument readMoistureSensor () {
  JsonDocument doc;

  for (int i = 0; i < PIN_COUNT; i++) {
    long sum = 0;
    for (int j = 0; j < 10; j++) {
      sum += analogRead(PINS[i]);
      delay(LOOP_DELAY);
    }
    int raw = sum / 10;
    int percent = map(raw, AIR_VALUE, WATER_VALUE, 0, 100);
    percent = constrain(percent, 0, 100);

    doc[i]["sensorId"] = PINS[i];
    doc[i]["measurement"] = percent;

    Serial.println(percent);
    delay(PIN_LOOP_DELAY);
  }

  return doc;
}