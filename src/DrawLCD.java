import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author Robin_prgn
 *
 */
public class DrawLCD extends JPanel{
	
	private CarreLCD[] tab;
	private static final int taille = 30;
	
	/**
	 * Constructeur
	 */
	
	public DrawLCD() {
		tab = new CarreLCD[32];
		int x = 50;
		for(int i = 0; i < 16; i++) {
    		tab[i] = new CarreLCD(x,50);
    		x += taille;
		}
		
		x = 50;
    	for(int i = 0; i < 16; i++) {
    		tab[i+16] = new CarreLCD(x,50+taille);
    		x += taille;
    	}
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!LCDInterface.getCharToDraw().getText().isEmpty()) {
					for(int i = 0; i < tab.length; i++) {
						if (tab[i].contient(e.getPoint())){
							tab[i].setEstClique();
							tab[i].setChar(LCDInterface.getCharToDraw().getText().charAt(0));	
						}
					}
					repaint();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
	}
	
	/**
	 * Methode paintComponent
	 * @param g
	 * 			Graphics
	 */
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
  
    	g.setColor(Color.black);
    	for(int i = 0; i < tab.length; i++) {
    		g.drawPolygon(tab[i].getPoly());
    	}
    	
    	for(int i = 0; i < tab.length; i++) {
    		if(tab[i].estClique()) {
    			tab[i].afficheCentre(g);
    		}else {
    			tab[i].efface(g);
    		}
    	}
    }
    
    /**
     * Methode getTab
     * @return
     * 			le tableau de carre
     */
    
    public CarreLCD[] getTab() {
    	return this.tab;
    }
    
    /**
     * Methode getTab
     * @param newtab
     * 				le tableau a passer en attribut
     */
    
    public void setTab(CarreLCD[] newtab) {
    	this.tab = newtab;
    }
    
    public String getAffichage() {
    	String res = "";
    	for(int i = 0; i < tab.length; i++) {
    		if(tab[i].estClique()) {
    			res += tab[i].toString();
    		}else {
    			res += " ";
    		}
    	}
    	return res;
    }
}
