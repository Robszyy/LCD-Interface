import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

/**
 * 
 */

/**
 * @author Robin
 *
 */
public class Carre extends Polygon{
	private Polygon poly;
	private boolean estClique = false;
	private char charToDraw;
	
	public Carre(int x, int y) {
		poly = new Polygon();
		poly.addPoint(x,y);
		poly.addPoint(x, y+30);
		poly.addPoint(x+30, y+30);
		poly.addPoint(x+30, y);
	}
	
	public boolean contient(Point p) {
		return poly.contains(p);
	}
	
	public Polygon getPoly() {
		return this.poly;
	}
	
	public void afficheCentre(Graphics g) {
		int x = poly.xpoints[0] + 15;
		int y = poly.ypoints[0] + 15;
		
		g.setColor(Color.black);
		g.fillOval(x-4, y-4, 8, 8);
	}
	
	public void efface(Graphics g) {
		int x = poly.xpoints[0];
		int y = poly.ypoints[0];
		g.create(x, y, 30, 30);
		//g.clearRect(x, y, 30, 30);
	}
	
	public boolean estClique() {
		return this.estClique;
	}
	
	public void setEstClique() {
		this.estClique = !this.estClique;
	}
	
	public void setChar(char c) {
		this.charToDraw = c;
	}
	
	public String toString() {
		if(this.estClique()) {
			return this.charToDraw+"";
		}else {
			return " ";
		}
	}
}
