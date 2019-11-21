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
	
	public void setUpScenario2() {
		
		Direction direction = Direction.valueOf("UP");
		ball = new Ball(50, 70, 51, 10, direction, 0, true);
	}
	
	public void setUpScenario3() {
		
		Direction direction = Direction.valueOf("UP");
		ball = new Ball(50, 51, 90, 10, direction, 0, true);
	}
	
	public void setUpScenario4() {
		
		Direction direction = Direction.valueOf("DOWN");
		ball = new Ball(50, 51, 90, 10, direction, 0, true);
	}
	
	public void setUpScenario5() {
		
		Direction direction = Direction.valueOf("DOWN");
		ball = new Ball(50, 430, 890, 10, direction, 0, true);
	}
	
	@Test
	void testMove() {
		
		setUpScenario1();
		assertEquals(70.0, ball.getPosX());
		assertEquals(90.0, ball.getPosY());
		assertFalse(ball.detectCollisionBorder(121, 141));
		ball.move();
		assertEquals(71.0, ball.getPosX());
		assertEquals(90.0, ball.getPosY());
		assertTrue(ball.detectCollisionBorder(121, 141));
		setUpScenario1();
		ball.move(121, 141);
		assertFalse(ball.detectCollisionBorder(121, 141));
		
		setUpScenario2();
		assertEquals(70.0, ball.getPosX());
		assertEquals(51.0, ball.getPosY());
		assertFalse(ball.detectCollisionBorder(121, 141));
		ball.move();
		assertEquals(70.0, ball.getPosX());
		assertEquals(50.0, ball.getPosY());
		assertTrue(ball.detectCollisionBorder(121, 141));
		
		setUpScenario3();
		assertEquals(51.0, ball.getPosX());
		assertEquals(90.0, ball.getPosY());
		assertFalse(ball.detectCollisionBorder(121, 141));
		ball.move();
		assertEquals(51.0, ball.getPosX());
		assertEquals(91.0, ball.getPosY());
		assertTrue(ball.detectCollisionBorder(121, 141));
		
		setUpScenario4();
		assertEquals(51.0, ball.getPosX());
		assertEquals(90.0, ball.getPosY());
		assertFalse(ball.detectCollisionBorder(121, 141));
		ball.move();
		assertEquals(51.0, ball.getPosX());
		assertEquals(91.0, ball.getPosY());
		assertTrue(ball.detectCollisionBorder(121, 141));
	}
	
	@Test
	void testStop() {
		
		setUpScenario1();
		assertTrue(ball.isMoving());
		ball.stop(120, 190);
		assertTrue(ball.isMoving());
		ball.stop(80, 96);
		assertFalse(ball.isMoving());
		
		setUpScenario2();
		assertTrue(ball.isMoving());
		ball.stop(390, 580);
		assertTrue(ball.isMoving());
		ball.stop(10, 40);
		assertTrue(ball.isMoving());
		ball.stop(60, 40);
		assertFalse(ball.isMoving());
		
		setUpScenario5();
		assertTrue(ball.isMoving());
		ball.stop(390, 580);
		assertTrue(ball.isMoving());
		ball.stop(10, 40);
		assertTrue(ball.isMoving());
		ball.stop(60, 40);
		assertTrue(ball.isMoving());
		ball.stop(430, 40);
		assertTrue(ball.isMoving());
		ball.stop(430, 980);
		assertTrue(ball.isMoving());
		ball.stop(430, 890);
		assertFalse(ball.isMoving());
	}
}