package gestionpersonnage;

import java.util.List;


public abstract class Personnage {
	protected List<Item> sac;
	protected Position pos;
	
	public abstract Direction getDirectionVoulue();
	protected abstract void setPosition(Position p);
	public abstract void utiliseEtoile();

}