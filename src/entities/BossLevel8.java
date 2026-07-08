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

public class BossLevel8 extends Boss {
    private Image bossImage;

    private long lastAttackTime = 0;
    private int attackCooldown = 1000;

    private double currentY;
    private double verticalSpeed = 1.0;
    private int verticalDirection = 1;
    private double startY;

    public BossLevel8() {
        super(300, 50, 150, 150, 100, 2.0);
        currentY = getY();
        startY = getY();

        ImageIcon icon = new ImageIcon("assets/chicken/boss2.png");
        bossImage = icon.getImage();
    }

    @Override
    public void move(int screenWidth) {
        super.move(screenWidth);

        currentY += verticalSpeed * verticalDirection;
        if (currentY > startY + 100 || currentY < startY) {
            verticalDirection *= -1;
        }
        setY(currentY);
    }

    @Override
    public ArrayList<Egg> generateEggs() {
        ArrayList<Egg> newEggs = new ArrayList<>();
        long now = System.currentTimeMillis();

        if (now - lastAttackTime >= attackCooldown) {
            lastAttackTime = now;
            int x = getX() + (getWidth() / 2);
            int y = getY() + (getHeight() / 2);

            newEggs.add(new Egg(x, y, 0, -1, 5));
            newEggs.add(new Egg(x, y, 0, 1, 5));
            newEggs.add(new Egg(x, y, -1, 0, 5));
            newEggs.add(new Egg(x, y, 1, 0, 5));
            newEggs.add(new Egg(x, y, 1, -1, 5));
            newEggs.add(new Egg(x, y, -1, -1, 5));
            newEggs.add(new Egg(x, y, 1, 1, 5));
            newEggs.add(new Egg(x, y, -1, 1, 5));
        }
        return newEggs;
    }

    @Override
    public void drawBoss(Graphics brush) {
        if (bossImage != null) {
            brush.drawImage(bossImage, getX(), getY(), getWidth(), getHeight(), null);
        } else {
            brush.setColor(Color.RED);
            brush.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }

}
