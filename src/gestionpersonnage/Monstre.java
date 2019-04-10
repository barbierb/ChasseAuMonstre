package gestionpersonnage;

import util.Clavier;

public class Monstre extends Personnage {


	@Override
	public void utiliseEtoile() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setPosition(Position p) {
		// TODO Auto-generated method stub

	}


	@Override
	public Direction getDirectionVoulue() {
		System.out.println("Printer les directions et leurs numéros");
		char c=0;
		do {
			System.out.println("Dis moi où tu veux aller :");
			c = (char) Clavier.lireString().charAt(0);
		} while(c<='1' || c>='9' || c=='5');
		return Direction.byNumero(Character.getNumericValue(c));
	}

	public static void main(String[] args) {
		Monstre oui=new Monstre();
		System.out.println(oui.getDirectionVoulue().getLabel());
	}


}
