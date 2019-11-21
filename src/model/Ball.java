package model;

/**
* <b>Description:</b> The class Ball in the package model.<br>
* @author Johan Giraldo.
*/

public class Ball {
	
//Constants
	
	public enum Direction {
		
		/**
		 * a constant that represents an upward direction movement.
		 */
		
		UP(0, -1),
		
		/**
		 * a constant that represents downward direction movement.
		 */
		
		DOWN(0, 1),
		
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
		
		UPPER_RIGHT_DIAGONAL(1, -1),
		
		/**
		 * a constant that represents lower right diagonal direction movement.
		 */
		
		LOWER_RIGHT_DIAGONAL(1, 1),
		
		/**
		 * a constant that represents upper left diagonal direction movement.
		 */
		
		UPPER_LEFT_DIAGONAL(-1, -1),
		
		/**
		 * a constant that represents lower left diagonal direction movement.
		 */
		
		LOWER_LEFT_DIAGONAL(-1, 1);
		
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
		
		/**
		 * <b>Description:</b> Sets the value of the attribute x.<br>
		 * @param x - the increments on x. 
		 */
		
		public void setX(int x) {
			this.x = x;
		}
		
		/**
		 * <b>Description:</b> Sets the value of the attribute y.<br>
		 * @param x - the increments on y. 
		 */
		
		public void setY(int y) {
			this.y = y;
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
	private double r;
	private double g;
	private double b;
	
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
		r = Math.random();
		g = Math.random();
		b = Math.random();
	}
	
//Methods
	
	/**
	 * <b>Description:</b> This method allows moving ball.<br>
	 * <b>Post:</b> The ball's x and y position changes.<br>
	 */
	
	public void move() {
		
		posX += direction.getX();
		posY += direction.getY();
	}
	
	/**
	 * <b>Description:</b> This method allows moving the ball and detecting the collisions.<br>
	 * <b>Post:</b> The ball's x and y position changes and If the ball collision, it bounces and adds one bounce.<br>
	 * @param width - The width of the screen.
 	 * @param height - The height of the screen.
	 */
	
	public void move(double width, double height) {
		
		if(detectCollisionBorder(width, height)) {
			
			bounces++;
		}
		
		move();
	}

	/**
	 * <b>Description:</b> This method allows detecting a collision with a screen border and changing the ball direction.<br>
	 * @param width - The width of the screen.
 	 * @param height - The height of the screen.
	 * @return True if the ball collision with a screen border, false in otherwise.
	 */
	
	public boolean detectCollisionBorder(double width, double height) {
		
		boolean collision = false;
		
		double limitXLeft = posX - radius;
		double limitXRight = posX + radius;
		
		if(limitXLeft <= 0) {
			
			collision = true;
			direction.setX(Math.abs(direction.getX()));
		}
		else if(limitXRight >= width) {
			
			collision = true;
			direction.setX(Math.abs(direction.getX()) * -1);
		}
		
		double limitYUp = posY - radius;
		double limitYDown = posY + radius;
		
		if(limitYUp <= 0) {
			
			collision = true;
			direction.setY(Math.abs(direction.getY()));
		}
		else if(limitYDown >= height) {
			
			collision = true;
			direction.setY(Math.abs(direction.getY()) * -1);
		}
		
		return collision;
	}
	
	/**
	 * <b>Description:</b> This method allows stopping the ball if the point given is inside the ball.<br>
	 * @param x - The point x position.
	 * @param y - The point y position.
	 */
	
	public void stop(double x, double y) {
		
		double distance;
		
		distance = Math.sqrt(Math.pow((posX - x), 2) + Math.pow(posY - y, 2));
		
		if(distance <= radius) {
			
			moving = false;
		}
	}
	
	/**
	 * <b>Description:</b> This method convert the ball attributes in string.<br>
	 * @return A String with the ball attributes.
	 */
	
	@Override
	public String toString() {
		
		String toString = "";
		
		toString += Double.toString(radius) + "\t";
		toString += Double.toString(posX) + "\t";
		toString += Double.toString(posY) + "\t";
		toString += Integer.toString(waitTime) + "\t";
		toString += direction.name() + "\t";
		toString += Integer.toString(bounces) + "\t";
		toString += Boolean.toString(moving) + "\t";
		
		return toString;
	}
	
//Getters
	
	/**
	 * <b>Description:</b> Gets the value of the attribute radius.<br>
	 * @return The attribute radius.
	 */
	
	public double getRadius() {
		return radius;
	}

	/**
	 * <b>Description:</b> Gets the value of the attribute posX.<br>
	 * @return The attribute posX.
	 */
	
	public double getPosX() {
		return posX;
	}

	/**
	 * <b>Description:</b> Gets the value of the attribute posY.<br>
	 * @return The attribute posY.
	 */
	
	public double getPosY() {
		return posY;
	}
	
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
	
	/**
	 * <b>Description:</b> Gets the value of the attribute r.<br>
	 * @return The attribute r.
	 */
	
	public double getR() {
		return r;
	}
	
	/**
	 * <b>Description:</b> Gets the value of the attribute g.<br>
	 * @return The attribute g.
	 */
	
	public double getG() {
		return g;
	}
	
	/**
	 * <b>Description:</b> Gets the value of the attribute b.<br>
	 * @return The attribute b.
	 */
	
	public double getB() {
		return b;
	}
	
//Setters
	
	/**
	 * <b>Description:</b> Sets the value of the attribute moving.<br>
	 * @param moving - The ball status. 
	 */
	
	public void setMoving(boolean moving) {
		
		this.moving = moving;
	}
}
