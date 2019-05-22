package testsPlateau;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testAffichagePlateau extends Application{
	 public void start(Stage stage) throws IOException {
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("../IHM/AffichagePlateau.fxml"));
         Parent root = loader.load();

         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Chasse au monstre");
         stage.show();
	 }
	
	 public static void main(String[] args) {
	         Application.launch(args);
	 }
}