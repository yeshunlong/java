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
	

	private int score = 0; // 得分
    private Timer timer; // 定时器
    private int intervel = 1000 / 100; // 时间间隔(毫秒)
    
    private int background_y = -6000+win_height;
    private int del_background_y = 1;
	
	public final static int win_width=400; //屏幕尺寸
	public final static int win_height=1080;
	private int state=0; //游戏状态
	private static final int START=0;
	private static final int RUNNING=1;
	private static final int PAUSE=2;
	private static final int GAMEOVER=3;
	public static BufferedImage background;
    public static BufferedImage start;
    public static BufferedImage airplane;
    public static BufferedImage bee;
    public static BufferedImage bullet; //英雄机子弹
    public static BufferedImage bullet_e; //敌机子弹
    public static BufferedImage hero0;
    public static BufferedImage pause;
    public static BufferedImage gameover;
    
    public static BufferedImage hero_w;
    public static BufferedImage e_0;
    public static BufferedImage e_1;
    public static BufferedImage e_2;
    public static BufferedImage e_3;
    public static BufferedImage e_4;

    
    private FlyingObject[] flyings = {}; // 敌机数组
//    private Airplane[] enemys = {}; 
    private Bullet[] bullets = {}; // 子弹数组
    private Hero hero = new Hero(); // 英雄机
    
    static { //初始化图片资源
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
    	paintBullets(g); // 画子弹
        paintFlyingObjects(g); // 画飞行物
        paintScore(g); // 画分数
        paintState(g); // 画游戏状态
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
        int x = 10; // x坐标
        int y = 25; // y坐标
        Font font = new Font("微软雅黑", Font.BOLD, 14); // 字体
        g.setColor(Color.white);
        g.setFont(font); // 设置字体
        g.drawString("SCORE:" + score, x, y); // 画分数
        y += 20; // y坐标增20
        g.drawString("LIFE:" + hero.getLife(), x, y); // 画命
        y += 20; // y坐标增20
        g.drawString("maker:王越的辅助",x,y); 
        
    }

    public void paintState(Graphics g) {
        switch (state) {
        case START: // 启动状态
            g.drawImage(start, 0, 0, null);
            break;
        case PAUSE: // 暂停状态
            g.drawImage(pause, 0, 0, null);
            break;
        case GAMEOVER: // 游戏终止状态
            g.drawImage(gameover, 0, 0, null);
            break;
        }
    }
    

	private void action() {
		// TODO Auto-generated method stub
		MouseAdapter l=new MouseAdapter() {
			@Override
            public void mouseMoved(MouseEvent e) { // 鼠标移动
                if (state == RUNNING) { // 运行状态下移动英雄机--随鼠标位置
                    int x = e.getX();
                    int y = e.getY();
                    hero.moveto(x, y);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) { // 鼠标进入
                if (state == PAUSE) { // 暂停状态下运行
                    state = RUNNING;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) { // 鼠标退出
                if (state != GAMEOVER&&state!=START) { // 游戏未结束，则设置其为暂停
                    state = PAUSE;
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) { // 鼠标点击
                switch (state) {
                case START:
                    state = RUNNING; // 启动状态下运行
                    break;
                case GAMEOVER: // 游戏结束，清理现场
                    flyings = new FlyingObject[0]; // 清空飞行物
                    bullets = new Bullet[0]; // 清空子弹
                    hero = new Hero(); // 重新创建英雄机
                    score = 0; // 清空成绩
                    state = START; // 状态设置为启动
                    break;
                }
            }
		};
		addMouseListener(l);
		addMouseMotionListener(l);
		timer = new Timer(); // 主流程控制
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (state == RUNNING) { // 运行状态
                    enterAction(); // 飞行物入场
                    stepAction(); // 走一步
                    shootAction(); // 飞机射击
                    bangAction(); // 子弹打飞行物
                    outOfBoundsAction(); // 删除越界飞行物及子弹
                    checkGameOverAction(); // 检查游戏结束
                }
                background_y+=del_background_y;
                repaint(); // 重绘，调用paint()方法
//                System.out.println(bullets.length);
            }

        }, intervel, intervel);
	}
	int flyEnteredIndex = 0; // 飞行物入场计数

    public void enterAction() {
        flyEnteredIndex++;
        if (flyEnteredIndex % 40 == 0) { // 400毫秒生成一个飞行物--10*40
            FlyingObject obj = nextOne(); // 随机生成一个飞行物
            flyings = Arrays.copyOf(flyings, flyings.length + 1);
            flyings[flyings.length - 1] = obj;
        }
    }

    public void stepAction() {
        for (int i = 0; i < flyings.length; i++) { // 飞行物走一步
            FlyingObject f = flyings[i];
            f.step();
        }

        for (int i = 0; i < bullets.length; i++) { // 子弹走一步
            Bullet b = bullets[i];
            b.step();
        }
//        hero.step(); // 英雄机走一步
    }
    /*
    public void flyingStepAction() {
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            f.step();
        }
    }
	*/
    int shootIndex = 0; // 射击计数

    public void shootAction() {
        shootIndex++;
        if (shootIndex % 30 == 0) { // 300毫秒发一颗
            Bullet[] bs = hero.shoot(); // 英雄打出子弹
//            System.out.println(bs.length);
            int pre_length=bullets.length;
            bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // 扩容
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
        
        if (shootIndex % 200 == 0) { // 100毫秒发一颗
        	
        	for (int i = 0; i < flyings.length; i++) {
        		Bullet[] bs = flyings[i].shoot(); // 敌机打出子弹
//              System.out.println(bs.length);
        		int pre_length=bullets.length;
        		bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // 扩容
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
        for (int i = 0; i < bullets.length; i++) { // 遍历所有子弹
            Bullet b = bullets[i];
            // 子弹和飞行物之间的碰撞检查
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
        int index = 0; // 索引
        FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // 活着的飞行物
        for (int i = 0; i < flyings.length; i++) {
            FlyingObject f = flyings[i];
            if (!f.outofbounds()) {
                flyingLives[index++] = f; // 不越界的留着
            }
        }
        flyings = Arrays.copyOf(flyingLives, index); // 将不越界的飞行物都留着

        index = 0; // 索引重置为0
        Bullet[] bulletLives = new Bullet[bullets.length];
        for (int i = 0; i < bullets.length; i++) {
            Bullet b = bullets[i];
            if (!b.outofbounds()) {
                bulletLives[index++] = b;
            }
        }
        bullets = Arrays.copyOf(bulletLives, index); // 将不越界的子弹留着
    }

    public void checkGameOverAction() {
        if (isGameOver()) {
            state = GAMEOVER; // 改变状态
        }
    }

    public boolean isGameOver() {
        
        for (int i = 0; i < flyings.length; i++) {
            int index = -1;
            FlyingObject obj = flyings[i];
            if (hero.hit(obj)) { // 检查英雄机与飞行物是否碰撞
                hero.subLife(); // 减命
                hero.setDoubleFire(0); // 双倍火力解除
                index = i; // 记录碰上的飞行物索引
            }
            if (index != -1) {
                FlyingObject t = flyings[index];
                flyings[index] = flyings[flyings.length - 1];
                flyings[flyings.length - 1] = t; // 碰上的与最后一个飞行物交换

                flyings = Arrays.copyOf(flyings, flyings.length - 1); // 删除碰上的飞行物
            }
        }
        
        return hero.getLife() <= 0;
    }

    public boolean bang(Bullet bullet) {
        int index = -1; // 击中的飞行物索引
        if(bullet.belong==0) { //英雄机的子弹
        	for (int i = 0; i < flyings.length; i++) {
                FlyingObject obj = flyings[i];
                if (obj.shootby(bullet)) { // 判断是否击中
                    index = i; // 记录被击中的飞行物的索引
                    break;
                }
            }
            if (index != -1) { // 有击中的飞行物
                FlyingObject one = flyings[index]; // 记录被击中的飞行物

                FlyingObject temp = flyings[index]; // 被击中的飞行物与最后一个飞行物交换
                flyings[index] = flyings[flyings.length - 1];
                flyings[flyings.length - 1] = temp;

                flyings = Arrays.copyOf(flyings, flyings.length - 1); // 删除最后一个飞行物(即被击中的)

                // 检查one的类型(敌人加分，奖励获取)
                if (one instanceof Enemy) { // 检查类型，是敌人，则加分
                    Enemy e = (Enemy) one; // 强制类型转换
                    score += e.getscore(); // 加分
                } else if (one instanceof Award) { // 若为奖励，设置奖励
                    Award a = (Award) one;
                    int type = a.gettype(); // 获取奖励类型
                    switch (type) {
                    case Award.DOUBLE_FIRE:
                        hero.addDoubleFire(); // 设置双倍火力
                        break;
                    case Award.life:
                        hero.addLife(); // 设置加命
                        break;
                    }
                }
            }
        }
        else { //敌机的子弹
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
