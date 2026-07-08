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

import javax.swing.ImageIcon;

public class NormalEnemy extends Enemy{
	private Image chickenImage;
	
	public NormalEnemy (int health, int x, int y) {
		super(x, y);
		setHealth(health);

		ImageIcon icon = new ImageIcon("assets/chicken/normal_chicken.png");
		chickenImage = icon.getImage();
	}

	@Override
	public int getScoreValue(){
		return 10;
	}

	@Override
	public void draw(Graphics brush){
		if(isAlive()){
			if(chickenImage != null){
				brush.drawImage(chickenImage, getX(), getY(),getWidth() ,getHeight() ,null);
			}else{
				brush.setColor(Color.RED);
				brush.fillRect(getX(), getY(), getWidth(), getHeight());
			}
		}
	}
}
