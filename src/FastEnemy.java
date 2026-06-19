import java.awt.Color;
import java.awt.Graphics;

public class FastEnemy extends Enemy {
    public FastEnemy(int health, int x, int y){
        super(x, y);
        setHealth(health);
    }

    @Override
    public int getScoreValue(){
        return 15;
    }

    @Override
    public void draw(Graphics brush){
        if(isAlive()){
            brush.setColor(Color.GREEN);
            brush.fillRect(getX(), getY(), getWidth(), getHeight());
        }
    }
}
