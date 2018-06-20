#include <LiquidCrystal.h>
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

boolean finBoot = false;
boolean debutWait = false;

void animation1();
void animation2();


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
  lcd.begin(16, 2);
  Serial.begin(9600);
  Serial.setTimeout(50);
  lcd.createChar(1, smiley);
  lcd.createChar(2, monstre);
}

void loop() {
  String texte = Serial.readString();
  String line1 = texte.substring(0, 16);
  String line2 = texte.substring(16, 32);
  
  if(texte.length() == 0 && !finBoot){
      lcd.setCursor(0, 0);
      lcd.print("                ");
      
      lcd.setCursor(3, 0);
      lcd.print("Booting");
      delay(500);
      
      lcd.setCursor(10, 0);
      lcd.print(".");
      delay(500);
  
      lcd.setCursor(11, 0);
      lcd.print(".");
      delay(500);
  
      lcd.setCursor(12, 0);
      lcd.print(".");
      delay(500);
      
      lcd.setCursor(0, 1);
      lcd.print("                ");
      
  }else if(texte.length() > 0) {
      finBoot = true;
      debutWait = false;

      if(texte.equals(" ")){
        /*
         * Séquence pour l'animation
         */
         animation1();
         animation2();
         

         
      }else{
         lcd.setCursor(0, 0);
         lcd.print("                ");
         lcd.setCursor(0, 1);
         lcd.print("                ");
               
         lcd.setCursor(0, 0);
         lcd.print(line1);
         lcd.setCursor(0, 1);
         lcd.print(line2);
      }
  }else if (texte.length() == 0 && debutWait && finBoot){
      lcd.setCursor(0, 0);
      lcd.print("                ");
      lcd.setCursor(0, 1);
      lcd.print("                ");
      
      lcd.setCursor(3, 0);
      lcd.print("Waiting");
      delay(500);
      
      lcd.setCursor(10, 0);
      lcd.print(".");
      delay(500);
  
      lcd.setCursor(11, 0);
      lcd.print(".");
      delay(500);
  
      lcd.setCursor(12, 0);
      lcd.print(".");
      delay(500); 
  }
  
  if(texte.length() == 0 && finBoot){
     debutWait = true;
  }else{
     debutWait = false;
  }
}

  void animation1(){
     lcd.setCursor(0, 0);
     lcd.print("                ");
     lcd.setCursor(0, 1);
     lcd.print("                ");

     //Première animation
     lcd.setCursor(4,1);
     lcd.print(" ");
     lcd.setCursor(0, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(3,1);
     lcd.print(" ");
     lcd.setCursor(1, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(2,1);
     lcd.print(" ");
     lcd.setCursor(2, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(1,1);
     lcd.print(" ");
     lcd.setCursor(3, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(0,1);
     lcd.print(" ");
     lcd.setCursor(4, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(0,0);
     lcd.print(" ");
     lcd.setCursor(5, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(1,0);
     lcd.print(" ");
     lcd.setCursor(6, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(2,0);
     lcd.print(" ");
     lcd.setCursor(7, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(3,0);
     lcd.print(" ");
     lcd.setCursor(8, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(4,0);
     lcd.print(" ");
     lcd.setCursor(9, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(5,0);
     lcd.print(" ");
     lcd.setCursor(10, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(6,0);
     lcd.print(" ");
     lcd.setCursor(11, 0);
     lcd.print(".");
     delay(50);
     
     lcd.setCursor(7,0);
     lcd.print(" ");
     lcd.setCursor(12, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(8,0);
     lcd.print(" ");
     lcd.setCursor(13, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(9,0);
     lcd.print(" ");
     lcd.setCursor(14, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(10,0);
     lcd.print(" ");
     lcd.setCursor(15, 0);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(11,0);
     lcd.print(" ");
     lcd.setCursor(15, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(12,0);
     lcd.print(" ");
     lcd.setCursor(14, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(13,0);
     lcd.print(" ");
     lcd.setCursor(13, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(14,0);
     lcd.print(" ");
     lcd.setCursor(12, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(15,0);
     lcd.print(" ");
     lcd.setCursor(11, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(15,1);
     lcd.print(" ");
     lcd.setCursor(10, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(14,1);
     lcd.print(" ");
     lcd.setCursor(9, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(13,1);
     lcd.print(" ");
     lcd.setCursor(8, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(12,1);
     lcd.print(" ");
     lcd.setCursor(7, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(11,1);
     lcd.print(" ");
     lcd.setCursor(6, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(10,1);
     lcd.print(" ");
     lcd.setCursor(5, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(9,1);
     lcd.print(" ");
     lcd.setCursor(4, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(8,1);
     lcd.print(" ");
     lcd.setCursor(3, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(7,1);
     lcd.print(" ");
     lcd.setCursor(2, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(6,1);
     lcd.print(" ");
     lcd.setCursor(1, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(5,1);
     lcd.print(" ");
     lcd.setCursor(1, 1);
     lcd.print(".");
     delay(50);
  
     lcd.setCursor(4,1);
     lcd.print(" ");
     lcd.setCursor(0, 1);
     lcd.print(".");
     delay(50);
  }

  void animation2(){
     lcd.setCursor(0, 0);
     lcd.print("                ");
     lcd.setCursor(0, 1);
     lcd.print("                ");

     lcd.setCursor(4, 0);
     lcd.print("Bonjour!");
     delay(2000);

     lcd.clear();
     delay(50);

     lcd.setCursor(7, 0);
     lcd.write(1);
     lcd.write(2);
     delay(2000);

     lcd.clear();
     delay(50);

  }


