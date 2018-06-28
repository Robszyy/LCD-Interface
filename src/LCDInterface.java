import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
	private static boolean finAnime = false;
	
	private static int nbAnimations = 0;
	private static int iterator = 0;
	
	private static String[] nbSlots;
	
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
	private static DrawLCD animatelcd;
	private static JComboBox slot;
	private static JComboBox comboRep;
	
	private static JRadioButton jrb1 = new JRadioButton("Date & Time");
	private static JRadioButton jrb2 = new JRadioButton("Send text");
	private static JRadioButton jrb3 = new JRadioButton("Animations");
	private static JRadioButton jrb4 = new JRadioButton("Hardware Informations");

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
		version.setText("LCD User Interface V2.0");
		version.setVisible(true);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menuOptions = new JMenu("Options");
		JMenu menuHelp = new JMenu("Help");
		save = new JCheckBoxMenuItem ("Save");
		save.setToolTipText("Save your choice");
		draw = new JMenuItem("Draw");
		draw.setToolTipText("Create your own drawing");
		
		animate = new JMenuItem("Animate");
		animate.setToolTipText("Create your own animation");
		
		JMenuItem reportItem = new JMenuItem("Report bug");
		reportItem.setToolTipText("Report a bug");
		
		menuOptions.add(animate);
		menuOptions.addSeparator();
		menuOptions.add(draw);
		menuOptions.addSeparator();
		menuOptions.add(save);
		menuHelp.add(reportItem);
		
		menuBar.add(menuOptions);
		menuBar.add(menuHelp);
		frame.setJMenuBar(menuBar);
		
		animate.setEnabled(true);
		draw.setEnabled(true);
		
		//Fonctionnalité Report Bug
		reportItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame errorFrame = new JFrame();
				errorFrame.setVisible(false);
				try {
					Desktop desktop = java.awt.Desktop.getDesktop();
					URI reportURL = new URI("https://www.google.com");
					desktop.browse(reportURL);
				} catch (Exception e1) {
					errorFrame.setVisible(true);
					JOptionPane.showMessageDialog(errorFrame, "Error, impossible to report bug, please retry later");
				} 
			}
			
		});
		
		//Fonctionnalité Animate
		//Listener sur MenuItem
		animate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame animateFrame = new JFrame("Choose your port");
				animateFrame.setIconImage(image);
				animateFrame.setSize(300, 100);
				JPanel main = new JPanel();
				JButton animateConnect = new JButton("Connect");
				portsAnimate = new JComboBox<String>();
				
				jrb1.setEnabled(false);
				jrb2.setEnabled(false);
				jrb3.setEnabled(false);
				jrb4.setEnabled(false);
				connectButton.setEnabled(false);
				ports.setEnabled(false);
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
							JFrame animateFrameFinal = new JFrame("Animate");
							animateFrameFinal.setSize(600, 400);
							animateFrameFinal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
							animateFrameFinal.setIconImage(image);
							animateFrameFinal.setVisible(true);
							
							//JComboBox qui contient le nombres de slots
							nbSlots = new String[64];
							for(int i = 0; i < 64; i++) {
								nbSlots[i] = (i+1)+"";
							}
							
							slot = new JComboBox(nbSlots);
							
							//JTextField pour entrer son charactere
							JTextField jtfChar = new JTextField();
							jtfChar.setColumns(2);
							
							//JTextField pour entrer son delay
							JTextField jtfDelay = new JTextField();
							jtfDelay.setColumns(4);
							
							//JButton pour ajouter au slot 
							JButton jbAdd = new JButton("Add");
							//JButton pour lancer l'animation
							JButton jbAnimate = new JButton("Animate");
							//JButton pour effacer tout
							JButton eraseAll = new JButton("Erase all");
							//JButton pour enregistrer l'animation
							JButton jbSave = new JButton("Save");
							//JButton pour charger une animation
							JButton jbLoad = new JButton("Load");
							
							
							//JLabels explicatifs
							JLabel textSlot = new JLabel("Slot : ");
							JLabel textChar = new JLabel("Character : ");
							JLabel textDelay = new JLabel("Delay : ");
							JLabel errorLoad = new JLabel("Error, the file seems to be damaged or wrong");
							errorLoad.setForeground(Color.red);
							errorLoad.setVisible(false);
							
							//Panel pour dessiner ses characteres
							animatelcd = new DrawLCD();
							animatelcd.setPreferredSize(new Dimension(600,150));
							
							//JPanel main
							JPanel main = new JPanel();
							main.setLayout(new FlowLayout());
							
							//JPanel pour chacune des fonctionnalités
							JPanel panelSlot = new JPanel();
							JPanel panelChar = new JPanel();
							JPanel panelButtonErase = new JPanel();
							JPanel panelDelay = new JPanel();
							JPanel buttonsLabel = new JPanel();
							buttonsLabel.setLayout(new BorderLayout());
							JPanel panelButtonUp = new JPanel();
							panelButtonUp.setLayout(new BorderLayout());
							
							//JPanel middle
							JPanel middle = new JPanel();
							middle.setLayout(new BorderLayout());
							
							//JPanel middleTotal
							JPanel middleTotal = new JPanel();
							middleTotal.setLayout(new BorderLayout());
							
							//JPanel bottomSaveLoad
							JPanel bottomSaveLoad = new JPanel();
							//JPanel bottomSaveLoad
							JPanel bottomLast = new JPanel();
							bottomLast.setLayout(new BorderLayout());
							
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
							bottom.add(eraseAll);
							
							bottomSaveLoad.add(jbSave);
							bottomSaveLoad.add(jbLoad);
							
							buttonsLabel.add(bottomSaveLoad,BorderLayout.CENTER);
							buttonsLabel.add(errorLoad,BorderLayout.SOUTH);
							
							bottomLast.add(bottom,BorderLayout.CENTER);
							bottomLast.add(buttonsLabel,BorderLayout.SOUTH);
							
							middleTotal.add(middle,BorderLayout.CENTER);
							middleTotal.add(bottomLast,BorderLayout.SOUTH);
							
							main.add(animatelcd);
							main.add(middleTotal);
							
							animateFrameFinal.add(main);
							
							ArrayList<AnimationLCD> tabAnimations = new ArrayList<AnimationLCD>();
							ArrayList<String> tabMessages = new ArrayList<String>();
							charToDraw = jtfChar;
							
							//Listener du bouton add
							jbAdd.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									errorLoad.setVisible(false);
									if(jtfChar.getText().length() == 1 && jtfDelay.getText().length() <= 5) {
										if(!tabAnimations.isEmpty() && !tabMessages.isEmpty() && iterator == 0) {
											tabAnimations.clear();
											tabMessages.clear();
										}
									
										try {
											int delayChoisi = 100;
											int slotChoisi = slot.getSelectedIndex()+1;
											String msg = animatelcd.getAffichage();
											
											delayChoisi = Integer.parseInt(jtfDelay.getText());
											
											tabAnimations.add(new AnimationLCD(slotChoisi, delayChoisi));
											tabMessages.add(msg);
											slot.removeItemAt(slot.getSelectedIndex());
											nbAnimations++;
											iterator++;
										}catch(NumberFormatException nfe) {
											errorLoad.setText("Error, please enter a valid delay");
											errorLoad.setVisible(true);
										}
									}else if (jtfChar.getText().length() != 1) {
										errorLoad.setText("Error, please enter a valid character");
										errorLoad.setVisible(true);
									}else if(jtfDelay.getText().length() > 5){
										errorLoad.setText("Error, please enter a valid delay (not up to 99999 milliseconds)");
										errorLoad.setVisible(true);
									}
								}	
							});
							
							//Listener du bouton animate
							jbAnimate.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									errorLoad.setVisible(false);
									PrintWriter output = new PrintWriter(portChoisi.getOutputStream());
									if(jbAnimate.getText().equals("Animate")) {
										if(tabAnimations.isEmpty() && tabMessages.isEmpty()) {
											errorLoad.setText("Error, no frame have been added");
											errorLoad.setVisible(true);
										}else {
											finAnime = false;
											jbAnimate.setText("Stop Animation");
											
											
												
											if(portChoisi.openPort() && checkPort(portChoisi)) {
												Thread thread = new Thread(){
													@Override public void run() {
														try {
															Thread.sleep(100); 
														} catch(Exception e) {
															
														}
	
														while(!finAnime) {
															for(int i = 0; i < tabAnimations.size(); i++) {
																int delay = tabAnimations.get(i).getDelay();
																
																for(int j = 0; j < tabMessages.size(); j++) {
																	output.print(tabMessages.get(j));
																	output.flush();
																	
																	try {
																		Thread.sleep(delay);
																	}catch(Exception excep) {
																		
																	}
																}
															}
															if(finAnime) {
																break;
															}
														}
													}
												};
												thread.start();
											}
										}
									}else if(jbAnimate.getText().equals("Stop Animation")) {
										finAnime = true;
										errorLoad.setVisible(false);
										if(portChoisi.openPort() && checkPort(portChoisi)) {
											Thread thread = new Thread(){
												@Override public void run() {
													try {
														Thread.sleep(100); 
													} catch(Exception e) {
														
													}
													while(finAnime) {
														output.print("                                ");
														output.flush();
																
														try {
															Thread.sleep(100);
														}catch(Exception excep) {
															
														}
														
														if(!finAnime) {
															break;
														}
													}
												}
											};
											thread.start();
										}
										jbAnimate.setText("Animate");
									}
								}
								
							});
							
							//Listener on JButton
							jbSave.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									JFrame frameDialog = new JFrame();
									frameDialog.setIconImage(image);
									JFileChooser fileChooser = new JFileChooser();
									
									if (fileChooser.showSaveDialog(frameDialog) == JFileChooser.APPROVE_OPTION) {
									  File file = fileChooser.getSelectedFile();
									  try {
										errorLoad.setVisible(false);
										String path = file.getPath() + ".lcd";
										file = new File(path);
										
										FileWriter filewriter = new FileWriter(file.getPath(), true);
										BufferedWriter buff = new BufferedWriter(filewriter);
										PrintWriter writer = new PrintWriter(buff);
										writer.println("start");
										
										writer.println();
										writer.println("Nombres de slots : ");
										writer.println(String.valueOf(nbAnimations));
										
										writer.println();
										writer.println("Messages : ");
										
										for(int i = 0; i < tabMessages.size(); i++) {
											writer.println(tabMessages.get(i));
										}
										
										writer.println();
										writer.println("Delays : ");
										
										for(int i = 0; i < tabAnimations.size(); i++) {
											writer.println(String.valueOf(tabAnimations.get(i).getDelay()));
										}
										
										writer.println();
										writer.println("close");
										writer.close();
																		
									  } catch (FileNotFoundException e2) {
										errorLoad.setText("An error occurred during encoding, please retry");
										errorLoad.setVisible(true);
										e2.printStackTrace();
									  } catch (IOException e1) {
										errorLoad.setText("An error occurred during encoding, please retry");
										errorLoad.setVisible(true);
										e1.printStackTrace();
									  }
									}
									
								}
								
							});
							
							//Listener du bouton Load
							jbLoad.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									JFrame frameDialog = new JFrame();
									frameDialog.setIconImage(image);
									JFileChooser fileChooser = new JFileChooser();
									
									if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
									  File file = fileChooser.getSelectedFile();
									  try {
											FileReader filereader = new FileReader(file.getPath());
											BufferedReader buff = new BufferedReader(filereader);
											String line, slots;
											ArrayList<String> listDelays = new ArrayList<String>();
											ArrayList<String> listMessage = new ArrayList<String>();
											
											if(buff.readLine().equals("start")) {
												errorLoad.setVisible(false);
												while((line = buff.readLine()) != null) {
													if(line.equals("Nombres de slots : ")) {
														slots = buff.readLine();
														line = buff.readLine();
													}else if(line.equals("Messages : ")){
														line = buff.readLine();
														while(!line.isEmpty()) {
															listMessage.add(line);
															line = buff.readLine();
														}
														
														tabMessages.clear();
														for(int i = 0; i < listMessage.size(); i++) {
															tabMessages.add(listMessage.get(i));
														}
														
													}else if(line.equals("Delays : ")){
														line = buff.readLine();
														while(!line.isEmpty()) {
															listDelays.add(line);
															line = buff.readLine();
														}
														
														tabAnimations.clear();
														for(int j = 0; j < listDelays.size(); j++) {
															tabAnimations.add(new AnimationLCD(j,Integer.parseInt(listDelays.get(j))));
														}
														
													}else if(line.equals("close")){
														buff.close();
														break;
													}
												}
											}else {
												errorLoad.setText("Error, the file seems to be damaged or wrong");
												errorLoad.setVisible(true);
												buff.close();
											}
										
										} catch (FileNotFoundException e1) {
											errorLoad.setText("Error, file not found");
											errorLoad.setVisible(true);
										} catch (IOException e1) {
											errorLoad.setText("Error, the file seems to be damaged or wrong");
											errorLoad.setVisible(true);
										}
									}
								}
								
							});
							
							//Listener du bouton Erase
							eraseAll.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									if(!tabAnimations.isEmpty() && !tabMessages.isEmpty()) {
										tabAnimations.clear();
										tabMessages.clear();
										jtfChar.setText("");
										jtfDelay.setText("");
										String[] nbSlotRep = new String[64];
										
										for(int i = 0; i < nbSlotRep.length; i++) {
											nbSlotRep[i] = (i+1)+"";
										}
										
										comboRep = new JComboBox(nbSlotRep);
										panelSlot.remove(slot);
										panelSlot.add(comboRep);
										animateFrameFinal.setSize(600, 401);
										animateFrameFinal.setSize(600, 400);
										
										CarreLCD[] tab = new CarreLCD[32];
										tab = animatelcd.getTab();
										for(int i = 0; i < tab.length; i++) {
											tab[i].setEstClique(false);
										}
										
										animatelcd.setTab(tab);
										animatelcd.repaint();
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
				
				//Listener window
				animateFrame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						animateFrame.setVisible(false);
						animateFrame.dispose();
						draw.setEnabled(true);
						animate.setEnabled(true);
						
						jrb1.setEnabled(true);
						jrb2.setEnabled(true);
						jrb3.setEnabled(true);
						jrb4.setEnabled(true);
						connectButton.setEnabled(true);
						ports.setEnabled(true);
						
						if(portChoisi != null) {
							portChoisi.closePort();
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
				JFrame drawFrameConnect = new JFrame("Choose your port");
				drawFrameConnect.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				drawFrameConnect.setIconImage(image);
				drawFrameConnect.setSize(300, 100);
				JPanel mainDraw = new JPanel();
				JButton drawConnect = new JButton("Connect");
				portsDraw = new JComboBox<String>();
				
				jrb1.setEnabled(false);
				jrb2.setEnabled(false);
				jrb3.setEnabled(false);
				jrb4.setEnabled(false);
				connectButton.setEnabled(false);
				ports.setEnabled(false);
				draw.setEnabled(false);
				animate.setEnabled(false);
				
				SerialPort[] portNamesDraw = SerialPort.getCommPorts();
				for(int i = 0; i < portNamesDraw.length; i++) {
					portsDraw.addItem(portNamesDraw[i].getSystemPortName());
				}
				
				mainDraw.add(portsDraw);
				mainDraw.add(drawConnect);
				
				drawFrameConnect.add(mainDraw);
				drawFrameConnect.setVisible(true);
				
				//Listener window
				drawFrameConnect.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						draw.setEnabled(true);
						animate.setEnabled(true);
						
						jrb1.setEnabled(true);
						jrb2.setEnabled(true);
						jrb3.setEnabled(true);
						jrb4.setEnabled(true);
						connectButton.setEnabled(true);
						ports.setEnabled(true);
						
						connectButton.setEnabled(true);
						ports.setEnabled(true);
						
						drawFrameConnect.setVisible(false);
						drawFrameConnect.dispose();
						
						if(portChoisi != null)
							portChoisi.closePort();
					}
				});
				
				drawConnect.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						portChoisi = SerialPort.getCommPort(portsDraw.getSelectedItem().toString());
						portChoisi.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
						
						drawConnect.setEnabled(false);
						portsDraw.setEnabled(false);
						
						draw.setEnabled(false);
						DrawLCD drawlcd = new DrawLCD();
						drawlcd.setPreferredSize(new Dimension(600,150));
						
						JFrame frameDraw = new JFrame("Draw");
						frameDraw.setSize(600, 350);
						frameDraw.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						frameDraw.setIconImage(image);
						
						JPanel main = new JPanel();
						main.setLayout(new FlowLayout());
						
						JPanel milieu = new JPanel();
						JPanel milieuInter = new JPanel();
						JPanel errorPanel = new JPanel();
						
						JPanel milieuTT = new JPanel();
						milieuTT.setLayout(new BorderLayout());
						
						JButton drawButton = new JButton("Draw");
						JButton erase = new JButton("Erase all");
						erase.setPreferredSize(new Dimension(100,35));
						drawButton.setPreferredSize(new Dimension(100,35));
						
						JLabel textDraw = new JLabel("Enter a character : ");
						JLabel errorLength = new JLabel();
						
						errorLength.setForeground(Color.red);
						errorLength.setVisible(false);
						charToDraw = new JTextField();
						charToDraw.setColumns(2);

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
														errorLength.setText("Error, please enter a valid character");
														errorLength.setVisible(true);
													}else if (charToDraw.getText().length() < 1){
														errorLength.setText("Error, please enter a valid character");
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
						
						//Listener bouton erase
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
						
						milieu.add(textDraw);
						milieu.add(charToDraw);

						milieuInter.add(drawButton);
						milieuInter.add(erase);
						
						errorPanel.add(errorLength);
						
						milieuTT.add(milieu,BorderLayout.NORTH);
						milieuTT.add(milieuInter,BorderLayout.CENTER);
						milieuTT.add(errorPanel,BorderLayout.SOUTH);
						
						main.add(drawlcd);
						main.add(milieuTT);
						
						frameDraw.add(main);
						frameDraw.setVisible(true);
						frameDraw.setIconImage(image);
						
						frameDraw.addWindowListener(new WindowAdapter() {
							public void windowClosing(WindowEvent e) {
								drawConnect.setEnabled(true);
								portsDraw.setEnabled(true);
								
								frameDraw.setVisible(false);
								frameDraw.dispose();
								
								if(portChoisi != null)
									portChoisi.closePort();
							}
						});
					}
				});
				//Fin Fonctionnalité Draw		
			}	
		});	
		
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
										output.print(new SimpleDateFormat("    HH:mm:ss      dd MMMMMMM yyyy").format(new Date()));
										output.flush();
										try {
											Thread.sleep(1000); 
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
						if(portChoisi.openPort() && checkPort(portChoisi)) {
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
										if (getCPUusage() < 10) {
											output.print("  RAM usage : " +String.valueOf(getRAMusage()) +"%");
											output.flush();
										}else {
											output.print(" RAM usage : " +String.valueOf(getRAMusage()) +"%");
											output.flush();
										}
										try {
											Thread.sleep(1000); 
										} catch(Exception e) {
											
										}
									}
								}
							};
							thread.start();
						}
					}else if (animations) {
						if(portChoisi.openPort() && checkPort(portChoisi)) {
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
										output.print("       ");
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
							if(portChoisi.openPort() && checkPort(portChoisi)) {
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
