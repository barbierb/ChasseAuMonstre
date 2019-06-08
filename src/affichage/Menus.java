package affichage;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import reseau.Serveur;

public class Menus {
	
	protected static Scene getSceneSolo() {
		Parent root = null;
		try {
			root = FXMLLoader.load(Affichage.class.getResource("menu_solo.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(root);
	}
	
	
	protected static Scene getSceneAide() {
		Parent root = null;
		try {
			root = FXMLLoader.load(Affichage.class.getResource("menu_aide.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(root);
	}
	
	protected static Scene getSceneMulti() {
		Parent root = null;
		try {
			root = FXMLLoader.load(Affichage.class.getResource("menu_multi.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(root);
	}
	

	public static Scene getSceneJeu() {
		Parent root = null;
		try {
			root = FXMLLoader.load(Affichage.class.getResource("AffichagePlateau.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(root);
	}
	
	static Scene getSceneHebergement(String nom) {
		Serveur.demarrerServeur(nom, System.getProperty("user.name"));
		Parent root = null;
		try {
			root = FXMLLoader.load(Affichage.class.getResource("menu_attente.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(root);
	}
}
