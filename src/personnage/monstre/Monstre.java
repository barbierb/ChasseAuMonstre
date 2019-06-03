package personnage.monstre;

import java.io.Serializable;

import personnage.Direction;
import personnage.Personnage;
import plateau.Case;
import plateau.Position;
import reseau.Client;

/**
 * Joueur monstre qui étend la classe personnage
 * @author Sylvain
 *
 */
public class Monstre extends Personnage implements Serializable {
	private static final long serialVersionUID = 42;

	/**
	 * Constructeur de Monstre
	 * @param p = la position  laquelle le monstre commence
	 */
	public Monstre(Position p) {
		super(p);
		this.estMonstre=true;
	}
	
	@Override
	protected boolean peutPasser(Position p) {
		Case[][] tab = Client.getInstance().getPlateau().getCases();
		return tab[p.getX()][p.getY()].getTourPassage()==-1;
	}

	@Override
	protected void setPosition(Position p) {
		this.pos=p;
		Client.getInstance().getPlateau().getCase(p).setTourPassage();
	}

	@Override
	public Direction getDirectionVoulue() {
		// TODO Auto-generated method stub
		return null;
	}


}
