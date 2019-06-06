package launcher;

import plateau.Plateau;
import reseau.MessageReseau;
import reseau.Serveur;

public class Engine extends Thread {
	
	private static Engine instance;
	
	private Plateau plateau;
	
	// Une engine créée par serveur pour gérer le déroulement complet d'une partie
	public Engine() {}
	
	public void run() {
		Serveur serv = Serveur.getInstance();
		
		
		
		
		serv.hote.envoyer(MessageReseau.ESTMONSTRE.toString());
		serv.opposant.envoyer(MessageReseau.ESTCHASSEUR.toString());
		
		
		
		
		
		
		plateau = serv.hote.recevoirPlateau(); // plateau sans monstre
		
		
		serv.opposant.envoyer(plateau); // plateau sans monstre
		

		plateau = serv.hote.recevoirPlateau(); // plateau avec monstre et chass
		
		serv.opposant.envoyer(plateau); // plateau avec monstre et chass
		
		System.out.println("ENGINE DEMARRAGE DE LA PARTIE");
		while(true) {
			System.out.println("ENGINE TOUR "+plateau.getTour());
			
			System.out.println("ENGINE EN ATTENTE DU PLATEAU DU MONSTRE");
			plateau = serv.hote.recevoirPlateau();
			System.out.println("ENGINE PLATEAU MONSTRE RECU");
			
			System.out.println("ENGINE ENVOYE DU PLATEAU AU CHASSEUR");
			serv.opposant.envoyer(plateau);
			System.out.println("ENGINE PLATEAU ENVOYE AU CHASSEUR");
			
			System.out.println("ENGINE EN ATTENTE DU PLATEAU DU CHASSEUR");
			plateau = serv.opposant.recevoirPlateau();
			System.out.println("ENGINE PLATEAU CHASSEUR RECU");

			System.out.println("ENGINE ENVOYE DU PLATEAU AU MONSTRE");
			serv.hote.envoyer(plateau);
			System.out.println("ENGINE PLATEAU ENVOYE AU MONSTRE");
			
			if(plateau.getWinner() != null) {
				break;
			}
		}
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
