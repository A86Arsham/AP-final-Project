import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy {
	private double x;
	private double y;
	private int width;
	private int height;
	private int health;
	private boolean isAlive;
	private long lastEggTime = 0;
	private int eggCooldown = 3000;

	private boolean movingToCell = false;

	public abstract int getScoreValue();

	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 30;
		this.height = 30;
		this.isAlive = true;
	}


	public int getX() {
		return (int)x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getY() {
		return (int)y;
	}

	public void setY(double y) {
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

	public void setEggCooldown(int ms) {
	    this.eggCooldown = ms;
	}

	public void takeDamage() {
		health--;
		if(health<=0) {
			isAlive = false;
		}
	}

	public void draw(Graphics brush) {
		if(isAlive) {
			brush.setColor(Color.RED);
			brush.fillRect((int)x, (int)y, width, height);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,width,height);
	}

	public Egg eggDrop() {
		long now = System.currentTimeMillis();
		if(now - lastEggTime >= eggCooldown) {
			lastEggTime = now;
			return new Egg((int)x + width/2, (int)y + height);
		}
		return null;
	}

	public void setMovingToCell(boolean v){
		this.movingToCell = v;
	}

	public boolean isMovingToCell(){
		return movingToCell;
	}

	public void moveTowardsCell(int targetCellX, int targetCellY){
		int speed = 3;
		int dx = targetCellX - (int)this.x;
		int dy = targetCellY - (int)this.y;
		double dist = Math.sqrt((dx * dx) + (dy * dy));
		if(dist < speed){
			this.x = targetCellX;
			this.y = targetCellY;
			this.movingToCell = false;
		}
		else{
			this.x += speed * dx / dist;
        	this.y += speed * dy / dist;
		}
	}

}
