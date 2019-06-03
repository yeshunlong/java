package shootgame;

import java.awt.image.BufferedImage;

public abstract class FlyingObject { //�������� ��Ϊ���� ��Ϊ������
	protected int x,y,w,h; //����Ϳ��
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
	public abstract boolean outofbounds(); //�ж��Ƿ����
	public abstract void step(); //��λʱ���ڽ����ƶ�
	public abstract boolean shootby(Bullet bullet);
	public abstract Bullet[] shoot();
	/*
	public boolean shootby(Bullet bullet) { //�ж��Ƿ񱻻���
		int xx=bullet.x;
		int yy=bullet.y;
		return x<xx && xx<x+w && y<yy && yy<y+h;
	}
	*/
}
