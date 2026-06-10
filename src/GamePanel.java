import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	boolean leftPressed = false, rightPressed = false, upPressed = false, downPressed = false;
	long lastShotTime = 0;
	Timer gameTimer;
	Plane playerPlane;
	ArrayList<Enemy> chickens = new ArrayList<Enemy>();
	ArrayList<Bullet> bullets = new ArrayList<>();

	public GamePanel() {
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		playerPlane = new Plane();
		gameTimer = new Timer(16,this);
		gameTimer.start();

		addComponentListener(new ComponentAdapter() {
		    @Override
		    public void componentShown(ComponentEvent e) {
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
					break;
				}
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
	            int chickenX = 80 + (col * 80);
	            int chickenY = 50 + (row * 60);

	            chickens.add(new NormalEnemy(2,chickenX, chickenY));
	        }
	    }
	}

}
