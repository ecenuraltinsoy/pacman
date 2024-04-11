import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.Instant;

public class GameFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	GamePanel gamePanel;
	MainMenuPanel mainMenuPanel;
	EndingScreenPanel endingScreenPanel;
	
	PacmanGame game;
	
	GameFrame() {
		setSize(620, 700);
		setResizable(false);
        setTitle("Pacman");
        setDefaultCloseOperation(3);
        
        setFocusable(true);
        setFocusTraversalKeysEnabled(false); // To disable GUI component traversal by keys such as Tab, etc.
        
        gamePanel = new GamePanel(this);
        gamePanel.setDoubleBuffered(true); // To prevent flicker
        gamePanel.setBackground(Color.black);
        addKeyListener(gamePanel);
              
        mainMenuPanel = new MainMenuPanel(this);
        endingScreenPanel = new EndingScreenPanel(this);

        setContentPane(mainMenuPanel);
        
        Timer timer = new Timer(16, new Repainter(this));
        timer.setInitialDelay(50);
        timer.start();
	}
	
	public void startGame() {
		setContentPane(gamePanel);
		revalidate();
	}
	
	public void successEndGame(int score) {
		setContentPane(endingScreenPanel);
		endingScreenPanel.prepareSuccessScreen(score);
		revalidate();
	}
	public void failureEndGame(int score) {
		setContentPane(endingScreenPanel);
		endingScreenPanel.prepareFailureScreen(score);
		revalidate();
	}
	
	@Override
    public void paint(Graphics g) 
    {
		super.paint(g);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}

