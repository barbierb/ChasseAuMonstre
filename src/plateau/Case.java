package plateau;

import java.io.Serializable;

import reseau.Client;

/**
 * La case permet de mémoriser le tour du passage du monstre, le nombre de fois où le chasseur est passé dessus ainsi que les item éventuellement dessus
 * @author Sylvain
 */
public class Case implements ContientItem, Serializable {
	private static final long serialVersionUID = 42;
	
	private int tourPassage;
	private int nbPassageChass;
	
	/**
	 * 0 == dernier tour de vie de la LV
	 * x > 0 == nb de tour de vie de la LV
	 * -1 == pas de longue vue dans la case
	 */
	private int longueVue;
	private boolean etoile;
	
	/**
	 * Constructeur d'une case
	 */
	public Case() {
		this.nbPassageChass = 0;
		this.tourPassage = -1;
		this.longueVue = -1;
		this.etoile = false;
	}

	/**
	 * @return le tour de Passage du monstre
	 */
	public int getTourPassage() {
		return tourPassage;
	}

	/**
	 * @return le nombre de fois où le chasseur est passé 
	 */
	public int getNbPassageChass() {
		return nbPassageChass;
	}

	/**
	 * Quand le chasseur passe sur une case
	 */
	public void incrNbPassageChass() {
		nbPassageChass++;
	}
	/**
	 * Mets à jour le tour de passage du monstre sur la case
	 */
	public void setTourPassage() {
		tourPassage = Client.getInstance().getPlateau().getTour();
	}

	
	
	
	@Override
	public boolean hasEtoile() {
		return etoile;
	}

	@Override
	public int getLongueVue() {
		return longueVue;
	}

	@Override
	public void placerEtoile() {
		etoile=true;		
	}
	@Override
	public void placerLVTemp() {
		placerLV(3);
	}
	@Override
	public void placerLV() {
		placerLV(5);
	}
	public void placerLV(int temps) {
		longueVue=temps;
	}
	
	@Override
	public void enleverEtoile() {
		etoile=false;		
	}

	@Override
	public void decrLV() {
		longueVue--;
	}

	public boolean hasLV() {
        return longueVue!=-1;
	}
}