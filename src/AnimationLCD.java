import java.io.PrintWriter;

import com.fazecast.jSerialComm.SerialPort;

/**
 * 
 */

/**
 * @author Robin_prgn
 *
 */
public class AnimationLCD {
	private int place;
	private int temps;
	private CarreLCD[] caracteres;
	
	/**
	 * Constructeur
	 * @param slot
	 * 				le numéro du slot
	 * @param dalay
	 * 				le temps de delay
	 * @param tab
	 * 				le tableau de caractere
	 */
	
	public AnimationLCD(int slot, int delay) {
		this.place = slot;
		this.temps = delay;
		//this.caracteres = tab;
	}
	
	public int getSlot() {
		return this.place;
	}
	
	public int getDelay() {
		return this.temps;
	}
	
	public CarreLCD[] getTabChar() {
		return this.caracteres;
	}
}
