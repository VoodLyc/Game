package view;
	
import controller.ControllerGame;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

/**
* <b>Description:</b> The class Main in the package view.<br>
* @author Johan Giraldo.
*/

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Game.fxml"));
			VBox root = (VBox) loader.load();
			Scene scene = new Scene(root);
			primaryStage.setMinHeight(600);
			primaryStage.setMinWidth(800);
			scene.getStylesheets().add(getClass().getResource("view.css").toExternalForm());
			ControllerGame controller = loader.getController();
			controller.setStage(primaryStage);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
