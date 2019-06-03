package shootgame;

import java.util.Random;

public class Airplane extends FlyingObject implements Enemy{ //普通敌人
	private int speed = 3;  //移动速度
	public Airplane(){
//        img = ShootGame.airplane;
		Random rand = new Random();
		int img_id=rand.nextInt(5); //[0,5)之间的整数
		if(img_id==0) img = ShootGame.e_0;
		if(img_id==1) img = ShootGame.e_1;
		if(img_id==2) img = ShootGame.e_2;
		if(img_id==3) img = ShootGame.e_3;
		if(img_id==4) img = ShootGame.e_4;
        w = img.getWidth();
        h = img.getHeight();
        y = -h;          
        Random rand1 = new Random();
        x = rand1.nextInt(ShootGame.win_width - w);
    }

    @Override
    public int getscore() {  
        return 5;
    }

    @Override
    public  boolean outofbounds() {   
        return y>ShootGame.win_height;
    }

    @Override
    public void step() {   
        y += speed;
    }
    
    public Bullet[] shoot() {
    	int xStep = w/4;      //4半
        int yStep = 20;  //步
        Bullet[] bullets = new Bullet[1];
        bullets[0] = new Bullet(x+2*xStep,y+yStep,1);  
        return bullets;
    }

	@Override
	public boolean shootby(Bullet bullet) {
		// TODO Auto-generated method stub
		boolean flag=false;
		if(bullet.belong==0) {
			int xx=bullet.x;
			int yy=bullet.y;
			flag=(x<xx && xx<x+w && y<yy && yy<y+h);
		}
		return flag;
	}
    
}
