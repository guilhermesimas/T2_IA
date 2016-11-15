import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Tile {

	private int x;
	private int y;
	private char c;

	public Tile(int x, int y, char c) {
		this.x=x;
		this.y=y;
		this.c=c;
	}
	/**
	 * Por enquanto Draw esta so desenhando o char na tela.
	 * Talvez seja melhor uma implementação que usa uma imagem
	 * em um enum. So teria que evitar carregar a imagem sempre
	 * Onde guardar a BufferedImage? Nova classe static que tem tudo isso?
	 * @param g
	 * @param width
	 * @param height
	 */
	public void draw(Graphics g,int width,int height){
		
		System.out.println((x-1)*width+" "+(y-1)*height);
		Graphics2D g2 = (Graphics2D)g;
		g2.translate((x-1)*width, (y-1)*height);
		g2.setPaint(Color.WHITE);
		g2.fill(new Rectangle(width,height));
		g2.setPaint(Color.BLACK);
		g2.draw(new Rectangle(width,height));
		System.out.println("C=<"+c+">");
		g2.setColor(Color.RED);
		g2.setFont(new Font("TimesRoman",Font.BOLD,height));
		g2.drawString(String.valueOf(c),0,height );

		g2.translate(-(x-1)*width, -(y-1)*height);
	}

}
