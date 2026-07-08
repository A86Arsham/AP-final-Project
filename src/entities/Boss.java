package entities;

import main.*;
import ui.*;
import game.*;
import entities.*;
import database.*;
import audio.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Boss {
    private double x;
    private double y;
    private int width;
    private int height;
    private int health;
    private int maxHealth;
    private double speed;
    private int direction;

    public Boss(double x, double y, int width, int height, int maxHealth, double speed){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.speed = speed;
        this.direction = 1;
    }

    public int getX(){
        return (int)this.x;
    }
    public int getY(){
        return (int)this.y;
    }
    public void setY(double newY){
        this.y = newY;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public void setDirection(int d){
        this.direction = d;
    }
    public int getDirection(){
        return this.direction;
    }
    public double getSpeed(){
        return this.speed;
    }

    public Rectangle getBounds(){
        return new Rectangle((int)this.x, (int)this.y, this.width, this.height);
    }

    public boolean isAlive(){
        if(this.health > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void takeDamage(int damage){
        this.health -= damage;
    }

    public void move(int screenWidth){
        this.x += (direction * speed);
        if(this.x <= 0 || this.x + width >= screenWidth){
            this.direction *= -1;
        }
    }

    public void drawBoss(Graphics brush){
        brush.setColor(Color.ORANGE);
        brush.drawRect((int)this.x, (int)this.y, this.width, this.height);
    }

    public void drawHealthBar(Graphics brush){
        int barWidth = 800;
        int barHeight = 30;
        int x = 0;
        int y = 0;

        brush.setColor(Color.RED);
        brush.fillRect(x, y, barWidth, barHeight);

        brush.setColor(Color.GREEN);
        brush.fillRect(x, y, (int)(barWidth * ((double)health / maxHealth)), barHeight);

        brush.setColor(Color.BLACK);
        brush.drawRect(x, y, barWidth, barHeight);
    }

    public abstract ArrayList<Egg> generateEggs();
}
