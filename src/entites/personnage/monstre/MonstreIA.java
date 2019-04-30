package entites.personnage.monstre;

import java.util.Random;

import entites.personnage.Direction;
import launcher.Engine;
import plateau.Position;
/**
 * Classe monstre pour son IA qui �tend la classe Monstre et qui g�re les d�placements calcul�s par l'IA
 * @author Sylvain
 */
public class MonstreIA extends Monstre {

	public MonstreIA(Position p) {
		super(p);
	}
	/**
	 * M�thode qui demande a l'"IA" dans quelle direction elle veut aller
	 * @return La direction dans laquelle monstre veut aller
	 */
	@Override
	public Direction getDirectionVoulue() {

		Direction direc = null;
		Position nextPos;
		int tourPassage;
		while(direc == null){
			direc = Direction.values()[new Random().nextInt(Direction.values().length)];

			nextPos = new Position(this.pos.getX() + direc.getX(), this.pos.getY()+ direc.getY());
			
			tourPassage = Engine.getInstance().getPlateau().getCase(nextPos).getTourPassage();

			if( !this.peutPasser(nextPos) || tourPassage != -1) {
				direc = null;
			}

		}
		return direc;
	}
}
