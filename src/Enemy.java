import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy {
	private int x;
	private int y;
	private int width;
	private int height;
	private int health;
	private boolean isAlive;

	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 40;
		this.height = 40;
		this.isAlive = true;
	}


	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void takeDamage() {
		health--;
		if(health<=0) {
			isAlive = false;
		}
	}

	public void draw(Graphics enemy_model) {
		if(isAlive) {
			enemy_model.setColor(Color.RED);
			enemy_model.fillRect(x, y, width, height);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x,y,width,height);
	}


}
