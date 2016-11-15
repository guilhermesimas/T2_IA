import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JComponent;

public class Mapa extends JComponent{
	
	public int limX = 0;
	public int limY = 0;
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		this.setPreferredSize(new Dimension(this.getParent().getWidth(),this.getParent().getHeight()/2));
		System.out.println(this.getHeight());
		g2.setPaint(Color.BLACK);
		g2.fill(new Rectangle(0,0,this.getWidth(),this.getHeight()));
		g2.setPaint(Color.GREEN);
		g2.draw(new Rectangle(0,0,this.getWidth(),this.getHeight()));
//		if (toModify != null) {
//			for (Tile t : toModify) {
//				t.draw(g, this.getWidth() / limX, this.getHeight() / limY);
//			}
//		}
		System.out.println("Mapa paint");
	}

	public void update(ArrayList<Tile> toModify) {
		// TODO Auto-generated method stub
		Graphics g = this.getGraphics();
		if(g==null){
			System.out.println("Graphics is null");
			return;
		}
		Graphics gnew=g.create(this.getX(), this.getY(), this.getWidth(), this.getHeight());
//		this.setForeground(Color.RED);
		for(Tile t:toModify){
			t.draw(gnew, this.getWidth()/limX, this.getHeight()/limY);
		}
	}

}
