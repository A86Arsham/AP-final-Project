import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FailedLoginPopup extends JPanel {
    public FailedLoginPopup(GameMain gameMain) {
        setLayout(new GridLayout(3, 1, 0, 20));
        setBackground(new Color(255, 200, 200));

        JLabel errorIcon = new JLabel("", JLabel.CENTER);
        errorIcon.setForeground(Color.RED);
        errorIcon.setFont(new Font("Arial", Font.BOLD, 72));
        add(errorIcon);

        JLabel messageLabel = new JLabel("Login Failed!", JLabel.CENTER);
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 36));
        add(messageLabel);

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 24));
        okButton.setBackground(new Color(0, 120, 215));
        okButton.setForeground(Color.WHITE);
        okButton.setBorderPainted(false);
        okButton.setFocusPainted(false);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMain.switchScreen("loginScreen");
            }
        });
        add(okButton);
    }
}
