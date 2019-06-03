package shoot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ShootPanel extends JPanel implements MouseMotionListener,MouseListener{
	final int win_width=400;
	final int win_height=1080; //地图尺寸400*6000
	final int back_height=6000;
	final int hero_width=97;
	final int hero_height=124; //地图尺寸400*6000
	final int boss_width=160;
	final int boss_height=129;//boss大小 160*129
	final int big_bullet_width=16;
	final int big_bullet_height=16;//大招图示大小 16*16
	final int fire_big_bullet_width=64;
	final int fire_big_bullet_height=64;//大招大小 64*64
	int big_bullet_num=3;
	boolean fire_big_bullet=false;
	int back_x=0,back_y=0;
	int back_del_y=1; //画布移动速度
	int hero_x=win_width/2-hero_width/2,hero_y=win_height*3/5;
	int hero_att=10;//英雄机攻击力
	int hero_hp=100;//英雄机生命值
	boolean paint_boss=false;
	int boss_x=win_width/2-boss_width/2,boss_y=win_height/5;
	int boss_dir=0; //0向左 1向右
	int boss_del_x=1; //boss移动速度
	int boss_hp=1000;//boss生命值
	int boss_att=10;//boos攻击力
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		if(hero_hp<=0) {
			Image img_gameover = new ImageIcon("img/gameover.png").getImage();
			g.drawImage(img_gameover, 0, 0, null);
			return;
		}
		Image img_back = new ImageIcon("img/background_2.png").getImage();
		g.drawImage(img_back, back_x, back_y, null);
		Image img_hero = new ImageIcon("img/hero0.png").getImage(); //英雄机大小 97*124
		g.drawImage(img_hero, hero_x, hero_y, null);
		if(paint_boss==true) {
			Image img_boss = new ImageIcon("img/Boss0.png").getImage(); //97*124
			g.drawImage(img_boss, boss_x, boss_y, null);
		}
		for(int i=1;i<=big_bullet_num;i++) {
			Image img_big_bullet = new ImageIcon("img/bigbullet.png").getImage(); //97*124
			g.drawImage(img_big_bullet, big_bullet_width*(2*i), big_bullet_height, null);
		}
		g.setColor(Color.white);
		g.setFont(new Font("微软雅黑",Font.BOLD,25));
		g.drawString("maker:王越的辅助", win_width/4, win_height*5/6);
		g.drawString("英雄机生命:", win_width/20, win_height/12);
		g.drawString(String.valueOf(hero_hp), win_width/4+50, win_height/12);
		if(paint_boss==true) {
			g.drawString("boss生命:", win_width/20, win_height/12+50);
			g.drawString(String.valueOf(boss_hp), win_width/4+50, win_height/12+50);
		}
		/*
		if(fire_big_bullet==true) {
			Image img_fire_big_bullet = new ImageIcon("img/boom3.png").getImage(); //64*64
			g.drawImage(img_fire_big_bullet, hero_x, hero_y+2*fire_big_bullet_height, null);
			fire_big_bullet=false;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
	}
	public void move_background() {
		new Thread() {
			public void run() {
				while(true) {
					back_y-=back_del_y;
					if((-back_y)>back_height/2) {
						paint_boss=true;
						break;
					}
					repaint();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	public void move_boss() {
		new Thread() {
			public void run() {
				while(true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(paint_boss==false) continue;
					if((-back_y)>back_height-win_height) System.exit(0);
					back_y-=back_del_y;
					if(boss_dir==0) {
						boss_x-=boss_del_x;
					}
					else if(boss_dir==1) {
						boss_x+=boss_del_x;
					}
					if(boss_x<=0) boss_dir=1;
					if(boss_x+boss_width>=win_width) boss_dir=0;
					repaint();
					
				}
			}
		}.start();
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
//		Point pos=e.getLocationOnScreen();
//		hero_x=pos.x;
//		hero_y=pos.y;
		hero_x=e.getX()-hero_width/2;
		hero_y=e.getY()-hero_height*2/3;
//		repaint();
//		System.out.println(hero_x);
	}
	@Override
	public void mouseClicked(MouseEvent e) { //放大招
		// TODO Auto-generated method stub
		if(big_bullet_num<=0) return;
		big_bullet_num--;
		fire_big_bullet=true;
		repaint();
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
