package gestionpersonnage;

import java.util.ArrayList;
import java.util.List;

public class Case {
	private int tourPassage;
	private int nbPassageChass;
	private List<Item> dedans;
	/**
	 * Constructeur d'une case 
	 */
	public Case() {
		this.dedans = new ArrayList<Item>();
		this.nbPassageChass = 0;
		this.tourPassage = -1;
	}

	/**
	 * @return the tourPassage
	 */
	public int getTourPassage() {
		return tourPassage;
	}

	/**
	 * @return the nbPassageChass
	 */
	public int getNbPassageChass() {
		return nbPassageChass;
	}

	/**
	 * @return the dedans
	 */
	public List<Item> getDedans() {
		return dedans;
	}
	
	public void incrNbPassageChass() {
		nbPassageChass++;
	}
	
	public void setTourPassage() {
		tourPassage=Plateau.getInstance().tourActuel;
	}
}
