public class Cell {
    private int row;
    private int col;
    private int x;
    private int y;
    private int counter;
    private Enemy occChicken;
    private boolean chickenIsRespawning;

    public Cell(int row, int col, int x, int y, int counter){
        this.row = row;
        this.col = col;
        this.x = x;
        this.y = y;
        this.counter = counter;
        this.occChicken = null;
        this.chickenIsRespawning = false;
    }

    public int getRow(){
        return this.row;
    }
    public int getCol(){
        return this.col;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getCounter(){
        return this.counter;
    }
    public void decreaseCounter(){
        this.counter--;
    }
    public Enemy getOccChicken(){
        return this.occChicken;
    }
    public void setOccChicken(Enemy chicken){
        this.occChicken = chicken;
    }
    public void setChickenIsRespawning(boolean tF){
        this.chickenIsRespawning = tF;
    }
    public boolean isTheChickenRespawning(){
        return this.chickenIsRespawning;
    }

    public void shift(int dx, int dy){
        this.x += dx;
        this.y += dy;

        if(this.occChicken!=null && this.occChicken.isAlive() && !this.getOccChicken().isMovingToCell()){
            this.occChicken.setX(this.occChicken.getX() + dx);
            this.occChicken.setY(this.occChicken.getY() + dy);
        }
    }
}
