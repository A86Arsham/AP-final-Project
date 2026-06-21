import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class BossLevel4 extends Boss {
    private long lastAttackTime = 0;
    private int attackCooldown = 1500;

    public BossLevel4(){
        super(350, 50, 100, 100, 50, 1.5);
    }

    @Override
    public ArrayList<Egg> generateEggs(){
        ArrayList<Egg> newEggs = new ArrayList<>();
        long now = System.currentTimeMillis();

        if(now - lastAttackTime >= attackCooldown){
            lastAttackTime = now;
            int x = getX() + (getWidth() / 2);
            int y = getY() + (getHeight() / 2);

            newEggs.add(new Egg(x, y, 0, -1, 4));
            newEggs.add(new Egg(x, y, 0 , 1, 4));
            newEggs.add(new Egg(x, y, -1, 0, 4));
            newEggs.add(new Egg(x, y, 1, 0 ,4));
        }

        return newEggs;
    }

    @Override
    public void drawBoss(Graphics brush){
        brush.setColor(Color.YELLOW);
        brush.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
