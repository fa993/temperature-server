#include <DHT.h>
#include <WiFi.h>
#include <HTTPClient.h>
#include "time.h"

const char* ssid     = "YOUR SSID HERE";
const char* password = "YOUR PASSWORD HERE";

const char* ntpServer = "pool.ntp.org";
const long  gmtOffset_sec = 5.5 * 3600;
const int   daylightOffset_sec = 3600;

#define DHTTYPE DHT11
#define DHTPIN 4

DHT dht(DHTPIN, DHTTYPE);

HTTPClient http;

String LAT = "19.225064029203295";
String LON = "72.96055783467293";
String ELE = "78";
int size = 10;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  Serial.println("Hello World");
  Serial.println(ssid);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected.");

  configTime(gmtOffset_sec, daylightOffset_sec, ntpServer);
  
  dht.begin();
  delay(2000);
}

void loop() {
  // put your main code here, to run repeatedly:
    float t = dht.readTemperature();
    Serial.println(t);
    WiFi.macAddress();
    String req = "http://192.168.29.198:8080/api/temp?temperature=";
    req.concat(t);
    req.concat("&size=");
    req.concat(size);
    req.concat("&latitude=");
    req.concat(LAT);
    req.concat("&longitude=");
    req.concat(LON);
    req.concat("&elevation=");
    req.concat(ELE);
    req.concat("&macAddress=");
    req.concat(WiFi.macAddress());
    req.concat("&time=");
    struct tm timeinfo;
    if(!getLocalTime(&timeinfo)){
      Serial.println("Failed to obtain time");
      return;
    }
    time_t sec = mktime(&timeinfo);
    req.concat(sec);
    http.begin(req);
    int resCode = http.GET();
    Serial.println(req);
    Serial.println(resCode);
    delay(5000);
}
