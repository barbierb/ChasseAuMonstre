package gestionpersonnage;

import util.Clavier;

public class Chasseur extends Personnage {

	public Chasseur(Position p) {
		super(p);
	}

	@Override
	public Direction getDirectionVoulue() {
		System.out.println("Printer les directions et leurs numéros de chasseur");
		char c=0;
		do {
			System.out.println("Dis moi où tu veux aller :");
			c = (char) Clavier.lireString().charAt(0);
		} while(c!='8' || c!='6' || c!='2' || c!='4');
		return Direction.byNumero(Character.getNumericValue(c));
	}


	@Override
	public void utiliseEtoile() {
		// TODO Auto-generated method stub

	}

}
