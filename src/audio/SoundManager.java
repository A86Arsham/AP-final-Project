package audio;

import main.*;
import ui.*;
import game.*;
import entities.*;
import database.*;
import audio.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundManager{
    public boolean bgMusic = true;
    public boolean shotSound = true;
    public boolean crashSound = true;
    public boolean endSound = true;

    private Clip bgClip;
    private Clip shootClip;
    private Clip crashClip;
    private Clip gameoverClip;
    private Clip winClip;

    private Clip loadSound(String filename){
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            return clip;
        }catch(Exception e){
            return null;
        }
    }

    public SoundManager(){
        bgClip = loadSound("assets/sound-effects/Chicken Invaders 2 Remastered OST - Main Theme.wav");
        shootClip = loadSound("assets/sound-effects/mixkit-short-laser-gun-shot-1670.wav");
        crashClip = loadSound("assets/sound-effects/mixkit-epic-impact-afar-explosion-2782.wav");
        gameoverClip = loadSound("assets/sound-effects/mixkit-retro-arcade-game-over-470.wav");
        winClip = loadSound("assets/sound-effects/Chicken Invaders 2 Remastered OST - Ending Theme.wav");
    }

    public void playBackgroundMusic(){
        if(bgMusic && bgClip != null){
            bgClip.setFramePosition(0);
            bgClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopBackgroundMusic(){
        if(bgClip != null){
            bgClip.stop();
        }
    }

    public void playShoot(){
        if(shotSound && shootClip != null){
            shootClip.setFramePosition(0);
            shootClip.start();
        }
    }

    public void playCrash(){
        if(crashSound && crashClip != null){
            crashClip.setFramePosition(0);
            crashClip.start();
        }
    }

    public void playGameover(){
        if(endSound && gameoverClip != null){
            stopBackgroundMusic();
            if(crashClip != null && crashClip.isRunning()){
                crashClip.stop();
            }
            gameoverClip.setFramePosition(0);
            gameoverClip.start();
        }

        
    }

    public void playWinning(){
        if(endSound && winClip != null){
            winClip.setFramePosition(0);
            winClip.start();
        }
    }
}