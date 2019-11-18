package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import model.Ball;
import model.Ball.Direction;

class BallTest {
	
	private Ball ball;
	
	public void setUpScenario1() {
		
		Direction direction = Direction.valueOf("RIGHT");
		ball = new Ball(50, 70, 90, 10, direction, 0, true);
	}
	
	@Test
	void testMove() {
		
		setUpScenario1();
		assertFalse(ball.detectCollisionBorder(121, 141));
		ball.move();
		assertTrue(ball.detectCollisionBorder(121, 141));
		setUpScenario1();
		ball.move(121, 141);
		assertFalse(ball.detectCollisionBorder(121, 141));
	}

}