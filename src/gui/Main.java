package gui;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) throws IOException {
        
        BorderPane testPane = FXMLLoader.load(getClass().getResource("/gui/TelaPrincipal.fxml"));
        
        Scene scene = new Scene(testPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tela Principal");
			
			primaryStage.show();

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
