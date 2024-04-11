import java.awt.Graphics;

public class GameObject {
	
	// Coordinate of the GameObject
	PointWorld Position; 
	
	// Update the GamObject. Other classes will override this function.
	void Update(double deltaTime, PacmanGame game) {
		
	}
	
	// Draws the GamObject. Other classes will override this function.
	void Draw(PacmanGame game, Graphics g){
		
	} 
}
