package testsPlateau;

import entites.items.Etoile;
import entites.items.LongueVue;
import entites.personnage.chasseur.Chasseur;
import entites.personnage.monstre.Monstre;
import plateau.Plateau;
import plateau.Position;

/**
 * Test de l'affichage du plateau
 * @author Vincent
 *
 */
public class TestAfficherPlateau {
	public static void main(String[] args) {
		Plateau p = new Plateau(10, 10);
		Chasseur c = new Chasseur(new Position(1,0));
		Monstre m = new Monstre(new Position(0, 0));
		
		p.getCase(1, 0).ajouterItem(new LongueVue(new Position(1, 0)));
		p.getCase(1, 4).ajouterItem(new Etoile());
		p.getCase(0, 0).setTourPassage();
		p.getCase(4, 5).ajouterItem(new Etoile());
		p.getCase(4, 5).ajouterItem(new LongueVue(new Position(4, 5)));
		p.getCase(2, 9).ajouterItem(new Etoile());
		p.getCase(3, 3).ajouterItem(new LongueVue(new Position(3, 3)));
		p.getCase(3, 3).setTourPassage();
		
		System.out.println("Affichage plateau vide");
		p.afficherPlateau();
		
		System.out.println("Affichage chasseur");
		p.afficherPlateau(c);
		
		System.out.println("Affichage monstre");
		p.afficherPlateau(m);
	}
}
