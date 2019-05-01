package launcher;

import entites.personnage.Type;
import util.Clavier;
/**
 * Classe qui lance le jeu
 * @author Sylvain
 */
public class Launcher {
	
	public static void main(String[] args) {
		System.out.println(Engine.RESET);
		char choix;
		do {
			System.out.println(Engine.CHOIX_JOUEUR1);
			choix = Clavier.lireString().charAt(0);
		} while(choix!='1' && choix!='2');
		
		ConfigurationPartie config = new ConfigurationPartie();
		if(choix=='1') {
			config.setJoueur1(Type.CHASSEUR);
		} else {
			config.setJoueur1(Type.MONSTRE);
		}

		System.out.println(Engine.RESET+"Vous jouerez le "+(choix=='1'?"Chasseur":"Monstre"));
		do {
			System.out.println(Engine.CHOIX_ADVERSAIRE);
			choix = Clavier.lireString().charAt(0);
		} while(choix!='1' && choix!='2');
		
		System.out.println(Engine.RESET+"Vous jouerez contre "+(choix=='1'?"une IA":"un autre joueur"));
		
		
		if(config.getJoueur1().equals(Type.MONSTRE)) {
			if(choix=='1') {
				config.setJoueur2(Type.CHASSEURIA);
			} else {
				config.setJoueur2(Type.CHASSEUR);
			}
		} else {
			if(choix=='1') {
				config.setJoueur2(Type.MONSTREIA);
			} else {
				config.setJoueur2(Type.MONSTRE);
			}
		}
		new Engine(config);
	}
}