package launcher;

import plateau.Plateau;
import util.Clavier;

public class Launcher {

	private final static String CHOIX_JOUEUR1 = 
			  "\n        Chasse Au Monstre\n\n"
			+ "Quel personnage voulez-vous jouer ?\n\n"
			+ "     (1) Chasseur (2) Monstre\n";
	private final static String CHOIX_ADVERSAIRE =
			  "\nVoulez vous jouer contre une IA ?\n\n"
			+ "     (1) IA (2) Autre Joueur\n";
	
	public static void main(String[] args) {
		char choix;
		do {
			System.out.println(CHOIX_JOUEUR1);
			choix = Clavier.lireString().charAt(0);
		} while(choix!='1'&&choix!='2');
		
		ConfigurationPartie config = new ConfigurationPartie();
		config.setJoueur1isChasseur(choix=='1');
		
		do {
			System.out.println(CHOIX_ADVERSAIRE);
			choix = Clavier.lireString().charAt(0);
		} while(choix!='1'&&choix!='2');

		config.setJoueur2isIA(choix=='1');
		
		new Plateau(config).start();
	}
	
}
