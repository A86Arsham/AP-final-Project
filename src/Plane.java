import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Plane {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private int lives;

    public Plane() {
        this.width = 40;
        this.height = 40;
        this.x = 375;
        this.y = 500;
        this.speed = 5;
        this.lives = 3;
    }

    public void move(boolean left, boolean right, boolean up, boolean down, int screenWidth, int screenHeight) {
        if (left) x -= speed;
        if (right) x += speed;
        if (up) y -= speed;
        if (down) y += speed;

        if (x < 0) x = 0;
        if (x > screenWidth - width) x = screenWidth - width;

        if (y < screenHeight / 2) y = screenHeight / 2;
        if (y > screenHeight - height) y = screenHeight - height;
    }

    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(x, y, width, height);
    }

    public int getX() {
    	return x;
    }
    public int getY() {
    	return y;
    }
    public int getWidth() {
    	return width;
    }
    public int getHeight() {
    	return height;
    }
    public int getLives() {
    	return lives;
    }

    public void setLives(int lives) {
    	this.lives = lives;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public void respawn() {
        this.x = 375; 
        this.y = 500;
    }
}