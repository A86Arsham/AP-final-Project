import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LoginPanel extends JPanel {

	public LoginPanel(GameMain gameMain) {
		setLayout(new GridLayout(7,1,0,10));
		setBackground(new Color(42, 41, 43));

		JLabel titleLabel = new JLabel("LOGIN",JLabel.CENTER);
        titleLabel.setForeground(Color.RED);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
		add(titleLabel);

		JLabel usernameLabel = new JLabel("Username:", JLabel.LEFT);
		usernameLabel.setForeground(Color.RED);
		usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		add(usernameLabel);

		JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameField.setHorizontalAlignment(JTextField.CENTER);
		add(usernameField);

		JLabel passwordLabel = new JLabel("Password:", JLabel.LEFT);
		passwordLabel.setForeground(Color.RED);
		passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		add(passwordLabel);

		JTextField passwordField = new JTextField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setHorizontalAlignment(JTextField.CENTER);
		add(passwordField);


		JButton loginButton = new JButton("Login");
		loginButton.setFont(new Font("Arial", Font.BOLD, 24));
		loginButton.setOpaque(true);
		loginButton.setBackground(new Color(0, 120, 215));
		loginButton.setForeground(Color.WHITE);
		loginButton.setBorderPainted(false);
		loginButton.setFocusPainted(false);
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = passwordField.getText();
				usernameField.setText("");
				passwordField.setText("");
				
				if((gameMain.currentUser = gameMain.db.loginUser(username, password))!=null) {
					gameMain.gamePanel.resetGame(); 
					gameMain.switchScreen("gameScreen");
				}
				else {
					gameMain.switchScreen("failedLoginScreen");
				}

			}
		});
		add(loginButton);

		JButton goToRegisterButton = new JButton("Dont have an account? Register");
		goToRegisterButton.setFont(new Font("Arial", Font.BOLD, 24));
		goToRegisterButton.setBackground(new Color(41, 42, 43));
		goToRegisterButton.setForeground(Color.WHITE);
		goToRegisterButton.setBorderPainted(false);
		goToRegisterButton.setFocusPainted(false);
		goToRegisterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameMain.switchScreen("registerScreen");

			}
		});
		add(goToRegisterButton);


	}


}
