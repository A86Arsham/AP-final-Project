import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HowToPlayPanel extends JPanel {
    
    public HowToPlayPanel(GameMain gameMain) {
        setLayout(new GridLayout(10, 1, 5, 5));
        setBackground(new Color(42, 41, 43));

        JLabel titleLabel = new JLabel("How to Play", JLabel.CENTER);
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        add(titleLabel);

        JLabel label1 = new JLabel("W / ↑ : Move Up", JLabel.CENTER);
        JLabel label2 = new JLabel("S / ↓ : Move Down", JLabel.CENTER);
        JLabel label3 = new JLabel("A / ← : Move Left", JLabel.CENTER);
        JLabel label4 = new JLabel("D / → : Move Right", JLabel.CENTER);
        JLabel label5 = new JLabel("Space : Shoot", JLabel.CENTER);
        JLabel label6 = new JLabel("P : Pause / Resume", JLabel.CENTER);
        JLabel label7 = new JLabel("Esc : Quit to Main Menu", JLabel.CENTER);

        Font font = new Font("Arial", Font.PLAIN, 22);
        Color textColor = Color.WHITE;

        label1.setForeground(textColor); 
        label1.setFont(font); 
        add(label1);

        label2.setForeground(textColor); 
        label2.setFont(font); 
        add(label2);

        label3.setForeground(textColor); 
        label3.setFont(font); 
        add(label3);

        label4.setForeground(textColor);
        label4.setFont(font); 
        add(label4);

        label5.setForeground(textColor);
        label5.setFont(font);
        add(label5);

        label6.setForeground(textColor); 
        label6.setFont(font); 
        add(label6);

        label7.setForeground(textColor); 
        label7.setFont(font); 
        add(label7);

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBackground(new Color(0, 120, 215));
        
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMain.switchScreen("menuScreen");
            }
        });
        add(backButton);
    }
}