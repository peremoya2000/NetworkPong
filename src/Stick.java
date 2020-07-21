import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Stick implements Runnable{
	private Thread myThread; 
	private boolean go;
	private int x,y;
	private boolean isSecond;
	private final int width = 25;
	private final int height = 90;
	private byte sleep;
	private boolean w=false;
	private boolean s=false;
	private boolean up=false;
	private boolean down=false;
	private byte dir=0;
	private float spd=0;
	private final float maxSpd= 5;
	
	public Stick(boolean isSecond, Viewer v) {
		KeyListener listener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
			    int key = e.getKeyCode();
			    if (key == KeyEvent.VK_W) {
			    	w=true;
			    }
			    if (key == KeyEvent.VK_S) {
			    	s=true;
			    }
			    if (key == KeyEvent.VK_UP) {
			        up=true;
			    }
			    if (key == KeyEvent.VK_DOWN) {
			        down=true;
			    }
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			    int key = e.getKeyCode();
			    if (key == KeyEvent.VK_W) {
			    	w=false;
			    }
			    if (key == KeyEvent.VK_S) {
			    	s=false;
			    }
			    if (key == KeyEvent.VK_UP) {
			        up=false;
			    }
			    if (key == KeyEvent.VK_DOWN) {
			        down=false;
			    }
			}

			@Override
			public void keyTyped(KeyEvent e) {				
			}
        };
        v.addKeyListener(listener);
		this.go=true;
		this.isSecond=isSecond;
		this.sleep=16;
		this.y=280;
		if(isSecond) {
			this.x=(int) (600-(width*1.5));		
		}else {
			this.x=0;
		}
		this.myThread=new Thread(this);
		myThread.start();
	}
	
	public void run() {
		while(go) {
			try {
				myThread.sleep(sleep);			
				move();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void move() {
		byte u;
		byte d;
		if(isSecond) {
			u=(byte)(up?1:0);
			d=(byte)(down?1:0);
		}else {
			u=(byte)(w?1:0);
			d=(byte)(s?1:0);
		}
		
		dir=(byte) (d-u);
		
		if(Math.abs(spd)<maxSpd)spd+=dir*.5;
		if (dir==0)spd=0;
		
		if(y+spd>0&&y+spd<560 - height) {
			y+=spd;
		}else {
			spd=0;
		}
	}
	//600 x 560
	
	public void paintSelf(Graphics g) {
		if(isSecond) {
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.BLUE);
		}
		g.fillRect(x,y,width,height);
	}
	
	public void kill() {
		this.go=false;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	} 

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	
}