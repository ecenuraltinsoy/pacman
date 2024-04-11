import java.awt.*;
import java.awt.event.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel implements ActionListener{

	JButton startButton;
	JLabel title;
	GameFrame gameFrame;

	public MainMenuPanel(GameFrame gameFrame) {
		setBackground(Color.black);
		setLayout(new GridBagLayout());
		
		JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        subPanel.setBackground(Color.black);
        
        add(subPanel);
		
        title = new JLabel("PACMAN");
        startButton = new JButton("Start");
		
        title.setForeground(new Color(255,255,153));
        title.setBackground(Color.black);
        title.setFont(new java.awt.Font("Arial Black", Font.BOLD, 50));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		startButton.setBackground(Color.black);
		startButton.setForeground(Color.white);
		startButton.setFont(new java.awt.Font("Arial Black", Font.BOLD, 12));
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		startButton.addActionListener(this);
	        
		subPanel.add(title);
        subPanel.add(Box.createVerticalStrut(50));
		subPanel.add(startButton);
	    
	    this.gameFrame = gameFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == startButton) {
			gameFrame.startGame();
		}
	}

}
