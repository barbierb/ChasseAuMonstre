package reseau;

import plateau.Plateau;
/**
 * Classe qui gère le déroulement de la partie
 * @author Sylvain
 */
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
			
			plateau = serv.opposant.recevoirPlateau(); // plateau sans monstre

			serv.hote.envoyer(plateau); // plateau sans monstre

			plateau = serv.opposant.recevoirPlateau(); // plateau avec monstre et chass

			serv.hote.envoyer(plateau); // plateau avec monstre et chass
			
			
			while(plateau.getWinner() == null) {

				plateau = serv.opposant.recevoirPlateau();

				serv.hote.envoyer(plateau);

				plateau = serv.hote.recevoirPlateau();
				
				plateau.setTour(plateau.getTour()+1);
				
				serv.opposant.envoyer(plateau);
				
			}
			
		} else {
			serv.hote.envoyer(MessageReseau.ESTMONSTRE.toString());
			serv.opposant.envoyer(MessageReseau.ESTCHASSEUR.toString());

			plateau = serv.hote.recevoirPlateau(); // plateau sans monstre

			serv.opposant.envoyer(plateau); // plateau sans monstre

			plateau = serv.hote.recevoirPlateau(); // plateau avec monstre et chass

			serv.opposant.envoyer(plateau); // plateau avec monstre et chass
			
			while(plateau.getWinner() == null) {

				plateau = serv.hote.recevoirPlateau();

				serv.opposant.envoyer(plateau);

				plateau = serv.opposant.recevoirPlateau();
				
				plateau.setTour(plateau.getTour()+1);

				serv.hote.envoyer(plateau);
			}
		}
	}

	public static Engine getInstance() {
		return Engine.instance;
	}

}
