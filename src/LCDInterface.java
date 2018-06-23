import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
	private static boolean enregistrer = false;
	private static boolean retour = false;
	
	private static JPanel jp;
	private static JTextField jtf;
	private static JButton connectButton;
	private static JLabel erreurPort;
	private static JCheckBoxMenuItem save;

	private static final URL url = System.class.getResource("/images/icon.png");
	private static final Image image = Toolkit.getDefaultToolkit().getImage(url);
	private static final PopupMenu popup = new PopupMenu();
    private static final TrayIcon trayIcon = new TrayIcon(image, "LCD User Interface", popup);
    private static final SystemTray tray = SystemTray.getSystemTray();
	
	/**
	 * Constructeur
	 */
	
	public LCDInterface() {
	}
	
	/**
	 * Methode checkPort
	 * @param sp
	 * 			le port a v�rifier
	 * @return
	 * 			boolean permettant de savoir si le port est correct
	 */
	public static boolean checkPort(SerialPort sp) {
		return true;
	}
	
	/**
	 * Methode getCPUusage
	 * @return
	 * 			le pourcentage d'usage du CPU
	 */
	
	public static int getCPUusage() {
		OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		double load =  operatingSystemMXBean.getSystemCpuLoad() * 100.0;
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
	 * 				arguments param�tres
	 */

	public static void main(String[] args) {
		
		//On cr�e la frame qui va acceuillir les JPanel
		JFrame frame = new JFrame();
		frame.setTitle("LCD User Interface");
		frame.setSize(500, 200);
		frame.setLayout(new BorderLayout());
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		//Menu d�roulant
		JComboBox<String> ports = new JComboBox<String>();
		//On met le menu en version "gris�e"
		ports.setEnabled(false);
		//On cr�e le bouton connect
		connectButton = new JButton("Connect");
		//On met le menu en version "gris�e"
		connectButton.setEnabled(false);
		//On cr�e le panel du bas qui va acceuillir le menu d�roulant et le bouton
		JPanel bas = new JPanel();
		bas.add(ports);
		bas.add(connectButton);
		
		//On cr�e un JPanel qui va contenir un texte avec un jtextfield permettant d'envoyer un message via le port
		jp = new JPanel();
		JLabel text = new JLabel();
		text.setText("Enter your text : ");
		text.setVisible(false);
		
		jtf = new JTextField();
		jtf.setColumns(20);
		jtf.setVisible(false);
		
		//JLabel qui va apparaitre en cas d'erreur
		erreurPort = new JLabel();
		erreurPort.setText("impossible to connect to this port, please retry with another port");
		erreurPort.setVisible(false);
		bas.add(erreurPort);
		
		JLabel version = new JLabel();
		version.setText("LCD User Interface V1.4");
		version.setVisible(true);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Options");
		save = new JCheckBoxMenuItem ("Enregistrer");
		menuBar.add(menu);
		menu.add(save);
		frame.setJMenuBar(menuBar);
		
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enregistrer = !enregistrer;
			}
		});
		

		
		jp.add(text);
		jp.add(jtf);
		
		JPanel milieu = new JPanel();
		milieu.setLayout(new BorderLayout());
		milieu.add(bas,BorderLayout.NORTH);
		milieu.add(jp,BorderLayout.CENTER);
		milieu.add(version,BorderLayout.SOUTH);
		
		//On cr�e le JPanel du haut qui va contenir les JRadioButton
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
				enregistrer = false;
				retour = true;
				save.setSelected(false);
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
				enregistrer = false;
				retour = true;
				save.setSelected(false);
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
				enregistrer = false;
				retour = true;
				save.setSelected(false);
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
				enregistrer = false;
				retour = true;
				save.setSelected(false);
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
		frame.add(milieu, BorderLayout.CENTER);
		
		// On ajoute dans le JComboBox les ports de communication
		SerialPort[] portNames = SerialPort.getCommPorts();
		for(int i = 0; i < portNames.length; i++)
			ports.addItem(portNames[i].getSystemPortName());
		
		// On ajoute le listener sur le bouton qui va cr�er les �changes
		connectButton.addActionListener(new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent arg0) {
				if(connectButton.getText().equals("Connect")) {
					portChoisi = SerialPort.getCommPort(ports.getSelectedItem().toString());
					portChoisi.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					if(date) {
						// On prend le port choisi sur le JComboBox
						if(portChoisi.openPort()) {
							connectButton.setText("Disconnect");
							ports.setEnabled(false);
								
							// On cr�e un nouveau Thread qui va envoyer les informations
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
										if(enregistrer) {
											output.print("  ");
											output.flush();
											enregistrer = false;
										}else {
											output.print(new SimpleDateFormat("    HH:mm:ss      dd MMMMMMM yyyy").format(new Date()));
											output.flush();
											if(retour) {
												output.print("yyy");
												output.flush();
												retour = false;
											}
										}
											
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
					portChoisi = SerialPort.getCommPort(ports.getSelectedItem().toString());
					portChoisi.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
						if(textSender) {
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
				} else if (connectButton.getText().equals("Disconnect")){
					// On se deconnect du port et on change le text du bouton pour refaire la manipulation
					portChoisi.closePort();
					ports.setEnabled(true);
					connectButton.setText("Connect");
				}
			}
		});
		
		frame.setVisible(true);
		frame.setIconImage(image);
		
		//On ajoute un listener sur la fermeture de la frame
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(save.isSelected()) {
					frame.setVisible(false);
				}else {
					System.exit(1);
				}
			}
		});
		
		//MenuItem Exit
	    MenuItem exitItem = new MenuItem("Exit");
	    exitItem.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            System.exit(1);
	        }
	    });
	    
	  //MenuItem Open
	    MenuItem open = new MenuItem("Open");
	    open.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            frame.setVisible(true);
	        }
	    });
	    
	    popup.add(open);
	    popup.addSeparator();
	    popup.add(exitItem);
	    trayIcon.setPopupMenu(popup);

	    try {
	        tray.add(trayIcon);
	    } catch (AWTException e) {
	        System.out.println("Impossible d'ajouter le TrayIcon !");
	    }
	}
}
