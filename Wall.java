import java.awt.*;


public class Wall extends GameObject{

	// Updates the wall cell
	@Override
	void Update(double deltaTime, PacmanGame game) {
		
	}

	// Draws the wall cell
	@Override
	void Draw(PacmanGame game, Graphics g) {
		
		PointWindow p = game.getWindowCoordinateFromWorldCoordinate(Position);
		
		int sizeX = (int)(1.0 * game.scale);
		int sizeY = (int)(1.0 * game.scale);
		
		int sizeX2 = (int)(0.7 * game.scale);
		int sizeY2 = (int)(0.7 * game.scale);
		
		int offset = (sizeX -  sizeX2) / 2;
		g.setColor(Color.blue);
		g.drawRect(p.x, p.y, sizeX, sizeY);
		g.fillRect(p.x + offset, p.y + offset, sizeX2, sizeY2);
	}
}
