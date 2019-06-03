package screen;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyPanel extends JPanel implements MouseListener { // 画布类 继承自JPanel 可使用MouseListener接口
	// 若需使用接口 还需要再在MyFrame里头注册监听器
	// 重载
	// Alt+/ 代码补全
	// 自动导入package ctrl+shift+o
	final int win_width = 1920;
	final int win_height = 1080;
	int px=0,py=0;
	double g_speed=1;
	int[] snowflakex=new int[500];
	int[] snowflakey=new int[500];
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		setBackground(Color.black);
		g.setColor(Color.white);
		Font f = new Font("微软雅黑", Font.ITALIC, 94);
		g.setFont(f);
		g.drawString("帅哥一号", 300, 300);
		Image img = new ImageIcon("img/wy.jpg").getImage();
		Image img1 = new ImageIcon("img/wy1.jpg").getImage();
//		Image img2 = new ImageIcon("img/timg.gif").getImage();
		g.drawImage(img, px, py, win_width/3, win_height, null);
		g.drawImage(img1, px+win_width/3, py, win_width/5*3, win_height, null);
//		g.drawImage(img2, win_width/2, 0, win_width, win_height, null);
		for (int i = 1; i <= 300; i++) {
			int x = snowflakex[i];
			int y = snowflakey[i];
			g.drawString("*", x, y);
		}
 
		/*
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.clearRect(0, 0, win_width, win_height);
		for (int i = 1; i <= 300; i++) {
			int x = (int) (Math.random() * win_width);
			int y = (int) (Math.random() * win_height);
			g.drawString("*", x, y);
		}
		*/
	}
	public void create_snow() {
		for (int i = 1; i <= 300; i++) {
			int x = (int) (Math.random() * win_width);
			int y = (int) (Math.random() * win_height);
			snowflakex[i]=x;snowflakey[i]=y;
		}
	}
	//创建线程，创建匿名内部类
	public void move() {
		Thread t = new Thread(){
			public void run() {
				while(true) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					g_speed+=0.1;
					if(g_speed>3.0) g_speed=1.0; 
					px+=(int)(100*g_speed);
					px%=win_width;
//					py+=100;
					for (int i = 1; i <= 300; i++) {
//						snowflakex[i]+=10;
						snowflakey[i]+=(int)(10*g_speed);
						snowflakey[i]%=win_height;
					}
					repaint();
				}
			}
		}; //匿名内部类只有对象t
		t.start();
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
