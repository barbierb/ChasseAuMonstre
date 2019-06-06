package personnage;

import java.io.Serializable;

import plateau.Case;
import plateau.Position;
import reseau.Client;

/**
 * Classe abstraite qui définit les personnages chasseur et monstre
 * @author Sylvain
 *
 */
public abstract class Personnage  implements Serializable {
	private static final long serialVersionUID = 42;
	protected Position pos;
	protected boolean modeEtoile;
	protected boolean aEtoile;
	protected int etoileTimer;
	protected boolean estMonstre;
	protected int nbEtoiles;

	protected final int MAX_TIMER_ETOILE = 3;

	protected Direction nouvelleDirection;

	public void setDirection(Direction d) {
		nouvelleDirection = d;
	}


	/**
	 * Teste si le personnage a le droit de passer en fonction de quel personnage il est. <br>
	 * Un monstre ne peut repasser où il est déjà allé<br>
	 * Un chasseur ne peut repasser sur la même case que 3 fois
	 * @param p la prochaine position du personnage
	 * @return true si il peut passer, false sinon
	 */
	protected abstract boolean peutPasser(Position p);

	public Direction getDirectionVoulue() {
		return nouvelleDirection;
	}
	protected abstract void setPosition(Position p);
	/**
	 * Constructeur de personnage 
	 * @param p : la position de base du personnage
	 */
	public Personnage(Position p) {
		this.pos = p;
		this.modeEtoile = false;
		this.aEtoile = false;
		this.etoileTimer = 0;
	}
	/**
	 * Vérifie si le personnage a une étoile dans son sac
	 */
	public boolean aEtoile() {
		/*
		for(Item i:sac) {
			if(i instanceof Etoile) return true;
		}
		return false;
		 */
		return false;
	}
	/**
	 * Utilise une étoile si le personnage a une étoile dans son sac
	 */
	public void utiliseEtoile() {
		if(aEtoile()) {
			this.modeEtoile=true;
			this.etoileTimer = MAX_TIMER_ETOILE;
			/*
			for(Item i : this.sac) {
				if(i instanceof Etoile) {
					this.sac.remove(i);
					break;
				}
			}
			 */
		}
	}
	/**
	 * @return la position du personnage
	 */
	public Position getPosition() {
		return pos;
	}

	/**
	 * Deplace le personnage et boucle tant que le déplacement est invalide
	 */
	public boolean deplace() {
		Case[][] tab = Client.getInstance().getPlateau().getCases();

		Position posActuelle = this.getPosition();
		int x = posActuelle.getX();
		int y = posActuelle.getY();

		Direction next = getDirectionVoulue();
		if(next == null) {
			return false;
		}

		int nextX = x + next.getX();
		int nextY = y + next.getY();
		Position nextPos = new Position(nextX, nextY);

		//Si la position actuelle plus le mouvement voulu est dans les bornes du tableau
		if(nextX<tab.length && nextX>=0 && nextY<tab[0].length && nextY>=0) {

			if(estMonstre && modeEtoile) {
				setPosition(nextPos);
				etoileTimer--;
				if(etoileTimer == 0) modeEtoile=false;
				return true;

			} else if (peutPasser(nextPos)) {
				setPosition(nextPos);
				return true;
			}

		}
		return false;
	}
	public boolean isEtoile() {
		return modeEtoile;
	}

	public int getNbEtoiles() {
		return nbEtoiles;
	}

	public void ajouterEtoile() {
		this.nbEtoiles++;
	}
}
