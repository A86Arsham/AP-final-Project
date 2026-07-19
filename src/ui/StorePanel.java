package ui;

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
import main.*;

public class StorePanel extends JPanel {
    public StorePanel(GameMain gameMain){
        setLayout(new GridLayout(6, 1, 10, 10));
        setBackground(new Color(5, 5, 15));
        buildUI(gameMain);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                removeAll();
                buildUI(gameMain);
                revalidate();
                repaint();
            }
        });
    }


    private void buildUI(GameMain gameMain) {
        JLabel titleLabel = new JLabel("STORE", JLabel.CENTER);
        titleLabel.setForeground(new Color(57, 255, 20));
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 36));
        add(titleLabel);

        int highscore;
        String currentType;
        if(gameMain.currentUser == null){
            JLabel scoreLabel = new JLabel("NOT LOGGED IN YET");
            highscore = 0;
            currentType = "Default";
            scoreLabel.setForeground(new Color(57, 255, 20));
            scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
            add(scoreLabel);
        }
        else{
            currentType = gameMain.currentUser.getType();
            highscore = gameMain.currentUser.getHighestScore();
            JLabel scoreLabel = new JLabel("High Score: " + highscore + "  |  Equipped: " + currentType, JLabel.CENTER);
            scoreLabel.setForeground(new Color(57, 255, 20));
            scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
            add(scoreLabel);
        }
        

        // -------------------------------------------------------------------------------------------
        JPanel fastPlanePanel = new JPanel();
        fastPlanePanel.setLayout(null);
        fastPlanePanel.setBackground(new Color(20, 20, 40));

        JLabel fastName = new JLabel("FAST PLANE");
        fastName.setBounds(20, 15, 200, 25);
        fastName.setForeground(new Color(0, 255, 255));
        fastName.setFont(new Font("Monospaced", Font.BOLD, 20));
        fastPlanePanel.add(fastName);

        JLabel fastStats = new JLabel("Speed: 7 | Fire Rate: 250ms | Lives: 3");
        fastStats.setForeground(Color.LIGHT_GRAY);
        fastStats.setFont(new Font("Monospaced", Font.PLAIN, 14));
        fastStats.setBounds(20, 45, 400, 20);
        fastPlanePanel.add(fastStats);

        JButton fastButton = new JButton();
        fastButton.setFont(new Font("Monospaced", Font.BOLD, 16));
        fastButton.setBackground(new Color(10, 10, 30));
        fastButton.setForeground(new Color(57, 255, 20));
        fastButton.setOpaque(true);
        fastButton.setBorderPainted(true);
        fastButton.setBorder(BorderFactory.createLineBorder(new Color(57, 255, 20), 2));
        fastButton.setBounds(550, 20, 200, 40);
       
        if (currentType.equals("Fast")) {
            fastButton.setText("Equipped");
            fastButton.setEnabled(false);
        } else if (highscore >= 5000) {
            fastButton.setText("Equip Fast Plane");
            fastButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                gameMain.currentUser.setType("Fast");
                gameMain.db.updateUser(gameMain.currentUser);
                removeAll();      
                buildUI(gameMain); 
                revalidate();      
                repaint(); 
                }
            });
        } else {
            fastButton.setText("Need " + (5000 - highscore) + " pts");
            fastButton.setEnabled(false);
        }
        fastPlanePanel.add(fastButton);
        add(fastPlanePanel);

        // -------------------------------------------------------------------------------------------
        JPanel heavyPlanePanel = new JPanel();
        heavyPlanePanel.setLayout(null);
        heavyPlanePanel.setBackground(new Color(20, 20, 40)); 

        JLabel heavyName = new JLabel("HEAVY PLANE");
        heavyName.setForeground(new Color(0, 255, 255));
        heavyName.setFont(new Font("Monospaced", Font.BOLD, 20));
        heavyName.setBounds(20, 15, 200, 25);
        heavyPlanePanel.add(heavyName);

        JLabel heavyStats = new JLabel("Speed: 4 | Fire Rate: 200ms | Lives: 5");
        heavyStats.setForeground(Color.LIGHT_GRAY);
        heavyStats.setFont(new Font("Monospaced", Font.PLAIN, 14));
        heavyStats.setBounds(20, 45, 400, 20);
        heavyPlanePanel.add(heavyStats);

        JButton heavyButton = new JButton();
        heavyButton.setFont(new Font("Monospaced", Font.BOLD, 16));
        heavyButton.setBackground(new Color(10, 10, 30));
        heavyButton.setForeground(new Color(57, 255, 20));
        heavyButton.setOpaque(true);
        heavyButton.setBorderPainted(true);
        heavyButton.setBorder(BorderFactory.createLineBorder(new Color(57, 255, 20), 2));
        heavyButton.setBounds(550, 20, 200, 40);

        if (currentType.equals("Heavy")) {
            heavyButton.setText("Equipped");
            heavyButton.setEnabled(false);
        } else if (highscore >= 8000) {
            heavyButton.setText("Equip Heavy Plane");
            heavyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                        gameMain.currentUser.setType("Heavy");
                        gameMain.db.updateUser(gameMain.currentUser);
                        removeAll();      
                        buildUI(gameMain); 
                        revalidate();      
                        repaint(); 
                }
            }); 
                
            
        } else {
            heavyButton.setText("Need " + (8000 - highscore) + " pts");
            heavyButton.setEnabled(false);
        }
        heavyPlanePanel.add(heavyButton);
        add(heavyPlanePanel);

        // -------------------------------------------------------------------------------------------
        JPanel sniperPlanePanel = new JPanel();
        sniperPlanePanel.setLayout(null);
        sniperPlanePanel.setBackground(new Color(20, 20, 40));

        JLabel sniperName = new JLabel("SNIPER PLANE");
        sniperName.setForeground(new Color(0, 255, 255));
        sniperName.setFont(new Font("Monospaced", Font.BOLD, 20));
        sniperName.setBounds(20, 15, 200, 25);
        sniperPlanePanel.add(sniperName);

        JLabel sniperStats = new JLabel("Speed: 5 | Fire Rate: 150ms | Lives: 3 | Special: 2x Boss Damage");
        sniperStats.setForeground(Color.LIGHT_GRAY);
        sniperStats.setFont(new Font("Monospaced", Font.PLAIN, 14));
        sniperStats.setBounds(20, 45, 350, 20);
        sniperPlanePanel.add(sniperStats);

        JLabel sniperSpecial = new JLabel("Special: 2x Boss Damage");
        sniperSpecial.setForeground(new Color(255, 50, 50));
        sniperSpecial.setFont(new Font("Monospaced", Font.BOLD, 14));
        sniperSpecial.setBounds(20, 65, 200, 20);
        sniperPlanePanel.add(sniperSpecial);

        JButton sniperButton = new JButton();
        sniperButton.setFont(new Font("Monospaced", Font.BOLD, 16));
        sniperButton.setBackground(new Color(10, 10, 30));
        sniperButton.setForeground(new Color(57, 255, 20));
        sniperButton.setOpaque(true);
        sniperButton.setBorderPainted(true);
        sniperButton.setBorder(BorderFactory.createLineBorder(new Color(57, 255, 20), 2));
        sniperButton.setBounds(550, 20, 200, 40);

        if (currentType.equals("Sniper")) {
            sniperButton.setText("Equipped");
            sniperButton.setEnabled(false);
        } else if (highscore >= 10000) {
            sniperButton.setText("Equip Sniper");
            sniperButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                        gameMain.currentUser.setType("Sniper");
                        gameMain.db.updateUser(gameMain.currentUser);
                        removeAll();      
                        buildUI(gameMain); 
                        revalidate();      
                        repaint(); 
                }
            });
        } else {
            sniperButton.setText("Need " + (10000 - highscore) + " pts");
            sniperButton.setEnabled(false);
        }
        sniperPlanePanel.add(sniperButton);
        add(sniperPlanePanel);

        // -------------------------------------------------------------------------------------------
        JButton backButton = new JButton("BACK TO MENU");
        backButton.setFont(new Font("Monospaced", Font.BOLD, 24));
        backButton.setBackground(new Color(10, 10, 30)); 
        backButton.setForeground(new Color(0, 255, 255));
        backButton.setOpaque(true);
        backButton.setBorderPainted(true);		
        backButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));

        backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                        gameMain.switchScreen("menuScreen");
                }
        });
        add(backButton);
    }
}
