package model;

/**
* <b>Description:</b> The class Ball in the package model.<br>
* @author Johan Giraldo.
*/

public class Ball {
	
//Constants
	
	enum Direction {
		
		/**
		 * a constant that represents an upward direction movement.
		 */
		
		UP(0, 1),
		
		/**
		 * a constant that represents downward direction movement.
		 */
		
		DOWN(0, -1),
		
		/**
		 * a constant that represents rightward direction movement.
		 */
		
		RIGHT(1, 0),
		
		/**
		 * a constant that represents leftward direction movement.
		 */
		
		LEFT(-1, 0),
		
		/**
		 * a constant that represents upper right diagonal direction movement.
		 */
		
		UPPER_RIGHT_DIAGONAL(1, 1),
		
		/**
		 * a constant that represents lower right diagonal direction movement.
		 */
		
		LOWER_RIGHT_DIAGONAL(1, -1),
		
		/**
		 * a constant that represents upper left diagonal direction movement.
		 */
		
		UPPER_LEFT_DIAGONAL(-1, 1),
		
		/**
		 * a constant that represents lower left diagonal direction movement.
		 */
		
		LOWER_LEFT_DIAGONAL(-1, -1);
		
		private int x;
		private int y;
		
		/**
		 * <b>Description:</b> Creates a new instance of Direction.<br>
		 * @param x The increment in x on the ball position.
		 * @param y The increment in y on the ball position.
		 * @param opposites Represents the opposites directions.
		 */
		
		private Direction(int x, int y) {
			
			this.x = x;
			this.y = y;
		}
		
		/**
		 * <b>Description:</b> Gets the value of the attribute x.<br>
		 * @return The attribute x.
		 */
		
		public int getX() {
			return x;
		}
		
		/**
		 * <b>Description:</b> Gets the value of the attribute y.<br>
		 * @return The attribute y.
		 */
		
		public int getY() {
			return y;
		}
	}
	
//Attributes
	
	private double radius;
	private double posX;
	private double posY;
	private int waitTime;
	private Direction direction;
	private int bounces;
	private boolean moving;
	
//Constructor
	
	/**
	 * <b>Description:</b> Creates a new instance of Ball.<br>
	 * @param radius The ball radius.
	 * @param posX The ball position on X.
	 * @param posY The ball position on Y.
	 * @param direction The ball movement direction.
	 * @param bounces The ball number of bounces.
	 * @param moving The ball status.
	 */
	
	public Ball(double radius, double posX, double posY, int waitTime, Direction direction, int bounces, boolean moving) {
		
		this.radius = radius;
		this.posX = posX;
		this.posY = posY;
		this.waitTime = waitTime;
		this.direction = direction;
		this.bounces = bounces;
		this.moving = moving;
	}
	
//Methods
	
	/**
	 * <b>Description:</b> This method allows moving ball.<br>
	 * <b>Post:</b> The ball's x and y position changes.<br>
	 */
	
	private void move() {
		
		posX += direction.getX();
		posY += direction.getY();
	}
	
	/**
	 * <b>Description:</b> This method allows moving the ball and detecting the collisions.<br>
	 * <b>Post:</b> The ball's x and y position changes and If the ball collision, it bounces.<br>
	 * @param width - The width of the screen.
 	 * @param height - The height of the screen.
	 */
	
	public void move(int width, int height) {
		
		if(detectCollisionBorder(width, height)) {
			
			invertDirection();
		}
		
		move();
	}
	
	/**
	 * <b>Description:</b> This method allows detecting a collision with a screen border.<br> 
	 * @param width - The width of the screen.
 	 * @param height - The height of the screen.
	 * @return True if the ball collision with a screen border, false in otherwise.
	 */
	
	public boolean detectCollisionBorder(int width, int height) {
		
		boolean collision = false;
		
		if((posX + direction.getX()) + radius > width) {
			
			collision = true;
		}
		else if((posX + direction.getX()) + radius < 0) {
			
			collision = true;
		}
		else if((posY + direction.getY()) + radius > height) {
			
			collision = true;
		}
		else if((posY + direction.getY()) + radius < 0) {
			
			collision = true;
		}
		
		return collision;
	}
	
	/**
	 * <b>Description:</b> This method allows inverting the ball direction.<br>
	 * <b>Post:</b> The ball direction was inverted.<br>
	 */
	
	public void invertDirection() {
		
		switch(direction) {
		
		case UP:
			
			direction = Direction.DOWN;
			
			break;
			
		case DOWN:
			
			direction = Direction.UP;
			
			break;
			
		case RIGHT:
			
			direction = Direction.LEFT;
			
			break;
			
		case LEFT:
			
			direction = Direction.RIGHT;
			
			break;
			
		case UPPER_RIGHT_DIAGONAL:
			
			direction = Direction.LOWER_RIGHT_DIAGONAL;
			
			break;
			
		case LOWER_RIGHT_DIAGONAL:
			
			direction = Direction.UPPER_RIGHT_DIAGONAL;
			
			break;
			
		case UPPER_LEFT_DIAGONAL:
			
			direction = Direction.LOWER_LEFT_DIAGONAL;
			
			break;
			
		case LOWER_LEFT_DIAGONAL:
			
			direction = Direction.UPPER_LEFT_DIAGONAL;
			
			break;
		}
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
	 * <b>Description:</b> Gets the value of the attribute moving.<br>
	 * @return The attribute moving.
	 */

	public boolean isMoving() {
		return moving;
	}
	
	/**
	 * <b>Description:</b> Gets the value of the attribute waitTime.<br>
	 * @return The attribute waitTime.
	 */
	
	public int getWaitTime() {
		return waitTime;
	}
}
