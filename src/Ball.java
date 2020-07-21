import java.awt.Color;
import java.awt.Graphics;

public class Ball implements Runnable{
	private Thread myThread; 
	private int x;
	private int y;
	private int xSpd;
	private int ySpd;
	private int width;
	private int height;
	private boolean go;
	private byte sleep;
	private BallTask bt;
	
	public Ball(BallTask bt) {
		this.bt=bt;
		this.myThread=new Thread(this);
		myThread.start();
	}
	public Ball(int x, int y, int xSpd, int ySpd,int w, int h, BallTask bt) {
		this.bt=bt;
		this.go=true;
		this.x=x;
		this.y=y;
		this.xSpd=xSpd;
		this.ySpd=ySpd;
		if(xSpd==0)this.xSpd=3;
		if(ySpd==0)this.ySpd=3;
		this.width=w;
		this.height=h;
		this.sleep=16;
		this.myThread=new Thread(this);
		myThread.start();
	}
	
	public void run() {
		while(go) {
			try {
				Thread.sleep(sleep);			
				move();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	//600 x 560
	
	public void move() throws InterruptedException {
		Stick stick=bt.getStick();
		if(y+ySpd<=0 || y+height+ySpd>560) {
			ySpd*=-1;
			bt.getSound().beep();
		}
		if(bt.isSecond()) {//right
			if(x+width<0) { //Send ball
				kill();
				bt.sendBall(this);
			}else if(x+width+xSpd>stick.getX() && (y >= stick.getY() && y < stick.getY()+stick.getHeight())){
				xSpd*=-1;
				bt.getSound().beep();
			}else if(x>600){
				//scored
				bt.pointScored();
				bt.getSound().point();
				xSpd=0;
				ySpd=0;
				y=280;
				x=10;
				Thread.sleep(600);
				xSpd=-4;
				ySpd=-3;
			}
		}else {//left
			if(x>600) { //send ball
				kill();
				bt.sendBall(this);
			}else if(x+xSpd<stick.getWidth() && (y >= stick.getY() && y < stick.getY()+stick.getHeight()))  {
				xSpd*=-1;
				bt.getSound().beep();
			}else if(x+width<0) {
				//scored
				bt.pointScored();
				bt.getSound().point();
				xSpd=0;
				ySpd=0;
				y=280;
				x=510;
				Thread.sleep(600);
				xSpd=4;
				ySpd=3;
			}
		}
		
		x+=xSpd;
		y+=ySpd;
		
	}
	
	
	public void paintSelf(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);
	}
	
	public void kill() {
		this.go=false;
	}
	
	public int getX() {
		return x;	
	}
	
	public int getY() {
		return y;	
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
		
	public int getxSpd() {
		return xSpd;
	}
	public void setxSpd(int xSpd) {
		this.xSpd = xSpd;
	}
	public int getySpd() {
		return ySpd;
	}
	public void setySpd(int ySpd) {
		this.ySpd = ySpd;
	}
	
}
