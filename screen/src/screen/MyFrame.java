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
		f.add(p); //�ѻ����̵�������
		/*
		 ���붯ͼ
		JLabel imag=new JLabel(new ImageIcon("img/timg.gif"));
		imag.setBounds(0, 0, 125, 125);
		f.add(imag);
		 */
		f.addMouseListener(p);
		f.setUndecorated(true); //ȥ�߿�
		f.setDefaultCloseOperation(3); //��X�رմ���
		f.setSize(p.win_width,p.win_height); //���ڴ�С ��Ҫ����������Ļ ֻҪ�鿴��ʾ�����еķֱ��ʼ���
		f.setLocationRelativeTo(null); //���ھ���
		f.setVisible(true); //�ô��ڿɼ�
		p.create_snow();
		p.move();
	}
}
