import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Plane {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private int lives;
    private int maxLives;
    private int bulletAmount;
    private int bulletShootCooldown;

    private boolean isShieldOn = false;
    private long shieldStartTime = 0;
    private int shieldDuration = 10000;

    private boolean isRapidFireOn = false;
    private long rapidFireStartTime = 0;
    private int rapidFireDuration = 8000;

    private boolean isFreezeBombOn = false;
    private long freezeBombStartTime = 0;
    private int freezeBombDuration = 3000;

    private Image shipImage;

    public Plane() {
        this.width = 60;
        this.height = 60;
        this.x = 375;
        this.y = 500;
        this.speed = 5;
        this.lives = 3;
        this.maxLives = 3;
        this.bulletAmount = 1;
        this.bulletShootCooldown = 300;

        ImageIcon icon = new ImageIcon("/Users/arsham/AP-final-Project/assets/airplan/1.png");
        shipImage = icon.getImage();
    }

    public void move(boolean left, boolean right, boolean up, boolean down, int screenWidth, int screenHeight) {
        if (left) x -= speed;
        if (right) x += speed;
        if (up) y -= speed;
        if (down) y += speed;

        if (x < 0) x = 0;
        if (x > screenWidth - width) x = screenWidth - width;

        if (y < screenHeight / 2) y = screenHeight / 2;
        if (y > screenHeight - height) y = screenHeight - height;
    }

    public void draw(Graphics g) {
        if(shipImage != null){ 
            g.drawImage(shipImage, x, y, width, height, null);
        }
        else{    
            if(isShieldOn){
                g.setColor(new Color(0,255,255, 100));
                g.fillOval(x - 10, y - 10, width + 20, height + 20);
                g.setColor(Color.CYAN);
                g.fillRect(x, y, width, height);
            }
            else{
                g.setColor(Color.CYAN);
                g.fillRect(x, y, width, height);
            }
        }  
    }

    public int getX() {
    	return x;
    }
    public int getY() {
    	return y;
    }
    public int getWidth() {
    	return width;
    }
    public int getHeight() {
    	return height;
    }
    public int getLives() {
    	return lives;
    }
    public int getMaxLives(){
        return maxLives;
    }

    public void setLives(int lives){
        this.lives = lives;
    }
    public void setMaxLives(int maxLives){
        this.maxLives = maxLives;
    }
    public void addLife(){
        if(this.lives + 1 > this.maxLives){
            return;
        }
        this.lives++;
    }
    
    public int getBulletCooldown(){
        return this.bulletShootCooldown;
    }
    public void setBulletCooldown(int cooldown){
        this.bulletShootCooldown = cooldown;
    }

    public int getBulletAmount(){
        return this.bulletAmount;
    }
    public void setBulletAmount(int amount){
        this.bulletAmount = amount;
    }
    public void addBulletAmount(){
        if(bulletAmount >= 4){
            return;
        }
        this.bulletAmount++;
    }

    public boolean isShielded(){
        return this.isShieldOn;
    }
    public void setShield(){
        this.isShieldOn = true;
    }
    public void setShieldStartTime(){
        this.shieldStartTime = System.currentTimeMillis();
    }

    public boolean isRapidFire(){
        return this.isRapidFireOn;
    }
    public void setRapidFire(){
        this.isRapidFireOn = true;
    }
    public void setRapidFireStartTime(){
        this.rapidFireStartTime = System.currentTimeMillis();
    }

    public boolean isFreezeBomb(){
        return this.isFreezeBombOn;
    }
    public void setFreezeBomb(){
        this.isFreezeBombOn = true;
    }
    public void setFreezeBombStartTime(){
        this.freezeBombStartTime = System.currentTimeMillis();
    }


    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public void respawn() {
        this.x = 375; 
        this.y = 500;
    }

    private long lastShotTime = 0;
    public void shootBullet(ArrayList<Bullet> bullets){
        long currentTime = System.currentTimeMillis();
        
        if(currentTime - lastShotTime >= bulletShootCooldown){
            for(int i = 0; i<bulletAmount; i++){
                int bulletX = this.x + (i+1) * (this.width / (bulletAmount+1));
                int bulletY = this.y;
                bullets.add(new Bullet(bulletX, bulletY));
                lastShotTime = currentTime;
            }
        }
    }

    public void updatePowerup(){
        long currentTime = System.currentTimeMillis();

        if(isRapidFireOn && (currentTime - rapidFireStartTime > rapidFireDuration)){
            isRapidFireOn = false;
            this.bulletShootCooldown = 300;
        }

        if(isShieldOn && (currentTime - shieldStartTime > shieldDuration)){
            isShieldOn = false;
        }

        if(isFreezeBombOn && (currentTime - freezeBombStartTime > freezeBombDuration)){
            isFreezeBombOn = false;
        }
    }
}