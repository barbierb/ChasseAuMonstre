package entites.personnage.chasseur;

import java.util.Random;

import entites.personnage.Direction;
import plateau.Position;
/**
 * Classe chasseur pour son IA qui �tend la classe Chasseur et qui g�re les d�placements calcul�s par l'IA
 * @author Sylvain
 */
public class ChasseurIA extends Chasseur {

	public ChasseurIA(Position p) {
		super(p);
	}

	@Override
	public Direction getDirectionVoulue() {
		//Case[][] plateau = Plateau.getInstance().getCases();
		//TODO IA pour la nouvelle direction cc Cantin
		return Direction.values()[new Random().nextInt(Direction.values().length)];
	}

}
