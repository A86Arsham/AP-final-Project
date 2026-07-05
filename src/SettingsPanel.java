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
        setBackground(new Color(42, 41, 43));

        JLabel titleLabel = new JLabel("Setting", JLabel.CENTER);
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        add(titleLabel);

        JCheckBox bgMusicBox = new JCheckBox("Background Music");
        JCheckBox shotSoundBox = new JCheckBox("Shoot Sound");
        JCheckBox crashSoundBox = new JCheckBox("Explosion Sound");
        JCheckBox endSoundBox = new JCheckBox("Game Over / Win Sound");

        bgMusicBox.setForeground(Color.WHITE);
        bgMusicBox.setFont(new Font("Arial", Font.PLAIN, 24));
        bgMusicBox.setBackground(new Color(42, 41, 43));

        shotSoundBox.setForeground(Color.WHITE);
        shotSoundBox.setFont(new Font("Arial", Font.PLAIN, 24));
        shotSoundBox.setBackground(new Color(42, 41, 43));

        crashSoundBox.setForeground(Color.WHITE);
        crashSoundBox.setFont(new Font("Arial", Font.PLAIN, 24));
        crashSoundBox.setBackground(new Color(42, 41, 43));

        endSoundBox.setForeground(Color.WHITE);
        endSoundBox.setFont(new Font("Arial", Font.PLAIN, 24));
        endSoundBox.setBackground(new Color(42, 41, 43));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e){
                if(gameMain.gamePanel != null && gameMain.gamePanel.soundManager != null){
                    bgMusicBox.setSelected(gameMain.gamePanel.soundManager.bgMusic);
                    shotSoundBox.setSelected(gameMain.gamePanel.soundManager.shotSound);
                    crashSoundBox.setSelected(gameMain.gamePanel.soundManager.crashSound);
                    endSoundBox.setSelected(gameMain.gamePanel.soundManager.endSound);
                }
            }
        });

        add(bgMusicBox);
        add(shotSoundBox);
        add(crashSoundBox);
        add(endSoundBox);
        

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBackground(new Color(0, 120, 215));
        backButton.setBackground(Color.WHITE);
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
                gameMain.gamePanel.soundManager.bgMusic = bgMusicBox.isSelected();
                if(!bgMusicBox.isSelected()){
                    gameMain.gamePanel.soundManager.stopBackgroundMusic();
                }
                else{
                    gameMain.gamePanel.soundManager.playBackgroundMusic();
                }
            }
        });

        shotSoundBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                gameMain.gamePanel.soundManager.shotSound = shotSoundBox.isSelected();
            }
        });

        crashSoundBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                gameMain.gamePanel.soundManager.crashSound = crashSoundBox.isSelected();
            }
        });

        endSoundBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                gameMain.gamePanel.soundManager.endSound = endSoundBox.isSelected();
            }
        });


    }
    
}
