package shootgame;

import java.util.Random;

public class Bee extends FlyingObject implements Award{ //带奖励敌人
	private int xSpeed = 1;   //x坐标移动速度
    private int ySpeed = 2;   //y坐标移动速度
    private int awardType;    //奖励类型

    public Bee(){
        img = ShootGame.bee;
        w = img.getWidth();
        h = img.getHeight();
        y = -h;
        Random rand = new Random();
        x = rand.nextInt(ShootGame.win_width - w);
        awardType = rand.nextInt(2);   //初始化时给奖励
    }

    @Override
    public boolean outofbounds() {
        return y>ShootGame.win_height;
    }

    @Override
    public void step() {      
        x += xSpeed;
        y += ySpeed;
        if(x > ShootGame.win_width-w){  
            xSpeed = -1;
        }
        if(x < 0){
            xSpeed = 1;
        }
    }

	@Override
	public int gettype() {
		// TODO Auto-generated method stub
		return awardType;
	}
	
	public Bullet[] shoot() {
    	int xStep = w/4;      //4半
        int yStep = 20;  //步
        Bullet[] bullets = new Bullet[1];
        bullets[0] = new Bullet(x+2*xStep,y+yStep,1);  
        return bullets;
    }
	
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
