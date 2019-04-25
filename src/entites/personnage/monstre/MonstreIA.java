package entites.personnage.monstre;

import java.util.Random;

import entites.personnage.Direction;
import plateau.Plateau;
import plateau.Position;

public class MonstreIA extends Monstre {

	public MonstreIA(Position p) {
		super(p);
		this.estMonstre=true;
	}

	@Override
	public Direction getDirectionVoulue() {

		Direction direc = null;
		Position nextPos;
		int tourPassage;
		while(direc == null){
			direc = Direction.values()[new Random().nextInt(Direction.values().length)];

			nextPos = new Position(this.pos.getX() + direc.getX(), this.pos.getY()+ direc.getY());
			
			tourPassage = Plateau.getInstance().getCase(nextPos).getTourPassage();

			if( !this.peutPasser(nextPos) || tourPassage != -1) {
				direc = null;
			}

		}
		return direc;
	}
}
