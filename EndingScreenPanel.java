import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;

public class EndingScreenPanel extends JPanel implements ActionListener{

	JLabel resultText;
	JButton restartButton;
	GameFrame gameFrame;
	
	public EndingScreenPanel(GameFrame gameFrame) {
		
		this.gameFrame = gameFrame;
		
		setBackground(Color.black);
		setLayout(new GridBagLayout());
		
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        subPanel.setBackground(Color.black);
		
        add(subPanel);
		
		resultText = new JLabel();
		restartButton = new JButton("Restart");
		
		restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		restartButton.setBackground(Color.black);
		restartButton.setForeground(Color.white);
		restartButton.setFont(new java.awt.Font("Arial Black", Font.BOLD, 12));
		restartButton.addActionListener(this);
	    
		resultText.setForeground(Color.white);
		resultText.setBackground(Color.black);
		resultText.setFont(new java.awt.Font("Arial Black", Font.BOLD, 24));
		resultText.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		subPanel.add(resultText);
        subPanel.add(Box.createVerticalStrut(50));
		subPanel.add(restartButton);

	}
	public void prepareFailureScreen(int score) {
		
		resultText.setText("YOU FAILED! :( Your score: " + score);
			
		
	}
	public void prepareSuccessScreen(int score) {
		
		resultText.setText("CONGRATULATIONS! :) Your score: " + score);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == restartButton) {
			gameFrame.startGame();
		}
	}

}
