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
	ArrayList<Enemy> chickens = new ArrayList<Enemy>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	ArrayList<Egg> eggs = new ArrayList<Egg>();
	
	int gridSpeed = 1;
	int gridDirection = 1; 
	
	int score = 0;
	int currentLevel = 1;
	
	long gameStartTime;
	long lastTakenDamageTime;
	
	GameMain gameMain;
			
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

		for (Enemy chicken : chickens) {
	        if (chicken.isAlive() && playerPlane.getBounds().intersects(chicken.getBounds())) {

	            int currentLives = playerPlane.getLives();
	            playerPlane.setLives(currentLives - 1);
	            playerPlane.respawn();

	            chicken.setAlive(false);
	        }
	    }
		for (Bullet b : bullets) {
			if(!b.isVisible()) {
				continue;
			}
			for(Enemy chicken : chickens) {
				if(chicken.isAlive() && b.getBounds().intersects(chicken.getBounds())) {
					chicken.takeDamage();
					b.destroy();
					if (!chicken.isAlive()) {
				        score += 10;
				    }
					break;
				}
			}
		}
		
		boolean hitEdge = false;
		for(Enemy chicken : chickens) {
			if(chicken.isAlive()) {
				if(chicken.getX()<=0 || chicken.getX() + chicken.getWidth() >= getWidth()) {
					hitEdge = true;
					break;
				}
			}
		}
		if(hitEdge) {
			gridDirection *= -1;
			for(Enemy chicken : chickens) {
				chicken.setY(chicken.getY() + 20);
			}
		}
		for(Enemy chicken : chickens) {
			chicken.setX(chicken.getX() + gridDirection*gridSpeed);
		}
		if(System.currentTimeMillis() - gameStartTime > 2000) {
			for(Enemy chicken : chickens) {
				Egg egg = chicken.eggDrop();
				if(egg!=null) {
					eggs.add(egg);
				}
			}
		}
		for(Egg egg : eggs) {
			egg.move();
			if(egg.getY()>getHeight()) {
				egg.destroy();
			}
			if(egg.isVisible() && egg.getBounds().intersects(playerPlane.getBounds()) && (System.currentTimeMillis() - lastTakenDamageTime>5000)) {
				egg.destroy();
				playerPlane.setLives(playerPlane.getLives() - 1);
				if(playerPlane.getLives() <= 0) {
					gameTimer.stop();
					JOptionPane.showMessageDialog(this, "GAME OVER! The Chickens Invaded!\nFinal Score: " + score);
					gameMain.switchScreen("menuScreen");
					return;
				}
				playerPlane.respawn();
				lastTakenDamageTime = System.currentTimeMillis();
			}
		}
		
		for (Bullet b : bullets) {
		    b.move();
		}
		
		
		repaint();
	}

	@Override
	public void paintComponent(Graphics brush) {
		super.paintComponent(brush);
        playerPlane.draw(brush);
        for(Enemy chicken : chickens) {
        	chicken.draw(brush);
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
	    chickens.clear();

	    for (int row = 0; row < 5; row++) {
	        for (int col = 0; col < 8; col++) {
	            int chickenX = 115 + (col * 70);
	            int chickenY = 40 + (row * 45);

	            chickens.add(new NormalEnemy(2,chickenX, chickenY));
	        }
	    }
	}

}
