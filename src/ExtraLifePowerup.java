import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
public class ExtraLifePowerup extends Powerup {
    private int r;
   
    public ExtraLifePowerup(int x, int y){
        super(x, y);
        r = 30;
    }

    @Override
    public void draw(Graphics brush){
        if(isVisible()){
            brush.setColor(Color.RED);
            brush.fillOval(getX(), getY(), r, r);
        }
    }
    @Override
    public Rectangle getBounds(){
        return new Rectangle(getX() ,getY(), r, r);
    }
    @Override
    public void applyEffect(Plane playerPlane){
        playerPlane.setMaxLives(5);
        playerPlane.addLife();
    }
}
