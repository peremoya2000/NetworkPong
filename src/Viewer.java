import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Viewer extends Canvas implements Runnable{
	private BufferedImage img;
	private BufferedImage background;
	private byte[] backup;
	private BufferStrategy bs;
	private Graphics2D graph2d;
	private Graphics g;
	private Thread myThread;
	private Ball ball;
	private Stick stick;
	private Score score;
	
	public Viewer(Ball b) {
		this.setVisible(true);
		try {
			this.background=ImageIO.read(new File("sample.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		img= new BufferedImage(600, 600, BufferedImage.TYPE_4BYTE_ABGR);
		graph2d= img.createGraphics();
		g=this.getGraphics();
		ball = b;
		
		this.myThread= new Thread(this);
	}
	
	public void init() {
		this.createBufferStrategy(2);
		bs=this.getBufferStrategy();
		
		int size=img.getData().getDataBuffer().getSize();
		backup= new byte[size];
		for(int i=0; i<size; ++i) {
			backup[i]=0;
		}
		
		myThread.start();
	}
	
	@Override
	public void run() {
		while(true) {
			myPaint();
			try {
				Thread.sleep(32);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
		
	public synchronized void myPaint() {
		g=bs.getDrawGraphics();
		if (g==null) {
			return;
		}
		img.getRaster().setDataElements(0, 0,600,600, backup);
		if (ball!=null)
			ball.paintSelf(graph2d);
		if (stick!=null)
			stick.paintSelf(graph2d);
		if(score!=null) {
			score.paintSelf(graph2d);
		}
		g.drawImage(background,0,0,600,600,null);
		g.drawImage(img,0,0,600,600,null);
		bs.show();
		g.dispose();
	}
	
	public void paint(Graphics g) {}
	
	public void setBall(Ball b) {
		this.ball = b;
	}

	public void setStick(Stick stick) {
		this.stick = stick;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	
	
}

