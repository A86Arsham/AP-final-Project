import java.awt.*;

public class ShooterEnemy extends Enemy{
    private long lastHorizontalShotTime = 0;
    private int horizontalShotCooldown = 3000;

    public ShooterEnemy(int health, int x, int y){
        super(x, y);
        setHealth(health);
    }

    @Override
    public int getScoreValue(){
        return 25;
    }

    public Egg shootHorizonalEgg(int playerX){
        long now = System.currentTimeMillis();
        if(now - lastHorizontalShotTime >= horizontalShotCooldown){
            lastHorizontalShotTime = now;
            int direction;
            if(playerX < getX()){
                direction = -1;
            }
            else{
                direction = 1;
            }
            return new Egg(getX() + (getWidth() / 2) , (getY() + getHeight() / 2), direction, 0);
        
        }
        return null;
    }

    @Override
    public void draw(Graphics brush){
        if(isAlive()){
            brush.setColor(Color.MAGENTA);
            brush.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
