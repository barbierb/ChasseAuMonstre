package personnage.monstre;

import java.util.Random;
import personnage.Direction;
import plateau.Position;
import reseau.Client;
/**
 * Classe monstre pour son IA qui étend la classe Monstre et qui gère les déplacements calculés par l'IA
 * @author Cantin
 */ 
public class MonstreIA extends Monstre {
	private static final long serialVersionUID = 42;
	private int taillePlateau = Client.getInstance().getPlateau().getTaille();

	public MonstreIA(Position p) {
		super(p);
	}
	
	
	/**
	 * Méthode qui demande a l'"IA" dans quelle direction elle veut aller
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
			
			tourPassage = Client.getInstance().getPlateau().getCase(nextPos).getTourPassage();
			
			if(nextPos.getX() > taillePlateau -1 || nextPos.getX() < 0  || nextPos.getY() > taillePlateau -1 || nextPos.getY() < 0) {
				direc = null;
			}
			if( !this.peutPasser(nextPos) || tourPassage != -1) {
				direc = null;
			}

		}
		return direc;
	}
}
