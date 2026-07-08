package entities;

import main.*;
import ui.*;
import game.*;
import entities.*;
import database.*;
import audio.*;

import java.awt.*;

import javax.swing.ImageIcon;

public class ZigzagEnemy extends Enemy{
    private int zigzagCounter = 0;
    private int zigzagDirection = 1;

    private Image chickenImage;

    public ZigzagEnemy(int health, int x, int y){
        super(x, y);
        setHealth(health);

        ImageIcon icon = new ImageIcon("assets/chicken/zigzag_chicken.png");
        chickenImage = icon.getImage();
    }

    @Override
    public int getScoreValue(){
        return 20;
    }

    public void updateZigzag(){
        zigzagCounter += (zigzagDirection * 2);
        if(zigzagCounter > 15){
            zigzagDirection = -1;
        }
        else if(zigzagCounter < -15){
            zigzagDirection = 1;
        }
        setX(getX() + (zigzagDirection * 2));
    }

    @Override
    public void draw(Graphics brush){
        if(isAlive()){
            if(chickenImage != null){
                brush.drawImage(chickenImage, getX(), getY(), getWidth(), getHeight(), null);
            }
            else{
                brush.setColor(Color.ORANGE);
                brush.fillRect(getX(), getY(), getWidth(), getHeight());
            }
        }    
    }

}