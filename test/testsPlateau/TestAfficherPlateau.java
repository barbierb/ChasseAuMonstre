package testsPlateau;

import entites.items.Etoile;
import entites.items.LongueVue;
import entites.personnage.chasseur.Chasseur;
import entites.personnage.monstre.Monstre;
import launcher.ConfigurationPartie;
import plateau.Plateau;
import plateau.Position;

/**
 * Test de l'affichage du plateau
 * @author Vincent
 *
 */
public class TestAfficherPlateau {
	public static void main(String[] args) {
		Plateau p = new Plateau(new ConfigurationPartie(true, true));
		Chasseur c = new Chasseur(new Position(0,1));
		Monstre m = new Monstre(new Position(0, 0));
		
		p.getCase(1, 0).ajouterItem(new LongueVue());
		p.getCase(1, 4).ajouterItem(new Etoile());
		p.getCase(4, 5).setTourPassage();
		p.getCase(4, 5).ajouterItem(new Etoile());
		p.getCase(4, 5).ajouterItem(new LongueVue());
		
		System.out.println("Affichage chasseur");
		p.afficherPlateau(c);
		
		System.out.println("Affichage monstre");
		p.afficherPlateau(m);
	}
}