package launcher;

import plateau.Plateau;
import reseau.MessageReseau;
import reseau.Serveur;

public class Engine extends Thread {
	
	private static Engine instance;
	
	// Une engine créée par serveur pour gérer le déroulement complet d'une partie
	public Engine() {}
	
	public void run() {
		Serveur serv = Serveur.getInstance();
		serv.hote.envoyer(MessageReseau.ESTMONSTRE.toString());
		attendre(100);
		serv.opposant.envoyer(MessageReseau.ESTCHASSEUR.toString());
		System.out.println("ENGINE en attente du plateau généré par le monstre");
		Plateau base = (Plateau) serv.hote.recevoirPlateau();
		attendre(5000);
		System.out.println("ENGINE plateau recu, envoi à l'opposant chasseur");
		serv.opposant.envoyer(base);
		
		
	}
	
	private void attendre(long duree) {
		try {
			sleep(duree);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static Engine getInstance() {
		return Engine.instance;
	}
	
}
