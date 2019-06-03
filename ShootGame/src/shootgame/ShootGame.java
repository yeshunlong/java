package shootgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShootGame extends JPanel{
	public static void main(String[] args) {
		JFrame f=new JFrame("ShootGame");
		ShootGame game=new ShootGame();
		f.add(game);
		f.setSize(win_width,win_height);
		f.setAlwaysOnTop(true);
		f.setDefaultCloseOperation(3);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		game.action();
	}
	

	private int score = 0; // �÷�
    private Timer timer; // ��ʱ��
    private int intervel = 1000 / 100; // ʱ����(����)
    
    private int background_y = -6000+win_height;
    private int del_background_y = 1;
	
	public final static int win_width=400; //��Ļ�ߴ�
	public final static int win_height=1080;
	private int state=0; //��Ϸ״̬
	private static final int START=0;
	private static final int RUNNING=1;
	private static final int PAUSE=2;
	private static final int GAMEOVER=3;
	public static BufferedImage background;
    public static BufferedImage start;
    public static BufferedImage airplane;
    public static BufferedImage bee;
    public static BufferedImage bullet; //Ӣ�ۻ��ӵ�
    public static BufferedImage bullet_e; //�л��ӵ�
    public static BufferedImage hero0;
    public static BufferedImage pause;
    public static BufferedImage gameover;
    
    public static BufferedImage hero_w;
    public static BufferedImage e_0;
    public static BufferedImage e_1;
    public static BufferedImage e_2;
    public static BufferedImage e_3;
    public static BufferedImage e_4;

    
    private FlyingObject[] flyings = {}; // �л�����
//    private Airplane[] enemys = {}; 
    private Bullet[] bullets = {}; // �ӵ�����
    private Hero hero = new Hero(); // Ӣ�ۻ�
    
    static { //��ʼ��ͼƬ��Դ
        try {
            background = ImageIO.read(ShootGame.class.getResource("background_2.png"));
            start = ImageIO.read(ShootGame.class.getResource("start.png"));
            airplane = ImageIO
                    .read(ShootGame.class.getResource("e7.png"));
            bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
            bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
            bullet_e = ImageIO.read(ShootGame.class.getResource("bullet_7.png"));
            hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
            pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
            gameover = ImageIO
                    .read(ShootGame.class.getResource("gameover.png"));
            
            hero_w = ImageIO.read(ShootGame.class.getResource("hero_w.png"));
            e_0 = ImageIO
                    .read(ShootGame.class.getResource("timg0.jpg"));
            e_1 = ImageIO
                    .read(ShootGame.class.getResource("timg1.jpg"));
            e_2 = ImageIO
                    .read(ShootGame.class.getResource("timg2.jpg"));
            e_3 = ImageIO
                    .read(ShootGame.class.getResource("timg3.jpg"));
            e_4 = ImageIO
                    .read(ShootGame.class.getResource("timg4.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void paint(Graphics g) {
    	// TODO Auto-generated method stub
    	super.paint(g);
    	g.drawImage(background, 0,background_y, null);
    	paintHero(g);
    	paintBullets(g); // ���ӵ�
        paintFlyingObjects(g); // ��������
        paintScore(g); // ������
        paintState(g); // ����Ϸ״̬
    }

	private void paintHero(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(hero.getimg(), hero.getx(), hero.gety(), null);
	}
	
    public void paintBullets(Graphics g) {
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            g.drawImage(b.getimg(), b.getx() - b.getw() / 2, b.gety(),null);
        }
    }

    public void paintFlyingObjects(Graphics g) {
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            g.drawImage(f.getimg(), f.getx(), f.gety(), null);
        }
    }

    public void paintScore(Graphics g) {
        int x = 10; // x����
        int y = 25; // y����
        Font font = new Font("΢���ź�", Font.BOLD, 14); // ����
        g.setColor(Color.white);
        g.setFont(font); // ��������
        g.drawString("SCORE:" + score, x, y); // ������
        y += 20; // y������20
        g.drawString("LIFE:" + hero.getLife(), x, y); // ����
        y += 20; // y������20
        g.drawString("maker:��Խ�ĸ���",x,y); 
        
    }

    public void paintState(Graphics g) {
        switch (state) {
        case START: // ����״̬
            g.drawImage(start, 0, 0, null);
            break;
        case PAUSE: // ��ͣ״̬
            g.drawImage(pause, 0, 0, null);
            break;
        case GAMEOVER: // ��Ϸ��ֹ״̬
            g.drawImage(gameover, 0, 0, null);
            break;
        }
    }
    

	private void action() {
		// TODO Auto-generated method stub
		MouseAdapter l=new MouseAdapter() {
			@Override
            public void mouseMoved(MouseEvent e) { // ����ƶ�
                if (state == RUNNING) { // ����״̬���ƶ�Ӣ�ۻ�--�����λ��
                    int x = e.getX();
                    int y = e.getY();
                    hero.moveto(x, y);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) { // ������
                if (state == PAUSE) { // ��ͣ״̬������
                    state = RUNNING;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) { // ����˳�
                if (state != GAMEOVER&&state!=START) { // ��Ϸδ��������������Ϊ��ͣ
                    state = PAUSE;
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) { // �����
                switch (state) {
                case START:
                    state = RUNNING; // ����״̬������
                    break;
                case GAMEOVER: // ��Ϸ�����������ֳ�
                    flyings = new FlyingObject[0]; // ��շ�����
                    bullets = new Bullet[0]; // ����ӵ�
                    hero = new Hero(); // ���´���Ӣ�ۻ�
                    score = 0; // ��ճɼ�
                    state = START; // ״̬����Ϊ����
                    break;
                }
            }
		};
		addMouseListener(l);
		addMouseMotionListener(l);
		timer = new Timer(); // �����̿���
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (state == RUNNING) { // ����״̬
                    enterAction(); // �������볡
                    stepAction(); // ��һ��
                    shootAction(); // �ɻ����
                    bangAction(); // �ӵ��������
                    outOfBoundsAction(); // ɾ��Խ������Ｐ�ӵ�
                    checkGameOverAction(); // �����Ϸ����
                }
                background_y+=del_background_y;
                repaint(); // �ػ棬����paint()����
//                System.out.println(bullets.length);
            }

        }, intervel, intervel);
	}
	int flyEnteredIndex = 0; // �������볡����

    public void enterAction() {
        flyEnteredIndex++;
        if (flyEnteredIndex % 40 == 0) { // 400��������һ��������--10*40
            FlyingObject obj = nextOne(); // �������һ��������
            flyings = Arrays.copyOf(flyings, flyings.length + 1);
            flyings[flyings.length - 1] = obj;
        }
    }

    public void stepAction() {
        for (int i = 0; i < flyings.length; i++) { // ��������һ��
            FlyingObject f = flyings[i];
            f.step();
        }

        for (int i = 0; i < bullets.length; i++) { // �ӵ���һ��
            Bullet b = bullets[i];
            b.step();
        }
//        hero.step(); // Ӣ�ۻ���һ��
    }
    /*
    public void flyingStepAction() {
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            f.step();
        }
    }
	*/
    int shootIndex = 0; // �������

    public void shootAction() {
        shootIndex++;
        if (shootIndex % 30 == 0) { // 300���뷢һ��
            Bullet[] bs = hero.shoot(); // Ӣ�۴���ӵ�
//            System.out.println(bs.length);
            int pre_length=bullets.length;
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // ����
//            System.out.println(bullets.length);
            if(bs.length==1) {
//            	System.out.println("hehe");
            	bullets[pre_length]=bs[0];
            }
            if(bs.length==2) {
            	bullets[pre_length]=bs[0];
            	bullets[pre_length+1]=bs[1];
            }
//            System.out.println(bullets.length);
        }
        
        if (shootIndex % 200 == 0) { // 100���뷢һ��
        	
        	for (int i = 0; i < flyings.length; i++) {
        		Bullet[] bs = flyings[i].shoot(); // �л�����ӵ�
//              System.out.println(bs.length);
        		int pre_length=bullets.length;
        		bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // ����
//              System.out.println(bullets.length);
        		if(bs.length==1) {
//              	System.out.println("hehe");
              	bullets[pre_length]=bs[0];
        		}
        	}     
//            System.out.println(bullets.length);
        }
        
//        System.out.println(bullets.length);
    }

    public void bangAction() {
    	int index = 0;
    	Bullet[] bulletLives = new Bullet[bullets.length];
        for (int i = 0; i < bullets.length; i++) { // ���������ӵ�
            Bullet b = bullets[i];
            // �ӵ��ͷ�����֮�����ײ���
            if(!bang(b)) {
//            	System.out.println("hehe");
            	bulletLives[index++] = b;
            }
            else {
            	/*
            	System.out.println(b.x);
            	System.out.println(b.y);
            	System.out.println(hero.x);
            	System.out.println(hero.y);
            	*/
            }
        }
        bullets = Arrays.copyOf(bulletLives, index);
//        if(index!=bullets.length) System.out.println("hehe");
    }

    public void outOfBoundsAction() {
        int index = 0; // ����
        FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // ���ŵķ�����
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            if (!f.outofbounds()) {
                flyingLives[index++] = f; // ��Խ�������
            }
        }
        flyings = Arrays.copyOf(flyingLives, index); // ����Խ��ķ����ﶼ����

        index = 0; // ��������Ϊ0
        Bullet[] bulletLives = new Bullet[bullets.length];
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            if (!b.outofbounds()) {
                bulletLives[index++] = b;
            }
        }
        bullets = Arrays.copyOf(bulletLives, index); // ����Խ����ӵ�����
    }

    public void checkGameOverAction() {
        if (isGameOver()) {
            state = GAMEOVER; // �ı�״̬
        }
    }

    public boolean isGameOver() {
        
        for (int i = 0; i < flyings.length; i++) {
            int index = -1;
            FlyingObject obj = flyings[i];
            if (hero.hit(obj)) { // ���Ӣ�ۻ���������Ƿ���ײ
                hero.subLife(); // ����
                hero.setDoubleFire(0); // ˫���������
                index = i; // ��¼���ϵķ���������
            }
            if (index != -1) {
                FlyingObject t = flyings[index];
                flyings[index] = flyings[flyings.length - 1];
                flyings[flyings.length - 1] = t; // ���ϵ������һ�������ｻ��

                flyings = Arrays.copyOf(flyings, flyings.length - 1); // ɾ�����ϵķ�����
            }
        }
        
        return hero.getLife() <= 0;
    }

    public boolean bang(Bullet bullet) {
        int index = -1; // ���еķ���������
        if(bullet.belong==0) { //Ӣ�ۻ����ӵ�
        	for (int i = 0; i < flyings.length; i++) {
                FlyingObject obj = flyings[i];
                if (obj.shootby(bullet)) { // �ж��Ƿ����
                    index = i; // ��¼�����еķ����������
                    break;
                }
            }
            if (index != -1) { // �л��еķ�����
                FlyingObject one = flyings[index]; // ��¼�����еķ�����

                FlyingObject temp = flyings[index]; // �����еķ����������һ�������ｻ��
                flyings[index] = flyings[flyings.length - 1];
                flyings[flyings.length - 1] = temp;

                flyings = Arrays.copyOf(flyings, flyings.length - 1); // ɾ�����һ��������(�������е�)

                // ���one������(���˼ӷ֣�������ȡ)
                if (one instanceof Enemy) { // ������ͣ��ǵ��ˣ���ӷ�
                    Enemy e = (Enemy) one; // ǿ������ת��
                    score += e.getscore(); // �ӷ�
                } else if (one instanceof Award) { // ��Ϊ���������ý���
                    Award a = (Award) one;
                    int type = a.gettype(); // ��ȡ��������
                    switch (type) {
                    case Award.DOUBLE_FIRE:
                        hero.addDoubleFire(); // ����˫������
                        break;
                    case Award.life:
                        hero.addLife(); // ���ü���
                        break;
                    }
                }
            }
        }
        else { //�л����ӵ�
        	if(hero.shootby(bullet)) {
        		index=0;
        		hero.subLife();
//        		System.out.println(hero.getLife());
        	}
        }
        return index != -1;
    }

    public FlyingObject nextOne() {
        Random random = new Random();
        int type = random.nextInt(20); // [0,20)
        if (type == 0) {
            return new Bee();
        } else {
            return new Airplane();
        }
    }
}
