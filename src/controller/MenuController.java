package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Game;
import model.InvalidPathException;

public class MenuController implements Initializable {
	
	/**
	 * a constant that represents the relative path where the scores will be saved.
	 */
	public static final String SCORES = "data/scores/scores.data";
	
	@FXML
	private MenuBar menuBar;
	@FXML
	private VBox box;
	
	private Game game;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		start();
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
	
	public void loadGame() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load game");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
		File initial = new File("data/games");
		fileChooser.setInitialDirectory(initial);
		File selectedFile = fileChooser.showOpenDialog(box.getScene().getWindow());
		try {
			game = new Game(selectedFile.toString());
			game.load();
		}
		catch(NullPointerException | InvalidPathException | IOException e) {
			
			ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
			Alert alert = new Alert(AlertType.WARNING, "Loading error!", ok);
			alert.setHeaderText(null);
			alert.setTitle(null);
			alert.showAndWait();
		}
	}
	
}
