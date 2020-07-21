import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Score{
	private short score;
	private boolean second;
	private String points;
	public Score(boolean isSecond) {
		this.score=0;
		this.second=isSecond;
	}
	public void paintSelf(Graphics g) {
		 g.setFont(new Font("MS Gothic", Font.BOLD, 22));
		 g.setColor(Color.WHITE);
		if(second) {
			points=String.valueOf(score);
		     g.drawString("Player2: "+points, 440, 40);
		}else {
			points=String.valueOf(score);
		     g.drawString("Player1: "+points, 40, 40);
		}
	}
	public int getScore() {
		return score;
	}
	public void setScore(short score) {
		this.score = score;
	}
	public void incrementScore() {
		this.score++;
	}

}