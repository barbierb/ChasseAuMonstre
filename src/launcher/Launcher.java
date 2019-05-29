package launcher;

import affichage.Affichage;
import javafx.application.Application;
import reseau.Client;
import reseau.Serveur;

public class Launcher {

	public static void main(String[] args) {
		Affichage.getInstance();
		Application.launch(args);
		boolean isHost = true;
		if(isHost) {
			Serveur.demarrerServeur("Jeuj", System.getProperty("user.name"));
		} else {
			Client.pingServeurs();
			Client.connecter("127.0.0.1", Serveur.PORT_JEU);
		}

	}
}
