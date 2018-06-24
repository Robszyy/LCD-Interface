import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

/**
 * 
 */

/**
 * Classe Carre
 * @author Robin_prgn
 *
 */

public class Carre extends Polygon{
	private Polygon poly;
	private boolean estClique = false;
	private char charToDraw;
	
	/**
	 * Constructeur
	 * @param x
	 * 			l'abscisse du point haut gauche du carre
	 * @param y
	 * 			l'ordonne du point haut gauche du carre
	 */
	
	public Carre(int x, int y) {
		poly = new Polygon();
		poly.addPoint(x,y);
		poly.addPoint(x, y+30);
		poly.addPoint(x+30, y+30);
		poly.addPoint(x+30, y);
	}
	
	/**
	 * Methode afficheCentre
	 * @param g
	 * 			Graphics
	 */
	
	public void afficheCentre(Graphics g) {
		int x = poly.xpoints[0] + 15;
		int y = poly.ypoints[0] + 15;
		
		g.setColor(Color.black);
		g.fillOval(x-4, y-4, 8, 8);
	}
	
	/**
	 * methode efface
	 * @param g
	 * 			Graphics
	 */
	
	public void efface(Graphics g) {
		int x = poly.xpoints[0];
		int y = poly.ypoints[0];
		g.create(x, y, 30, 30);
	}
	
	/**
	 * Methode estClique
	 * @return
	 * 			si le carre est clique
	 */
	
	public boolean estClique() {
		return this.estClique;
	}
	
	/**
	 * Methode setEstClique
	 * Permet d'inverser l'attribut booleen estClique
	 */
	
	public void setEstClique() {
		this.estClique = !this.estClique;
	}
	
	/**
	 * Methode setEstClique
	 * @param b
	 * 			booleen a attribuer
	 */
	
	public void setEstClique(boolean b) {
		this.estClique = b;
	}
	
	/**
	 * Methode setChar
	 * @param c
	 * 			charactere a attribuer
	 */
	
	public void setChar(char c) {
		this.charToDraw = c;
	}
	
	/**
	 * Methode contient
	 * @param p
	 * 			Point
	 * @return
	 * 			si le point est dans le polygone poly
	 */
	
	public boolean contient(Point p) {
		return poly.contains(p);
	}
	
	/**
	 * Methode getPoly
	 * @return
	 * 			l'attribut poly
	 */
	
	public Polygon getPoly() {
		return this.poly;
	}
	
	/**
	 * Methode toString
	 * @return 
	 * 			String 
	 */
	
	public String toString() {
		if(this.estClique()) {
			return this.charToDraw+"";
		}else {
			return " ";
		}
	}
}
