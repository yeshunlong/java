package shootgame;

public class Hero extends FlyingObject{
	private int DOUBLE_FIRE;
	private int life;
	public Hero() {
		DOUBLE_FIRE=0;
		life=3;
//		img=ShootGame.hero0;
		img=ShootGame.hero_w;
		w=img.getWidth();
		h=img.getHeight();
		x=ShootGame.win_width/2-w/2; //x,y是英雄机的中心坐标
		y=ShootGame.win_height*4/5-h/2;
	}
	public int isDoubleFire() {
        return DOUBLE_FIRE;
    }
	public void setDoubleFire(int x) {
        DOUBLE_FIRE=x;
    }
	public int getLife() {
		return life;
	}
    public void addDoubleFire(){
    	DOUBLE_FIRE = 40;
    }
    public void addLife(){  //增命
        life++;
    }
    public void subLife(){   //减命
        life--;
    }
    public void moveto(int xx,int yy) {
    	x=xx-w/2;
    	y=yy-h/2;
    }
    public Bullet[] shoot() {
    	int xStep = w/4;      //4半
        int yStep = 20;  //步
        if(DOUBLE_FIRE>0){  //双倍火力
            Bullet[] bullets = new Bullet[2];
            bullets[0] = new Bullet(x+xStep,y-yStep,0);  //y-yStep(子弹距飞机的位置)
            bullets[1] = new Bullet(x+3*xStep,y-yStep,0);
            return bullets;
        }else{      //单倍火力
            Bullet[] bullets = new Bullet[1];
            bullets[0] = new Bullet(x+2*xStep,y-yStep,0);  
            return bullets;
        }
    }
    public boolean hit(FlyingObject other){
        int x1 = other.x - w/2;                 //x坐标最小距离
        int x2 = other.x + w/2 + other.w;   //x坐标最大距离
        int y1 = other.y - h/2;                //y坐标最小距离
        int y2 = other.y + h/2 + other.h; //y坐标最大距离
    
        int herox = x + w/2;               //英雄机x坐标中心点距离
        int heroy = y + h/2;               //英雄机y坐标中心点距离
        
        return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;   //区间范围内为撞上了
    }
	@Override
	public boolean outofbounds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	} //英雄机
	@Override
	public boolean shootby(Bullet bullet) {
		// TODO Auto-generated method stub
		boolean flag=false;
		if(bullet.belong==1) {
			int xx=bullet.x;
			int yy=bullet.y;
			flag=(x<xx && xx<x+w && y<yy && yy<y+h);
		}
		return flag;
	}
	
}
