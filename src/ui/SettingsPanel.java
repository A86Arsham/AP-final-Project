/**
 * audio configuration panel
 * provides checkboxes to toggle sounds and music
 * changes are saved to uses's profile instantly
 */
package ui;

import main.*;
import ui.*;
import game.*;
import entities.*;
import database.*;
import audio.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SettingsPanel extends JPanel{
    public SettingsPanel(GameMain gameMain){
        setLayout(new GridLayout(6, 1, 10, 10));
        setBackground(new Color(5, 5, 15));

        JLabel titleLabel = new JLabel("SETTING", JLabel.CENTER);
        titleLabel.setForeground(new Color(57, 255, 20));
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 36));
        add(titleLabel);

        JCheckBox bgMusicBox = new JCheckBox("Background Music");
        JCheckBox shotSoundBox = new JCheckBox("Shoot Sound");
        JCheckBox crashSoundBox = new JCheckBox("Explosion Sound");
        JCheckBox endSoundBox = new JCheckBox("Game Over / Win Sound");

        bgMusicBox.setForeground(new Color(0, 255, 255));
        bgMusicBox.setFont(new Font("Monospaced", Font.PLAIN, 24));

        shotSoundBox.setForeground(new Color(0, 255, 255));
        shotSoundBox.setFont(new Font("Monospaced", Font.PLAIN, 24));

        crashSoundBox.setForeground(new Color(0, 255, 255));
        crashSoundBox.setFont(new Font("Monospaced", Font.PLAIN, 24));

        endSoundBox.setForeground(new Color(0, 255, 255));
        endSoundBox.setFont(new Font("Monospaced", Font.PLAIN, 24));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e){
                if(gameMain.gamePanel != null && gameMain.soundManager != null){
                    bgMusicBox.setSelected(gameMain.soundManager.bgMusic);
                    shotSoundBox.setSelected(gameMain.soundManager.shotSound);
                    crashSoundBox.setSelected(gameMain.soundManager.crashSound);
                    endSoundBox.setSelected(gameMain.soundManager.endSound);
                }
            }
        });

        add(bgMusicBox);
        add(shotSoundBox);
        add(crashSoundBox);
        add(endSoundBox);
        

        JButton backButton = new JButton("BACK TO MENU");
        backButton.setFont(new Font("Monospaced", Font.BOLD, 24));
        backButton.setOpaque(true);
        backButton.setBackground(new Color(10, 10, 30));
        backButton.setForeground(new Color(0, 255, 255));
        backButton.setBorderPainted(true);
        backButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));
        add(backButton);
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMain.switchScreen("menuScreen");
            }
        });
        
        bgMusicBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                gameMain.soundManager.bgMusic = bgMusicBox.isSelected();
                if(!bgMusicBox.isSelected()){
                    gameMain.soundManager.stopBackgroundMusic();
                }
                else{
                    gameMain.soundManager.playBackgroundMusic();
                }

                if(gameMain.currentUser != null){
                    gameMain.currentUser.setSoundsSetting(gameMain.soundManager);
                    gameMain.db.updateUser(gameMain.currentUser);
                }
            }
        });

        shotSoundBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                gameMain.soundManager.shotSound = shotSoundBox.isSelected();
                if(gameMain.currentUser != null){
                    gameMain.currentUser.setSoundsSetting(gameMain.soundManager);
                    gameMain.db.updateUser(gameMain.currentUser);
                }
            }
        });

        crashSoundBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                gameMain.soundManager.crashSound = crashSoundBox.isSelected();
                if(gameMain.currentUser != null){
                    gameMain.currentUser.setSoundsSetting(gameMain.soundManager);
                    gameMain.db.updateUser(gameMain.currentUser);
                }
            }
        });

        endSoundBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                gameMain.soundManager.endSound = endSoundBox.isSelected();
                if(gameMain.currentUser != null){
                    gameMain.currentUser.setSoundsSetting(gameMain.soundManager);
                    gameMain.db.updateUser(gameMain.currentUser);
                }
            }
        });


    }
    
}
