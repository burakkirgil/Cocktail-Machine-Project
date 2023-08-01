
#include <ESP8266WiFi.h>
#include <Firebase_ESP_Client.h>
#include <Arduino_JSON.h>
#include <LiquidCrystal_I2C.h>

#define FIREBASE_EMAIL      "omerfgunes1@gmail.com"
#define FIREBASE_PASS       "123456"
#define FIREBASE_API_KEY    "AIzaSyCXhMsIkuV3Aq3qIGuW93r8ObfyERDxksI";
#define FIREBASE_RTD        "esp-db-b8658-default-rtdb.firebaseio.com";
#define FIREBASE_PROJECT_ID "esp-db-b8658"

const char* ssid     = "Ofg";
const char* password = "12345678";

const int motorPin1 = 16;  // Pin D5 on NodeMCU (GPIO 0)
const int motorPin2 = 2;  // Pin D6 on NodeMCU (GPIO 4)
const int motorPin3 = 14;  // Pin D7 on NodeMCU (GPIO 5)
const int motorPin4 = 12;  // Pin D8 on NodeMCU (GPIO 6)

// Define Firebase Data object
FirebaseData fbdo;

FirebaseAuth auth;
FirebaseConfig config;

bool taskCompleted = false;

unsigned long dataMillis = 0;

int current_order_num = 0;

//Motor drive inxes
#define COCKTAIL_NUM 8
const char* cocktails[COCKTAIL_NUM] = {"MARGARITA", "MARTINI", "MOJITO", "JULEP", "COSMOPOLITAN", "LONGISLAND", "BELLINI", "BLACKRUSSIAN"};

typedef struct {
    uint8_t motor1;
    uint8_t motor2;
    uint8_t motor3;
    uint8_t motor4;
} engines_t;

engines_t engines_mux[COCKTAIL_NUM] = {
    {.motor1 = LOW, .motor2 = LOW, .motor3 = HIGH, .motor4 = HIGH},
    {.motor1 = LOW, .motor2 = HIGH, .motor3 = HIGH, .motor4 = LOW},
    {.motor1 = HIGH, .motor2 = LOW, .motor3 = HIGH, .motor4 = LOW},
    {.motor1 = LOW, .motor2 = HIGH, .motor3 = HIGH, .motor4 = LOW},
    {.motor1 = HIGH, .motor2 = LOW, .motor3 = HIGH, .motor4 = LOW},
    {.motor1 = LOW, .motor2 = LOW, .motor3 = HIGH, .motor4 = LOW},
    {.motor1 = HIGH, .motor2 = HIGH, .motor3 = LOW, .motor4 = LOW},
    {.motor1 = LOW, .motor2 = HIGH, .motor3 = LOW, .motor4 = LOW},
};

LiquidCrystal_I2C lcd(0x27, 16, 2);

void setup() {
  // Init Serial Port
  Serial.begin(115200);

  // Init LCD Screen
  lcd.init();
  lcd.backlight();
  lcd.setCursor(0,0);
  lcd.print("Cocktail Maker");
  lcd.setCursor(0,1);
  lcd.print("Version:1.0.0");

  // Motors PIN Configuration
  pinMode(motorPin1, OUTPUT);
  pinMode(motorPin2, OUTPUT);
  pinMode(motorPin3, OUTPUT);
  pinMode(motorPin4, OUTPUT);

  digitalWrite(motorPin1, HIGH);
  digitalWrite(motorPin2, HIGH);
  digitalWrite(motorPin3, HIGH);
  digitalWrite(motorPin4, HIGH);


  // We start by connecting to a WiFi network

  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  /* Explicitly set the ESP8266 to be a WiFi-client, otherwise, it by default,
     would try to act as both a client and an access-point and could cause
     network-issues with your other WiFi-devices on your WiFi-network. */
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  // Firebase configuration
  config.api_key = FIREBASE_API_KEY;
  config.database_url = FIREBASE_RTD;
  auth.user.email = FIREBASE_EMAIL;
  auth.user.password = FIREBASE_PASS;

  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);
}

void loop() {
    // Firebase.ready() should be called repeatedly to handle authentication tasks.

    if (Firebase.ready() && (millis() - dataMillis > 3000 || dataMillis == 0))
    {
        dataMillis = millis();

        if (!taskCompleted) {
          
          String documentPath = "info/last_order";
          Serial.println("Getting last order num into DB.");

          if (Firebase.Firestore.getDocument(&fbdo, FIREBASE_PROJECT_ID, "", documentPath.c_str(), ""))
          {
            Serial.printf("ok\n%s\n\n", fbdo.payload().c_str());
            JSONVar orders_object = JSON.parse(fbdo.payload().c_str());
          
            if (JSON.typeof(orders_object) == "undefined") {
              Serial.println("Parsing input failed!");
              return;
            }
              JSONVar field_object = JSON.parse(JSON.stringify(orders_object["fields"]));
              JSONVar ingredient_object = JSON.parse(JSON.stringify(field_object["last_prepared_order"]));
              sscanf(((const char *)ingredient_object["integerValue"]), "%d", &current_order_num);

              Serial.print("Last prepared order num is:");
              Serial.println(current_order_num);
              taskCompleted = true;
          } else {
            Serial.println(fbdo.errorReason());
          }
        }

        char ordersPath[50] = {0};
        sprintf(ordersPath, "Orders/%d", current_order_num);
        if (Firebase.Firestore.getDocument(&fbdo, FIREBASE_PROJECT_ID, "", ordersPath, ""))
        {
            //Serial.printf("ok\n%s\n\n", fbdo.payload().c_str());
            JSONVar orders_object = JSON.parse(fbdo.payload().c_str());
            if (JSON.typeof(orders_object) == "undefined") {
              Serial.println("Parsing input failed!");
              return;
            }
            JSONVar field_object = JSON.parse(JSON.stringify(orders_object["fields"]));

            //Serial.println("field_object:");
            //Serial.println(field_object);

            JSONVar ingredient_object = JSON.parse(JSON.stringify(field_object["ingredient"]));

            //Serial.println("ingredient_object:");
            //Serial.println(ingredient_object);
            //Serial.println("ingredient_val:");
            //Serial.println((const char *)ingredient_object["stringValue"]);
            current_order_num++;
            uint8_t found = 0;
            for (uint8_t inx = 0; inx < COCKTAIL_NUM; inx ++) {
              if (strstr((const char *)ingredient_object["stringValue"], cocktails[inx]) != NULL) {
                  digitalWrite(motorPin1, HIGH);
                  digitalWrite(motorPin2, HIGH);
                  digitalWrite(motorPin3, HIGH);
                  digitalWrite(motorPin4, HIGH);
                  if (found) {
                    delay(5000);
                  }
                  Serial.printf("%s - Driving engines\n\n", cocktails[inx]);
                  digitalWrite(motorPin1, engines_mux[inx].motor1);
                  digitalWrite(motorPin2, engines_mux[inx].motor2);
                  digitalWrite(motorPin3, engines_mux[inx].motor3);
                  digitalWrite(motorPin4, engines_mux[inx].motor4);

                  // Display cocktail name on the screen
                  lcd.clear();
                  lcd.setCursor(0,0);
                  lcd.print("Preparing:");
                  lcd.setCursor(0,1);
                  lcd.print(cocktails[inx]);
                  delay(20000);
                  found = 1;
                }
            }
                              
            FirebaseJson content;
            content.set("fields/last_prepared_order/integerValue", current_order_num);
            // info is the collection id, countries is the document id in collection info.
            String documentPath = "info/last_order";
            Serial.println("Updating last order num into DB.");
            Firebase.Firestore.deleteDocument(&fbdo, FIREBASE_PROJECT_ID, "", documentPath.c_str(), "", ""); //Delete last ordered counter from DB
            if (Firebase.Firestore.createDocument(&fbdo, FIREBASE_PROJECT_ID, "" /* databaseId can be (default) or empty */, documentPath.c_str(), content.raw()))
                Serial.printf("ok\n%s\n\n", fbdo.payload().c_str());
            else
                Serial.println(fbdo.errorReason());
        } else {
            digitalWrite(motorPin1, HIGH);
            digitalWrite(motorPin2, HIGH);
            digitalWrite(motorPin3, HIGH);
            digitalWrite(motorPin4, HIGH);
            Serial.println(fbdo.errorReason());

            // Display waiting status on the screen
            lcd.clear();
            lcd.setCursor(0,0);
            lcd.print("Cocktail Maker");
            lcd.setCursor(0,1);
            lcd.print("Ready!");
        }
        
    }

}
