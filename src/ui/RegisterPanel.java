/**
 * user registration panel
 * handles new account creation, checks for duplicates, writes the account to the database
 */
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
public class RegisterPanel extends JPanel {
	public RegisterPanel(GameMain gameMain) {
		setLayout(new GridLayout(7,1,10,10));
		setBackground(new Color(5, 5, 15));
		
		JLabel titleLabel = new JLabel("REGISTER",JLabel.CENTER);
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
		
		JButton registerButton = new JButton("REGISTER");
		registerButton.setFont(new Font("Monospaced", Font.BOLD, 24));
		registerButton.setOpaque(true);
		registerButton.setBackground(new Color(10, 10, 30));
		registerButton.setForeground(new Color(0, 255, 255));
		registerButton.setBorderPainted(true);
		registerButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));
		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText().trim();
			    String password = passwordField.getText().trim();
			    usernameField.setText("");
			    passwordField.setText("");
			    if(username.isEmpty() || password.isEmpty() || username.contains(",") || password.contains(",") ||gameMain.db.doesTheUserExist(username)) {
			    	gameMain.switchScreen("failedRegisterScreen");
			    	return;
			    }
			    gameMain.currentUser = gameMain.db.registerUser(username, password);
				gameMain.gamePanel.resetGame();
				gameMain.switchScreen("gameScreen");
			}
		});
		add(registerButton);
		
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
