package affichage;

import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 * Classe principale qui démarre l'application
 * @author Sylvain
 *
 */
public class Affichage extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	private static Affichage affichage;

	public static Stage stage;
	/**
	 * Variable qui définit si le jeu attend le placement d'une longueVue
	 */
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
		Font.loadFont(MenuPrincControl.class.getResourceAsStream("../data/NewsgeekSerif.TTF"), 15);
		primaryStage.setTitle("Chasse Au Monstre");
		primaryStage.setScene(new Scene(root,1280,720));
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> System.exit(0));
	}

	public static InputStream chargerImg(String chemin) {
		InputStream in = MenuPrincControl.class.getResourceAsStream(chemin);
		if(in == null) {
			in = MenuPrincControl.class.getResourceAsStream("/"+chemin);
		}
		return in;
	}

}
