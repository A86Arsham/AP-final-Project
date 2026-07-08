package entities;

import main.*;
import ui.*;
import game.*;
import entities.*;
import database.*;
import audio.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class RapidFirePowerup extends Powerup {
    private int r;
    private Image powerupImage;
    
    public RapidFirePowerup(int x, int y){
        super(x, y);
        this.r = 30;

        ImageIcon icon = new ImageIcon("assets/powerup2/fast_shot.png");
        powerupImage = icon.getImage();
    }

    public void draw(Graphics brush){
        if(isVisible()){
            if(powerupImage != null){
                brush.drawImage(powerupImage, getX(), getY(), r, r, null);
            }else{
                brush.setColor(Color.YELLOW);
                brush.fillOval(getX(), getY(), r, r);
            }
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
