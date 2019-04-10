package gestionpersonnage;

import util.Clavier;

public class Monstre extends Personnage {


	public Monstre(Position p) {
		super(p);
	}

	@Override
	public void utiliseEtoile() {
		if(aEtoile()) {
			//TODO
		}
	}


	@Override
	public Direction getDirectionVoulue() {
		System.out.println("Printer les directions et leurs numéros de monstre");
		char c=0;
		do {
			System.out.println("Dis moi où tu veux aller :");
			c = (char) Clavier.lireString().charAt(0);
		} while(c<='1' || c>='9' || c=='5');
		return Direction.byNumero(Character.getNumericValue(c));
	}

	public static void main(String[] args) {
		Monstre oui=new Monstre(new Position(0, 1));
		System.out.println(oui.getDirectionVoulue().getLabel());
	}


}
