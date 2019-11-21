package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.Game;
import model.InvalidPathException;
import model.Score;

class GameTest {
	
	private Game game;
	
	
	private void setUpScenario1() throws InvalidPathException {
		
		game = new Game("data/test/ffesfefs");
	}
	
	private void setUpScenario2() throws InvalidPathException {
		
		game = new Game("data/test/invalidGameTest.game");
	}
	
	private void setUpScenario3() {
		
		try {
			game = new Game("data/test/game.game");
			game.load();
		}
		catch(InvalidPathException | IOException e) {
			
			e.printStackTrace();
		}
	}
	
	@Test
	void testGame() {
		
		assertThrows(InvalidPathException.class, () -> { setUpScenario1();});
		assertThrows(InvalidPathException.class, () -> { setUpScenario2(); game.load();});
	}
	
	@Test
	void testSortScores() {
		
		Score[][] scores = new Score[3][10];
		 
		scores[0][0] = new Score("SAC", 30);
		scores[0][1] = new Score("scec", 80);
		scores[0][2] = new Score("scec", 3);
		
		setUpScenario3();
		
		game.addScore("test");
		game.setPoints(3);
		game.addScore("test");
		game.setPoints(90);
		game.addScore("test");
		game.setPoints(34);
		game.addScore("test");
		game.setPoints(24);
		game.addScore("test");
		game.setPoints(24);
		game.addScore("test");
		game.setPoints(93);
		game.addScore("test");
		game.setPoints(340);
		game.addScore("test");
		game.setPoints(103);
		game.addScore("test");
		game.setPoints(109);
		game.addScore("test");
		scores = game.getScores();
		
		assertEquals(scores[0][0].getPoints(), 0);
		assertEquals(scores[0][1].getPoints(), 3);
		assertEquals(scores[0][2].getPoints(), 24);
		assertEquals(scores[0][3].getPoints(), 24);
		assertEquals(scores[0][4].getPoints(), 34);
		assertEquals(scores[0][5].getPoints(), 90);
		assertEquals(scores[0][6].getPoints(), 93);
		assertEquals(scores[0][7].getPoints(), 103);
		assertEquals(scores[0][8].getPoints(), 109);
		assertEquals(scores[0][9].getPoints(), 340);
		
		game.setPoints(4);
		game.addScore("test");
		scores = game.getScores();
		
		assertEquals(scores[0][0].getPoints(), 0);
		assertEquals(scores[0][1].getPoints(), 3);
		assertEquals(scores[0][2].getPoints(), 4);
		assertEquals(scores[0][3].getPoints(), 24);
		assertEquals(scores[0][4].getPoints(), 24);
		assertEquals(scores[0][5].getPoints(), 34);
		assertEquals(scores[0][6].getPoints(), 90);
		assertEquals(scores[0][7].getPoints(), 93);
		assertEquals(scores[0][8].getPoints(), 103);
		assertEquals(scores[0][9].getPoints(), 109);
		
		game.setPoints(104);
		game.addScore("test");
		scores = game.getScores();
		
		assertEquals(scores[0][0].getPoints(), 0);
		assertEquals(scores[0][1].getPoints(), 3);
		assertEquals(scores[0][2].getPoints(), 4);
		assertEquals(scores[0][3].getPoints(), 24);
		assertEquals(scores[0][4].getPoints(), 24);
		assertEquals(scores[0][5].getPoints(), 34);
		assertEquals(scores[0][6].getPoints(), 90);
		assertEquals(scores[0][7].getPoints(), 93);
		assertEquals(scores[0][8].getPoints(), 103);
		assertEquals(scores[0][9].getPoints(), 104);
		
		game.setPoints(33);
		game.addScore("test");
		scores = game.getScores();
		
		assertEquals(scores[0][0].getPoints(), 0);
		assertEquals(scores[0][1].getPoints(), 3);
		assertEquals(scores[0][2].getPoints(), 4);
		assertEquals(scores[0][3].getPoints(), 24);
		assertEquals(scores[0][4].getPoints(), 24);
		assertEquals(scores[0][5].getPoints(), 33);
		assertEquals(scores[0][6].getPoints(), 34);
		assertEquals(scores[0][7].getPoints(), 90);
		assertEquals(scores[0][8].getPoints(), 93);
		assertEquals(scores[0][9].getPoints(), 103);

	}

}
