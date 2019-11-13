package model;

/**
* <b>Description:</b> The class Ball in the package model.<br>
* @author Johan Giraldo.
*/

public class Ball {
	
//Attributes
	
	private double radius;
	private double centerX;
	private double centerY;
	private int direction;
	private int bounces;
	private boolean bouncing;
	
//Constructor
	
	/**
	 * <b>Description:</b> Creates a new instance of Ball.<br>
	 * @param radius The ball radius.
	 * @param centerX The ball position on X.
	 * @param centerY The ball position on Y.
	 * @param direction The ball movement direction.
	 */
	
	public Ball(double radius, double centerX, double centerY, int direction) {
		
		this.radius = radius;
		this.centerX = centerX;
		this.centerY = centerY;
		this.direction = direction;
		bounces = 0;
		bouncing = true;
	}
	
//Getters
	
	/**
	 * <b>Description:</b> Gets the value of the attribute bounces.<br>
	 * @return The attribute bounces.
	 */
	
	public int getBounces() {
		return bounces;
	}
	
	/**
	 * <b>Description:</b> Gets the value of the attribute bouncing.<br>
	 * @return The attribute bouncing.
	 */

	public boolean isBouncing() {
		return bouncing;
	}
}
