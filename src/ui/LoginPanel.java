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
public class LoginPanel extends JPanel {

	public LoginPanel(GameMain gameMain) {
		setLayout(new GridLayout(8,1,0,10));
		setBackground(new Color(5, 5, 15));

		JLabel titleLabel = new JLabel("LOGIN",JLabel.CENTER);
        titleLabel.setForeground(new Color(57, 255, 20));
		titleLabel.setFont(new Font("Monospaced", Font.BOLD, 36));
		add(titleLabel);

		JLabel usernameLabel = new JLabel("USERNAME:", JLabel.LEFT);
		usernameLabel.setForeground(new Color(0, 255, 255));
		usernameLabel.setFont(new Font("Monospaced", Font.PLAIN, 18));
		add(usernameLabel);

		JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Monospaced", Font.PLAIN, 18));
		usernameField.setBackground(new Color(10, 10, 30));
		usernameField.setForeground(Color.WHITE);
        usernameField.setHorizontalAlignment(JTextField.CENTER);
		add(usernameField);

		JLabel passwordLabel = new JLabel("PASSWORD:", JLabel.LEFT);
		passwordLabel.setForeground(new Color(0, 255, 255));
		passwordLabel.setFont(new Font("Monospaced", Font.PLAIN, 18));
		add(passwordLabel);

		JTextField passwordField = new JTextField();
        passwordField.setFont(new Font("Monospaced", Font.PLAIN, 18));
		passwordField.setBackground(new Color(10, 10, 30));
		passwordField.setForeground(Color.WHITE);
        passwordField.setHorizontalAlignment(JTextField.CENTER);
		add(passwordField);


		JButton loginButton = new JButton("LOGIN");
		loginButton.setFont(new Font("Monospaced", Font.BOLD, 24));
		loginButton.setOpaque(true);
		loginButton.setBackground(new Color(10, 10, 30));
		loginButton.setForeground(new Color(0, 255, 255));
		loginButton.setBorderPainted(true);
		loginButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = passwordField.getText();
				usernameField.setText("");
				passwordField.setText("");
				
				User loggedInUser = gameMain.db.loginUser(username, password);
				if (loggedInUser != null) {
					gameMain.currentUser = loggedInUser;
					SoundManager savedSounds = loggedInUser.getSoundsSetting();
					if(savedSounds != null){
						gameMain.soundManager.bgMusic = savedSounds.bgMusic;
						gameMain.soundManager.shotSound = savedSounds.shotSound;
						gameMain.soundManager.crashSound = savedSounds.crashSound;
						gameMain.soundManager.endSound = savedSounds.endSound;
					}
					if(!savedSounds.bgMusic){
						gameMain.soundManager.stopBackgroundMusic();
					}
					else{
						gameMain.soundManager.playBackgroundMusic();
					}
				
					gameMain.gamePanel.resetGame(); 
					gameMain.switchScreen("gameScreen");
				}
				else {
					gameMain.switchScreen("failedLoginScreen");
				}

			}
		});
		add(loginButton);

		JButton goToRegisterButton = new JButton("DON'T HAVE AN ACCOUNT? REGISTER");
		goToRegisterButton.setFont(new Font("Monospaced", Font.BOLD, 24));
		goToRegisterButton.setBackground(new Color(10, 10, 30));
		goToRegisterButton.setForeground(new Color(0, 255, 255));
		goToRegisterButton.setOpaque(true);
		goToRegisterButton.setBorderPainted(true);
		goToRegisterButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));
		goToRegisterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameMain.switchScreen("registerScreen");

			}
		});
		add(goToRegisterButton);
		
		JButton backButton = new JButton("BACK TO MENU");
		backButton.setFont(new Font("Monospaced", Font.BOLD, 24));
		backButton.setBackground(new Color(10, 10, 30)); 
		backButton.setForeground(new Color(0, 255, 255));
		backButton.setOpaque(true);
		backButton.setBorderPainted(true);		
		backButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));
		backButton.setFocusPainted(false);

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameMain.switchScreen("menuScreen");
			}
		});
		add(backButton);




	}


}
