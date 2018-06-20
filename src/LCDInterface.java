import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import com.fazecast.jSerialComm.SerialPort;
import com.sun.management.OperatingSystemMXBean;


public class LCDInterface {
	private static SerialPort portChoisi;
	
	private static boolean date = false;
	private static boolean textSender = false;
	private static boolean cpuinfos = false;
	private static boolean animations = false;
	
	private static JPanel jp;
	private static JTextField jtf;
	private static JButton connectButton;
	private static JLabel erreurPort;
	
	private static boolean res = false;
	
	/**
	 * Constructeur
	 */
	
	public LCDInterface() {
	}
	
	/**
	 * Methode checkPort
	 * @param sp
	 * 			le port a vérifier
	 * @return
	 * 			boolean permettant de savoir si le port est correct
	 */
	public static boolean checkPort(SerialPort sp) {
		if(sp.getNumDataBits() == 4){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Methode getCPUusage
	 * @return
	 * 			le pourcentage d'usage du CPU
	 */
	
	public static int getCPUusage() {
		OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		double load = load =  operatingSystemMXBean.getSystemCpuLoad() * 100.0;
		return (int) load ;
	}
	
	/**
	 * Methode getRAMusage
	 * @return
	 * 			le pourcentage d'usage de la RAM
	 */
	
	public static int getRAMusage() {
		double memFree, memMax, memUsageEnGo, memUsageEnPercent;
		OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		
		memFree = (operatingSystemMXBean.getFreePhysicalMemorySize() / 1000000000) - 1;
		memMax = (operatingSystemMXBean.getTotalPhysicalMemorySize() / 1000000000) - 1;
		memUsageEnGo = memMax - memFree;
		memUsageEnPercent = (memUsageEnGo / memMax) * 100;
		
		return (int) memUsageEnPercent;	
	}
	
	/**
	 * Methode main
	 * @param args
	 * 				arguments paramètres
	 */

	public static void main(String[] args) {
		
		//On crée la frame qui va acceuillir les JPanel
		JFrame frame = new JFrame();
		frame.setTitle("Arduino LCD Interface");
		frame.setSize(500, 200);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Menu déroulant
		JComboBox<String> ports = new JComboBox<String>();
		//On met le menu en version "grisée"
		ports.setEnabled(false);
		//On crée le bouton connect
		connectButton = new JButton("Connect");
		//On met le menu en version "grisée"
		connectButton.setEnabled(false);
		//On crée le panel du bas qui va acceuillir le menu déroulant et le bouton
		JPanel bas = new JPanel();
		bas.add(ports);
		bas.add(connectButton);
		
		//On crée un JPanel qui va contenir un texte avec un jtextfield permettant d'envoyer un message via le port
		jp = new JPanel();
		JLabel text = new JLabel();
		text.setText("Enter your text");
		text.setVisible(false);
		
		jtf = new JTextField();
		jtf.setColumns(20);
		jtf.setVisible(false);
		
		//JLabel qui va apparaitre en cas d'erreur
		erreurPort = new JLabel();
		erreurPort.setText("impossible to connect to this port, please retry with another port");
		erreurPort.setVisible(false);
		bas.add(erreurPort);
		
		jp.add(text);
		jp.add(jtf);
		
		//On crée le JPanel du haut qui va contenir les JRadioButton
		JPanel haut = new JPanel();
		JRadioButton jrb1 = new JRadioButton("Date & Time");
		jrb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(portChoisi != null)
					portChoisi.closePort();
				
				ports.setEnabled(true);
				connectButton.setEnabled(true);
				
				text.setVisible(false);
				jtf.setVisible(false);
				
				connectButton.setText("Connect");
				date = true;
				cpuinfos = false;
				textSender = false;
				animations = false;
			}
		});
		JRadioButton jrb2 = new JRadioButton("Send text");
		jrb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(portChoisi != null)
					portChoisi.closePort();
				
				ports.setEnabled(true);
				connectButton.setEnabled(true);
				
				text.setVisible(true);
				jtf.setVisible(true);
				
				connectButton.setText("Send text");
				date = false;
				cpuinfos = false;
				textSender = true;
				animations = false;
			}
		});
		JRadioButton jrb3 = new JRadioButton("Animations");
		jrb3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(portChoisi != null)
					portChoisi.closePort();
				
				ports.setEnabled(true);
				connectButton.setEnabled(true);
				
				text.setVisible(false);
				jtf.setVisible(false);
				
				connectButton.setText("Connect");
				date = false;
				cpuinfos = false;
				textSender = false;
				animations = true;
			}
		});
		
		
		JRadioButton jrb4 = new JRadioButton("Hardware Informations");
		jrb4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(portChoisi != null)
					portChoisi.closePort();
				
				ports.setEnabled(true);
				connectButton.setEnabled(true);
				
				text.setVisible(false);
				jtf.setVisible(false);
				
				connectButton.setText("Connect");
				date = false;
				cpuinfos = true;
				textSender = false;
				animations = false;
			}
		});
		ButtonGroup bg = new ButtonGroup();
		bg.add(jrb1);
		bg.add(jrb2);
		bg.add(jrb3);
		bg.add(jrb4);
		
		haut.add(jrb1);
		haut.add(jrb2);
		haut.add(jrb3);
		haut.add(jrb4);
		
		frame.add(haut, BorderLayout.NORTH);
		frame.add(bas, BorderLayout.CENTER);
		frame.add(jp, BorderLayout.SOUTH);
		
		// On ajoute dans le JComboBox les ports de communication
		SerialPort[] portNames = SerialPort.getCommPorts();
		for(int i = 0; i < portNames.length; i++)
			ports.addItem(portNames[i].getSystemPortName());
		
		// On ajoute le listener sur le bouton qui va créer les échanges
		connectButton.addActionListener(new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent arg0) {
				if(connectButton.getText().equals("Connect")) {
					if(date) {
						// On prend le port choisi sur le JComboBox
						portChoisi = SerialPort.getCommPort(ports.getSelectedItem().toString());
						portChoisi.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
						if(portChoisi.openPort()) {
							connectButton.setText("Disconnect");
							ports.setEnabled(false);
								
							// On crée un nouveau Thread qui va envoyer les informations
							Thread thread = new Thread(){
								@Override public void run() {
									// delay pour le rafraichissement
									try {
										Thread.sleep(100); 
									} catch(Exception e) {
											
									}

									// Boucle infini
									PrintWriter output = new PrintWriter(portChoisi.getOutputStream());
										
									while(true) {
										output.print(new SimpleDateFormat("hh:mm:ss a     dd MMMMMMM yyyy").format(new Date()));
										output.flush();
										try {
											Thread.sleep(100); 
										} catch(Exception e) {
												
										}
									}
										
								}
							};
							thread.start();
						}else {
							erreurPort.setVisible(true);
						}
					}else if (cpuinfos) {
						portChoisi = SerialPort.getCommPort(ports.getSelectedItem().toString());
						portChoisi.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
						if(portChoisi.openPort()) {
							connectButton.setText("Disconnect");
							ports.setEnabled(false);
							
							Thread thread = new Thread(){
								@Override public void run() {
									try {
										Thread.sleep(100); 
									} catch(Exception e) {
										
									}
	
									PrintWriter output = new PrintWriter(portChoisi.getOutputStream());
									while(true) {
										//output.print("Processors :    " +Runtime.getRuntime().availableProcessors() +" cores");
										//output.flush();
										
										output.print("CPU usage : " +String.valueOf(getCPUusage()) +"%");
										if (getCPUusage() < 9)
											output.print("  RAM usage : " +String.valueOf(getRAMusage()) +"%");
										else
											output.print(" RAM usage : " +String.valueOf(getRAMusage()) +"%");
										output.flush();
										
										try {
											Thread.sleep(100); 
										} catch(Exception e) {
											
										}
									}
								}
							};
							thread.start();
						}
					}else if (animations) {
						portChoisi = SerialPort.getCommPort(ports.getSelectedItem().toString());
						portChoisi.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
						if(portChoisi.openPort()) {
							connectButton.setText("Disconnect");
							ports.setEnabled(false);
							
							Thread thread = new Thread(){
								@Override public void run() {
									try {
										Thread.sleep(100); 
									} catch(Exception e) {
										
									}
	
									PrintWriter output = new PrintWriter(portChoisi.getOutputStream());
									while(true) {
										output.print(" ");
										output.flush();
										
										try {
											Thread.sleep(100); 
										} catch(Exception e) {
											
										}
									}
								}
							};
							thread.start();
						}
					}
				} else if(connectButton.getText().equals("Send text")) {
						if(textSender) {
							portChoisi = SerialPort.getCommPort(ports.getSelectedItem().toString());
							portChoisi.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
							if(portChoisi.openPort()) {
								Thread thread = new Thread(){
									@Override public void run() {
										try {
											Thread.sleep(100); 
										} catch(Exception e) {
											
										}
		
										PrintWriter output = new PrintWriter(portChoisi.getOutputStream());
										
										while(true) {
											output.print(jtf.getText());
											output.flush();
											try {
												Thread.sleep(100); 
											} catch(Exception e) { 
												
											}
										}
									}
								};
								thread.start();
							}
						}
				} else {
					// On se deconnect du port et on change le text du bouton pour refaire la manipulation
					portChoisi.closePort();
					ports.setEnabled(true);
					connectButton.setText("Connect");
				}
			}
		});
		
		frame.setVisible(true);
	}

}
