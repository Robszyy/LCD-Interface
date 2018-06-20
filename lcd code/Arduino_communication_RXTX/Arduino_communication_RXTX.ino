#include <LiquidCrystal.h>
#include "animations.h"

LiquidCrystal lcdd(12, 11, 5, 4, 3, 2);

boolean finBoot = false;
boolean debutWait = false;

byte smiley[8] = {
  B00000,
  B10001,
  B00000,
  B00000,
  B10001,
  B01110,
  B00000,
};

byte monstre[8]={
  B00000,
  B01010,
  B11111,
  B11111,
  B11111,
  B01110,
  B00100,
  B00000
};

void setup() {
  lcdd.begin(16, 2);
  Serial.begin(9600);
  Serial.setTimeout(50);
  lcdd.createChar(1, smiley);
  lcdd.createChar(2, monstre);
}

void loop() {
  String texte = Serial.readString();
  String line1 = texte.substring(0, 16);
  String line2 = texte.substring(16, 32);
  
  if(texte.length() == 0 && !finBoot){
      lcdd.setCursor(0, 0);
      lcdd.print("                ");
      
      lcdd.setCursor(3, 0);
      lcdd.print("Booting");
      delay(500);
      
      lcdd.setCursor(10, 0);
      lcdd.print(".");
      delay(500);
  
      lcdd.setCursor(11, 0);
      lcdd.print(".");
      delay(500);
  
      lcdd.setCursor(12, 0);
      lcdd.print(".");
      delay(500);
      
      lcdd.setCursor(0, 1);
      lcdd.print("                ");
      
  }else if(texte.length() > 0) {
      finBoot = true;
      debutWait = false;

      if(texte.equals(" ")){
        /*
         * Séquence pour l'animation
         */
         animationPoints();
         animationBonjour(); 
         animationPointsV2();
         animationLCDI(); 
         
      }else{
         lcdd.setCursor(0, 0);
         lcdd.print("                ");
         lcdd.setCursor(0, 1);
         lcdd.print("                ");
               
         lcdd.setCursor(0, 0);
         lcdd.print(line1);
         lcdd.setCursor(0, 1);
         lcdd.print(line2);
      }
  }else if (texte.length() == 0 && debutWait && finBoot){
      lcdd.setCursor(0, 0);
      lcdd.print("                ");
      lcdd.setCursor(0, 1);
      lcdd.print("                ");
      
      lcdd.setCursor(3, 0);
      lcdd.print("Waiting");
      delay(500);
      
      lcdd.setCursor(10, 0);
      lcdd.print(".");
      delay(500);
  
      lcdd.setCursor(11, 0);
      lcdd.print(".");
      delay(500);
  
      lcdd.setCursor(12, 0);
      lcdd.print(".");
      delay(500); 
  }
  
  if(texte.length() == 0 && finBoot){
     debutWait = true;
  }else{
     debutWait = false;
  }
}






