package gestionpersonnage;

import java.util.Random;

public class MonstreIA extends Personnage {

	public MonstreIA(Position p) {
		super(p);
		this.estMonstre=true;
	}

	@Override
	public Direction getDirectionVoulue() {
		//Case[][] plateau = Plateau.getInstance().getCases();
		//TODO IA pour la nouvelle direction cc Cantin
		return Direction.values()[new Random().nextInt(Direction.values().length)];
	}


	@Override
	public void utiliseEtoile() {
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean peutPasser(Position p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void setPosition(Position p) {
		// TODO Auto-generated method stub
		
	}
	
	
}
