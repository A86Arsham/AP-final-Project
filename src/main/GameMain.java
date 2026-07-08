package main;

import main.*;
import ui.*;
import game.*;
import entities.*;
import database.*;
import audio.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class GameMain{
	JFrame window;
	JPanel screens;
	CardLayout controller;
	DatabaseManager db = new DatabaseManager();
	User currentUser = null;
	public GamePanel gamePanel;
	public SoundManager soundManager;
	public GameMain() {
		window = new JFrame("Chicken Invaders");
		window.setSize(800,600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);

		soundManager = new SoundManager();
		soundManager.playBackgroundMusic();

		controller = new CardLayout();
		screens = new JPanel(controller);

		MainMenu mainMenu = new MainMenu(this);
		screens.add(mainMenu, "menuScreen");

		LoginPanel loginPanel = new LoginPanel(this);
		screens.add(loginPanel, "loginScreen");

		RegisterPanel registerPanel = new RegisterPanel(this);
		screens.add(registerPanel, "registerScreen");

		FailedLoginPopup failedLoginPopup = new FailedLoginPopup(this);
		screens.add(failedLoginPopup, "failedLoginScreen");
		
		FailedRegisterPopup failedRegisterPopup = new FailedRegisterPopup(this);
		screens.add(failedRegisterPopup, "failedRegisterScreen");
		
		SettingsPanel settingsPanel = new SettingsPanel(this);
		screens.add(settingsPanel, "settingsScreen");

		HighScorePanel highScorePanel = new HighScorePanel(this);
		screens.add(highScorePanel, "highScoreScreen");

		HowToPlayPanel howToPlayPanel = new HowToPlayPanel(this);
		screens.add(howToPlayPanel, "howToPlayScreen");

		gamePanel = new GamePanel(this);
		screens.add(gamePanel, "gameScreen");
		
		window.add(screens);
		window.setVisible(true);
	}

	public void switchScreen(String name) {
		controller.show(screens, name);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GameMain());
	}
}