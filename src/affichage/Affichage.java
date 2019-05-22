package affichage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Affichage extends Application {

	private static Affichage affichage;
	//TODO VINCENT & SYLVAIN: STOCKER ET CHARGER LES SCENES

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

		Affichage yo = Affichage.getInstance();

		Parent root = FXMLLoader.load(getClass().getResource("menu_princ.fxml"));
		primaryStage.setTitle("Chasse Au Monstre");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	protected void setSceneSolo(ActionEvent e,Stage stage) {

	}

}
