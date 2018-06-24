/* Fichier animations.cpp */
#include "animations.h"
#include "arduino.h"
#include <LiquidCrystal.h>

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

byte coeur[8]={
  B00000,
  B01010,
  B11111,
  B11111,
  B11111,
  B01110,
  B00100,
  B00000
};

byte etoile[8]={
  B00000,
  B00100,
  B01110,
  B11111,
  B11111,
  B01110,
  B00100,
  B00000
};

void setupChar(){
   lcd.createChar(4, etoile);
   lcd.createChar(3, coeur);
}

void animationPoints(){
 //On informe au fichier que c'est un lcd 16*2
 lcd.begin(16, 2);
 //Animation
 lcd.clear();

 //Factorisation
 /*
 int j = 4;
 int h = 1;
 int k = 0;
 bool inverse = false;
 bool inverseNorm = false;
 
 for(int i=0; i<16;i++){	
 	if(j==-1){
 		h=0;
 		inverse=true;
 		j++;
 	}else if (inverse){
 		j++;
 	}else{
 		j--;
 	}
 	lcd.setCursor(j,h);
 	lcd.print(" ");

 	lcd.setCursor(i, k);
 	lcd.print("-");
 	delay(50);

 	if(i==15){
 		 while(j!=15){
 			lcd.setCursor(j,h);
 			lcd.print(" ");
 			j++;
 		}
 		if(j==15){
 			lcd.setCursor(j,h);
 			lcd.print(" ");
 		}
 		k = 1;
 		h = 1;
 		j = 15;
 		for(int z=15;z >= 0;z--){
 			lcd.setCursor(z, k);
 			lcd.print("-");
 			delay(50);

 			if(z <= 11){
 				lcd.setCursor(j,h);
		 		lcd.print(" ");
		 		delay(50);
		 		j--;
 			}
 			if(z == 0){
 				while(j!=0){
 					lcd.setCursor(j,h);
		 			lcd.print(" ");
		 			delay(50);
		 			j--;
 				}
 			}
 		}
 	}

  }
 }
//EndFac
*/

 lcd.setCursor(3, 1);
 lcd.print(" ");
 lcd.setCursor(0, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(2, 1);
 lcd.print(" ");
 lcd.setCursor(1, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(1, 1);
 lcd.print(" ");
 lcd.setCursor(2, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(0, 1);
 lcd.print(" ");
 lcd.setCursor(3, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(0, 0);
 lcd.print(" ");
 lcd.setCursor(4, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(1, 0);
 lcd.print(" ");
 lcd.setCursor(5, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(2, 0);
 lcd.print(" ");
 lcd.setCursor(6, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(3, 0);
 lcd.print(" ");
 lcd.setCursor(7, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(4, 0);
 lcd.print(" ");
 lcd.setCursor(8, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(5, 0);
 lcd.print(" ");
 lcd.setCursor(9, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(6, 0);
 lcd.print(" ");
 lcd.setCursor(10, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(7, 0);
 lcd.print(" ");
 lcd.setCursor(11, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(8, 0);
 lcd.print(" ");
 lcd.setCursor(12, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(9, 0);
 lcd.print(" ");
 lcd.setCursor(13, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(10, 0);
 lcd.print(" ");
 lcd.setCursor(14, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(11, 0);
 lcd.print(" ");
 lcd.setCursor(15, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(12, 0);
 lcd.print(" ");
 lcd.setCursor(15, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(13, 0);
 lcd.print(" ");
 lcd.setCursor(14, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(14, 0);
 lcd.print(" ");
 lcd.setCursor(13, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(15, 0);
 lcd.print(" ");
 lcd.setCursor(12, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(15, 1);
 lcd.print(" ");
 lcd.setCursor(11, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(14, 1);
 lcd.print(" ");
 lcd.setCursor(10, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(13, 1);
 lcd.print(" ");
 lcd.setCursor(9, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(12, 1);
 lcd.print(" ");
 lcd.setCursor(8, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(11, 1);
 lcd.print(" ");
 lcd.setCursor(7, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(10, 1);
 lcd.print(" ");
 lcd.setCursor(6, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(9, 1);
 lcd.print(" ");
 lcd.setCursor(5, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(8, 1);
 lcd.print(" ");
 lcd.setCursor(4, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(7, 1);
 lcd.print(" ");
 lcd.setCursor(3, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(6, 1);
 lcd.print(" ");
 lcd.setCursor(2, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(5, 1);
 lcd.print(" ");
 lcd.setCursor(1, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(4, 1);
 lcd.print(" ");
 lcd.setCursor(0, 1);
 lcd.print("-");
 delay(50);
}



void animationPointsV2(){
 //On informe au fichier que c'est un lcd 16*2
 lcd.begin(16, 2);

 lcd.clear();

 //Animation
 lcd.setCursor(3, 0);
 lcd.print(" ");
 lcd.setCursor(0, 0);
 lcd.print("-");
 delay(50);
 	
 lcd.setCursor(2, 1);
 lcd.print(" ");
 lcd.setCursor(1, 1);
 lcd.print("-");
 delay(50);
 
 lcd.setCursor(1, 0);
 lcd.print(" ");
 lcd.setCursor(2, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(0, 1);
 lcd.print(" ");
 lcd.setCursor(3, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(0, 0);
 lcd.print(" ");
 lcd.setCursor(4, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(1, 1);
 lcd.print(" ");
 lcd.setCursor(5, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(2, 0);
 lcd.print(" ");
 lcd.setCursor(6, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(3, 1);
 lcd.print(" ");
 lcd.setCursor(7, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(4, 0);
 lcd.print(" ");
 lcd.setCursor(8, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(5, 1);
 lcd.print(" ");
 lcd.setCursor(9, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(6, 0);
 lcd.print(" ");
 lcd.setCursor(10, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(7, 1);
 lcd.print(" ");
 lcd.setCursor(11, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(8, 0);
 lcd.print(" ");
 lcd.setCursor(12, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(9, 1);
 lcd.print(" ");
 lcd.setCursor(13, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(10, 0);
 lcd.print(" ");
 lcd.setCursor(14, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(11, 1);
 lcd.print(" ");
 lcd.setCursor(15, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(12, 0);
 lcd.print(" ");
 lcd.setCursor(15, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(13, 1);
 lcd.print(" ");
 lcd.setCursor(14, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(14, 0);
 lcd.print(" ");
 lcd.setCursor(13, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(15, 1);
 lcd.print(" ");
 lcd.setCursor(12, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(15, 0);
 lcd.print(" ");
 lcd.setCursor(11, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(14, 1);
 lcd.print(" ");
 lcd.setCursor(10, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(13, 0);
 lcd.print(" ");
 lcd.setCursor(9, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(12, 1);
 lcd.print(" ");
 lcd.setCursor(8, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(11, 0);
 lcd.print(" ");
 lcd.setCursor(7, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(10, 1);
 lcd.print(" ");
 lcd.setCursor(6, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(9, 0);
 lcd.print(" ");
 lcd.setCursor(5, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(8, 1);
 lcd.print(" ");
 lcd.setCursor(4, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(7, 0);
 lcd.print(" ");
 lcd.setCursor(3, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(6, 1);
 lcd.print(" ");
 lcd.setCursor(2, 1);
 lcd.print("-");
 delay(50);

 lcd.setCursor(5, 0);
 lcd.print(" ");
 lcd.setCursor(1, 0);
 lcd.print("-");
 delay(50);

 lcd.setCursor(4, 1);
 lcd.print(" ");
 lcd.setCursor(0, 1);
 lcd.print("-");
 delay(50);
 //End
}

void animationBonjour(){
 //Animation
 lcd.clear();

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
 //End
}

void animationLCDI(){
 //Animation
 lcd.clear();

 lcd.setCursor(4, 0);
 lcd.print("LCD User");
 delay(500);
 lcd.setCursor(1, 1);
 lcd.print("Interface V1.5");
 delay(500);
 lcd.setCursor(0, 0);
 lcd.print("                ");
 delay(500);
 lcd.setCursor(0, 1);
 lcd.print("                ");
 delay(500);
 lcd.setCursor(4, 0);
 lcd.print("LCD User");
 lcd.setCursor(1, 1);
 lcd.print("Interface V1.5");
 delay(500);
 lcd.setCursor(0, 0);
 lcd.print("                ");
 lcd.setCursor(0, 1);
 lcd.print("                ");
 delay(500);
 lcd.setCursor(4, 0);
 lcd.print("LCD User");
 lcd.setCursor(1, 1);
 lcd.print("Interface V1.5");
 delay(500);
 lcd.setCursor(0, 0);
 lcd.print("                ");
 lcd.setCursor(0, 1);
 lcd.print("                ");
 delay(500);
 lcd.setCursor(4, 0);
 lcd.print("LCD User");
 lcd.setCursor(1, 1);
 lcd.print("Interface V1.5");
 delay(2000);
 animationLigne(0, false, true);
 animationLigne(0, true, true);
 animationLigne(0, false , true);
 animationLigne(1, false, false);
 animationLigne(1, true, false);
 animationLigne(1, false , false);
 //End
}

void animationLigne(int j, bool b, bool t){
 //Animation
 if(b){
 	if(t){
	 for(int i=0; i < 16; i++){
	 	lcd.setCursor(i, j);
	 	lcd.print("-");
	 	delay(50);
	 }
	}else{
	 for(int i=15; i >= 0; i--){
	 	lcd.setCursor(i, j);
	 	lcd.print("-");
	 	delay(50);
	 }
	}
 }else{
 	if(t){
		for(int i=0; i < 16; i++){
		 	lcd.setCursor(i, j);
		 	lcd.print(" ");
		 	delay(50);
	   	}
	}else{
		for(int i=15; i >= 0; i--){
	 	lcd.setCursor(i, j);
	 	lcd.print(" ");
	 	delay(50);
	 }
	}
 }
}

void animationLove(){
	//On informe au fichier que c'est un lcd 16*2
 lcd.begin(16, 2);

 lcd.clear();

 //Animation
 lcd.setCursor(3, 0);
 lcd.print(" ");
 lcd.setCursor(0, 0);
 lcd.write(3);
 delay(150);
 	
 lcd.setCursor(2, 1);
 lcd.print(" ");
 lcd.setCursor(1, 1);
 lcd.write(3);
 delay(150);
 
 lcd.setCursor(1, 0);
 lcd.print(" ");
 lcd.setCursor(2, 0);
 lcd.write(3);
 delay(150);

 lcd.setCursor(0, 1);
 lcd.print(" ");
 lcd.setCursor(3, 1);
 lcd.write(3);
 delay(150);

 lcd.setCursor(0, 0);
 lcd.print(" ");
 lcd.setCursor(4, 0);
 lcd.write(3);
 delay(150);

 lcd.setCursor(1, 1);
 lcd.print(" ");
 lcd.setCursor(5, 1);
 lcd.write(3);
 delay(150);

 lcd.setCursor(2, 0);
 lcd.print(" ");
 lcd.setCursor(6, 0);
 lcd.write(3);
 delay(150);

 lcd.setCursor(3, 1);
 lcd.print(" ");
 lcd.setCursor(7, 1);
 lcd.write(3);
 delay(150);

 lcd.setCursor(4, 0);
 lcd.print(" ");
 lcd.setCursor(8, 0);
 lcd.write(3);
 delay(150);

 lcd.setCursor(5, 1);
 lcd.print(" ");
 lcd.setCursor(9, 1);
 lcd.write(3);
 delay(150);

 lcd.setCursor(6, 0);
 lcd.print(" ");
 lcd.setCursor(10, 0);
 lcd.write(3);
 delay(150);

 lcd.setCursor(7, 1);
 lcd.print(" ");
 lcd.setCursor(11, 1);
 lcd.write(3);
 delay(150);

 lcd.setCursor(8, 0);
 lcd.print(" ");
 lcd.setCursor(12, 0);
 lcd.write(3);
 delay(150);

 lcd.setCursor(9, 1);
 lcd.print(" ");
 lcd.setCursor(13, 1);
 lcd.write(3);
 delay(150);

 lcd.setCursor(10, 0);
 lcd.print(" ");
 lcd.setCursor(14, 0);
 lcd.write(3);
 delay(150);

 lcd.setCursor(11, 1);
 lcd.print(" ");
 lcd.setCursor(15, 1);
 lcd.write(3);
 delay(150);

 lcd.setCursor(12, 0);
 lcd.print(" ");
 delay(150);

 lcd.setCursor(13, 1);
 lcd.print(" ");
 delay(150);

 lcd.setCursor(14, 0);
 lcd.print(" ");
 delay(150);

 lcd.setCursor(15, 1);
 lcd.print(" ");
 delay(150);
}

void animationSendingLove(){
  //Animation
  lcd.clear();
  for(int i = 0; i < 2; i++){
	  lcd.setCursor(1, 0);
	  lcd.print("Sending Love   ");
	  delay(250);

	  lcd.setCursor(1, 0);
	  lcd.print("Sending Love.");
	  delay(250);

	  lcd.setCursor(1, 0);
	  lcd.print("Sending Love..");
	  delay(250);

	  lcd.setCursor(1, 0);
	  lcd.print("Sending Love...");
	  delay(250);
   }
}

void animationEtoile(){
  //Animation
  lcd.begin(16,2);

  lcd.clear();

  lcd.setCursor(0,0);
  lcd.write(4);
  delay(50);

  lcd.setCursor(15,0);
  lcd.write(4);
  delay(50);

  lcd.setCursor(15,1);
  lcd.write(4);
  delay(50);

  lcd.setCursor(0,1);
  lcd.write(4);
  delay(50);
  for(int i = 0; i < 6; i++){
  	  lcd.setCursor(8,0);
	  lcd.print(" ");
	  lcd.setCursor(7,0);
	  lcd.write(4);
	  delay(150);

	  lcd.setCursor(8,1);
	  lcd.print(" ");
	  lcd.setCursor(8,0);
	  lcd.write(4);
	  delay(150);

	  lcd.setCursor(7,1);
	  lcd.print(" ");
	  lcd.setCursor(8,1);
	  lcd.write(4);
	  delay(150);

	  lcd.setCursor(7,0);
	  lcd.print(" ");
	  lcd.setCursor(7,1);
	  lcd.write(4);
	  delay(150);
  }
}
