import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Tile {

	private int x;
	private int y;
	private char c;

	public Tile(int x, int y, char c) {
		// TODO Auto-generated constructor stub
		this.x=x;
		this.y=y;
		this.c=c;
	}
	public void draw(Graphics g,int width,int height){
		Graphics2D g2 = (Graphics2D)g;
		g2.translate((x-1)*width, (y-1)*height);
		g2.setPaint(Color.WHITE);
		g2.fill(new Rectangle(width,height));
		g2.setPaint(Color.BLACK);
		g2.draw(new Rectangle(width,height));
		char c[] = new char[1];
		c[0]=this.c;
		g2.drawChars(c, 0, 1, width, height);
		g2.translate(-(x-1)*width, -(y-1)*height);
	}

}
