public class SoundManager {
    public boolean bgMusic;
    public boolean shotSound;
    public boolean crashSound;
    public boolean endSound;

    public SoundManager(boolean bg, boolean shot, boolean crash, boolean end){
        this.bgMusic = bg;
        this.shotSound = shot;
        this.crashSound = crash;
        this.endSound = end;
    }

    public SoundManager(){
        this(true, true, true, true);
    }

    public void playBackgroundMusic(){

    }
}
