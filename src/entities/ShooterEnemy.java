package entities;

import main.*;
import ui.*;
import game.*;
import entities.*;
import database.*;
import audio.*;

import java.awt.*;

import javax.swing.ImageIcon;

public class ShooterEnemy extends Enemy{
    private long lastHorizontalShotTime = 0;
    private int horizontalShotCooldown = 3000;

    private Image chickenImage;

    public ShooterEnemy(int health, int x, int y){
        super(x, y);
        setHealth(health);
        
        ImageIcon icon = new ImageIcon("assets/chicken/shooter_chicken.png");
        chickenImage = icon.getImage();
    }

    @Override
    public int getScoreValue(){
        return 25;
    }

    public Egg shootHorizonalEgg(int playerX){
        long now = System.currentTimeMillis();
        if(now - lastHorizontalShotTime >= horizontalShotCooldown){
            lastHorizontalShotTime = now;
            int direction;
            if(playerX < getX()){
                direction = -1;
            }
            else{
                direction = 1;
            }
            return new Egg(getX() + (getWidth() / 2) , (getY() + getHeight() / 2), direction, 0);
        
        }
        return null;
    }

    @Override
    public void draw(Graphics brush){
        if(isAlive()){
            if(chickenImage != null){
                brush.drawImage(chickenImage, getX(), getY(), getWidth(), getHeight(), null);
            }
            else{
                brush.setColor(Color.MAGENTA);
                brush.fillRect(getX(), getY(), getWidth(), getHeight());
            }
        }
    }
}
