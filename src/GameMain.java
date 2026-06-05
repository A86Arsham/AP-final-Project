import javax.swing.*;
import java.awt.*;
public class GameMain{
	JFrame window;
	JPanel screens;
	CardLayout controller;

	public GameMain() {
		window = new JFrame("Chicken Invaders");
		window.setSize(800,600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setResizable(false);

		controller = new CardLayout();
		screens = new JPanel(controller);

		MainMenu mainMenu = new MainMenu(this);
		screens.add(mainMenu, "menuScreen");

		LoginPanel loginPanel = new LoginPanel(this);
		screens.add(loginPanel, "loginScreen");

		RegisterPanel registerPanel = new RegisterPanel(this);
		screens.add(registerPanel, "registerScreen");

		window.add(screens);
		window.setVisible(true);
	}

	public void switchScreen(String name) {
		controller.show(screens, name);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GameMain());
	}
}