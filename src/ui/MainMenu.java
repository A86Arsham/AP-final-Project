package ui;

import main.*;
import ui.*;
import game.*;
import entities.*;
import database.*;
import audio.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
public class MainMenu extends JPanel{
	private GameMain gameMain; 		
	public MainMenu(GameMain gameMain) {
			this.gameMain = gameMain;

			JPanel mainPanel = new JPanel();
			mainPanel.setSize(800,600);
			GridLayout gridLayout = new GridLayout(6, 1, 0, 10);
			mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
			mainPanel.setBackground(new Color(5, 5, 15));
			mainPanel.setLayout(gridLayout);


			JLabel titleLable = new JLabel("CHICKEN INVADERS",JLabel.CENTER);
			Font titleFont = new Font("Monospaced", Font.BOLD, 36);
			titleLable.setForeground(new Color(57, 255, 20));
			titleLable.setFont(titleFont);
			titleLable.setVisible(true);
			mainPanel.add(titleLable);

			JButton newGameButton = new JButton("NEW GAME");
			newGameButton.setFont(new Font("Monospaced", Font.BOLD, 24));
			newGameButton.setOpaque(true);
			newGameButton.setBackground(new Color(10, 10, 30));
			newGameButton.setForeground(new Color(0, 255, 255));
			newGameButton.setBorderPainted(true);
			newGameButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));
			newGameButton.setFocusPainted(false);
			mainPanel.add(newGameButton);

			JButton highScoresButton = new JButton("HIGH SCORES");
			highScoresButton.setFont(new Font("Monospaced", Font.BOLD, 24));
			highScoresButton.setOpaque(true);
			highScoresButton.setBackground(new Color(10, 10, 30));
			highScoresButton.setForeground(new Color(0, 255, 255));
			highScoresButton.setBorderPainted(true);
			highScoresButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));
			highScoresButton.setFocusPainted(false);
			mainPanel.add(highScoresButton);

			JButton settingsButton = new JButton("SETTINGS");
			settingsButton.setFont(new Font("Monospaced", Font.BOLD, 24));
			settingsButton.setOpaque(true);
			settingsButton.setBackground(new Color(10, 10, 30));
			settingsButton.setForeground(new Color(0, 255, 255));
			settingsButton.setBorderPainted(true);
			settingsButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));
			settingsButton.setFocusPainted(false);
			mainPanel.add(settingsButton);

			JButton howToPlayButton =  new JButton("HOW TO PLAY");
			howToPlayButton.setFont(new Font("Monospaced", Font.BOLD, 24));
			howToPlayButton.setOpaque(true);
			howToPlayButton.setBackground(new Color(10, 10, 30));
			howToPlayButton.setForeground(new Color(0, 255, 255));
			howToPlayButton.setBorderPainted(true);
			howToPlayButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));
			howToPlayButton.setFocusPainted(false);
			mainPanel.add(howToPlayButton);

			JButton exitButton = new JButton("EXIT");
			exitButton.setFont(new Font("Monospaced", Font.BOLD, 24));
			exitButton.setOpaque(true);
			exitButton.setBackground(new Color(10, 10, 30));
			exitButton.setForeground(new Color(0, 255, 255));
			exitButton.setBorderPainted(true);
			exitButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));
			exitButton.setFocusPainted(false);
			mainPanel.add(exitButton);

			newGameButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					gameMain.switchScreen("loginScreen");
				}
			});

			settingsButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e){
					gameMain.switchScreen("settingsScreen");
				}
			});

			highScoresButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					gameMain.switchScreen("highScoreScreen");
				}
			});

			howToPlayButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e){
					gameMain.switchScreen("howToPlayScreen");
				}
			});

			exitButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);

				}
			});

			addComponentListener(new ComponentAdapter() {
				@Override
				public void componentShown(ComponentEvent e) {
					gameMain.soundManager.playBackgroundMusic();
				}
			});

			this.setLayout(new BorderLayout());
		    this.add(mainPanel, BorderLayout.CENTER);



		}

	}


