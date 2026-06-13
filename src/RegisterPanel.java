import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class RegisterPanel extends JPanel {
	public RegisterPanel(GameMain gameMain) {
		setLayout(new GridLayout(6,1,10,10));
		setBackground(new Color(42, 41, 43));
		
		JLabel titleLabel = new JLabel("Register",JLabel.CENTER);
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
		
		JButton registerButton = new JButton("Register");
		registerButton.setFont(new Font("Arial", Font.BOLD, 24));
		registerButton.setBackground(new Color(0, 120, 215));
		registerButton.setForeground(Color.WHITE);
		registerButton.setBorderPainted(false);
		registerButton.setFocusPainted(false);
		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText().trim();
			    String password = passwordField.getText().trim();
			    usernameField.setText("");
			    passwordField.setText("");
			    if(username.isEmpty() || password.isEmpty() || gameMain.db.doesTheUserExist(username)) {
			    	gameMain.switchScreen("failedRegisterScreen");
			    	return;
			    }
			    gameMain.currentUser = gameMain.db.registerUser(username, password);
				gameMain.switchScreen("gameScreen");
			}
		});
		add(registerButton);
		
		
	}
}
