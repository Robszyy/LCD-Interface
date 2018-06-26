#include <LiquidCrystal.h>
#include "animations.h"

LiquidCrystal lcdd(12, 11, 5, 4, 3, 2);

void setup() {
  lcdd.begin(16, 2);
  Serial.begin(9600);
  Serial.setTimeout(50);
  setupChar();
  animationPoints();
  animationBonjour();
  animationPoints();
  animationBooting();
  animationBooting();
  animationBooting();

  lcdd.clear();
}

void loop() {
  String texte = Serial.readString();
  String line1 = texte.substring(0, 16);
  String line2 = texte.substring(16, 32);
  
  if(texte.length() > 0) {
    lcdd.setCursor(0, 0);
    lcdd.print("                ");
    lcdd.setCursor(0, 1);
    lcdd.print("                ");
         
    lcdd.setCursor(0, 0);
    lcdd.print(line1);
    lcdd.setCursor(0, 1);
    lcdd.print(line2);
  }
}






