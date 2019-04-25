package entites.personnages;

import java.util.Random;

import plateau.Position;

public class ChasseurIA extends Chasseur {

	public ChasseurIA(Position p) {
		super(p);
		this.estMonstre=false;
	}

	@Override
	public Direction getDirectionVoulue() {
		//Case[][] plateau = Plateau.getInstance().getCases();
		//TODO IA pour la nouvelle direction cc Cantin
		return Direction.values()[new Random().nextInt(Direction.values().length)];
	}

}