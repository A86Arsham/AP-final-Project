import java.awt.Color;
import java.awt.Graphics;

public class NormalEnemy extends Enemy{
	public NormalEnemy (int health, int x, int y) {
		super(x, y);
		setHealth(health);
	}

	@Override
	public int getScoreValue(){
		return 10;
	}

	@Override
	public void draw(Graphics brush){
		if(isAlive()){
			brush.setColor(Color.RED);
			brush.fillRect(getX(), getY(), getWidth(), getHeight());
		}
	}
}
