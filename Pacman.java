import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class Pacman extends GameObject{
	
	
	// Holds the current direction of Pacman
	int facing = Direction.RIGHT; 
	int requestedFacing = Direction.RIGHT;
	public final double speed = 4.0;
	
	// Updates the Pacman Object. Calls UpdateMovement, CheckForGhostCollision, CheckForDotCollision
	@Override
	void Update(double deltaTime, PacmanGame game) {
		checkForDotCollision(deltaTime, game);
		
		if (game.KeysDown.contains(KeyEvent.VK_RIGHT)) {
			requestedFacing = Direction.RIGHT;
		}
		else if (game.KeysDown.contains(KeyEvent.VK_LEFT)) {
			requestedFacing = Direction.LEFT;
		}
		else if (game.KeysDown.contains(KeyEvent.VK_UP)) {
			requestedFacing = Direction.UP;
		}
		else if (game.KeysDown.contains(KeyEvent.VK_DOWN)) {
			requestedFacing = Direction.DOWN;
		}	
		
		if (requestedFacing * facing > 0) {
			facing = requestedFacing;
		}
		else {
			if (canTurnToDirection(requestedFacing, game)) {
				beginMoveInDirection(requestedFacing);
			}
		}
		
		if (facing == Direction.RIGHT) {
			Position.x = checkMoveX(speed * deltaTime / 1000, game);			
		}
		else if (facing == Direction.LEFT) {
			Position.x = checkMoveX(-speed * deltaTime / 1000, game);
		}
		else if (facing == Direction.UP) {
			Position.y = checkMoveY(-speed * deltaTime / 1000, game);
		}
		else if (facing == Direction.DOWN) {
			Position.y = checkMoveY(speed * deltaTime / 1000, game);
		}
		
		if (checkForGhostCollision(game)) {
			game.state = GameState.Failed;
		}
		if (checkGameEnding(game)) {
			game.state = GameState.Success;
		}
	}
	
	public boolean canTurnToDirection(int direction, PacmanGame game) {
		if (direction == Direction.DOWN || direction == Direction.UP) {
			int nearestMapColumn = (int)(Position.x + 0.5);
			double intDistance = Math.abs(nearestMapColumn - Position.x);
			if (intDistance < 0.1) {
				int targetMapColumn = nearestMapColumn;
				int targetMapRow = (int)Position.y;
				
				if (direction == Direction.UP) {
					targetMapRow--;
				}
				else {
					targetMapRow++;
				}
				
				if (!(game.ObjectMap[targetMapRow][targetMapColumn] instanceof Wall)) {
					return true;
				}
			}
		}

		if (direction == Direction.RIGHT || direction == Direction.LEFT) {
			int nearestMapRow = (int)(Position.y + 0.5);
			double intDistance = Math.abs(nearestMapRow - Position.y);
			
			if (intDistance < 0.1) {
				int targetMapColumn = (int)Position.x;
				int targetMapRow = nearestMapRow;
				
				if (direction == Direction.LEFT) {
					targetMapColumn--;
				}
				else {
					targetMapColumn++;
				}

				if (!(game.ObjectMap[targetMapRow][targetMapColumn] instanceof Wall)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void beginMoveInDirection(int direction) {
		if (direction == Direction.DOWN || direction == Direction.UP) {
			int nearestMapColumn = (int)(Position.x + 0.5);
			facing = direction;
			Position.x = nearestMapColumn; // to fit exactly in the grid
		}
		else {
			int nearestMapRow = (int)(Position.y + 0.5);
			facing = direction;
			Position.y = nearestMapRow; // to fit exactly in the grid
		}
	}
	
	public double checkMoveX(double moveAmount, PacmanGame game) {
		double posToGo = Position.x + moveAmount;
		
		int mapColumn = 0;
		if (moveAmount > 0.0) {
			mapColumn = (int)posToGo + 1;
		}
		else {
			mapColumn = (int)posToGo;
		}
		int mapRow = (int)Position.y;
		
		// Check if position is out of bounds for some reason and don't let pacman move
		if (mapColumn > game.mapWidth - 1 || mapColumn < 0) {
			return Position.x;
		}
		
		// Move adjacent to wall if we are moving into a wall
		if (game.ObjectMap[mapRow][mapColumn] instanceof Wall) {
			if (moveAmount > 0) {
				return (int)posToGo;
			}
			else {
				return (int)posToGo + 1;
			}
		}
		
		return posToGo;
	}

	public double checkMoveY(double moveAmount, PacmanGame game) {
		double posToGo = Position.y + moveAmount;
		
		int mapRow = 0;
		if (moveAmount > 0.0) {
			mapRow = (int)posToGo + 1;
		}
		else {
			mapRow = (int)posToGo;
		}
		int mapColumn = (int)Position.x;
		
		// Check if position is out of bounds for some reason and don't let pacman move
		if (mapRow > game.mapHeight - 1 || mapRow < 0) {
			return Position.y;
		}
		
		// Move adjacent to wall if we are moving into a wall
		if (game.ObjectMap[mapRow][mapColumn] instanceof Wall) {
			if (moveAmount > 0) {
				return (int)posToGo;
			}
			else {
				return (int)posToGo + 1;
			}
		}
		
		return posToGo;
	}

	// Updates the position of Pacman according to data such as Keys down, collision with walls, etc.
	public void updateMovement(double deltaTime, PacmanGame game) {
		
	} 
	
	// Returns if Pacman collides with a ghost
	public boolean checkForGhostCollision(PacmanGame game) {
		
		for (int a = 0; a < game.Objects.size(); a++) {
			if (game.Objects.get(a) instanceof Ghost) {
				if (Math.abs(game.Objects.get(a).Position.x - this.Position.x) < 0.5 && Math.abs(game.Objects.get(a).Position.y - this.Position.y) < 0.5) {
					return true;
				}
			}
		}
		return false;
	}
	
	// Checks if Pacman collides with a dot and updates score accordingly
	public void checkForDotCollision(double deltaTime, PacmanGame game) {
		
		int mapRow = (int)(Position.y + 0.5);
		int mapColumn = (int)(Position.x + 0.5);
		
		if (game.ObjectMap[mapRow][mapColumn] instanceof Dot) {
			
			game.Objects.remove(game.ObjectMap[mapRow][mapColumn]);
			game.score++;
			game.ObjectMap[mapRow][mapColumn] = null;
		}
	} 
	public boolean checkGameEnding(PacmanGame game) {
		for (int a = 0; a < game.Objects.size(); a++) {
			if (game.Objects.get(a) instanceof Dot) {
				return false;
			}
		}
		return true;
	}

	// Draws the Pacman GameObject
	@Override
	void Draw(PacmanGame game, Graphics g) {

		PointWindow p = game.getWindowCoordinateFromWorldCoordinate(Position);
		
		int sizeX = (int)(1.0 * game.scale);
		int sizeY = (int)(1.0 * game.scale);
		
		if (facing == Direction.RIGHT) {
			g.drawImage(game.pacmanImageRight, p.x, p.y, sizeX, sizeY, null);
		}
		else if (facing == Direction.LEFT) {
			g.drawImage(game.pacmanImageLeft, p.x, p.y, sizeX, sizeY, null);
		}
		else if (facing == Direction.UP) {
			g.drawImage(game.pacmanImageUp, p.x, p.y, sizeX, sizeY, null);
		}
		else if (facing == Direction.DOWN) {
			g.drawImage(game.pacmanImageDown, p.x, p.y, sizeX, sizeY, null);
		}
	}

}
