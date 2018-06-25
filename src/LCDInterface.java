import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
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
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
	private static boolean finDraw = false;
	private static boolean portChoix = false;
	
	private static JPanel jp;
	private static JTextField jtf;
	private static JButton connectButton;
	private static JLabel erreurPort;
	private static JCheckBoxMenuItem save;
	private static JMenuItem draw;
	private static JMenuItem animate;
	private static JComboBox<String> ports;
	private static JComboBox<String> portsDraw;
	private static JComboBox<String> portsAnimate;
	private static JTextField charToDraw;
	private static CarreLCD[] tab;

	private static final URL url = System.class.getResource("/images/icon.png");
	private static final Image image = Toolkit.getDefaultToolkit().getImage(url);
	private static final PopupMenu popup = new PopupMenu();
    private static final TrayIcon trayIcon = new TrayIcon(image, "LCD User Interface", popup);
    private static final SystemTray tray = SystemTray.getSystemTray();
	
	/**
	 * Constructeur
	 */
	
	public LCDInterface() {}
	
	/**
	 * Methode checkPort
	 * @param sp
	 * 			le port a vérifier
	 * @return
	 * 			boolean permettant de savoir si le port est correct
	 */
	
	public static boolean checkPort(SerialPort sp) {
		if(sp.isOpen())
			return true;
		else
			return false;
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
	 * Methode getCharToDraw
	 * @return
	 * 			le charactere a afficher
	 */
	
	public static JTextField getCharToDraw() {
		return charToDraw;
	}
	
	/**
	 * Methode main
	 * @param args
	 * 				arguments paramètres
	 */

	public static void main(String[] args) {
		
		//On crée la frame qui va acceuillir les JPanel
		JFrame frame = new JFrame();
		frame.setTitle("LCD User Interface");
		frame.setSize(500, 200);
		frame.setLayout(new BorderLayout());
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		//Menu déroulant
		ports = new JComboBox<String>();
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
		version.setText("LCD User Interface V1.6");
		version.setVisible(true);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Options");
		save = new JCheckBoxMenuItem ("Save");
		save.setToolTipText("Save your choice");
		draw = new JMenuItem("Draw [Beta]");
		draw.setToolTipText("Create your own drawing");
		
		animate = new JMenuItem("Animate [Alpha]");
		draw.setToolTipText("Animate your own drawing");
		
		menu.add(animate);
		menu.addSeparator();
		menu.add(draw);
		menu.addSeparator();
		menu.add(save);
		
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		animate.setEnabled(true);
		draw.setEnabled(true);
		
		//Fonctionnalité Animate
		//Listener sur MenuItem
		animate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame animateFrame = new JFrame("Choose your port");
				animateFrame.setSize(300, 100);
				JPanel main = new JPanel();
				JButton animateConnect = new JButton("Connect");
				portsAnimate = new JComboBox<String>();
				
				draw.setEnabled(false);
				animate.setEnabled(false);
				
				SerialPort[] portNamesAnimate = SerialPort.getCommPorts();
				for(int i = 0; i < portNamesAnimate.length; i++) {
					portsAnimate.addItem(portNamesAnimate[i].getSystemPortName());
				}
				//Listener sur JButton
				animateConnect.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						portChoisi = SerialPort.getCommPort(portsAnimate.getSelectedItem().toString());
						portChoisi.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
						
						if(portChoisi.openPort() && checkPort(portChoisi)) {
							animateConnect.setEnabled(false);
							portsAnimate.setEnabled(false);
							
							//Nouvelle frame contenant les objets pour l'animation
							JFrame animateFrameFinal = new JFrame("Aniamte [Alpha]");
							animateFrameFinal.setSize(600, 400);
							animateFrameFinal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
							animateFrameFinal.setVisible(true);
							
							//JComboBox qui contient le nombres de slots
							String[] nbSlots = {"1","2","3","4","5","6","7","8","9","10"};
							JComboBox slot = new JComboBox(nbSlots);
							
							//JTextField pour entrer son charactere
							JTextField jtfChar = new JTextField();
							jtfChar.setColumns(2);
							
							//JTextField pour entrer son delay
							JTextField jtfDelay = new JTextField();
							jtfDelay.setColumns(4);
							
							//JButton pour ajouter au slot 
							JButton jbAdd = new JButton("Add");
							//JTextField pour lancer l'animation
							JButton jbAnimate = new JButton("Animate");
							
							//JLabels explicatifs
							JLabel textSlot = new JLabel("Slot : ");
							JLabel textChar = new JLabel("Character : ");
							JLabel textDelay = new JLabel("Delay : ");
							
							//Panel pour dessiner ses characteres
							DrawLCD drawlcd = new DrawLCD();
							drawlcd.setPreferredSize(new Dimension(600,150));
							
							//JPanel main
							JPanel main = new JPanel();
							main.setLayout(new FlowLayout());
							
							//JPanel pour chacune des fonctionnalités
							JPanel panelSlot = new JPanel();
							JPanel panelChar = new JPanel();
							JPanel panelDelay = new JPanel();
							
							//JPanel middle
							JPanel middle = new JPanel();
							middle.setLayout(new BorderLayout());
							
							//JPanel middleTotal
							JPanel middleTotal = new JPanel();
							middleTotal.setLayout(new BorderLayout());
							
							//JPanel bottom
							JPanel bottom = new JPanel();
							
							//Ajouts aux panels
							panelSlot.add(textSlot);
							panelSlot.add(slot);
							
							panelChar.add(textChar);
							panelChar.add(jtfChar);
							
							panelDelay.add(textDelay);
							panelDelay.add(jtfDelay);
							
							middle.add(panelSlot,BorderLayout.NORTH);
							middle.add(panelChar,BorderLayout.CENTER);
							middle.add(panelDelay,BorderLayout.SOUTH);
							
							bottom.add(jbAdd);
							bottom.add(jbAnimate);
							
							middleTotal.add(middle,BorderLayout.CENTER);
							middleTotal.add(bottom,BorderLayout.SOUTH);
							
							main.add(drawlcd);
							main.add(middleTotal);
							
							animateFrameFinal.add(main);
							
							ArrayList<AnimationLCD> tabAnimations = new ArrayList<AnimationLCD>();
							charToDraw = jtfChar;
							
							//Listener du bouton add
							jbAdd.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									if(jtfChar.getText().length() == 1 && jtfDelay.getText().length() <= 5) {
										int delayChoisi = 100;
										int slotChoisi = slot.getSelectedIndex()+1;
										
										try {
											delayChoisi = Integer.parseInt(jtfDelay.getText());
										}catch(NumberFormatException nfe) {
											System.out.println("Ce n'est pas un chiffre");
										}
										
										tabAnimations.add(new AnimationLCD(slotChoisi, delayChoisi, drawlcd.getTab()));
										slot.removeItemAt(slot.getSelectedIndex());
									}
								}	
							});
							
							//Listener du bouton animate
							jbAnimate.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									System.out.println("1");
									if(jbAnimate.getText().equals("Animate")) {
										System.out.println("2");
										jbAnimate.setText("Stop Animation");
										//while(true) {
											System.out.println("3");
											if(!tabAnimations.isEmpty()) {
												for(int i = 0; i < tabAnimations.size(); i++) {
													tabAnimations.get(i).sendAnimation(portChoisi);
												}
											}else {
												System.out.println("impossible, liste vide");
											}
										//}
									}else if(jbAnimate.getText().equals("Stop Animation")) {
										jbAnimate.setText("Animate");
									}
								}
								
							});
													
							//Listener window
							animateFrameFinal.addWindowListener(new WindowAdapter() {
								public void windowClosing(WindowEvent e) {
									animateFrameFinal.setVisible(false);
									animateFrameFinal.dispose();
									
									if(portChoisi != null) {
										portChoisi.closePort();
										animateConnect.setEnabled(true);
										portsAnimate.setEnabled(true);
									}
								}
							});
						}
					}
				});
				
				main.add(portsAnimate);
				main.add(animateConnect);
				
				animateFrame.add(main);
				animateFrame.setVisible(true);
			}
		});
		//Fin Fonctionnalité Animate
		
		//Fonctionnalité Draw
		draw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				draw.setEnabled(false);
				DrawLCD drawlcd = new DrawLCD();
				drawlcd.setPreferredSize(new Dimension(600,150));
				
				JFrame frameDraw = new JFrame("Draw [Beta]");
				frameDraw.setSize(600, 350);
				frameDraw.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				
				JPanel main = new JPanel();
				main.setLayout(new FlowLayout());
				JPanel milieu = new JPanel();
				JPanel milieuTT = new JPanel();
				JPanel basError = new JPanel();
				basError.setLayout(new BorderLayout());
				milieuTT.setLayout(new BorderLayout());
				JPanel bas = new JPanel();
				JButton drawButton = new JButton("Draw");
				JButton erase = new JButton("Erase all");
				erase.setPreferredSize(new Dimension(100,35));
				JLabel textDraw = new JLabel("Enter a character : ");
				JLabel errorLength = new JLabel();
				errorLength.setForeground(Color.red);
				errorLength.setVisible(false);
				charToDraw = new JTextField();
				charToDraw.setColumns(2);
				
				portsDraw = new JComboBox<String>();
				SerialPort[] portNamesDraw = SerialPort.getCommPorts();
				for(int i = 0; i < portNamesDraw.length; i++) {
					portsDraw.addItem(portNamesDraw[i].getSystemPortName());
				}

				drawButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tab = drawlcd.getTab();
						finDraw = false;
						if(!portChoix) {
							portChoisi = SerialPort.getCommPort(portsDraw.getSelectedItem().toString());
							portChoisi.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
							portChoix = true;
						}
						
						if(portChoisi.openPort() && checkPort(portChoisi)) {
							Thread thread = new Thread(){
								@Override public void run() {
									try {
										Thread.sleep(100); 
									} catch(Exception e) {
										
									}
	
									PrintWriter output = new PrintWriter(portChoisi.getOutputStream());
									while(!finDraw) {
										for(int i = 0; i < tab.length; i++) {
											if(charToDraw.getText().length() == 1) {
												output.print(tab[i]);
												output.flush();
												errorLength.setVisible(false);
											}else if (charToDraw.getText().length() > 1){
												errorLength.setText("ERROR, TOO MUCH ARGUMENTS");
												errorLength.setVisible(true);
											}else if (charToDraw.getText().length() < 1){
												errorLength.setText("ERROR, NO ARGUMENT");
												errorLength.setVisible(true);
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
						}
					}
				});
				
				erase.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tab = drawlcd.getTab();
						finDraw = true;
						
						if(!portChoix) {
							portChoisi = SerialPort.getCommPort(portsDraw.getSelectedItem().toString());
							portChoisi.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
							portChoix = true;
						}	
						
						for(int i = 0; i < tab.length; i++) {
							tab[i].setEstClique(false);
						}
						
						drawlcd.repaint();
						
						if(portChoisi.openPort() && checkPort(portChoisi)) {
							Thread thread = new Thread(){
								@Override public void run() {
									
									PrintWriter output = new PrintWriter(portChoisi.getOutputStream());
									while(finDraw) {
										output.print("                                ");
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
					
				});
				
				portsDraw.setEnabled(true);
				
				bas.add(portsDraw);
				bas.add(drawButton);
				
				milieu.add(textDraw);
				milieu.add(charToDraw);
				
				basError.add(erase,BorderLayout.NORTH);
				basError.add(errorLength,BorderLayout.CENTER);
				
				milieuTT.add(milieu,BorderLayout.NORTH);
				milieuTT.add(bas,BorderLayout.CENTER);
				milieuTT.add(basError,BorderLayout.SOUTH);
				
				main.add(drawlcd);
				main.add(milieuTT);
				
				frameDraw.add(main);
				frameDraw.setVisible(true);
				frameDraw.setIconImage(image);
				
				frameDraw.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						draw.setEnabled(true);
						frameDraw.setVisible(false);
						frameDraw.dispose();
						
						if(portChoisi != null)
							portChoisi.closePort();
					}
				});
			}
		});
		//Fin Fonctionnalité Draw
		
		//Fonctionnalité Save
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enregistrer = !enregistrer;
			}
		});
		//Fin Fonctionnalité Save
		
		jp.add(text);
		jp.add(jtf);
		
		JPanel milieu = new JPanel();
		milieu.setLayout(new BorderLayout());
		milieu.add(bas,BorderLayout.NORTH);
		milieu.add(jp,BorderLayout.CENTER);
		milieu.add(version,BorderLayout.SOUTH);
		
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
		for(int i = 0; i < portNames.length; i++) {
			ports.addItem(portNames[i].getSystemPortName());
		}
		// On ajoute le listener sur le bouton qui va créer les échanges
		connectButton.addActionListener(new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent arg0) {
				if(connectButton.getText().equals("Connect")) {
					portChoisi = SerialPort.getCommPort(ports.getSelectedItem().toString());
					portChoisi.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					if(date) {
						// On prend le port choisi sur le JComboBox
						if(portChoisi.openPort() && checkPort(portChoisi)) {
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
