import java.awt.*;
public class Bullet {
	private int x,y;
	private int width = 6, height = 12;
	private int speed;
	private boolean visible;

	public Bullet(int x, int y){
		this.x = x;
		this.y = y;
		this.speed = 14;
		this.visible = true;
	}

	public void move() {
		this.y-=this.speed;
		if(y<0) {
			this.visible = false;
		}
	}

	public void draw(Graphics brush) {
		if(!visible) {
			return;
		}
		brush.setColor(Color.YELLOW);
		brush.fillRect(x, y, width, height);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void destroy() {
		this.visible = false;
	}

	public boolean isVisible() {
		return this.visible;
	}
}
