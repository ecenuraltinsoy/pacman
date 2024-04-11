import java.awt.Graphics;
import java.util.ArrayList;

public class Ghost extends GameObject{

	public final double speed = 5.0;
	
	// Holds the color of the Ghost
	GhostColor color = GhostColor.yellow; 
	
	// Holds the current direction of Ghost 
	int facing = Direction.NONE; 

	// Handles ghost movement AI
	@Override
	void Update(double deltaTime, PacmanGame game) {
		if (deltaTime == 0.0) {
			return;
		}
		
		chooseRandomDirection(deltaTime, game);
		
		if (facing == Direction.LEFT) {
		Position.x = checkMoveX(-speed * deltaTime / 1000, game);
		}
		else if (facing == Direction.RIGHT) {
		Position.x = checkMoveX(speed * deltaTime / 1000, game);
		}
		else if (facing == Direction.UP) {
		Position.y = checkMoveY(-speed * deltaTime / 1000, game);
		}
		else if (facing == Direction.DOWN) {
		Position.y = checkMoveY(speed * deltaTime / 1000, game);
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
	
	public boolean canContinueToMoveInDirection(int direction, double deltaTime, PacmanGame game) {
		if (direction == Direction.LEFT) {
			double moveAmount = -speed * deltaTime / 1000;
			double moveX = checkMoveX(moveAmount, game);
			
			return (moveX != Position.x);
		}
		else if (direction == Direction.RIGHT) {
			double moveAmount = speed * deltaTime / 1000;
			double moveX = checkMoveX(moveAmount, game);
			
			return (moveX != Position.x);
		}
		else if (direction == Direction.UP) {
			double moveAmount = -speed * deltaTime / 1000;
			double moveY = checkMoveY(moveAmount, game);
			
			return (moveY != Position.y);
		}
		else if (direction == Direction.DOWN) {
			double moveAmount = speed * deltaTime / 1000;
			double moveY = checkMoveY(moveAmount, game);
			
			return (moveY != Position.y);
		}
		
		return false;
	}
	
	public void beginTurnInDirection(int direction) {
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

	// Draws the ghost object
	@Override
	void Draw(PacmanGame game, Graphics g) {

		PointWindow p = game.getWindowCoordinateFromWorldCoordinate(Position);
		
		int sizeX = (int)(1.0 * game.scale);
		int sizeY = (int)(1.0 * game.scale);
		
		
		if (color == GhostColor.yellow) {
			g.drawImage(game.ghost1Image, p.x, p.y, sizeX, sizeY, null);
		}
		else if (color == GhostColor.green) {
			g.drawImage(game.ghost2Image, p.x, p.y, sizeX, sizeY, null);
		}
		else if (color == GhostColor.blue) {
			g.drawImage(game.ghost3Image, p.x, p.y, sizeX, sizeY, null);
		}
	}
	
	int chooseRandomFromArrayList(ArrayList<Integer> list) {
		int a = (int) (Math.random() * list.size());
		
		return list.get(a);
	}
	
	int cantTurnUpdates = 0;
	
	void chooseRandomDirection(double deltaTime, PacmanGame game) {
		if (facing == Direction.NONE) {
			ArrayList<Integer> directions = new ArrayList<Integer>();
			if (canContinueToMoveInDirection(Direction.UP, deltaTime, game)) {
				directions.add(Direction.UP);
			}
			if (canContinueToMoveInDirection(Direction.DOWN, deltaTime, game)) {
				directions.add(Direction.DOWN);
			}
			if (canContinueToMoveInDirection(Direction.RIGHT, deltaTime, game)) {
				directions.add(Direction.RIGHT);
			}
			if (canContinueToMoveInDirection(Direction.LEFT, deltaTime, game)) {
				directions.add(Direction.LEFT);
			}
			facing = chooseRandomFromArrayList(directions);
		}
		else {
			if (cantTurnUpdates == 0) {
				if (facing == Direction.LEFT || facing == Direction.RIGHT) {
					ArrayList<Integer> turnList = new ArrayList<Integer>();
					if (canTurnToDirection(Direction.UP, game)) {
						turnList.add(Direction.UP);
					}
					if (canTurnToDirection(Direction.DOWN, game)) {
						turnList.add(Direction.DOWN);
					}
					if (turnList.size() > 0) {
						beginTurnInDirection(chooseRandomFromArrayList(turnList));
						cantTurnUpdates = 20;
					}
				}
				else if (facing == Direction.UP || facing == Direction.DOWN) {
					ArrayList<Integer> turnList = new ArrayList<Integer>();
					if (canTurnToDirection(Direction.RIGHT, game)) {
						turnList.add(Direction.RIGHT);
					}
					if (canTurnToDirection(Direction.LEFT, game)) {
						turnList.add(Direction.LEFT);
					}
					if (turnList.size() > 0) {
						beginTurnInDirection(chooseRandomFromArrayList(turnList));
						cantTurnUpdates = 20;
					}
				}
			}
			else {
				if (cantTurnUpdates > 0) {
					cantTurnUpdates--;
				}
			}
		}
	}
}
