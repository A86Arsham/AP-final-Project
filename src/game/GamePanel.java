/**
 * game engine
 * handles the swing Timer loop, keyboard inputs , collision detection, level progression, entity spawning and drawing all graphics
 */

package game;

import main.*;
import ui.*;
import game.*;
import entities.*;
import database.*;
import audio.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	private Image normalBackgroundImage;
	private Image bossBackgroundImage;

	Random random = new Random();
	boolean leftPressed = false, rightPressed = false, upPressed = false, downPressed = false;
	long lastShotTime = 0;
	Timer gameTimer;
	Plane playerPlane;
	ArrayList<Powerup> powerups = new ArrayList<>();
	ArrayList<Cell> grid = new ArrayList<>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	ArrayList<Egg> eggs = new ArrayList<Egg>();
	ArrayList<Explosion> explosions = new ArrayList<>();

	double gridSpeed = 1.0;
	int gridDirection = 1;

	int score = 0;
	int currentLevel = 1;

	long gameStartTime;
	long lastTakenDamageTime;

	GameMain gameMain;

	Boss currentBoss = null;

	long lastEdgeHitTime = 0;
	
	boolean isPaused = false;

	LevelConfig[] levels = {
		new LevelConfig(new String[]{"Normal"}, 2, 1.0, 20, 3000, false),
		new LevelConfig(new String[]{"Normal", "Fast"}, 2, 1.5, 20, 2000, false),
		new LevelConfig(new String[]{"Normal", "Zigzag"}, 3, 2.0, 25, 1500, false),
		new LevelConfig(null, 0, 1.5, 0, 1500, true),
		new LevelConfig(new String[]{"Shooter", "Fast"}, 3, 2.5, 25, 1000, false),
		new LevelConfig(new String[]{"Zigzag", "Shooter"}, 4, 3.0, 30, 800, false),
		new LevelConfig(new String[]{"Normal", "Fast", "Zigzag", "Shooter"}, 4, 3.5, 30, 700, false),
		new LevelConfig(null, 0, 2.0, 0, 1000, true)
	};

	public GamePanel(GameMain gameMain) {
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		playerPlane = new Plane();
		gameTimer = new Timer(16,this);
    	gameStartTime = System.currentTimeMillis();
		lastTakenDamageTime = System.currentTimeMillis();
		this.gameMain = gameMain;


		ImageIcon normalIcon = new ImageIcon("assets/background/background.jpg");
		normalBackgroundImage = normalIcon.getImage();
		ImageIcon bossIcon = new ImageIcon("assets/background/background2.jpg");
		bossBackgroundImage = bossIcon.getImage();

		addComponentListener(new ComponentAdapter() {
		    @Override
		    public void componentShown(ComponentEvent e) {
		    	gameStartTime = System.currentTimeMillis();
		    	gameTimer.start();

		        requestFocusInWindow();

				gameMain.soundManager.playBackgroundMusic();

		    }
		});
		spawnGrid();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		playerPlane.updatePowerup();
		//plane movement
		playerPlane.move(leftPressed, rightPressed, upPressed, downPressed, getWidth(), getHeight());

		//boss movement and shooting
		if(currentBoss != null && currentBoss.isAlive()){
			currentBoss.move(getWidth());

			ArrayList<Egg> bossEggs = currentBoss.generateEggs();
			for(int i=0; i<bossEggs.size(); i++){
				eggs.add(bossEggs.get(i));
			}

			if(currentBoss.getBounds().intersects(playerPlane.getBounds()) && System.currentTimeMillis() - lastTakenDamageTime > 2000){
				playerPlane.setLives(playerPlane.getLives() - 1);
				if(playerPlane.getLives() <= 0){
					gameTimer.stop();
					gameMain.soundManager.playGameover();

					if (score > gameMain.currentUser.getHighestScore()) {
						gameMain.currentUser.setHighestScore(score);
					}
					if (currentLevel > gameMain.currentUser.getLastReachedLevel()) {
						gameMain.currentUser.setLastReachedLevel(currentLevel);
					}
					gameMain.db.updateUser(gameMain.currentUser);
					

					String username = gameMain.currentUser.getUsername();
					gameMain.db.saveGameHistory(username, score, currentLevel, gameMain.soundManager);
					JOptionPane.showMessageDialog(this, "GAME OVER! Final Score: " + score);
            		gameMain.switchScreen("menuScreen");
					return;
				}
				lastTakenDamageTime = System.currentTimeMillis();
				playerPlane.respawn();
			}
		}

		//plane taking damage
		for (Cell cell : grid) {
			Enemy chicken = cell.getOccChicken();
			if (chicken != null && chicken.isAlive() && chicken.getBounds().intersects(playerPlane.getBounds())) {
				if(!playerPlane.isShielded()){
					if (System.currentTimeMillis() - lastTakenDamageTime > 2000) { 
						playerPlane.setLives(playerPlane.getLives() - 1);
						lastTakenDamageTime = System.currentTimeMillis();
						playerPlane.respawn();
						
						if (playerPlane.getLives() <= 0) {
							gameTimer.stop();
							gameMain.soundManager.playGameover();
							
							if(gameMain.currentUser != null){
								if (score > gameMain.currentUser.getHighestScore()) {
									gameMain.currentUser.setHighestScore(score);
								}
								if (currentLevel > gameMain.currentUser.getLastReachedLevel()) {
									gameMain.currentUser.setLastReachedLevel(currentLevel);
								}
								gameMain.db.updateUser(gameMain.currentUser);
							}

							String username = gameMain.currentUser.getUsername();
							gameMain.db.saveGameHistory(username, score, currentLevel, gameMain.soundManager);

							JOptionPane.showMessageDialog(this, "GAME OVER! The Chickens Invaded!\nFinal Score: " + score);
							gameMain.switchScreen("menuScreen");
							return;
						}
					}
				}
			}
		}

		//chicken respawn
		if(!playerPlane.isFreezeBomb()){
			for(Cell cell : grid){
				Enemy chicken = cell.getOccChicken();
				if(chicken!=null && !chicken.isAlive() && !cell.isTheChickenRespawning()){
					cell.decreaseCounter();
					if(cell.getCounter() > 0){
						int spawnX;
						if (Math.random() < 0.5) {
							spawnX = 0;
						} 
						else {
							spawnX = 750;
						}
						int spawnY = 0;
						LevelConfig config = levels[currentLevel-1];
						Enemy replacement = createEnemy(config.enemyTypes, config.cellHealth, spawnX, spawnY, cell.getCol());
						replacement.setMovingToCell(true);
						cell.setOccChicken(replacement);
						cell.setChickenIsRespawning(true);
					}
					else{
						cell.setOccChicken(null);
					}
				}
				chicken = cell.getOccChicken();
				if (chicken != null && cell.isTheChickenRespawning()) {
					chicken.moveTowardsCell(cell.getX(), cell.getY());
					if (!chicken.isMovingToCell()) {
						cell.setChickenIsRespawning(false);
					}
				}
			}
		}
		//bullet damage
		for (int i = 0; i < bullets.size(); i++) {
    		Bullet b = (Bullet) bullets.get(i);
			if (!b.isVisible()) {
				continue;
			}
			for (int j = 0; j < grid.size(); j++) {
				Cell cell = (Cell) grid.get(j);
				Enemy chicken = cell.getOccChicken();
				if (chicken != null && chicken.isAlive() && b.getBounds().intersects(chicken.getBounds())) {
					chicken.takeDamage();
					b.destroy();
					if (!chicken.isAlive()) {
						score += chicken.getScoreValue();

						explosions.add(new Explosion(chicken.getX() + chicken.getWidth()/2, chicken.getY() + chicken.getHeight()/2));
						gameMain.soundManager.playCrash();

						if(Math.random() <= 1.0){
							int randomNumber = random.nextInt(5) + 1;
							Powerup powerup;
							switch(randomNumber){
								case 1:
									powerup = new AddFirePowerup(chicken.getX() + (chicken.getWidth()/2), chicken.getY() + chicken.getHeight());
									powerups.add(powerup);
									break;
								case 2:
									powerup = new RapidFirePowerup(chicken.getX() + (chicken.getWidth()/2), chicken.getY() + chicken.getHeight());
									powerups.add(powerup);
									break;
								case 3:
									powerup = new ExtraLifePowerup(chicken.getX() + (chicken.getWidth()/2), chicken.getY() + chicken.getHeight());
									powerups.add(powerup);
									break;
								case 4:
									powerup = new ShieldPowerup(chicken.getX() + (chicken.getWidth()/2), chicken.getY() + chicken.getHeight());
									powerups.add(powerup);
									break;
								case 5:
									powerup = new FreezeBombPowerup(chicken.getX() + (chicken.getWidth()/2), chicken.getY() + chicken.getHeight());
									powerups.add(powerup);
									break;
								default:
									break;

							}
						}
					}
					break;
				}
				
			}

			if(currentBoss != null && currentBoss.isAlive()){
				if(b.isVisible() && b.getBounds().intersects(currentBoss.getBounds())){
					currentBoss.takeDamage(1);
					b.destroy();
				}
			}
			
		}
		
		//grid movement
		if(!playerPlane.isFreezeBomb()){
			boolean hitEdge = false;
			if(System.currentTimeMillis() - lastEdgeHitTime > 1000){
				for (int i = 0; i < grid.size(); i++) {
					Cell cell = (Cell) grid.get(i);
					Enemy chicken = cell.getOccChicken();
					if (chicken != null && chicken.isAlive()) {
						if (chicken.getX() <= 0 || chicken.getX() + chicken.getWidth() >= getWidth()) {
							hitEdge = true;
							break;
						}
					}
				}
			}
			if (hitEdge) {
				lastEdgeHitTime = System.currentTimeMillis();
				gridDirection *= -1;
				for (int i = 0; i < grid.size(); i++) {
					Cell cell = (Cell) grid.get(i);
					cell.shift(0, 10);
					Enemy chicken = cell.getOccChicken();
					if (chicken != null && chicken.isAlive()) {
						chicken.setX(cell.getX());
					}
				}
			}
			for (int i = 0; i < grid.size(); i++) {
				Cell cell = (Cell) grid.get(i);
				cell.shift(gridDirection * gridSpeed, 0);
			}
		}

		//check if the grid hits the bottom
		boolean invaded = false;
		for(Cell cell : grid){
			Enemy chicken = cell.getOccChicken();
			if(chicken != null && chicken.isAlive()){
				if(chicken.getY() + chicken.getHeight() >= getHeight()){
					invaded = true;
					break;
				}
			}
		}
		if(invaded){
			gameTimer.stop();
			String username = gameMain.currentUser.getUsername();
			gameMain.db.saveGameHistory(username, score, currentLevel, gameMain.soundManager);

			if(score > gameMain.currentUser.getHighestScore()){
				gameMain.currentUser.setHighestScore(score);
			}
			if(currentLevel > gameMain.currentUser.getLastReachedLevel()){
				gameMain.currentUser.setLastReachedLevel(currentLevel);
			}
			gameMain.db.updateUser(gameMain.currentUser);

			if(gameMain.soundManager.endSound){
				gameMain.soundManager.playGameover();
			}

			JOptionPane.showMessageDialog(this, "The chickens have invaded! Game Over.");
   			gameMain.switchScreen("menuScreen");
			return;
		}

		//special enemy
		if(!playerPlane.isFreezeBomb()){
			for (Cell cell : grid) {
				Enemy chicken = cell.getOccChicken();
				if (chicken != null && chicken.isAlive() && !chicken.isMovingToCell()) {
					
					if (chicken instanceof ZigzagEnemy) {
						((ZigzagEnemy) chicken).updateZigzag();
					}
					
					if (chicken instanceof ShooterEnemy) {
						Egg horizontalEgg = ((ShooterEnemy) chicken).shootHorizonalEgg(playerPlane.getX());
						if (horizontalEgg != null) {
							eggs.add(horizontalEgg);
						}
					}
				}
			}
		}
		//eggs spawning
		if(!playerPlane.isFreezeBomb()){
			if (System.currentTimeMillis() - gameStartTime > 2000) {
				for (int i = 0; i < grid.size(); i++) {
					Cell cell = (Cell) grid.get(i);
					Enemy chicken = cell.getOccChicken();
					if (chicken != null && chicken.isAlive() && !chicken.isMovingToCell() && isOnFront(cell, grid)) {
						Egg egg = chicken.eggDrop();
						if (egg != null) {
							eggs.add(egg);
						}
					}
				}
			}	
		}

		//egg movement and damage
		if(!playerPlane.isFreezeBomb()){
			for (int i = 0; i < eggs.size(); i++) {
				Egg egg = (Egg) eggs.get(i);
				egg.move();
				if (egg.getY() > getHeight()) {
					egg.destroy();
				}
				if (egg.isVisible() && egg.getBounds().intersects(playerPlane.getBounds()) && (System.currentTimeMillis() - lastTakenDamageTime > 5000)) {
					if(!playerPlane.isShielded()){
						egg.destroy();

						explosions.add(new Explosion(playerPlane.getX() + playerPlane.getWidth()/2, playerPlane.getY() + playerPlane.getHeight()/2));
						gameMain.soundManager.playCrash();
						
						playerPlane.setLives(playerPlane.getLives() - 1);
						if (playerPlane.getLives() <= 0) {
							gameTimer.stop();
							gameMain.soundManager.playGameover();

							if(gameMain.currentUser != null){
								if (score > gameMain.currentUser.getHighestScore()) {
									gameMain.currentUser.setHighestScore(score);
								}
								if (currentLevel > gameMain.currentUser.getLastReachedLevel()) {
									gameMain.currentUser.setLastReachedLevel(currentLevel);
								}
								gameMain.db.updateUser(gameMain.currentUser);
							}

							String username = gameMain.currentUser.getUsername();
							gameMain.db.saveGameHistory(username, score, currentLevel, gameMain.soundManager);
							JOptionPane.showMessageDialog(this, "GAME OVER! The Chickens Invaded!\nFinal Score: " + score);
							gameMain.switchScreen("menuScreen");
							return;
						}
						playerPlane.respawn();
						lastTakenDamageTime = System.currentTimeMillis();
					}else{
						egg.destroy();
					}
				}
			}	
		}

		for(Powerup powerup : powerups){
			powerup.move();
			if(powerup.getY() > getHeight()){
				powerup.destroy();
			}
			if(powerup.isVisible() && powerup.getBounds().intersects(playerPlane.getBounds())){
				powerup.applyEffect(playerPlane);
				powerup.destroy();
			}
		}

		//bullet movement
		for (int i = 0; i < bullets.size(); i++) {
    		Bullet b = (Bullet) bullets.get(i);
    		b.move();
		}	
		
		for(int i = 0; i<explosions.size(); i++){
			Explosion exp = explosions.get(i);
			exp.updateFrame();
			if(!exp.isVisible()){
				explosions.remove(i);
			}
		}

		//spawn level
		if(!levels[currentLevel - 1].isBossLevel && grid.size() > 0 && isLevelComplete()){
			score += 200;
			currentLevel++;

			spawnGrid();
		}
		if(levels[currentLevel - 1].isBossLevel && currentBoss != null && !currentBoss.isAlive()){
			gameMain.soundManager.playCrash();
			
			if(currentLevel == 4){
				score += 500;
			}
			else if(currentLevel == 8){
				score += 1000;
				gameTimer.stop();
				gameMain.soundManager.playWinning();

				if(gameMain.currentUser != null){
					if (score > gameMain.currentUser.getHighestScore()) {
						gameMain.currentUser.setHighestScore(score);
					}
					if (currentLevel > gameMain.currentUser.getLastReachedLevel()) {
						gameMain.currentUser.setLastReachedLevel(currentLevel);
					}
					gameMain.db.updateUser(gameMain.currentUser);
				}

				String username = gameMain.currentUser.getUsername();
				gameMain.db.saveGameHistory(username, score, currentLevel, gameMain.soundManager);
				JOptionPane.showMessageDialog(this, "You win! Final Score: " + score);
				gameMain.switchScreen("menuScreen");
				return;
			}
			currentLevel++;
			currentBoss = null;
			spawnGrid();
		}

		repaint();
	}

	@Override
	public void paintComponent(Graphics brush) {
		super.paintComponent(brush);
		if(levels[currentLevel-1].isBossLevel){
			brush.drawImage(bossBackgroundImage, 0, 0, getWidth(), getHeight(), null);
		}
		else{
			brush.drawImage(normalBackgroundImage, 0, 0, getWidth(), getHeight(), null);
		}
        playerPlane.draw(brush);
        for(Cell cell : grid){
			Enemy chicken = cell.getOccChicken();
			if(chicken != null && chicken.isAlive()){
				chicken.draw(brush);
			}
		}
		if(currentBoss != null && currentBoss.isAlive()){
			currentBoss.drawBoss(brush);
			currentBoss.drawHealthBar(brush);
		}
		for(Explosion e : explosions){
			e.draw(brush);
		}
        for(Bullet b : bullets) {
        	b.draw(brush);
        }
        for(Egg egg : eggs) {
        	egg.draw(brush);
        }
		for(Powerup powerup : powerups){
			powerup.draw(brush);
		}

        brush.setColor(Color.WHITE);
        brush.setFont(new Font("Arial", Font.BOLD, 18));
        brush.drawString("Score: " + score, 10, 20);
        brush.drawString("Level: " + currentLevel, 10, 45);
        brush.drawString("Lives: " + playerPlane.getLives(), 10, 70);

		String username = gameMain.currentUser.getUsername();
		brush.drawString("Player: " + username, getWidth() - 160, 25);

		brush.drawString("Bullets: " + playerPlane.getBulletAmount(), getWidth() - 160, 50);

		int powerupY = 75;
		if(playerPlane.isShielded()){
			brush.setColor(new Color(0, 255, 255));
			brush.drawString("Shield Active", getWidth() - 160, powerupY);
			powerupY += 25;
		}
		if (playerPlane.isRapidFire()) {
            brush.setColor(Color.YELLOW);
            brush.drawString("Rapid Fire", getWidth() - 160, powerupY);
            powerupY += 25;
        }
        if (playerPlane.isFreezeBomb()) {
            brush.setColor(Color.WHITE);
            brush.drawString("Enemies Frozen!", getWidth() - 160, powerupY);
        }

		if(isPaused){
			brush.setColor(new Color(0,0,0,150));
			brush.fillRect(0, 0, getWidth(), getHeight());
			brush.setColor(Color.RED);
			brush.setFont(new Font("Arial", Font.BOLD, 60));
			brush.drawString("PAUSED", getWidth() / 2 - 125, getHeight() / 2);
		}
	}

	@Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) leftPressed = true;
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) rightPressed = true;
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) upPressed = true;
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) downPressed = true;
        if (keyCode == KeyEvent.VK_SPACE) {
        	// long currentTime = System.currentTimeMillis();
        	// if (currentTime - lastShotTime >= 300) {
        	// 	int bulletX = playerPlane.getX() + (playerPlane.getWidth() / 2);
        	// 	int bulletY = playerPlane.getY();
        	// 	bullets.add(new Bullet(bulletX, bulletY));
            //     lastShotTime = currentTime;
            // }
			if(!isPaused){
			playerPlane.shootBullet(bullets);
			gameMain.soundManager.playShoot();
			}	
		}
		if (keyCode == KeyEvent.VK_P){
			isPaused = !isPaused;
			if(isPaused){
				gameTimer.stop();
			}
			else{
				gameTimer.start();
			}
			repaint();
		}
		if (keyCode == KeyEvent.VK_ESCAPE){
			gameTimer.stop();
			isPaused = false;
			
			String username = gameMain.currentUser.getUsername();
			gameMain.db.saveGameHistory(username, score, currentLevel, gameMain.soundManager);
			if(score > gameMain.currentUser.getHighestScore()){
				gameMain.currentUser.setHighestScore(score);
			}
			if(currentLevel > gameMain.currentUser.getLastReachedLevel()){
				gameMain.currentUser.setLastReachedLevel(currentLevel);
			}
			gameMain.db.updateUser(gameMain.currentUser);
			
			gameMain.switchScreen("menuScreen");
		}
//---------------testing cheats------------------
		if (keyCode == KeyEvent.VK_4) {
			currentLevel = 4;
			spawnGrid(); 
		}
		if (keyCode == KeyEvent.VK_8) {
			currentLevel = 8;
			spawnGrid(); 
		}
		if(keyCode == KeyEvent.VK_N){
			currentLevel++;
			spawnGrid();
		}
//-----------------------------------------------
    }

	@Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) leftPressed = false;
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) rightPressed = false;
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) upPressed = false;
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) downPressed = false;
    }

	@Override
    public void keyTyped(KeyEvent e) {}

	public void spawnGrid() {
	    grid.clear();
		eggs.clear();
		bullets.clear();
		powerups.clear();
		LevelConfig config = levels[currentLevel - 1];

		if(config.isBossLevel){
			if(currentLevel == 4){
				currentBoss = new BossLevel4();
			}
			else if(currentLevel == 8){
				currentBoss = new BossLevel8();
			}
			return;
		}
		this.gridSpeed = config.gridSpeed;

	    for (int row = 0; row < 5; row++) {
	        for (int col = 0; col < 8; col++) {
	            int chickenX = 115 + (col * 80);
	            int chickenY = 40 + (row * 45);
				
				Enemy enemy = createEnemy(config.enemyTypes, config.cellHealth, chickenX, chickenY, col);
				enemy.setEggCooldown(config.eggCooldown);

				Cell cell = new Cell(row, col, chickenX, chickenY, config.cellHealth);
				cell.setOccChicken(enemy);
				grid.add(cell);
	        }
	    }
	}

	public boolean isOnFront(Cell targetCell,ArrayList<Cell> grid) {
		for(Cell otherCell : grid) {
			Enemy chicken = otherCell.getOccChicken();
			if(chicken != null && chicken.isAlive() && targetCell.getCol() == otherCell.getCol() && targetCell.getRow() < otherCell.getRow()) {
				return false;
			}
		}
		return true;
	}

	public void resetGame() {
		score = 0;
		currentLevel = 1;
		playerPlane.setMaxLives(3);
		playerPlane.setLives(3);
		playerPlane.respawn();
		bullets.clear();
		eggs.clear();
		powerups.clear();
		explosions.clear();
		gameStartTime = System.currentTimeMillis();
		spawnGrid();
		gameTimer.start();
		currentBoss = null;
		playerPlane.resetPowerups();
	}

	private Enemy createEnemy(String[] types, int health, int x, int y, int col){
		String type;
		if(types.length == 1){
			type = types[0];
		}
		else if(types.length == 2){
			int index = col % 2;
			type = types[index];
		}
		else{
			int index = col % 3;
			type = types[index];
		}

		if(type.equals("Normal")){
			return new NormalEnemy(health, x, y);
		}
		else if(type.equals("Fast")){
			return new FastEnemy(health, x, y);
		}
		else if(type.equals("Zigzag")){
			return new ZigzagEnemy(health, x, y);
		}
		else if(type.equals("Shooter")){
			return new ShooterEnemy(health, x, y);
		}
		else{
			throw new IllegalArgumentException("Unknown enemy type: " + type);
		}
	}

	private boolean isLevelComplete(){
		for(Cell cell : grid){
			Enemy chicken = cell.getOccChicken();
			if(chicken != null && chicken.isAlive()){
				return false;
			}
			if(cell.isTheChickenRespawning()){
				return false;
			}
			if(cell.getCounter() > 0){
				return false;
			}
		}
		return true;
	}
}
