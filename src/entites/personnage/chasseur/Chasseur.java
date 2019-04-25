package entites.personnage.chasseur;

import entites.personnage.Direction;
import entites.personnage.Personnage;
import plateau.Case;
import plateau.Plateau;
import plateau.Position;
import util.Clavier;

public class Chasseur extends Personnage {

	public final static int MAXPASSAGE = 3;

	/**
	 * Constructeur de Chasseur 
	 * @param p = la position à laquelle le chasseur commence
	 */
	public Chasseur(Position p) {
		super(p);
		this.estMonstre=false;
	}

	/**
	 * Méthode qui demande au joueur dans quelle direction il veut aller
	 * @return La direction dans laquelle le chasseur joueur veut aller
	 */
	public Direction getDirectionVoulue() {
		System.out.println("Printer les directions et leurs numéros de chasseur");
		char c=0;
		do {
			System.out.println("Dis moi où tu veux aller :");
			c = (char) Clavier.lireString().charAt(0);
		} while(c!='8' || c!='6' || c!='2' || c!='4');
		return Direction.byNumero(Character.getNumericValue(c));
	}

	@Override
	protected boolean peutPasser(Position p) {
		Case[][] tab = Plateau.getInstance().getCases();
		return tab[p.getX()][p.getY()].getNbPassageChass()<Chasseur.MAXPASSAGE;
	}

	@Override
	protected void setPosition(Position p) {		
		this.pos=p;
		Plateau.getInstance().getCase(p).incrNbPassageChass();				
	}



}