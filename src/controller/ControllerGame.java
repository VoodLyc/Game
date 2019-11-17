package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Ball;
import model.Game;
import model.InvalidPathException;
import thread.ThreadBall;
import thread.ThreadPrinter;

/**
* <b>Description:</b> The class ControllerGame in the package controller.<br>
* @author Johan Giraldo.
*/

public class ControllerGame implements Initializable {
	
	/**
	 * a constant that represents the relative path where the games will be loaded.
	 */
	public static final String GAMES = "data/games";
	
	@FXML
	private MenuBar menuBar;
	@FXML
	private VBox box;
	
	private Stage stage;
	private Canvas canvas;
	private GraphicsContext gc;
	private Game game;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		start();
	}
	
	public void setStage(Stage stage) {
		
		this.stage = stage;
	}
	
	public double getWidth() {
		return box.getWidth();
	}
	
	public double getHeight() {
		return box.getHeight() - menuBar.getHeight();
	}
	
	public void start() {
		
		menuBar.getMenus().clear();
		Menu file = new Menu("File");
		MenuItem loadGame = new MenuItem("Load game");
		loadGame.setOnAction(event -> loadGame());
		MenuItem saveGame = new MenuItem("Save game");
		menuBar.getMenus().addAll(file);
		file.getItems().addAll(loadGame, saveGame);
	}
	
	public void initGraphics() {
		
		canvas = new Canvas(box.getWidth(), box.getHeight());
		gc = canvas.getGraphicsContext2D();
		box.getChildren().add(canvas);
		new ThreadPrinter(this).start();
		initGame();
	}
	
	public boolean win() {	
		return game.win();
	}
	
	public void printBalls() {
		
		ArrayList<Ball> balls = game.getBalls();
		gc.clearRect(0, 0, box.getWidth(), box.getHeight());
		
		for(Ball ball : balls) {
			
			gc.setFill(Color.YELLOW);
			gc.fillOval((ball.getPosX() - ball.getRadius()), (ball.getPosY() - ball.getRadius()), (ball.getRadius() * 2), (ball.getRadius() * 2));
		}
	}
	
	public void initGame() {
		
		ArrayList<Ball> balls = game.getBalls();
		
		for(Ball ball : balls) {
			
			new ThreadBall(ball, this).start();
		}
	}
	
	public void loadGame() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load game");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
		File initial = new File(GAMES);
		fileChooser.setInitialDirectory(initial);
		File selectedFile = fileChooser.showOpenDialog(box.getScene().getWindow());
		
		try {
			game = new Game(selectedFile.toString());
			game.load();
			initGraphics();
			initGame();
		}
		catch(NullPointerException e) {
			
			ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
			Alert alert = new Alert(AlertType.ERROR, "Please select a file!", ok);
			alert.setHeaderText(null);
			alert.setTitle("Loading error");
			alert.show();
			
		}
		catch(InvalidPathException | IOException e) {
			
			ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
			Alert alert = new Alert(AlertType.ERROR, "Please select a valid game file!", ok);
			alert.setHeaderText(null);
			alert.setTitle("Loading error");
			alert.show();
		}
	}
}
