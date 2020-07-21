import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundEffects implements Runnable{
	Thread myThread;
	public SoundEffects() {
		this.myThread = new Thread(this);
	}
	
	public synchronized void beep() {
		try {
		    Clip clip = AudioSystem.getClip();
		    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
		    BallTask.class.getResourceAsStream("/plop.wav"));
		    clip.open(inputStream);
		    clip.start(); 
		} catch (Exception e) {
		    System.err.println(e.getMessage());
		}	
	}
	
	public synchronized void point() {
		try {
		    Clip clip = AudioSystem.getClip();
		    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
		    		BallTask.class.getResourceAsStream("/punt.wav"));
		    clip.open(inputStream);
		    clip.start(); 
		} catch (Exception e) {
		    System.err.println(e.getMessage());
		}	
	}
	
	public synchronized void playMusic() {
		myThread.start();
	}

	@Override
	public void run() {
		try {
		    Clip clip = AudioSystem.getClip();
		    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
		    		BallTask.class.getResourceAsStream("/e1m1.wav"));
		    clip.open(inputStream);
		    clip.start(); 
		} catch (Exception e) {
		    System.err.println(e.getMessage());
		}
	}
	
}
