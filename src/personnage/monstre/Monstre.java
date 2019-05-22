package personnage.monstre;

import personnage.Direction;
import personnage.Personnage;
import launcher.Engine;
import plateau.Case;
import plateau.Position;
import util.Clavier;

/**
 * Joueur monstre qui étend la classe personnage
 * @author Sylvain
 *
 */
public class Monstre extends Personnage {

	/**
	 * Constructeur de Monstre
	 * @param p = la position  laquelle le monstre commence
	 */
	public Monstre(Position p) {
		super(p);
		this.estMonstre=true;
	}

	@Override
	public String toString() {
		return "pos=" + pos + ", getDirectionVoulue()=" + getDirectionVoulue()
		+ ", aEtoile()=" + aEtoile() + ", getPosition()=" + getPosition()
		+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
		+ "]";
	}

	/**
	 * Mthode qui demande au joueur dans quelle direction il veut aller
	 * @return La direction dans laquelle le chasseur joueur veut aller
	 */
	public Direction getDirectionVoulue() {
		char c=0;
		do {
			System.out.println("⭦ ⭡ ⭧");
			System.out.println("⭠   ⭢  Via le pavé numérique, entrez une direction pour vous déplacer.");
			System.out.println("⭩ ⭣ ⭨");
			String tmp = Clavier.lireString();
			if(tmp != null && tmp.length()>0)
				c = tmp.charAt(0);
		} while(c<='1' || c>='9' || c=='5');

		return Direction.byNumero(Character.getNumericValue(c));
	}
	
	@Override
	protected boolean peutPasser(Position p) {
		Case[][] tab = Engine.getInstance().getPlateau().getCases();
		return tab[p.getX()][p.getY()].getTourPassage()==-1;
	}

	@Override
	protected void setPosition(Position p) {
		this.pos=p;
		Engine.getInstance().getPlateau().getCase(p).setTourPassage();
	}


}
