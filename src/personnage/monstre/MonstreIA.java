package personnage.monstre;

import java.util.Random;

import personnage.Direction;
import plateau.Position;
/**
 * Classe monstre pour son IA qui étend la classe Monstre et qui gère les déplacements calculés par l'IA
 * @author Sylvain
 */
public class MonstreIA extends Monstre {
	private static final long serialVersionUID = 42;

	public MonstreIA(Position p) {
		super(p);
	}
	
	@Override
	public Direction getDirectionVoulue() {
		System.out.println("DIR VOULUE CHASSEURIA");
		return Direction.values()[new Random().nextInt(Direction.values().length)];
	}
}
