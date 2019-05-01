package entites.personnage.chasseur;

import entites.personnage.Direction;
import entites.personnage.Personnage;
import launcher.Engine;
import plateau.Case;
import plateau.Position;
import util.Clavier;

/**
<<<<<<< HEAD
 * Joueur chasseur qui �tend la classe personnage
=======
 * Joueur chasseur qui �tend la classe personnage
>>>>>>> branch 'Benoit' of https://git-iut.univ-lille1.fr/ChasseAuMonstre/chasseaumonstre.git
 * @author Sylvain
 *
 */
public class Chasseur extends Personnage {

	public final static int MAXPASSAGE = 3;

	/**
	 * Constructeur de Chasseur 
	 * @param p = la position � laquelle le chasseur commence
	 */
	public Chasseur(Position p) {
		super(p);
		this.estMonstre=false;
	}

	/**
	 * M�thode qui demande au joueur dans quelle direction il veut aller
	 * @return La direction dans laquelle le chasseur joueur veut aller
	 */
	public Direction getDirectionVoulue() {
		char c=0;
		do {
			System.out.println("⭦ ⭡ ⭧");
			System.out.println("⭠   ⭢  Via le pavé tactile, entrez une direction pour vous déplacer.");
			System.out.println("⭩ ⭣ ⭨");
			c = (char) Clavier.lireString().charAt(0);
		} while(c!='8' && c!='6' && c!='2' && c!='4');
		return Direction.byNumero(Character.getNumericValue(c));
	}

	@Override
	protected boolean peutPasser(Position p) {
		Case[][] tab = Engine.getInstance().getPlateau().getCases();
		return tab[p.getX()][p.getY()].getNbPassageChass()<Chasseur.MAXPASSAGE;
	}

	@Override
	protected void setPosition(Position p) {		
		this.pos=p;
		Engine.getInstance().getPlateau().getCase(p).incrNbPassageChass();				
	}

}