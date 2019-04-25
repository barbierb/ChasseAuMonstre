package gestionpersonnage;

import util.Clavier;

public class Monstre extends Personnage {

	/**
	 * Constructeur de Monstre
	 * @param p = la position à laquelle le monstre commence
	 */
	public Monstre(Position p) {
		super(p);
		this.estMonstre=true;
	}

	@Override
	public String toString() {
		return "Monstre [sac=" + sac + ", pos=" + pos + ", getDirectionVoulue()=" + getDirectionVoulue()
		+ ", aEtoile()=" + aEtoile() + ", getPosition()=" + getPosition() + ", getSac()=" + getSac()
		+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
		+ "]";
	}

	/**
	 * Méthode qui demande au joueur dans quelle direction il veut aller
	 * @return La direction dans laquelle le chasseur joueur veut aller
	 */
	public Direction getDirectionVoulue() {
		char c=0;
		do {
			System.out.println("Dis moi où tu veux aller :");
			c = (char) Clavier.lireString().charAt(0);
		} while(c<='1' || c>='9' || c=='5');

		return Direction.byNumero(Character.getNumericValue(c));
	}

	@Override
	protected boolean peutPasser(Position p) {
		Case[][] tab = Plateau.getInstance().getCases();
		return tab[p.getX()][p.getY()].getTourPassage()!=-1;
	}

	@Override
	protected void setPosition(Position p) {
		this.pos=p;
		Plateau.getInstance().getCase(p).setTourPassage();
	}


}
