package personnage.chasseur;

import java.util.Random;

import personnage.Direction;
import plateau.Position;
/**
 * Classe chasseur pour son IA qui �tend la classe Chasseur et qui gère les déplacements calculés par l'IA
 * @author Sylvain
 */
public class ChasseurIA extends Chasseur {
	
	private static final long serialVersionUID = 42;

	public ChasseurIA(Position p) {
		super(p);
		System.out.println("CHASSEUR IA PTDR");
	}
	
	@Override
	public Direction getDirectionVoulue() {
		System.out.println("DIR VOULUE CHASSEURIA");
		return Direction.values()[new Random().nextInt(Direction.values().length)];
	}

}
