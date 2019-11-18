package model;

import java.io.Serializable;

/**
* <b>Description:</b> The class Score in the package model.<br>
* @author Johan Giraldo.
*/

public class Score implements Serializable, Comparable <Score> {
	
//Attributes
	
	private String name;
	private int points;
	private static final long serialVersionUID = 3484710925042948564L;
	
//Constructor
	
	/**
	 * <b>Description:</b> Creates a new instance of Score.<br>
	 * @param name - The player's name who got the points.
	 * @param points - The points that the player got in the game.
	 */
	
	public Score(String name, int points) {
		
		this.name = name;
		this.points = points;
	}
	
//Methods
	
	/**
	 *<b>Description:</b> This method allows comparing a score with other score by the points.<br>
	 *@param score The score with which it compares.
	 *@return 0 if the points are equals, 1  if the score's points is major than the score's points which it compares, -1 if the score's points is minor than the score's points which it compares.
	 */

	@Override
	public int compareTo(Score score) {
		
		int result = 0;
		
		if(points < score.getPoints()) {
			
			result = 1;
		}
		else if(points > score.getPoints()) {
			
			result = -1;
		}
		
		return result;
	}
	
//Getters
	
	/**
	 * <b>Description:</b> Gets the value of the attribute name.<br>
	 * @return The attribute name.
	 */
	
	public String getName() {
		return name;
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
	 * <b>Description:</b> Sets the value of the attribute name.<br>
	 * @param name - The name of the player who got the points. 
	 */
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * <b>Description:</b> Sets the value of the attribute points.<br>
	 * @param points - The points that the player got in the game. 
	 */

	public void setPoints(int points) {
		this.points = points;
	}
}
