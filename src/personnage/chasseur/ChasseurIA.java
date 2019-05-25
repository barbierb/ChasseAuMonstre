package personnage.chasseur;

import personnage.Direction;
import plateau.Position;
/**
 * Classe chasseur pour son IA qui �tend la classe Chasseur et qui gère les déplacements calculés par l'IA
 * @author Sylvain
 */
public class ChasseurIA extends Chasseur {
	private static final long serialVersionUID = 42;
	private static Direction[] directions_chasseur = new Direction[4];
	private boolean monstre_detecte = false;
	private Position position_monstre = null;

	public static void main(String[] args) {
		int index_tab = 0;

		for (int i = 1; i < 10; i++) {
			if(!Direction.byNumero(i).estDiagonale())
				directions_chasseur[index_tab] = Direction.byNumero(i);
			index_tab++;
		}
	}


	public ChasseurIA(Position p) {
		super(p);
	}
/*
	@Override
	public Direction getDirectionVoulue() {
		if(monstre_detecte) {
			//longue vue active le truc wola
			return null;
		}else {
			return Direction.values()[new Random().nextInt(directions_chasseur.length-1)];
		}
	}
	*/
	public Direction getDirectionTraque(){
		
		boolean aDroite;
		boolean aGauche;
		return null;
	}

}
