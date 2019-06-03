package shootgame;

import java.awt.image.BufferedImage;

public abstract class FlyingObject { //飞行物类 作为基类 且为抽象类
	protected int x,y,w,h; //坐标和宽高
	protected BufferedImage img;
	public void setx(int xx) {
		x=xx;
	}
	public void sety(int yy) {
		y=yy;
	}
	public void setw(int ww) {
		w=ww;
	}
	public void seth(int hh) {
		h=hh;
	}
	public void setimg(BufferedImage img1) {
		img=img1;
	}
	public int getx() {
		return x;
	}
	public int gety() {
		return y;
	}
	public int getw() {
		return w;
	}
	public int geth() {
		return h;
	}
	public BufferedImage getimg() {
		return img;
	}
	public abstract boolean outofbounds(); //判断是否出界
	public abstract void step(); //单位时间内进行移动
	public abstract boolean shootby(Bullet bullet);
	public abstract Bullet[] shoot();
	/*
	public boolean shootby(Bullet bullet) { //判断是否被击中
		int xx=bullet.x;
		int yy=bullet.y;
		return x<xx && xx<x+w && y<yy && yy<y+h;
	}
	*/
}
