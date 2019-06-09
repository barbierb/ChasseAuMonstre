package personnage.chasseur;

import java.io.Serializable;

import affichage.Affichage;
import personnage.Direction;
import personnage.Personnage;
import plateau.Case;
import plateau.Position;
import reseau.Client;

/**
 * Joueur chasseur qui étend la classe personnage
 * @author Sylvain
 */
public class Chasseur extends Personnage implements Serializable {
	private static final long serialVersionUID = 42;

	public final static int MAXPASSAGE = 3;

	/**
	 * Constructeur de Chasseur 
	 * @param p = la position à laquelle le chasseur commence
	 */
	public Chasseur(Position p) {
		super(p);
		this.estMonstre=false;
	}

	@Override
	protected boolean peutPasser(Position p) {
		Case[][] tab = Client.getInstance().getPlateau().getCases();
		return tab[p.getX()][p.getY()].getNbPassageChass() < Chasseur.MAXPASSAGE;
	}

	@Override
	protected void setPosition(Position p) {		
		this.pos = p;
		System.out.println("Chasseur "+p);
		Client.getInstance().getPlateau().getCase(p).incrNbPassageChass();				
	}

	public void placerLongueVue() {
		Affichage.placerLongueVue=true;		
	}

	@Override
	public Direction getDirectionVoulue() {
		System.out.println("DIR VOULUE CHASSEUR");
		return nouvelleDirection;
	}

}