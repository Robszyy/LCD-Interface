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
	
	public AnimationLCD(int slot, int delay, CarreLCD[] tab) {
		this.place = slot;
		this.temps = delay;
		this.caracteres = tab;
	}
	
	/**
	 * Methode sendAnimation
	 * @param output
	 * 				la sortie pour print
	 * Permet d'envoyer une animation
	 */
	
	public void sendAnimation(SerialPort port) {
		Thread thread = new Thread(){
			@Override 
			public void run() {
				try {
					Thread.sleep(100);
				}catch(Exception e){
					
				}
				
				PrintWriter output = new PrintWriter(port.getOutputStream());
				
				for(int i = 0; i < caracteres.length; i++) {
					output.print(caracteres[i]);
					output.flush();
					System.out.println("ok");
					
					try {
						Thread.sleep(temps);
					}catch(Exception e){
						
					}
				}
			}
		};
		thread.start();
	}
}
