package shootgame;

public class Bullet extends FlyingObject{ //�ӵ�
	private int speed = 3;  //�ƶ����ٶ�
	public int belong; //0��ʾӢ�ۻ����ӵ� 1��ʾ�л����ӵ�
    public Bullet(int xx,int yy,int b){
        x = xx;
        y = yy;
        belong = b;
        if(belong==0) img = ShootGame.bullet;
        else img = ShootGame.bullet_e;
    }

    @Override
    public void step(){   
        if(belong==0) y-=speed;
        if(belong==1) y+=speed;
    }

    @Override
    public boolean outofbounds() {
        return y<-h||y>ShootGame.win_height;
    }
    
    public boolean shootby(Bullet bullet) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Bullet[] shoot() {
		// TODO Auto-generated method stub
		return null;
	}
}
