package gestionpersonnage;

import util.Clavier;

public class Chasseur extends Personnage {

	/**
	 * Constructeur de Chasseur 
	 * @param p = la position à laquelle le chasseur commence
	 */
	public Chasseur(Position p) {
		super(p);
	}

	/**
	 * Méthode qui demande au joueur dans quelle direction il veut aller
	 * @return La direction dans laquelle le chasseur joueur veut aller
	 */
	public Direction getDirectionVoulue() {
		System.out.println("Printer les directions et leurs numéros de chasseur");
		char c=0;
		do {
			System.out.println("Dis moi où tu veux aller :");
			c = (char) Clavier.lireString().charAt(0);
		} while(c!='8' || c!='6' || c!='2' || c!='4' && respectePlateau());
		return Direction.byNumero(Character.getNumericValue(c));
	}

	

}
