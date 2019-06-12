package reseau;

import plateau.Plateau;

public class Engine extends Thread {

	private static Engine instance;

	private Plateau plateau;

	// Une engine créée par serveur pour gérer le déroulement complet d'une partie
	public Engine() {}

	public void run() {
		Serveur serv = Serveur.getInstance();
		if(serv.solo && serv.nomServeur.equals("chasseurlocal")) {
			serv.hote.envoyer(MessageReseau.ESTCHASSEUR.toString());
			serv.opposant.envoyer(MessageReseau.ESTMONSTRE.toString());
			
			System.out.println("ENGINE 0");
			plateau = serv.opposant.recevoirPlateau(); // plateau sans monstre

			System.out.println("ENGINE 1");
			serv.hote.envoyer(plateau); // plateau sans monstre

			System.out.println("ENGINE 3");
			plateau = serv.opposant.recevoirPlateau(); // plateau avec monstre et chass

			System.out.println("ENGINE 4");
			serv.hote.envoyer(plateau); // plateau avec monstre et chass
			
			
			System.out.println("ENGINE DEMARRAGE DE LA PARTIE");
			while(true) {
				System.out.println("ENGINE TOUR "+plateau.getTour());

				System.out.println("ENGINE EN ATTENTE DU PLATEAU DU MONSTRE");
				plateau = serv.opposant.recevoirPlateau();
				System.out.println("ENGINE PLATEAU MONSTRE RECU");

				System.out.println("ENGINE ENVOYE DU PLATEAU AU CHASSEUR");
				serv.hote.envoyer(plateau);
				System.out.println("ENGINE PLATEAU ENVOYE AU CHASSEUR");

				System.out.println("ENGINE EN ATTENTE DU PLATEAU DU CHASSEUR");
				plateau = serv.hote.recevoirPlateau();
				System.out.println("ENGINE PLATEAU CHASSEUR RECU");

				plateau.setTour(plateau.getTour()+1);
				
				System.out.println("ENGINE ENVOYE DU PLATEAU AU MONSTRE");
				serv.opposant.envoyer(plateau);
				System.out.println("ENGINE PLATEAU ENVOYE AU MONSTRE");

				if(plateau.getWinner() != null) {
					break;
				}
				
			}
			
			
			
		} else {
			serv.hote.envoyer(MessageReseau.ESTMONSTRE.toString());
			serv.opposant.envoyer(MessageReseau.ESTCHASSEUR.toString());

			System.out.println("ENGINE 0");
			plateau = serv.hote.recevoirPlateau(); // plateau sans monstre

			System.out.println("ENGINE 1");
			serv.opposant.envoyer(plateau); // plateau sans monstre

			System.out.println("ENGINE 3");
			plateau = serv.hote.recevoirPlateau(); // plateau avec monstre et chass

			System.out.println("ENGINE 4");
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
	}

	public static Engine getInstance() {
		return Engine.instance;
	}

}
