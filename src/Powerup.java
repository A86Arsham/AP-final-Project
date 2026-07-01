import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Powerup {
    private int x;
    private int y;
    private int speed = 4;
    private boolean visible;

    public Powerup(int x, int y){
        this.x = x;
        this.y = y;
        this.visible = true;
    }

    public abstract void draw(Graphics brush);
    public abstract Rectangle getBounds();
    public abstract void applyEffect(Plane playerPlane);

    public void move(){
        this.y += speed;
    }

    public void destroy(){
        this.visible = false;
    }

    public boolean isVisible(){
        return visible;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
}
