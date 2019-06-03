package screen;

import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class MyFrame {
	@SuppressWarnings("null")
	public static void main(String[] args) {
		JFrame f = new JFrame();
		MyPanel p = new MyPanel();
		f.add(p); //把画布铺到窗口上
		/*
		 加入动图
		JLabel imag=new JLabel(new ImageIcon("img/timg.gif"));
		imag.setBounds(0, 0, 125, 125);
		f.add(imag);
		 */
		f.addMouseListener(p);
		f.setUndecorated(true); //去边框
		f.setDefaultCloseOperation(3); //按X关闭窗口
		f.setSize(p.win_width,p.win_height); //窗口大小 若要铺满整个屏幕 只要查看显示设置中的分辨率即可
		f.setLocationRelativeTo(null); //窗口居中
		f.setVisible(true); //让窗口可见
		p.create_snow();
		p.move();
	}
}
