import java.awt.Color;
import java.awt.Graphics;

public class Dot extends GameObject{

	// Updates the wall cell
	@Override
	void Update(double deltaTime, PacmanGame game) {
		
	}

	
	// Draws the dot cell
	@Override
	void Draw(PacmanGame game, Graphics g) {
		
		PointWindow p = game.getWindowCoordinateFromWorldCoordinate(Position);
				
		int sizeX = (int)(0.5 * game.scale);
		int sizeY = (int)(0.5 * game.scale);
		
		g.setColor(Color.white);
		g.fillOval(p.x, p.y, sizeX, sizeY);
	}
}
