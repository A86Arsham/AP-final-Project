import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MainMenu extends JPanel{
	private GameMain gameMain; 		
	public MainMenu(GameMain gameMain) {
			this.gameMain = gameMain;

			JPanel mainPanel = new JPanel();
			mainPanel.setSize(800,600);
			GridLayout gridLayout = new GridLayout(6, 1, 0, 10);
			mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
			mainPanel.setBackground(new Color(42,41,43));
			mainPanel.setLayout(gridLayout);


			JLabel titleLable = new JLabel("Chicken Invasion",JLabel.CENTER);
			Font titleFont = new Font("Arial", Font.BOLD, 36);
			titleLable.setForeground(Color.RED);
			titleLable.setFont(titleFont);
			titleLable.setVisible(true);
			mainPanel.add(titleLable);

			JButton newGameButton = new JButton("New Game");
			newGameButton.setFont(new Font("Arial", Font.BOLD, 24));
			newGameButton.setBackground(new Color(0, 120, 215));
			newGameButton.setForeground(Color.WHITE);
			newGameButton.setBorderPainted(false);
			newGameButton.setFocusPainted(false);
			mainPanel.add(newGameButton);

			JButton highScoresButton = new JButton("High Scores");
			highScoresButton.setFont(new Font("Arial", Font.BOLD, 24));
			highScoresButton.setBackground(new Color(0, 120, 215));
			highScoresButton.setForeground(Color.WHITE);
			highScoresButton.setBorderPainted(false);
			highScoresButton.setFocusPainted(false);
			mainPanel.add(highScoresButton);

			JButton settingsButton = new JButton("Settings");
			settingsButton.setFont(new Font("Arial", Font.BOLD, 24));
			settingsButton.setBackground(new Color(0, 120, 215));
			settingsButton.setForeground(Color.WHITE);
			settingsButton.setBorderPainted(false);
			settingsButton.setFocusPainted(false);
			mainPanel.add(settingsButton);

			JButton howToPlayButton =  new JButton("How to Play");
			howToPlayButton.setFont(new Font("Arial", Font.BOLD, 24));
			howToPlayButton.setBackground(new Color(0, 120, 215));
			howToPlayButton.setForeground(Color.WHITE);
			howToPlayButton.setBorderPainted(false);
			howToPlayButton.setFocusPainted(false);
			mainPanel.add(howToPlayButton);

			JButton exitButton = new JButton("Exit");
			exitButton.setFont(new Font("Arial", Font.BOLD, 24));
			exitButton.setBackground(new Color(0, 120, 215));
			exitButton.setForeground(Color.WHITE);
			exitButton.setBorderPainted(false);
			exitButton.setFocusPainted(false);
			mainPanel.add(exitButton);

			newGameButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					gameMain.switchScreen("loginScreen");
				}
			});

			exitButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);

				}
			});

			this.setLayout(new BorderLayout());
		    this.add(mainPanel, BorderLayout.CENTER);



		}

	}


