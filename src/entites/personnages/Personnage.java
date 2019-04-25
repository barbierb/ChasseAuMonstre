package entites.personnages;

import java.util.ArrayList;
import java.util.List;

import entites.items.Etoile;
import entites.items.Item;
import plateau.Case;
import plateau.Plateau;
import plateau.Position;

public abstract class Personnage {
	protected List<Item> sac;
	protected Position pos;
	protected boolean etoile;
	protected int etoileTimer;
	protected boolean estMonstre;

	protected final int MAX_TIMER_ETOILE = 3;

	
	/**
	 * Teste si le personnage a le droit de passer en fonction de quel personnage il est.
	 * Un monstre ne peut repasser où il est déjà allé
	 * Un chasseur ne peut 
	 * @param p la prochaine position du personnage
	 * @return true si il peut passer, false sinon
	 */
	protected abstract boolean peutPasser(Position p);
	public abstract Direction getDirectionVoulue();
	protected abstract void setPosition(Position p);
	
	public Personnage(Position p) {
		this.sac = new ArrayList<Item>();
		this.pos = p;
		this.etoile = false;
		this.etoileTimer = 0;
	}
	/**
	 * Vérifie si le personnage a une étoile dans son sac
	 */
	public boolean aEtoile() {
		for(Item i:sac) {
			if(i instanceof Etoile) return true;
		}
		return false;
	}
	/**
	 * Utilise une étoile si le personnage a une étoile dans son sac
	 */
	public void utiliseEtoile() {
		if(aEtoile()) {
			this.etoile=true;
			this.etoileTimer = MAX_TIMER_ETOILE;
		}
	}
	/**
	 * @return la position du personnage
	 */
	public Position getPosition() {
		return pos;
	}
	/**
	 * @return le sac du personnage
	 */
	public List<Item> getSac() {
		return sac;
	}

	public void deplace() {
		Case[][] tab = Plateau.getInstance().getCases();
		
		Position posActuelle = this.getPosition();
		int x = posActuelle.getX();
		int y = posActuelle.getY();

		boolean flag=true;

		while(flag) {
			Direction next = getDirectionVoulue();

			int nextX = x+next.getX();
			int nextY = y+next.getY();
			Position nextPos = new Position(nextX, nextY);
			
			//Si la position actuelle plus le mouvement voulu est dans les bornes du tableau
			if(nextX<tab.length && nextX>=0 && nextY<tab[0].length && nextY>=0) {
				
				if(estMonstre && etoile) {
					setPosition(nextPos);
					etoileTimer --;
					if(etoileTimer == 0) etoile=false;
					flag=false;
					
				} else if (peutPasser(nextPos)) {
					setPosition(nextPos);
					flag=false;
					
				} else  {
					System.out.println("Vous ne pouvez pas aller là");
				}
			}

		}
	}


}