import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
public class ExtraLifePowerup extends Powerup {
    private int r;
    private Image powerupImage;
   
    public ExtraLifePowerup(int x, int y){
        super(x, y);
        r = 30;

        ImageIcon icon = new ImageIcon("assets/powerup2/heal.png");
        powerupImage = icon.getImage();
    }

    @Override
    public void draw(Graphics brush){
        if(isVisible()){
            if(powerupImage != null){
                brush.drawImage(powerupImage, getX(), getY(), r, r, null);
            }else{
            brush.setColor(Color.RED);
            brush.fillOval(getX(), getY(), r, r);
            }
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
