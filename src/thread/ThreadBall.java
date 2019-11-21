package thread;

import controller.ControllerGame;
import model.Ball;

/**
* <b>Description:</b> The class ThreaBall in the package thread.<br>
* @author Johan Giraldo.
*/

public class ThreadBall extends Thread {

//Attributes
	
	private Ball ball;
	private ControllerGame controller;
	
	/**
	 * <b>Description:</b> Creates a new instance of ThreadBall.<br>
	 * @param ball The ball that the thread moves.
	 * @param controller The controller that show the ball.
	 */
	
	public ThreadBall(Ball ball, ControllerGame controller) {
		
		this.ball = ball;
		this.controller = controller;
		setDaemon(true);
	}
	
	@Override
	public void run() {
		
		while(ball.isMoving()) {
			
			ball.move(controller.getWidth(), controller.getHeight());
			
			try {
				
				sleep(ball.getWaitTime());
			}
			catch(InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	
}
