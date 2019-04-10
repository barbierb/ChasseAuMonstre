package gestionpersonnage;

import java.util.ArrayList;
import java.util.List;


public abstract class Personnage {
	protected List<Item> sac;
	protected Position pos;
	
	public Personnage(Position p) {
		this.sac = new ArrayList<Item>();
		this.pos = p;
	}
	
	public boolean aEtoile() {
		for(Item i:sac) {
			if(i instanceof Etoile) return true;
		}
		return false;
	}
	
	public abstract Direction getDirectionVoulue();
	protected abstract void setPosition(Position p);
	public abstract void utiliseEtoile();
	public Position getPosition() {
		return pos;
	}

}