package gestionpersonnage;

import java.util.ArrayList;
import java.util.List;


public abstract class Personnage {
	protected List<Item> sac;
	protected Position pos;
	protected boolean etoile;
	
	public abstract Direction getDirectionVoulue();

	public Personnage(Position p) {
		this.sac = new ArrayList<Item>();
		this.pos = p;
		this.etoile = false;
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
	protected void setPosition(Position p) {
		this.pos=p;
	}
}