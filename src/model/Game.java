package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
* <b>Description:</b> The class Game in the package model.<br>
* @author Johan Giraldo.
*/

public class Game {
	
//Constants
	
	/**
	 * a constant that represents the game's number of difficulty levels.
	 */
	public static final int NUMBER_OF_LEVELS = 3;
	
	/**
	 * a constant that represents the number of best scores that the game saves for each difficulty level.
	 */
	public static final int NUMBER_OF_BESTS_SCORES = 10;
	
	/**
	 * a constant that represents an upward direction movement.
	 */
	public static final int UP = 0;
	
	/**
	 * a constant that represents downward direction movement.
	 */
	public static final int DOWN = 1;
	
	/**
	 * a constant that represents leftward direction movement.
	 */
	public static final int LEFT = 2;
	
	/**
	 * a constant that represents rightward direction movement.
	 */
	public static final int RIGHT = 3;
	
//Attributes
	
	private String path;
	private int difficulty;
	private boolean win;
	private Score[][] scores;
	private ArrayList<Ball> balls;
	
//Constructor
	
	/**
	 * <b>Description:</b> Creates a new instance of Game.<br>
	 * @param path - The path of the file with the game configuration.
	 * @throws InvalidPathException - If the path doesn't exist or the path isn't a file.
	 */
	
	public Game(String path) throws InvalidPathException {
		
		File file = new File(path);
		
		if(!file.exists() || !file.isFile()) {
			
			throw new InvalidPathException();
		}
		
		this.path = path;
		difficulty = -1;
		scores = new Score[NUMBER_OF_LEVELS][NUMBER_OF_BESTS_SCORES];
		balls = new ArrayList<Ball>();
	}
	
//Methods
	
	/**
	 * <b>Description:</b> This method allows reading the valid lines of the game configuration file (lines that don't start with '#').<br>
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	
	public void load() throws FileNotFoundException, IOException {
		
		FileReader file = new FileReader(path);
		BufferedReader reader = new BufferedReader(file);
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		
		while((line = reader.readLine()) != null) {
			
			if(line.charAt(0) != '#') {	
				
				lines.add(line);
			}
		}
		
		reader.close();
		initGame(lines);
	}
	
	/**
	 * <b>Description:</b> This method allows creating the balls and setting the difficulty attribute.<br>
	 * @param lines - The lines of the game configuration file.
	 */
	
	public void initGame(ArrayList<String> lines) {
		
		difficulty = Integer.parseInt(lines.get(0));
		String[] attributes = new String[7];
		
		for(int i = 1; i < lines.size(); i++) {
			
			attributes = lines.get(i).split("\t");
			Ball ball = createBall(attributes);
			
			if(ball != null) {
				
				balls.add(ball);
			}
		}
	}
	
	/**
	 * <b>Description:</b> This method allows setting the movement direction as a number.<br>
	 * @param direction - A String with the movement direction.
	 * @return The direction movement as a number.
	 */
	
	public int selectDirection(String direction) {
		
		int result = UP;
		
		if(direction.equals("DOWN") || direction.equals("ABAJO")) {
			
			result = DOWN;
		}
		else if(direction.equals("LEFT") || direction.equals("IZQUIERDA")) {
			
			result = LEFT;
			
		}
		else if(direction.equals("RIGHT") || direction.equals("DERECHA")) {
			
			result = RIGHT;
		}
		
		return result;
	}
	
	/**
	 * <b>Description:</b> This method allows creating a ball with an array of String with the attributes.<br>
	 * @param attributes - The ball attributes.
	 * @return A ball with the attributes given.
	 */
	
	public Ball createBall(String[] attributes) {
		
		Ball ball;
		
		try {
			
			double radius = Double.parseDouble(attributes[0]);
			double centerX = Double.parseDouble(attributes[1]);
			double centerY = Double.parseDouble(attributes[2]);
			int direction = selectDirection(attributes[3]);
			ball = new Ball(radius, centerX, centerY, direction);
		}
		catch(NumberFormatException e) {
			
			ball = null;
		}
		
		return ball;
	}
	
	/**
	 * <b>Description:</b> This method allows checking if the player win the game.<br>
	 * @return true if the player win, false in otherwise.
	 */
	
	public boolean win() {
		
		boolean win = true;
		boolean running = true;
		
		for(int i = 0; i < balls.size() && running; i++) {
			
			if(balls.get(i).isBouncing()) {
				
				win = false;
				running = false;
			}
		}
		
		return win;
	}
	
//Setters
	
	/**
	 * <b>Description:</b> Sets the value of the attribute path.<br>
	 * @param path - The path of the file with the game configuration. 
	 */

	public void setPath(String path) {
		this.path = path;
	}
}
