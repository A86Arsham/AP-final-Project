/**
 * leaderboard UI
 * reads the database, sorts users by their highest scores and displays the top 5 players
 */
package ui;

import main.*;
import ui.*;
import game.*;
import entities.*;
import database.*;
import audio.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HighScorePanel extends JPanel{
    JLabel[] scoreLabels = new JLabel[5];
    public HighScorePanel(GameMain gameMain){
        setLayout(new GridLayout(7 , 1, 10, 10));
        setBackground(new Color(5, 5, 15));

        JLabel titleLabel = new JLabel("HIGH SCORES", JLabel.CENTER);
        titleLabel.setForeground(new Color(57, 255, 20));
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 36));
        add(titleLabel);
        
        for(int i=0; i<5; i++){
            scoreLabels[i] = new JLabel(" ", JLabel.CENTER);
            scoreLabels[i].setForeground(new Color(0,255,255));
            scoreLabels[i].setFont(new Font("Monospaced", Font.PLAIN, 36));
            add(scoreLabels[i]);
        }

        // ArrayList<User> topUsers = gameMain.db.getTopScore();
        // for(int i=0; i<5 && i<topUsers.size(); i++){
        //     JLabel userLabel = new JLabel(i+1 + "." + topUsers.get(i).getUsername() + " - " + topUsers.get(i).getHighestScore());
        //     userLabel.setForeground(new Color(0, 255, 255));
        //     userLabel.setFont(new Font("Monospaced", Font.PLAIN, 36));
        //     add(userLabel);
        // }

        JButton backButton = new JButton("BACK TO MENU");
        backButton.setFont(new Font("Monospaced", Font.BOLD, 24));
        backButton.setOpaque(true);
        backButton.setForeground(new Color(0, 255, 255));
        backButton.setBackground(new Color(10, 10, 30));
        backButton.setBorderPainted(true);
		backButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));
        add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMain.switchScreen("menuScreen");
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e){
                ArrayList<User> topUsers = gameMain.db.getTopScore();
                for(int i=0; i<5; i++){
                    if(i < topUsers.size()){
                        scoreLabels[i].setText(i+1 + ". " + topUsers.get(i).getUsername() + " - " + topUsers.get(i).getHighestScore());
                    }else{
                        scoreLabels[i].setText(" ");
                    }
                }
            }
        });
    }
}
