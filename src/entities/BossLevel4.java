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
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class BossLevel4 extends Boss {
    private Image bossImage;

    private long lastAttackTime = 0;
    private int attackCooldown = 1500;

    private double currentY = getY();
    private double verticalSpeed = 0.5;
    private int verticalDirection = 1;
    private double startY = getY();

    public BossLevel4(){
        super(350, 50, 100, 100, 50, 1.5);

        ImageIcon icon = new ImageIcon("assets/chicken/boss1.png");
        bossImage = icon.getImage();
    }

    @Override
    public ArrayList<Egg> generateEggs(){
        ArrayList<Egg> newEggs = new ArrayList<>();
        long now = System.currentTimeMillis();

        if(now - lastAttackTime >= attackCooldown){
            lastAttackTime = now;
            int x = getX() + (getWidth() / 2);
            int y = getY() + (getHeight() / 2);

            newEggs.add(new Egg(x, y, 0, -1, 4));
            newEggs.add(new Egg(x, y, 0 , 1, 4));
            newEggs.add(new Egg(x, y, -1, 0, 4));
            newEggs.add(new Egg(x, y, 1, 0 ,4));
        }

        return newEggs;
    }

    @Override
    public void drawBoss(Graphics brush){
        if(bossImage != null){
            brush.drawImage(bossImage, getX(), getY(), getWidth(), getHeight(), null);
        }
        else{
            brush.setColor(Color.YELLOW);
            brush.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public void move(int screenWidth){
        super.move(screenWidth);

        currentY += verticalSpeed * verticalDirection;
        if(currentY > (startY + 30) || currentY < startY){
            verticalDirection *= -1;
        }
        setY(currentY);
    }
}
