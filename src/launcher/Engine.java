package launcher;

import java.io.IOException;

import plateau.Plateau;
import reseau.Connexion;
import reseau.MessageReseau;
import reseau.Serveur;

public class Engine extends Thread {
	private static Engine instance;
	private Connexion hote;
	private Connexion opposant;
	
	public Engine() {}
	
	public void run() {
		Serveur serv = Serveur.getInstance();
		try {
			serv.hote.envoyer(MessageReseau.ESTMONSTRE.toString());
			serv.opposant.envoyer(MessageReseau.ESTCHASSEUR.toString());
			
			Plateau base = (Plateau) serv.hote.recevoirPlateau();
			System.out.println("plateau recu");
		
		} catch (IOException e) {
			System.out.println("Engine: Exception IO, fin de la partie.");
			try {
				Serveur.getInstance().hote.getSocket().close();
				Serveur.getInstance().opposant.getSocket().close();
				System.exit(1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Engine getInstance() {
		return Engine.instance;
	}
	
}
