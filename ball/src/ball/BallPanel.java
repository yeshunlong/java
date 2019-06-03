package ball;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;

public class BallPanel extends JPanel implements MouseMotionListener{
	final int win_width = 1920;
	final int win_height = 1080;
	final int ball_size=100;
	int Rect_x=win_width/3;
	int Rect_width=win_width/4;
	int Ball_x=win_width/2-ball_size;
	int Ball_y=win_height/2-ball_size;
	int del_x=ball_size/30;
	int Ball_dir=0;
	int touch_num=0;
	public int cal_time() {
		Date now_time=new Date();
		SimpleDateFormat f=new SimpleDateFormat("mm-ss");
		String time=f.format(now_time);
		String time_m=time.substring(0, 2);
		int int_time_m=Integer.parseInt(time_m);
		String time_s=time.substring(3, 5);
		int int_time_s=Integer.parseInt(time_s);
//		String time_ss=time.substring(6, 9);
//		int int_time_ss=Integer.parseInt(time_ss);
//		int int_time=Integer.parseInt(time);
//		return int_time_m*1000*60+int_time_s*1000+int_time_ss;
		return int_time_m*60+int_time_s;
	}
	int start_time=cal_time();
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		String score=String.valueOf(cal_time()-start_time);
		double size_rate=1.0-(double)touch_num/10;
		double speed_rate=(double)touch_num/10+1.0;
		del_x=(int)(speed_rate*ball_size/30);
//		System.out.println(size_rate);
		if(size_rate<0.5) size_rate=0.5;
		Rect_width=(int)(size_rate*win_width/4);
//		System.out.println(Rect_width);
		g.setColor(Color.green);
		g.fillOval(Ball_x, Ball_y, ball_size, ball_size);
		g.fillRect(Rect_x-win_width/8, win_height/4*3-ball_size, Rect_width, ball_size/2);
		g.setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,30));
		g.setColor(Color.blue);
		g.drawString("score:", win_width*2/3, win_height/4);
		g.drawString(score, win_width*2/3+ball_size*2, win_height/4);
	}
	public void moveball() {
		new Thread() {
			public void run() {
				while(true) {
					if(Ball_dir==0) {
						Ball_x+=del_x;
						Ball_y+=del_x;
					}
					if(Ball_dir==1) {
						Ball_x+=del_x;
						Ball_y-=del_x;
					}
					if(Ball_dir==2) {
						Ball_x-=del_x;
						Ball_y-=del_x;
					}
					if(Ball_dir==3) {
						Ball_x-=del_x;
						Ball_y+=del_x;
					}
					if(Ball_x+ball_size>win_width) { 
						if(Ball_dir==0) Ball_dir=3;
						if(Ball_dir==1) Ball_dir=2;
					}
					if(Ball_x<0) {
						if(Ball_dir==2) Ball_dir=1;
						if(Ball_dir==3) Ball_dir=0;
					}
					if(Ball_y<0) {
						if(Ball_dir==1) Ball_dir=0;
						if(Ball_dir==2) Ball_dir=3;
					}
					if(Ball_y+ball_size>win_height/4*3-ball_size) {
//						System.out.println("hehe");
						if(Ball_x>=Rect_x-win_width/8 && Ball_x<=Rect_x-win_width/8+Rect_width) {
							touch_num++;
							if(Ball_dir==0) Ball_dir=1;
							if(Ball_dir==3) Ball_dir=2;
						}
					}
					if(Ball_y>win_height/4*3) System.exit(0);
					repaint();
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}.start();
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		Point pos=e.getLocationOnScreen();
		Rect_x=pos.x;
		repaint();
//		System.out.println(Rect_width);
//		System.out.println(((cal_time()-(double)start_time)/cal_time()));
	}
}
