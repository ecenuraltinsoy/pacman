import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Repainter implements ActionListener
{
	private JFrame Frame;
	
	Repainter(JFrame frame){
		Frame = frame;
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
        
		Frame.getContentPane().validate();
		Frame.getContentPane().repaint(); 	
		Frame.repaint();
    }
}

