import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {

	public static final int FRAME_HEIGHT = 1000;
	public static final int FRAME_WIDTH = 1000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("T2 IA");
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Interface inter = new Interface();
		inter.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.add(inter);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.repaint();
	}

}
