import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JLabel;

import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

class PacmanGame {
	// Stores relevant keys which are pressed
	public ArrayList<Integer> KeysDown; 
	
	// Stores GameObjects
	public ArrayList<GameObject> Objects; 
	
	public Map map;
	
	public double scale = 20.0;
	public double offsetX = 6.0;
	public double offsetY = 5.0;
	
	public BufferedImage pacmanImageUp;
	public BufferedImage pacmanImageDown;
	public BufferedImage pacmanImageLeft;
	public BufferedImage pacmanImageRight;
	public BufferedImage ghost1Image;
	public BufferedImage ghost2Image;
	public BufferedImage ghost3Image;
	
	Instant lastUpdateInstant;
	
	GameState state = GameState.Playing;

	// Resets GameObjects for a new game
	public void setupNewGame()
	{
		loadImage();
		KeysDown = new ArrayList<Integer>();
		Objects = new ArrayList<GameObject>();
		
		map = new Map();
		
		mapWidth = map.MapData[0].length;
		mapHeight = map.MapData.length;
		
		ObjectMap = new GameObject[mapHeight][mapWidth];
		
		for(int y = 0; y < map.MapData.length; y++) {
			for(int x = 0; x < map.MapData[y].length; x++) {
				if (map.MapData[y][x] == 0) {
					Wall wall = new Wall();
					wall.Position = new PointWorld();
					wall.Position.x = x;
					wall.Position.y = y;
					
					ObjectMap [y][x] = wall;
					Objects.add(wall);
				} 
				else if (map.MapData[y][x] == 1){
					Dot dots = new Dot();
					dots.Position = new PointWorld();
					dots.Position.x = x + 0.35;
					dots.Position.y = y + 0.25;
					ObjectMap [y][x] = dots;
					Objects.add(dots);
				}
				else if (map.MapData[y][x] == 2) {
					Pacman pacman = new Pacman();
					pacman.Position = new PointWorld();
					pacman.Position.x = x;
					pacman.Position.y = y;
					Objects.add(pacman);
				}
				else if (map.MapData[y][x] == 4) {
					Ghost ghost1 = new Ghost();
					ghost1.color = GhostColor.yellow;
					ghost1.Position = new PointWorld();
					ghost1.Position.x = x;
					ghost1.Position.y = y;
					Objects.add(ghost1);
				}
				else if (map.MapData[y][x] == 5) {
					Ghost ghost2 = new Ghost();
					ghost2.color = GhostColor.green;
					ghost2.Position = new PointWorld();
					ghost2.Position.x = x;
					ghost2.Position.y = y;
					Objects.add(ghost2);
				}
				else if (map.MapData[y][x] == 6) {
					Ghost ghost3 = new Ghost();
					ghost3.color = GhostColor.blue;
					ghost3.Position = new PointWorld();
					ghost3.Position.x = x;
					ghost3.Position.y = y;
					Objects.add(ghost3);
				}
			}
		}
	}
	public void loadImage() {
		try {
			pacmanImageUp = ImageIO.read(new FileImageInputStream(new File ("images/pac-man1.png")));
			pacmanImageDown = ImageIO.read(new FileImageInputStream(new File ("images/pac-man2.png")));
			pacmanImageLeft = ImageIO.read(new FileImageInputStream(new File ("images/pac-man.png")));
			pacmanImageRight = ImageIO.read(new FileImageInputStream(new File ("images/pac-man3.png")));
			ghost1Image = ImageIO.read(new FileImageInputStream(new File ("images/ghost1.png")));
			ghost2Image = ImageIO.read(new FileImageInputStream(new File ("images/ghost2.png")));
			ghost3Image = ImageIO.read(new FileImageInputStream(new File ("images/ghost3.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// Updates for the current frame and calls update of Objects
	public void update() 
	{
		double deltaTime = 0.0;
		Instant updateInstant = Instant.now();
		
		if (lastUpdateInstant != null)
		{
			deltaTime = Duration.between(lastUpdateInstant, updateInstant).toMillis();
		}	
		
		for (int a = 0; a < Objects.size(); a++) {
			Objects.get(a).Update(deltaTime, this);
		}
		
		if (score > 1000) {
			state = GameState.Success;
		}
		
		lastUpdateInstant = updateInstant;
	}
	
	public void Draw(Graphics g)
	{
		for (int a = 0; a < Objects.size(); a++) {
			Objects.get(a).Draw(this, g);
		}
	}
	
	public PointWindow getWindowCoordinateFromWorldCoordinate(PointWorld pointWorld) {
		
		PointWindow point =  new PointWindow();
		point.x = (int) (pointWorld.x * scale + offsetX);
		point.y = (int) (pointWorld.y * scale + offsetY);
		
		return point;
	}
	
	GameObject [][] ObjectMap;
	int mapHeight;
	int mapWidth;
	
	public int score = 0;
}
