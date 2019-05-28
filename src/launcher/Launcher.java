package launcher;

import javafx.application.Application;

public class Launcher {

	public static void main(String[] args) {
		
		Application.launch(args);
		/*
		Affichage.getInstance();
		boolean isHost = true;
		if(isHost) {
			Serveur.demarrerServeur("Jeuj", System.getProperty("user.name"));
		} else {
			Client.pingServeurs();
			Client.connecter("127.0.0.1", Serveur.PORT_JEU);
		}*/

	}
}
