import java.awt.*;

public class ZigzagEnemy extends Enemy{
    private int zigzagCounter = 0;
    private int zigzagDirection = 1;

    public ZigzagEnemy(int health, int x, int y){
        super(x, y);
        setHealth(health);
    }

    @Override
    public int getScoreValue(){
        return 20;
    }

    public void updateZigzag(){
        zigzagCounter += (zigzagDirection * 2);
        if(zigzagCounter > 15 || zigzagCounter < -15){
            zigzagCounter *= -1;
        }
        setX(getX() + (zigzagDirection * 2));
    }

    @Override
    public void draw(Graphics brush){
        if(isAlive()){
            brush.setColor(Color.ORANGE);
            brush.fillRect(getX(), getY(), getWidth(), getHealth());
        }
    }

}