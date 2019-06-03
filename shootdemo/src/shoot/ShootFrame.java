package shoot;

import javax.swing.JFrame;

import ball.BallPanel;

public class ShootFrame {
	public static void main(String[] args) {
		JFrame f=new JFrame();
		ShootPanel p=new ShootPanel();
		f.add(p);
		f.addMouseMotionListener(p);
		f.addMouseListener(p);
		f.setSize(p.win_width, p.win_height);
		f.setLocationRelativeTo(null);
		f.setTitle("ShootGame");
		f.setDefaultCloseOperation(3);
		f.setVisible(true);
		p.move_background();
		p.move_boss();
	}
}
