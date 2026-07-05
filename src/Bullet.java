import java.awt.*;

import javax.swing.ImageIcon;
public class Bullet {
	private Image bulletImage;

	private int x,y;
	private int width = 10, height = 20;
	private int speed;
	private boolean visible;

	public Bullet(int x, int y){
		this.x = x;
		this.y = y;
		this.speed = 14;
		this.visible = true;

		ImageIcon icon = new ImageIcon("assets/airplan/shot.png");
		bulletImage = icon.getImage();
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
		if(bulletImage != null){
			brush.drawImage(bulletImage, x, y,width,height, null);
		}else{
		brush.setColor(Color.YELLOW);
		brush.fillRect(x, y, width, height);
		}
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
