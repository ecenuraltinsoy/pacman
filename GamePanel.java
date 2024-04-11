import java.awt.*;
import java.awt.event.*;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	PacmanGame game;
	GameFrame gameFrame;
	JLabel scoreInfo;

	public GamePanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		
		scoreInfo = new JLabel ();
		scoreInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
		scoreInfo.setBackground(Color.black);
		scoreInfo.setForeground(Color.white);
		scoreInfo.setFont(new java.awt.Font("Arial Black", Font.BOLD, 12));
		add(scoreInfo);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);

		if (game == null) {
			game = new PacmanGame();
			game.setupNewGame();
		}
		
		game.update();
		game.Draw(g);
		
		updateScoreInformation();
		
		if (game.state == GameState.Success) {
			gameFrame.successEndGame(game.score);
			game = null;
		}
		else if (game.state == GameState.Failed) {
			gameFrame.failureEndGame(game.score);
			game = null;
		}
	}
	
	public void updateScoreInformation() {
		scoreInfo.setText("Score: " + game.score);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (game == null) {
			return;
		}
		
		if (!game.KeysDown.contains(e.getKeyCode()))
		{
			game.KeysDown.add(e.getKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if (game == null) {
			return;
		}

		if (game.KeysDown.contains(e.getKeyCode()))
		{
			// We have to remove keycodes like this instead of KeysDown.remove(e.getKeyCode()) because our arraylist holds integers
			// calling remove with an integer tries to remove the element as an index, not as a value.
			game.KeysDown.remove(Integer.valueOf(e.getKeyCode()));
		}
	}
	
}
