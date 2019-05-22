package affichage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Affichage extends Application {

	private static Affichage affichage;
	//TODO VINCENT & SYLVAIN: STOCKER ET CHARGER LES SCENES
	
	protected static Stage stage;

	public Affichage() {
		//TODO VINCENT & SYLVAIN: CHARGER LE MENU PRINCIPAL 1
	}

	public static Affichage getInstance() {
		if(Affichage.affichage == null)
			Affichage.affichage = new Affichage();
		return Affichage.affichage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage=primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("menu_princ.fxml"));
		primaryStage.setTitle("Chasse Au Monstre");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	protected static Scene getSceneControls() {
		Pane labels = new Pane();
		labels.getChildren().add(new Label("Titre"));
		Scene controls = new Scene(labels);
		return controls;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	

}
