package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
	
	/**
	 * a constant that represents the relative path where the games will be loaded.
	 */
	
	public static final String GAMES = "data/games";
	
	/**
	 * a constant that represents the relative path where the games will be loaded.
	 */
	
	public static final String SCORES = "data/scores/scores.dat";
	
//Attributes
	
	private String path;
	private int difficulty;
	private int points;
	private Score[][] scores;
	private ArrayList<Ball> balls;
	
//Constructor
	
	/**
	 * <b>Description:</b> Creates a new instance of Game.<br>
	 * @param path - The path of the file with the game configuration.
	 * @throws InvalidPathException If the path does not exist or is not a file.
	 */
	
	public Game(String path) throws InvalidPathException {
		
		File file = new File(path);
		
		if(!file.isFile()) {
			
			throw new InvalidPathException();
		}
		
		this.path = path;
		difficulty = -1;
		points = 0;
		scores = new Score[NUMBER_OF_LEVELS][NUMBER_OF_BESTS_SCORES];
		balls = new ArrayList<Ball>();
	}
	
//Methods
	
	/**
	 * <b>Description:</b> This method allows reading the valid lines of the game configuration file (lines that don't start with '#').<br>
	 * @throws FileNotFoundException If the named file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
	 * @throws IOException If an I/O error occurs.
	 * @throws InvalidPathException If the file is not a valid game (do not have the expected format).
	 */
	
	public void load() throws FileNotFoundException, IOException, InvalidPathException{
		
		FileReader file = new FileReader(path);
		BufferedReader reader = new BufferedReader(file);
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		
		while((line = reader.readLine()) != null) {
			
			if(!line.isEmpty() && line.charAt(0) != '#') {	
				
				lines.add(line);
			}
		}
		
		reader.close();
		
		try {
			
			initGame(lines);
		}
		catch(IllegalArgumentException e) {
			
			throw new InvalidPathException();
		}
	}
	
	/**
	 * <b>Description:</b> This method allows creating the balls and setting the difficulty attribute.<br>
	 * @param lines - The lines of the game configuration file.
	 * @throws IlegallArgumentException Thrown to indicate that a method has been passed an illegal or inappropriate argument.
	 */
	
	public void initGame(ArrayList<String> lines) throws IllegalArgumentException {
		
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
	 * @throws IlegallArgumentException Thrown to indicate that a method has been passed an illegal or inappropriate argument.
	 */
	
	public Ball createBall(String[] attributes) throws IllegalArgumentException {
		
		Ball ball = null;
		
		double radius = Double.parseDouble(attributes[0]);
		double posX = Double.parseDouble(attributes[1]);
		double posY = Double.parseDouble(attributes[2]);
		int waitTime = Integer.parseInt(attributes[3]);
		Direction direction = Direction.valueOf(attributes[4]);
		int bounces = Integer.parseInt(attributes[5]);
		boolean moving = Boolean.parseBoolean(attributes[6]);
		
		ball = new Ball(radius, posX, posY, waitTime, direction, bounces, moving);
		
		return ball;
	}
	
	/**
	 * <b>Description:</b> This method allows getting the names of the high scores.<br>
	 * @param difficulty The game difficulty.
	 * @return An array of strings with the names of the high scores.
	 */
	
	public String[] showHighScoresNames(int difficulty) {
		
		String[] names = new String[NUMBER_OF_BESTS_SCORES];
		
		for(int i = 0; i < scores[difficulty].length; i++) {
			
			if(scores[difficulty][i] != null) {
				
				names[i] = scores[difficulty][i].getName();
			}
			else {
				
				names[i] = "-";
			}
		}
		
		return names;
	}
	
	/**
	 * <b>Description:</b> This method allows getting the points of the high scores.<br>
	 * @param difficulty The game difficulty.
	 * @return An array of strings with the points of the high scores.
	 */
	
	public int[] showHighScoresPoints(int difficulty) {
		
		int[] names = new int[NUMBER_OF_BESTS_SCORES];
		
		for(int i = 0; i < scores[difficulty].length; i++) {
			
			if(scores[difficulty][i] != null) {
				
				names[i] = scores[difficulty][i].getPoints();
			}
			else {
				
				names[i] = -1;
			}
		}
		
		return names;
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
			
			calculateScore();
		}
		
		return win;
	}
	
	/**
	 * <b>Description:</b> This method allows calculating the points.<br>
	 * <b>Post:</b> The value of the attribute points is set.<br>
	 */
	
	private void calculateScore() {
		
		int points = 0;
		
		for(Ball ball : balls) {
			
			points += ball.getBounces();
		}
		
		this.points = points;
	}
	
	
	/**
	 * <b>Description:</b> This method allows verifying if there are a free slot.<br>
	 * @return True if there are a free slot, false in otherwise.
	 */
	
	public boolean checkFreeSlot() {
		
		boolean free = false;
		boolean running = true;
		
		for(int i = 0; i < scores[difficulty].length && running; i++) {
			
			if(scores[difficulty][i] == null) {
				
				free = true;
				running = false;
			}
		}
		
		return free;
	}
	
	/**
	 * <b>Description:</b> This method allows verifying if the score is a high score.<br>
	 * @return True if is a high score, false in otherwise.
	 */
	
	public boolean isHighScore() {
		
		return (getMinorScore().getPoints() > points);
	}
	
	/**
	 * <b>Description:</b> This method allows getting the minor high score.<br>
	 * <b>Pre:</b> No one element in scores can be null.<br>
	 * @return The minor high score.
	 */
	
	public Score getMinorScore() {
		
		return scores[difficulty][(NUMBER_OF_BESTS_SCORES - 1)];
	}
	
	/**
	 * <b>Description:</b> This method allows adding a score to the scores.
	 * @param name - The player's name who got the points.
	 */
	
	public void addScore(String name) {
		
		if(checkFreeSlot()) {
			
			addScoreFreeSlot(name);
		}
		else {
			
			addScoreSorted(name);
		}
	}
	
	/**
	 * <b>Description:</b> This method allows adding a score and sort them.<br>
	 * @param name - The player's name who got the points.
	 */
	
	private void addScoreSorted(String name) {
		
		scores[difficulty][(NUMBER_OF_BESTS_SCORES - 1)] = new Score(name, points);
		sortScores();
	}
	
	/**
	 * <b>Description:</b> This method allows sorting the scores from major to minor by the points.<br>
	 * <b>Post:</b> The score are sorted by points from major to minor.<br>
	 */
	
	public void sortScores(){
		
		for(int i = 1; i < scores[difficulty].length; i++){
			for(int j = i - 1; j >= 0 && scores[difficulty][j] != null && scores[difficulty][j+1] != null && scores[difficulty][j].compareTo(scores[difficulty][j+1]) > 0; j--){
				
				Score one = scores[difficulty][j];
				Score two = scores[difficulty][j+1];
				
				scores[difficulty][j] = two;
				scores[difficulty][j+1] = one;
			}
		}
	}
	
	/**
	 * <b>Description:</b> This method allows adding a score.<br>
	 * @param name - The player's name who got the points.
	 */
	
	private void addScoreFreeSlot(String name) {
		
		boolean running = true;
		
		for(int i = 0; i < scores[difficulty].length && running; i++) {
			
			if(scores[difficulty][i] == null) {
				
				scores[difficulty][i] = new Score(name, points);
				running = false;
				sortScores();
			}
		}
	}
	
	/**
	 * <b>Description:</b> This method allows stopping a ball if the point given is inside the ball.<br>
	 * @param x - The point x position.
	 * @param y - The point y position.
	 */
	
	public void stopBalls(double x, double y) {
		
		for(Ball ball : balls) {
			
			ball.stop(x, y);
		}
	}
	
	/**
	 * <b>Description:</b> This method allows saving a game.<br>
	 * @param path The path where the game will be saved.
	 * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified path name has failed.
	 */
	
	public void saveGame(String path) throws FileNotFoundException {
		
		File file = new File(path);
		PrintWriter writer = new PrintWriter(file);
		writer.append(saveGame());
		writer.close();
	}
	
	/**
	 * <b>Description:</b> This method allows making the to string to the game.<br>
	 * @return A string with all the game information.
	 */
	
	public String saveGame() {
		
		String data = "#level\n";
		data += Integer.toString(difficulty) + "\n";
		data += "#radius\tposX\tposY\twait time\tdirection\tbounces\n";
		
		for(Ball ball : balls) {
			
			data += ball.toString() + "\n";
		}
		
		return data;
	}
	
	/**
	 * <b>Description:</b> This method allows loading the scores.<br>
	 * @param path - The path where the scores are.
	 * @throws IOException If an I/O error occurs.
	 * @throws InvalidPathException Thrown when an application tries to load in a class through its string name, but no definition for the class with the specified name could be found.

	 */
	
	public void loadScores(String path) throws IOException, ClassNotFoundException {
		
		File test = new File(path);
		
		if(test.exists()) {
			
			FileInputStream file = new FileInputStream(path);
			ObjectInputStream input = new ObjectInputStream(file);
			scores = (Score[][]) input.readObject();
			input.close();
		}
	}
	
	/**
	 * <b>Description:</b> This method allows serializing the scores.<br>
	 * @param path - The path where the scores will be saved.
	 * @throws FileNotFoundException If the named file does not exist, is a directory rather than a regular file, or for some other reason cannot be opened for reading.
	 * @throws IOException If an I/O error occurs.
	 * @throws InvalidPathException If the path does not exist or is not a file.
	 */
	
	public void saveScores(String path) throws FileNotFoundException, IOException, InvalidPathException {
		
		File file1 = new File(path);
		
		if(!file1.isDirectory()) {
			
			FileOutputStream file = new FileOutputStream(path);
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(scores);
			output.close();
		}
		else {
			
			throw new InvalidPathException();
		}
	}
	
//Getters
	
	/**
	 * <b>Description:</b> Gets the value of the attribute balls.<br>
	 * @return The attribute balls.
	 */
	
	public ArrayList<Ball> getBalls() {
		return balls;
	}
	
	/**
	 * <b>Description:</b> Gets the value of the attribute points.<br>
	 * @return The attribute points.
	 */
	
	public int getPoints() {
		return points;
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
