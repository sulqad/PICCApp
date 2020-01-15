package mrcards;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import java.net.URL;
import java.io.File;

import mrcards.MrCardsAppController;

public class MrCardsApp extends Application{
	
	public void start(Stage stage) throws Exception{

		FXMLLoader loader = new FXMLLoader();
		URL xmlUrl = getClass().getResource("fxmlMrcardsApp.fxml");
		loader.setLocation(xmlUrl);
		Parent root = loader.load();

		Scene scene =new Scene(root, 500, 600);
		stage.setTitle("Mr. Cards");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}