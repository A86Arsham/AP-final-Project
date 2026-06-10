import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FailedRegisterPopup extends JPanel {

    public FailedRegisterPopup(GameMain gameMain) {
        setLayout(new GridLayout(3, 1, 10, 10));
        setBackground(new Color(42, 41, 43));

        JLabel titleLabel = new JLabel("Registration Failed", JLabel.CENTER);
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        add(titleLabel);

        JLabel messageLabel = new JLabel("Username already exists or fields are empty.", JLabel.CENTER);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(messageLabel);

        JButton backButton = new JButton("Try Again");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBackground(new Color(0, 120, 215));
        backButton.setForeground(Color.WHITE);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMain.switchScreen("registerScreen");
            }
        });
        add(backButton);
    }
}