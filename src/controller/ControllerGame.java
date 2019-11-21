package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
	private BorderPane border;
	private GraphicsContext gc;
	private Game game;
	private boolean restart;


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
		MenuItem saveGame = new MenuItem("Save game");
		Menu scores = new Menu("Scores");
		MenuItem highScores = new MenuItem("High scores");
		saveGame.setDisable(true);
		highScores.setDisable(true);
		
		//Set the on action to the menuItem.
		loadGame.setOnAction(event -> loadGame(saveGame, highScores));
		saveGame.setOnAction(event -> saveGame());
		highScores.setOnAction(event -> showHighScores(saveGame, highScores));
		menuBar.getMenus().addAll(file, scores);
		scores.getItems().addAll(highScores);
		file.getItems().addAll(loadGame, saveGame);
		restart = false;
	}
	
	public void stopBall(MouseEvent mouse) {
		
		//Gets the mouse click position.
		double x = mouse.getX();
		double y = mouse.getY();
		
		game.stopBalls(x, y);
	}
	
	public void showHighScores(MenuItem saveGame, MenuItem highScores) {
		
		saveGame.setDisable(true);
		highScores.setDisable(true);
		//Clears the VBox's children.
		box.getChildren().remove(pane);
		border = new BorderPane();
		Insets boxPadding = new Insets(50, 50, 50, 50);
		border.setPadding(boxPadding);
		
		//Creates a GridPane.
		GridPane grid = new GridPane();
		
		//Creates 9 columns in the GridPane.
		for(int i = 0; i < 8; i++) {
			
			ColumnConstraints column = new ColumnConstraints();
			column.setHgrow(Priority.ALWAYS);
			grid.getColumnConstraints().add(column);
		}
		
		//Creates 12 rows in the GridPane.
		for(int i = 0; i < 11; i++) {
			
			RowConstraints row = new RowConstraints();
			row.setVgrow(Priority.ALWAYS);
			grid.getRowConstraints().add(row);
		}
		
		showScoresOne(grid);
		showScoresTwo(grid);
		showScoresThree(grid);
		
		//Adds the labels in the gridPane.
		border.setCenter(grid);
		box.getChildren().add(border);
	}
	
	public void showScoresOne(GridPane grid) {
		
		//Creates the headers labels
		Label level = new Label("LEVEL" + " " + 1);
		Label rank = new Label("RANK");
		Label score = new Label("SCORE");
		Label name = new Label("NAME");
		
		//Adds the labels
		grid.add(level, 1, 0);
		grid.add(rank, 0, 1);
		grid.add(score, 1, 1);
		grid.add(name, 2, 1);
		
		//Add the rank labels
		for(int i = 1; i < 11; i++) {
			
			Label number = new Label("   " + i);
			grid.add(number, 0, i+1);
		}
		
		//Add the scores labels
		for(int i = 0; i < 10; i++) {
			
			int[] scores = game.showHighScoresPoints(0);
			Label points;
			
			if(scores[i] == -1) {
				
				points = new Label("   " + "-");
			}
			else {
				
				points = new Label("   " + scores[i]);
			}
			grid.add(points, 1, i+2);
		}
		
		//Add the names labels
		for(int i = 0; i < 10; i++) {
			
			String[] names = game.showHighScoresNames(0);
			Label scoreName = new Label(names[i]);
			grid.add(scoreName, 2, i+2);
		}
	}
	
	public void showScoresTwo(GridPane grid) {
		
		//Creates the headers labels
		Label level = new Label("LEVEL" + " " + 2);
		Label rank = new Label("RANK");
		Label score = new Label("SCORE");
		Label name = new Label("NAME");
		
		//Adds the labels
		grid.add(level, 4, 0);
		grid.add(rank, 3, 1);
		grid.add(score, 4, 1);
		grid.add(name, 5, 1);
		
		//Add the rank labels
		for(int i = 1; i < 11; i++) {
			
			Label number = new Label("   " + i);
			grid.add(number, 3, i+1);
		}
		
		//Add the scores labels
		for(int i = 0; i < 10; i++) {
			
			int[] scores = game.showHighScoresPoints(1);
			Label points;
			
			if(scores[i] == -1) {
				
				points = new Label("   " + "-");
			}
			else {
				
				points = new Label("   " + scores[i]);
			}
			
			grid.add(points, 4, i+2);
		}
		
		//Add the names labels
		for(int i = 0; i < 10; i++) {
			
			String[] names = game.showHighScoresNames(1);
			Label points = new Label(names[i]);
			grid.add(points, 5, i+2);
		}
	}
	
	public void showScoresThree(GridPane grid) {
		
		//Creates the headers labels
		Label level = new Label("LEVEL" + " " + 3);
		Label rank = new Label("RANK");
		Label score = new Label("SCORE");
		Label name = new Label("NAME");
		
		//Adds the labels
		grid.add(level, 7, 0);
		grid.add(rank, 6, 1);
		grid.add(score, 7, 1);
		grid.add(name, 8, 1);
		
		//Add the rank labels
		for(int i = 1; i < 11; i++) {
			
			Label number = new Label("   " + i);
			grid.add(number, 6, i+1);
		}
		
		//Add the scores labels
		for(int i = 0; i < 10; i++) {
			
			int[] scores = game.showHighScoresPoints(1);
			Label points;
			
			if(scores[i] == -1) {
				
				points = new Label("   " + "-");
			}
			else {
				
				points = new Label("   " + scores[i]);
			}
			
			grid.add(points, 7, i+2);
		}
		
		//Add the names labels
		for(int i = 0; i < 10; i++) {
			
			String[] names = game.showHighScoresNames(1);
			Label points = new Label(names[i]);
			grid.add(points, 8, i+2);
		}
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
		
		if(!restart) {
			
			//Creates an alert to show the score to the user.
			ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
			Alert alert = new Alert(AlertType.NONE, "Score: " + game.getPoints(), ok);
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
	}
	
	public void saveHighScore() {
		
		//Creates a text input dialog to receive the player name.
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText(null);
		dialog.setContentText("Please enter your name!");
		dialog.setGraphic(null);
		dialog.initStyle(StageStyle.UNDECORATED);

		//Applies the CSS to the text input dialog.
		setCss(dialog);
		ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		TextField text = dialog.getEditor();
		dialog.getDialogPane().getButtonTypes().clear();
		dialog.getDialogPane().getButtonTypes().add(ok);
		dialog.showAndWait();
		
		//Saves the score with the name given.
		game.addScore(askName(text,dialog));
		
		try {
			
			game.saveScores(Game.SCORES);
			
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
			
			if(name.isEmpty()) {
				
				//Shows an alert if the name is empty.
				ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
				Alert alert = new Alert(AlertType.ERROR, "Please enter your name!", ok);
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
	
	public void saveGame() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Game Files", "*.game"));
		File initial = new File(Game.GAMES);
		fileChooser.setInitialDirectory(initial);
	    File selectedFile = fileChooser.showSaveDialog(stage);
	    try {
	    	
	    	game.saveGame(selectedFile.getPath());
	    }
	    catch(NullPointerException e) {
			
	    	//Shows an alert if the user doesn't select a file.
			ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
			Alert alert = new Alert(AlertType.ERROR, "Please create a file!", ok);
			alert.setHeaderText(null);
			alert.setTitle("Saving error");
			
			//Applies the CSS to the alert.
			setCss(alert);
			alert.show();
	    }
	    catch(IOException e) {
	    	
			//Shows an alert if the user doesn't select a file.
			ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
			Alert alert = new Alert(AlertType.ERROR, "Saving error!", ok);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			
			//Applies the CSS to the alert.
			setCss(alert);
			alert.show();
	    }
	}
	
	public void loadGame(MenuItem saveGame, MenuItem highScores) {
		
		
		//Ends the game if exists.
		if(game != null) {
			restart = true;
			game.endGame();
		}
		
		//Creates a file chooser to choose the file to load.
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load game");
		
		//Add an extension filter to the file chooser.
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Game Files", "*.game"));
		
		//Creates a default dir to load the game file.
		File initial = new File(Game.GAMES);
		fileChooser.setInitialDirectory(initial);
		File selectedFile = fileChooser.showOpenDialog(stage);
		
		try {
			
			//Creates a new game with the file selected.
			game = new Game(selectedFile.getPath());
			game.load();
			game.loadScores(Game.SCORES);
			pane.getChildren().clear();
			restart = false;
			saveGame.setDisable(false);
			highScores.setDisable(false);
			
			//Removes the borderPane if exists
			if(box.getChildren().contains(border))
				box.getChildren().remove(border);
			
			//Adds the AnchorPane if doesn't have it.
			if(!box.getChildren().contains(pane))
				box.getChildren().add(pane);
			
			//Run the threads that show the balls on the screen.
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
		catch(InvalidPathException | IOException | ClassNotFoundException e) {
			
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