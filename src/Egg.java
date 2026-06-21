import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Egg {
	private int x;
	private int y;
	private int width = 8;
	private int height = 12;
	private int speed = 4;
	private boolean visible;
	private int dx;
	private int dy;

	public Egg(int x, int y) {
		this.x = x;
		this.y = y;
		this.visible = true;
		this.dx = 0;
		this.dy = speed;
	}

	public Egg(int x, int y, int dx, int dy){
		this.x = x;
		this.y = y;
		this.visible = true;
		this.dx = dx * speed;
		this.dy = dy * speed;
	}

	public Egg(int x, int y, int dx, int dy, int speed){
		this.x = x;
		this.y = y;
		this.dx = dx * speed;
		this.dy = dy * speed;
		this.visible = true;
	}

	public int getY() {
		return this.y;
	}

	public void move() {
		this.y += this.dy;
		this.x += this.dx;
	}

	public void draw(Graphics brush) {
		if(visible) {
			brush.setColor(Color.WHITE);
			brush.fillOval(x, y, width, height);
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x ,y, width, height);
	}

	public void destroy() {
		this.visible = false;
	}

	public boolean isVisible() {
		return this.visible;
	}
}
