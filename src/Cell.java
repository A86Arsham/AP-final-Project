public class Cell {
    private int row;
    private int col;
    private int x;
    private int y;
    private int counter;
    private Enemy occ_chicken;

    public Cell(int row, int col, int x, int y, int counter){
        this.row = row;
        this.col = col;
        this.x = x;
        this.y = y;
        this.counter = counter;
        this.occ_chicken = null;
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
        return this.occ_chicken;
    }
    public void setOccChicken(Enemy chicken){
        this.occ_chicken = chicken;
    }
}
