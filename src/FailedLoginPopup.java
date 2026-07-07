import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FailedLoginPopup extends JPanel {
    public FailedLoginPopup(GameMain gameMain) {
        setLayout(new GridBagLayout()); 
        setBackground(new Color(5, 5, 15));

        JPanel popupBox = new JPanel();
        popupBox.setLayout(new BorderLayout(10, 10));
        popupBox.setBackground(new Color(5, 5, 15));
        popupBox.setPreferredSize(new Dimension(350, 150)); 
        popupBox.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 3)); 
        
        JLabel titleLabel = new JLabel("LOGIN FAILED", JLabel.CENTER);
        titleLabel.setForeground(new Color(57, 255, 20));
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        popupBox.add(titleLabel, BorderLayout.NORTH);
        
        JLabel messageLabel = new JLabel("INVALID USERNAME OR PASSWORD.", JLabel.CENTER);
        messageLabel.setForeground(new Color(0, 255, 255));
        messageLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
        popupBox.add(messageLabel, BorderLayout.CENTER);
        
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Monospaced", Font.BOLD, 16));
        okButton.setOpaque(true);
        okButton.setBackground(new Color(5, 5, 15));
        okButton.setForeground(new Color(0, 255, 255));
        okButton.setBorderPainted(true);
        okButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 2));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMain.switchScreen("loginScreen");
            }
        });    
        popupBox.add(okButton, BorderLayout.SOUTH);

        add(popupBox);
    }
}