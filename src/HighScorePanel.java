import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HighScorePanel extends JPanel{
    public HighScorePanel(GameMain gameMain){
        setLayout(new GridLayout(7 , 1, 10, 10));
        setBackground(new Color(42, 41, 43));

        JLabel titleLabel = new JLabel("High Scores", JLabel.CENTER);
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        add(titleLabel);
        
        ArrayList<User> topUsers = gameMain.db.getTopScore();
        for(int i=0; i<5 && i<topUsers.size(); i++){
            JLabel userLabel = new JLabel(i+1 + "." + topUsers.get(i).getUsername() + " - " + topUsers.get(i).getHighestScore());
            userLabel.setForeground(Color.WHITE);
            userLabel.setFont(new Font("Arial", Font.PLAIN, 36));
            add(userLabel);
        }

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBackground(new Color(0,120,215));
        add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMain.switchScreen("menuScreen");
            }
        });
    }
}
