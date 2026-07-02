import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class RapidFirePowerup extends Powerup {
    private int r;
    
    public RapidFirePowerup(int x, int y){
        super(x, y);
        this.r = 30;
    }

    public void draw(Graphics brush){
        if(isVisible()){
            brush.setColor(Color.YELLOW);
            brush.fillOval(getX(), getY(), r, r);
        }
    }
    public Rectangle getBounds(){
        return new Rectangle(getX() ,getY(), r, r);
    }

    public void applyEffect(Plane playerPlane){
        playerPlane.setRapidFire();
        playerPlane.setBulletCooldown(100);
        playerPlane.setRapidFireStartTime();
    }
}
