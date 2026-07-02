import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class AddFirePowerup extends Powerup {
    private int r;
    
    public AddFirePowerup(int x, int y){
        super(x, y);
        r = 30;
    }
    
    public void draw(Graphics brush){
        if(isVisible()){
            brush.setColor(Color.ORANGE);
            brush.fillOval(getX(), getY(), r, r);
        }
    }
    public Rectangle getBounds(){
        return new Rectangle(getX() ,getY(), r, r);
    }
    public void applyEffect(Plane playerPlane){
        playerPlane.addBulletAmount();
    }
}
