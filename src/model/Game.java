package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import model.Ball.Direction;

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
	
//Attributes
	
	private String path;
	private int difficulty;
	private int points;
	private boolean win;
	private Score[][] scores;
	private ArrayList<Ball> balls;
	
//Constructor
	
	/**
	 * <b>Description:</b> Creates a new instance of Game.<br>
	 * @param path - The path of the file with the game configuration.
	 * @throws InvalidPathException If the path doesn't exist or isn't a file.
	 */
	
	public Game(String path) throws InvalidPathException {
		
		File file = new File(path);
		
		if(!file.isFile()) {
			
			throw new InvalidPathException();
		}
		
		this.path = path;
		difficulty = -1;
		points = 0;
		win = false;
		scores = new Score[NUMBER_OF_LEVELS][NUMBER_OF_BESTS_SCORES];
		balls = new ArrayList<Ball>();
	}
	
//Methods
	
	/**
	 * <b>Description:</b> This method allows reading the valid lines of the game configuration file (lines that don't start with '#').<br>
	 * @throws FileNotFoundException If the named file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
	 * @throws IOException If an I/O error occurs.
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
	 * <b>Description:</b> This method allows creating a ball with an array of String with the attributes.<br>
	 * @param attributes - The ball attributes.
	 * @return A ball with the attributes given.
	 */
	
	public Ball createBall(String[] attributes) {
		
		Ball ball;
		
		try {
			
			double radius = Double.parseDouble(attributes[0]);
			double posX = Double.parseDouble(attributes[1]);
			double posY = Double.parseDouble(attributes[2]);
			int waitTime = Integer.parseInt(attributes[3]);
			Direction direction = Direction.valueOf(attributes[4]);
			int bounces = Integer.parseInt(attributes[5]);
			boolean moving = Boolean.parseBoolean(attributes[6]);
			
			ball = new Ball(radius, posX, posY, waitTime, direction, bounces, moving);
		}
		catch(IllegalArgumentException e) {
			
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
			
			if(balls.get(i).isMoving()) {
				
				win = false;
				running = false;
			}
		}
		
		if(win) {
			
			this.win = true;
		}
		
		return win;
	}
	
	/**
	 * <b>Description:</b> This method allows serializing the scores.<br>
	 * @param path - The path where the scores will be saved.
	 * @throws FileNotFoundException If the named file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
	 * @throws IOException If an I/O error occurs.
	 * @throws InvalidPathException If the path doesn't exist or isn't a file.
	 */
	
	public void saveScores(String path) throws FileNotFoundException, IOException, InvalidPathException {
		
		File file1 = new File(path);
		
		if(file1.isFile()) {
			
			FileOutputStream file = new FileOutputStream(path);
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(scores);
			output.close();
		}
		else {
			
			throw new InvalidPathException();
		}
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
