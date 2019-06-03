package affichage;

import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Affichage extends Application {

	private static Affichage affichage;
	
	public static Stage stage;

	public static boolean placerLongueVue;

	public Affichage() {
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
		//stage.setResizable(false);
		primaryStage.setTitle("Chasse Au Monstre");
		primaryStage.setScene(new Scene(root,1280,720));
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

    public static InputStream chargerImg(String chemin) {
		InputStream in = MenuPrincControl.class.getResourceAsStream(chemin);
		if(in == null) {
			in = MenuPrincControl.class.getResourceAsStream("/"+chemin);
		}
		return in;
	}

}
