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
		x=ShootGame.win_width/2-w/2; //x,y��Ӣ�ۻ�����������
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
    public void addLife(){  //����
        life++;
    }
    public void subLife(){   //����
        life--;
    }
    public void moveto(int xx,int yy) {
    	x=xx-w/2;
    	y=yy-h/2;
    }
    public Bullet[] shoot() {
    	int xStep = w/4;      //4��
        int yStep = 20;  //��
        if(DOUBLE_FIRE>0){  //˫������
            Bullet[] bullets = new Bullet[2];
            bullets[0] = new Bullet(x+xStep,y-yStep,0);  //y-yStep(�ӵ���ɻ���λ��)
            bullets[1] = new Bullet(x+3*xStep,y-yStep,0);
            return bullets;
        }else{      //��������
            Bullet[] bullets = new Bullet[1];
            bullets[0] = new Bullet(x+2*xStep,y-yStep,0);  
            return bullets;
        }
    }
    public boolean hit(FlyingObject other){
        int x1 = other.x - w/2;                 //x������С����
        int x2 = other.x + w/2 + other.w;   //x����������
        int y1 = other.y - h/2;                //y������С����
        int y2 = other.y + h/2 + other.h; //y����������
    
        int herox = x + w/2;               //Ӣ�ۻ�x�������ĵ����
        int heroy = y + h/2;               //Ӣ�ۻ�y�������ĵ����
        
        return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;   //���䷶Χ��Ϊײ����
    }
	@Override
	public boolean outofbounds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void step() {
		// TODO Auto-generated method stub
		
	} //Ӣ�ۻ�
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
