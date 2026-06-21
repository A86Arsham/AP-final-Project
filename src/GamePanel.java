import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	boolean leftPressed = false, rightPressed = false, upPressed = false, downPressed = false;
	long lastShotTime = 0;
	Timer gameTimer;
	Plane playerPlane;
//	ArrayList<Enemy> chickens = new ArrayList<Enemy>();
	ArrayList<Cell> grid = new ArrayList<>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	ArrayList<Egg> eggs = new ArrayList<Egg>();

	double gridSpeed = 1.0;
	int gridDirection = 1;

	int score = 0;
	int currentLevel = 1;

	long gameStartTime;
	long lastTakenDamageTime;

	GameMain gameMain;

	Boss currentBoss = null;

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

		addComponentListener(new ComponentAdapter() {
		    @Override
		    public void componentShown(ComponentEvent e) {
		    	gameStartTime = System.currentTimeMillis();
		    	gameTimer.start();

		        requestFocusInWindow();
		    }
		});
		spawnGrid();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		playerPlane.move(leftPressed, rightPressed, upPressed, downPressed, getWidth(), getHeight());

		if(currentBoss != null && currentBoss.isAlive()){
			currentBoss.move(getWidth());

			ArrayList<Egg> bossEggs = currentBoss.generateEggs();
			for(int i=0; i<bossEggs.size(); i++){
				eggs.add(bossEggs.get(i));
			}

			if(currentBoss.getBounds().intersects(playerPlane.getBounds())){
				playerPlane.setLives(playerPlane.getLives() - 1);
				playerPlane.respawn();
			}
		}

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
					NormalEnemy replacement = new NormalEnemy(2, spawnX, spawnY);
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
			// if(chicken == null || !chicken.isAlive()){
			// 	if(cell.getCounter() > 0){
			// 		Enemy newChicken = new NormalEnemy(2, cell.getX(), cell.getY());
			// 		cell.setOccChicken(newChicken);
			// 		cell.decreaseCounter();
			// 	}
			// 	else{
			// 		cell.setOccChicken(null);
			// 	}
			// }
		}

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
						score += 10;
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
		

		boolean hitEdge = false;
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
		if (hitEdge) {
			gridDirection *= -1;
			for (int i = 0; i < grid.size(); i++) {
				Cell cell = (Cell) grid.get(i);
				cell.shift(0, 20);
			}
		}
		for (int i = 0; i < grid.size(); i++) {
			Cell cell = (Cell) grid.get(i);
			cell.shift(gridDirection * gridSpeed, 0);
		}
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
		for (int i = 0; i < eggs.size(); i++) {
    		Egg egg = (Egg) eggs.get(i);
			egg.move();
			if (egg.getY() > getHeight()) {
				egg.destroy();
			}
			if (egg.isVisible() && egg.getBounds().intersects(playerPlane.getBounds()) && (System.currentTimeMillis() - lastTakenDamageTime > 5000)) {
				egg.destroy();
				playerPlane.setLives(playerPlane.getLives() - 1);
				if (playerPlane.getLives() <= 0) {
					gameTimer.stop();
					JOptionPane.showMessageDialog(this, "GAME OVER! The Chickens Invaded!\nFinal Score: " + score);
					gameMain.switchScreen("menuScreen");
					return;
				}
				playerPlane.respawn();
				lastTakenDamageTime = System.currentTimeMillis();
			}
		}	

		for (int i = 0; i < bullets.size(); i++) {
    		Bullet b = (Bullet) bullets.get(i);
    		b.move();
		}	
		
		if(!levels[currentLevel - 1].isBossLevel && grid.size() > 0 && isLevelComplete()){
			score += 200;
			currentLevel++;

			spawnGrid();
		}
		if(levels[currentLevel - 1].isBossLevel && currentBoss != null && !currentBoss.isAlive()){
			if(currentLevel == 4){
				score += 500;
			}
			else if(currentLevel == 8){
				score += 1000;
				gameTimer.stop();
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
        for(Bullet b : bullets) {
        	b.draw(brush);
        }
        for(Egg egg : eggs) {
        	egg.draw(brush);
        }

        brush.setColor(Color.WHITE);
        brush.setFont(new Font("Arial", Font.BOLD, 18));
        brush.drawString("Score: " + score, 10, 20);
        brush.drawString("Level: " + currentLevel, 10, 45);
        brush.drawString("Lives: " + playerPlane.getLives(), 10, 70);
	}

	@Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) leftPressed = true;
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) rightPressed = true;
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) upPressed = true;
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) downPressed = true;
        if (keyCode == KeyEvent.VK_SPACE) {
        	long currentTime = System.currentTimeMillis();
        	if (currentTime - lastShotTime >= 300) {
        		int bulletX = playerPlane.getX() + (playerPlane.getWidth() / 2);
        		int bulletY = playerPlane.getY();
        		bullets.add(new Bullet(bulletX, bulletY));
                lastShotTime = currentTime;
            }

        }
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
	            int chickenX = 115 + (col * 70);
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
		playerPlane.setLives(3);
		playerPlane.respawn();
		bullets.clear();
		eggs.clear();
		gameStartTime = System.currentTimeMillis();
		spawnGrid();
		gameTimer.start();
		currentBoss = null;
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
