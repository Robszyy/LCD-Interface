import java.io.PrintWriter;
import java.util.Comparator;

import com.fazecast.jSerialComm.SerialPort;

/**
 * 
 */

/**
 * @author Robin_prgn
 *
 */
public class AnimationLCD implements Comparator<AnimationLCD>, Comparable<AnimationLCD>{
	private int place;
	private int temps;
	
	/**
	 * Constructeur
	 * @param slot
	 * 				le numéro du slot
	 * @param dalay
	 * 				le temps de delay
	 */
	
	public AnimationLCD(int slot, int delay) {
		this.place = slot;
		this.temps = delay;
	}
	
	public int getSlot() {
		return this.place;
	}
	
	public int getDelay() {
		return this.temps;
	}
	
	public void setDelay(int delay) {
		this.temps = delay;
	}

	@Override
	public int compareTo(AnimationLCD o) {
		return new Integer (this.place).compareTo(new Integer(o.getSlot()));
	}

	@Override
	public int compare(AnimationLCD o1, AnimationLCD o2) {
		return new Integer (o1.getSlot()).compareTo(new Integer(o2.getSlot()));
	}
}
