package ball;

import javax.swing.JFrame;

public class BallFrame {
	public static void main(String[] args) {
		
		JFrame f=new JFrame();
		BallPanel p=new BallPanel();
		f.add(p);
		f.addMouseMotionListener(p);
		f.setSize(p.win_width, p.win_height);
		f.setLocationRelativeTo(null);
		f.setTitle("µ²°å½ÓÇò");
		f.setDefaultCloseOperation(3);
		f.setVisible(true);
		p.moveball();
	}
}
