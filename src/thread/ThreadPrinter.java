package thread;

import controller.ControllerGame;

/**
* <b>Description:</b> The class ThreaBall in the package thread.<br>
* @author Johan Giraldo.
*/

public class ThreadPrinter extends Thread {
	
//Attributes
	
	private ControllerGame controller;
	/**
	 * <b>Description:</b> Creates a new instance of ThreadBall.<br>
	 * @param ball The ball that the thread moves.
	 * @param controller The controller that show the ball.
	 */
	
	public ThreadPrinter(ControllerGame controller) {
		
		this.controller = controller;
		setDaemon(true);
	}
	
	@Override
	public void run() {
		
		while(!controller.win()) {
			
			controller.printBalls();
			try {
				sleep(2);
			}
			catch(InterruptedException e) {
				
			}
		}
	}

}
