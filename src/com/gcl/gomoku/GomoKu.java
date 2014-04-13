package com.gcl.gomoku;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class GomoKu extends JFrame {
	
	ImageIcon background = new ImageIcon(getClass().getResource("background.jpg"));
	static Image backgroundImage;
	public static final int GRID_X_MIN = 12;
	public static final int GRID_X_MAX = 372;
	public static final int GRID_Y_MIN = 76;
	public static final int GRID_Y_MAX = 436;
	public static final int R = 14;
	int X;
	int Y;
	static int count;
	int[][] mValue = new int[17][17];
	int[][] white = new int[17][17];
	BWThread thrd;
	int black_time;
	int white_time;
	int set_time;
	boolean isBlack = true;
	boolean isSetTime = false;
	String str;

	public GomoKu(){
		Container container = getContentPane();	
		JPanel panel = new JPanel();		
		panel.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
				count+=1;
				X = e.getX();
				Y = e.getY();
				
				System.out.println("X:" + X + "," +" Y:" + Y );
				if(X>GRID_X_MIN&&X<GRID_X_MAX&&Y>GRID_Y_MIN&&Y<GRID_Y_MAX){
					int m = (X-GRID_X_MIN)%20;
					int i = (X-GRID_X_MIN)/20;
					int n = (Y-GRID_Y_MIN)%20;
					int j = (Y-GRID_Y_MIN)/20;
					if(m > R/2&&i<15&&i>0)
						i = i+1;
					if(n > R/2&&j<15&&i>0)
						j = j+1;
					if(i>=16) i=16;
					if(j>=16) j=16;
					if(mValue[i][j]==0){
						if(isBlack){
							mValue[i][j] = 1;
						}
						else{
					
							mValue[i][j] = 2;
						}
						repaint();
						isBlack = !isBlack;
					
					}
					else  {
							JOptionPane.showMessageDialog(GomoKu.this, "此处已有棋子，请重下！");
					}
				
				System.out.println("i:" + i+ "," +"j:" +j);
				}
				
				String str_result = win();
				if(str_result!=null){
					JOptionPane.showMessageDialog(GomoKu.this, str_result);
					System.out.println(str_result);
					thrd.kill();
				}
				
				if(X>=400&&X<=470&&Y>=53&&Y<=83){
					int result_start =JOptionPane.showConfirmDialog(GomoKu.this, "确定要重新开始游戏？");
					if(result_start == 0){
						thrd = new BWThread();
						thrd.start();
						System.out.println("确定.");
					}
					else if(result_start == 1){
						System.out.println("否.");	
					}
					else{
						System.out.println("取消");
					}
				}
								
				if(X>=400&&X<=470&&Y>=105&&Y<=131){
					String result_set = JOptionPane.showInputDialog(GomoKu.this, "请输入您的设置时间(单位为分钟):");
					if(result_set!=null){
						isSetTime = true;
						set_time = Integer.parseInt(result_set)*60;
						System.out.println("确定.");
					}
					else{
						System.out.println("取消.");
					}
				}
				if(X>=400&&X<=470&&Y>=154&&Y<=181){
					JOptionPane.showMessageDialog(GomoKu.this, "这是一个五子棋游戏，操作...");
				}
				if(X>=400&&X<=470&&Y>=254&&Y<=282){
					int result_giveup = JOptionPane.showConfirmDialog(GomoKu.this, "确定要认输吗？");
					if(result_giveup == 0){
						thrd.kill();
						if(isBlack){
							JOptionPane.showMessageDialog(GomoKu.this, "白棋获胜！！！");
						}
						else
							JOptionPane.showMessageDialog(GomoKu.this, "黑棋获胜！！！");
					}
				}
				if(X>=400&&X<=470&&Y>=303&&Y<=332){
					JOptionPane.showMessageDialog(GomoKu.this, "此游戏由gcl制作.");
				}
				if(X>=400&&X<=470&&Y>=352&&Y<=382){
					int result_exit = JOptionPane.showConfirmDialog(GomoKu.this, "确定要退出游戏？");
					if(result_exit == 0){	
						System.exit(0);
					}
				}
			}
			

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});		
		panel.setOpaque(false);
		container.add(panel);

		setResizable(false);
		setVisible(true);		
		System.out.println("width: " + background.getIconWidth());
		System.out.println("height: " + background.getIconHeight());
		setBounds((Tools.getScreenWidth()-background.getIconWidth())/2,
		(Tools.getScreenHeight()-background.getIconHeight())/2,
		background.getIconHeight()+(getInsets().left+getInsets().right)+20,
		background.getIconHeight()+(getInsets().bottom+getInsets().top)
	);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBackImage(this,background,false);	
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new GomoKu();
		
	}
	
	public static void setBackImage(JFrame frame, ImageIcon icon,boolean isAutoSize){
		if(frame == null || icon == null)
			return;
		Container pane = frame.getContentPane();
		((JPanel)pane).setOpaque(false);
		JLayeredPane layerp = frame.getLayeredPane();
		Component[] coms = layerp.getComponentsInLayer(new Integer(Integer.MIN_VALUE));
		if(coms.length > 0){
			for(Component com : coms){
				layerp.remove(com);
			}
		
		}
		
		final JLabel lab = new JLabel(icon);		
		if(isAutoSize){
			icon.setImage(icon.getImage().getScaledInstance(frame.getSize().width, frame.getSize().height, Image.SCALE_SMOOTH));
		}
		lab.setBounds(0, 0,icon.getIconWidth(), icon.getIconHeight());
		layerp.add(lab, new Integer(Integer.MIN_VALUE));		
	}
	
	public void drawGrid(Graphics g){
		for(int x=GRID_X_MIN;x<=GRID_X_MAX;){
			g.drawLine(x, GRID_Y_MIN, x, GRID_Y_MAX);
			x=x+20;
		}		
		for(int y=GRID_Y_MIN;y<=GRID_Y_MAX;){
			g.drawLine(GRID_X_MIN, y, GRID_X_MAX, y);
			y=y+20;
		}					
	}	
	public void drawChess(Graphics g,Color color,int x,int y){
		g.setColor(color);
		g.fillOval(x*20+GRID_X_MIN-R/2, y*20+GRID_Y_MIN-R/2+20, R, R);
	}		
	@Override
	public void paint(Graphics arg0) {
		// TODO Auto-generated method stub
		super.paint(arg0);
		drawGrid(arg0);	
		arg0.drawString("黑棋 : " + formatTime(black_time) , 55, 473);
		arg0.drawString("白棋 : " + formatTime(white_time) , 290, 473);
		
			for(int i =0; i<17;i++){
				for(int j = 0;j<17;j++){
					if(mValue[i][j]==1)
						drawChess(arg0,Color.black,i,j);
					else if(mValue[i][j]==2){
						drawChess(arg0,Color.white,i,j);
					}
				}
			
			}				
	}
	
	public String formatTime(int time){
		int seconds;
		int minutes;
		if(isSetTime){
			minutes = time/60;
			seconds = time%60;
			if(seconds>=0&&seconds<10)
			{
				return minutes + ":" + "0" + seconds;
			}
			else
			{
				return minutes + ":" + seconds;
			}
		}
		else
			return "无限制时间";
	
	}
	class BWThread extends Thread implements Runnable{
		boolean alive = true;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			black_time = set_time;
			white_time = set_time;
			
			while(alive&&black_time>0&&white_time>0){
				
				try {
					Thread.sleep(1000);
					if(isBlack){
						black_time--;
						

						System.out.println(formatTime(black_time));

					}
					else{
						white_time--;
						System.out.println(formatTime(white_time));
					}
					repaint();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			if(black_time==0){
				JOptionPane.showMessageDialog(GomoKu.this, "白棋获胜！！！");
			}
			if(white_time==0){
				JOptionPane.showMessageDialog(GomoKu.this, "黑棋获胜！！！");
			}
		}
		
		public void kill(){
			
			alive = false;
		}
		
	}
	
	private String win(){
		//纵向4个连续就胜利
		for(int i = 0; i<=16;i++){
			for(int j=0;j<=13;j++){
				for(int k=1;k<=3;k++){
					if(mValue[i][j]!=0){
						if(mValue[i][j]!=mValue[i][j+k])
							break;
						else if(k==3){
							if(mValue[i][j]==1){
								str="黑棋胜利！！！";
							}
							else if(mValue[i][j]==2){
								str="黑棋胜利！！！";	
							}
						}
					}
				}
			}
		}
		
		//横向4个连续就胜利
		for(int i=0;i<=13;i++){
			for(int j=0;j<=16;j++){
				for(int k=1;k<=3;k++){
					if(mValue[i][j]!=0){
						if(mValue[i][j]!=mValue[i+k][j])
							break;
						else if(k==3){
							if(mValue[i][j]==1){
								str="黑棋胜利！！！";
							}
							else if(mValue[i][j]==2){
								str="黑棋胜利！！！";	
							}
						}
					}
				}
			}
		}
		//左上到右下，连续4个就胜利
		for(int i=3;i<=13;i++){
			for(int j=3;j<=13;j++){
				for(int k=1;k<=3;k++){
					if(mValue[i][j]!=0){
						if(mValue[i][j]!=mValue[i+k][j+k])
							break;
						else if(k==3){
							if(mValue[i][j]==1){
								str="黑棋胜利！！！";
							}
							else if(mValue[i][j]==2){
								str="黑棋胜利！！！";	
							}
						}
					}
				}
			}
		}
		
		//左下右上，连续4个就胜利
		for(int i=3;i<=13;i++){
			for(int j=3;j<=13;j++){
				for(int k=1;k<=3;k++){
					if(mValue[i][j]!=0){
						if(mValue[i][j]!=mValue[i+k][j-k])
							break;
						else if(k==3){
							if(mValue[i][j]==1){
								str="黑棋胜利！！！";
							}
							else if(mValue[i][j]==2){
								str="黑棋胜利！！！";	
							}
						}
					}
				}
			}
		}
		System.out.println(str);
		return str;
	}
}







