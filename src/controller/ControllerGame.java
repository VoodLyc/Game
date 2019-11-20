package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
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
	 * a constant that represents the relative path where the CSS is.
	 */
	
	public static final String CSS_PATH = "/view/view.css";
	
	@FXML
	private MenuBar menuBar;
	@FXML
	private VBox box;
	@FXML
	private AnchorPane pane;
	
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
		return pane.getWidth();
	}
	
	public double getHeight() {
		return pane.getHeight();
	}
	
	public void start() {
		
		//Add menus and menu items to the menu bar.
		menuBar.getMenus().clear();
		Menu file = new Menu("File");
		MenuItem loadGame = new MenuItem("Load game");
		
		//Set the on action to the menuItem.
		loadGame.setOnAction(event -> loadGame());
		MenuItem saveGame = new MenuItem("Save game");
		menuBar.getMenus().addAll(file);
		file.getItems().addAll(loadGame, saveGame);
	}
	
	
	public void stopBall(MouseEvent mouse) {
		
		//Gets the mouse click position.
		double x = mouse.getX();
		double y = mouse.getY();
		
		game.stopBalls(x, y);
	}
	
	public void initGraphics() {
		
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		
		//Creates a canvas
		canvas = new Canvas(visualBounds.getWidth(), visualBounds.getHeight());
		
		//Adds on mouse clicked method to canvas
		canvas.setOnMouseClicked(event -> stopBall(event));
		gc = canvas.getGraphicsContext2D();
		
		//Adds the canvas in the AnchorPane
		pane.getChildren().add(canvas);
		
		//Runs the thread that draws the balls.
		new ThreadPrinter(this).start();
		initGame();
	}
	
	public boolean win() {	
		return game.win();
	}
	
	public void printBalls() {
		
		ArrayList<Ball> balls = game.getBalls();
		gc.clearRect(0, 0, pane.getWidth(), pane.getHeight());
		
		for(Ball ball : balls) {
			
			//Gets the ball attributes
			double x = (ball.getPosX() - ball.getRadius());
			double y = (ball.getPosY() - ball.getRadius());
			double wh = (ball.getRadius() * 2);
			
			//Draws the ball on the screen
			gc.setFill(Color.YELLOW);
			gc.fillOval(x, y, wh, wh);
		}
	}
	
	public void showWin() {
		
		//Creates an alert to show the score to the user.
		ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
		Alert alert = new Alert(AlertType.INFORMATION, "Score : " + game.getPoints(), ok);
		alert.setHeaderText(null);
		alert.setTitle("You win!");
		
		//Applies the CSS to the alert.
		setCss(alert);
		alert.showAndWait();
			
		if(game.checkFreeSlot()) {
			
			saveHighScore();
		}
		else if(game.isHighScore()) {
			
			saveHighScore();
		}
	}
	
	public void saveHighScore() {
		
		//Creates a text input dialog to receive the player name.
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("High Score!");
		dialog.setHeaderText(null);
		
		//Applies the CSS to the text input dialog.
		setCss(dialog);
		ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
		TextField text = dialog.getEditor();
		dialog.getDialogPane().getButtonTypes().clear();
		dialog.getDialogPane().getButtonTypes().add(ok);
		dialog.showAndWait();
		
		//Saves the score with the name given.
		game.addScore(askName(text,dialog));
		
		try {
			
			game.saveScores(Game.SAVES);
			
		} 
		catch(IOException | InvalidPathException e) {
			
			//Shows an alert if an I/O error occurs.
			Alert alert = new Alert(AlertType.ERROR, "Saving error", ok);
			alert.setHeaderText(null);
			alert.setTitle("Saving error");
			
			//Applies the CSS to the alert.
			setCss(alert);
			alert.show();
			e.printStackTrace();
		}
	}
	
	public String askName(TextField text, TextInputDialog dialog) {
		
		String name = text.getText();
		boolean running = true;
		
		while(running) {
			
			if(name.equals("")) {
				
				//Shows an alert if the name is empty.
				ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
				Alert alert = new Alert(AlertType.ERROR, "Please enter your name!", ok);
				alert.setHeaderText(null);
				alert.setTitle("Error!");
				
				//Applies the CSS to the alert.
				setCss(alert);
				alert.showAndWait();
				dialog.showAndWait();
				name = text.getText();
			}
			else if(name.length() < 5) {
				
				//Shows an alert if the name length is less than 5 characters.
				ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
				Alert alert = new Alert(AlertType.ERROR, "Name should be at least 5 characters long!", ok);
				alert.setHeaderText(null);
				alert.setTitle("Error!");
				
				//Applies the CSS to the alert.
				setCss(alert);
				alert.showAndWait();
				dialog.showAndWait();
				name = text.getText();
			}
			else {
				
				running = false;
			}
		}
		
		return name;
	}
	
	public <T extends Dialog<?>> void setCss(T dialog) {
		
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource(CSS_PATH).toExternalForm());
		dialogPane.getStyleClass().add("dialog");
	}
	
	public void initGame() {
		
		ArrayList<Ball> balls = game.getBalls();
		
		for(Ball ball : balls) {
			
			//Creates a thread for each ball.
			new ThreadBall(ball, this).start();
		}
	}
	
	public void loadGame() {
		
		//Creates a file chooser to choose the file to load.
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load game");
		
		//Add an extension filter to the file chooser.
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
		
		//Creates a default dir to load the game file.
		File initial = new File(Game.GAMES);
		fileChooser.setInitialDirectory(initial);
		File selectedFile = fileChooser.showOpenDialog(stage);
		
		try {
			
			//Creates a new game with the file selected.
			game = new Game(selectedFile.toString());
			game.load();
			pane.getChildren().clear();
			
			//Run the threads that show the balls on the screen
			initGraphics();
			initGame();
		}
		catch(NullPointerException e) {
			
			//Shows an alert if the user doesn't select a file.
			ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
			Alert alert = new Alert(AlertType.ERROR, "Please select a file!", ok);
			alert.setHeaderText(null);
			alert.setTitle("Loading error");
			
			//Applies the CSS to the alert.
			setCss(alert);
			alert.show();
			
		}
		catch(InvalidPathException | IOException e) {
			
			//Shows an alert if the user selects an invalid file (a file that doesn't have the expected format).
			ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
			Alert alert = new Alert(AlertType.ERROR, "Please select a valid game file!", ok);
			alert.setHeaderText(null);
			alert.setTitle("Loading error");
			
			//Applies the CSS to the alert.
			setCss(alert);
			alert.show();
		}
	}
}
