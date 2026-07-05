import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Explosion {
    private Image explosionImage;
    private int x, y;
    private int width = 50;
    private int height = 50;
    private int frameCount = 0;
    private int maxFrames = 15;
    private boolean visible;

    public Explosion(int x, int y){
        this.x = x;
        this.y = y;
        this.visible = true;

        ImageIcon icon = new ImageIcon("assets/airplan/Explosion.png");
        explosionImage = icon.getImage();
    }

    public void updateFrame(){
        frameCount++;
        if(frameCount >= maxFrames){
            visible = false;
        }
    }

    public void draw(Graphics brush){
        if(!visible){
            return;
        }
        if(explosionImage != null){
            brush.drawImage(explosionImage, x-(width/2), y-(width/2), width, height, null);
        }else{
            brush.setColor(new Color(255, 100, 0, 150));
            brush.fillOval(x - 20, y - 20, 40,40);
        }
    }

    public boolean isVisible(){
        return visible;
    }
     
}
