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
	protected int etoileTimer;
	protected boolean estMonstre;
	protected int nbEtoile;

	protected final int MAX_TIMER_ETOILE = 3;

	protected Direction nouvelleDirection;

	public void setDirection(Direction d) {
		this.nouvelleDirection = d;
	}
	public void setNbEtoiles(int i) {
		this.nbEtoile = i;
	}
	
	
	/**
	 * Teste si le personnage a le droit de passer en fonction de quel personnage il est. <br>
	 * Un monstre ne peut repasser où il est déjà allé<br>
	 * Un chasseur ne peut repasser sur la même case que 3 fois
	 * @param p la prochaine position du personnage
	 * @return true si il peut passer, false sinon
	 */
	protected abstract boolean peutPasser(Position p);
	public abstract Direction getDirectionVoulue();
	protected abstract void setPosition(Position p);
	/**
	 * Constructeur de personnage 
	 * @param p : la position de base du personnage
	 */
	public Personnage(Position p) {
		this.pos = p;
		this.modeEtoile = false;
		this.nbEtoile = 0;
		this.etoileTimer = 0;
	}
	/**
	 * Vérifie si le personnage a une étoile dans son sac
	 */
	public boolean aEtoile() {
		return nbEtoile>0;
	}
	/**
	 * Utilise une étoile si le personnage a une étoile dans son sac
	 */
	public void utiliseEtoile() {
		this.modeEtoile=true;
		this.etoileTimer = MAX_TIMER_ETOILE;
		nbEtoile--;
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
		Case[][] tab = Client.getInstance().plateau.getCases();
		

		Position posActuelle = this.getPosition();
		int x = posActuelle.getX();
		int y = posActuelle.getY();
		
		Direction next = getDirectionVoulue();
		if(next == null) {
			return false;
		}
		System.out.println("deplacement");
		
		int nextX = x + next.getX();
		int nextY = y + next.getY();
		Position nextPos = new Position(nextX, nextY);

		//Si la position actuelle plus le mouvement voulu est dans les bornes du tableau
		if(nextX<tab.length && nextX>=0 && nextY<tab[0].length && nextY>=0) {
			
			
			
			if(estMonstre && modeEtoile) {
				setPosition(nextPos);
				
				etoileTimer--;
				if(etoileTimer == 0) modeEtoile=false;
				
				if(Client.getInstance().getPlateau().getCase(nextPos).hasEtoile()) {
					nbEtoile++;
					Client.getInstance().getPlateau().getCase(nextPos).enleverEtoile();
				}
				
				return true;

			} else if (peutPasser(nextPos)) {
				setPosition(nextPos);

				if(Client.getInstance().getPlateau().getCase(nextPos).hasEtoile()) {
					nbEtoile++;
					Client.getInstance().getPlateau().getCase(nextPos).enleverEtoile();
				}
				return true;
			}

		}
		return false;
	}
	public boolean isEtoile() {
		return modeEtoile;
	}
	public void setModeEtoile(boolean b) {
		this.modeEtoile = b;
	}

	public int getNbEtoiles() {
		return nbEtoile;
	}

	public void ajouterEtoile() {
		this.nbEtoile++;
	}
}
