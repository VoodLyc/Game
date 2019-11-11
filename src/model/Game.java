package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
* <b>Description:</b> The class Game in the package model.<br>
* @author Johan Giraldo.
*/

public class Game {
	
//Attributes
	
	private String path;
	private int difficulty;
	private boolean win;
	
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
	}
	
//Setters
	
	/**
	 * <b>Description:</b> Sets the value of the attribute path.<br>
	 * @param name - The path of the file with the game configuration. 
	 */

	public void setPath(String path) {
		this.path = path;
	}
}
