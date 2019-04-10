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
	public abstract void utiliseEtoile();
	public Position getPosition() {
		return pos;
	}
	public List<Item> getSac() {
		return sac;
	}
	public void setPosition(Position p) {
		this.pos=p;
	}
}