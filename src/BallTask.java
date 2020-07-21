import java.net.Socket;
import javax.swing.JFrame;

public class BallTask extends JFrame{
	private ServerConnection sc;
	private ClientConnection cc;
	private RemoteBallTask rbt;
	private Viewer v;
	private Ball ball;
	private Stick stick;
	private boolean second;
	private Score score;
	private SoundEffects sound;
	public static void main(String[] args) {
		BallTask s= new BallTask();
	}
	
	public BallTask() {
		rbt= new  RemoteBallTask(this);
		this.ball = null;
		System.out.println("New Screen");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(600, 600);
		this.setVisible(true);
		v= new Viewer(ball);
		v.setSize(600, 600);
		this.add(v);
		v.init();
		sc=new ServerConnection(this);
		sc.start();
		cc=new ClientConnection(this);
		cc.start();
	}
	
	public synchronized void createBall(Ball b) {
		this.ball=b;
		v.setBall(b);
	}
	
	public void setRemoteBallTaskSocket(Socket s) {
		rbt.setSocket(s);
	}
	public boolean rbtActive() {
		return rbt.hasSocket();
	}
	
	public void ping() {
		rbt.ping();
	}
	
	public synchronized void sendBall(Ball b) {
		b.kill();
		this.ball = null;
		rbt.sendBall(b);
	}
	
	public synchronized void pointScored() {
		rbt.pointScored();
	}
	
	public void setClientPort(int port) {
		this.sound = new SoundEffects();
		if(port==666) {
			this.second = true;
			this.ball = new Ball(100, 100, 3, 3, 10, 10, this);
			sound.playMusic();
		}else {
			this.second = false;
		}	
		this.stick = new Stick(second, v);
		this.score = new Score(second);
		v.setBall(ball);
		v.setStick(stick);
		v.setScore(score);
		
		cc.setPort(port);
	}
	public void resetConnections() {
		if(ball!=null)ball.kill();
		this.ball=null;
		if(score!=null)score.setScore((short) 0);
		sc.reset();
	}

	public boolean isSecond() {
		return second;
	}

	public Stick getStick() {
		return stick;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}
	
	public SoundEffects getSound() {
		return sound;
	}
	
}
