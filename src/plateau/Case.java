package plateau;

import java.util.ArrayList;
import java.util.List;

import entites.items.Item;
import launcher.Engine;

/**
 * La case permet de m�moriser le tour du passage du monstre, le nombre de fois o� le chasseur est pass� dessus ainsi que les item �ventuellement dessus
 * @author Sylvain
 */
public class Case {
	private int tourPassage;
	private int nbPassageChass;
	private List<Item> dedans;
	private static int cptCase = 0;
	public final int numCase;

	/**
	 * Constructeur d'une case 
	 */
	public Case() {
		this.dedans = new ArrayList<Item>();
		this.nbPassageChass = 0;
		this.tourPassage = -1;
		this.numCase = cptCase;
		cptCase++;

	}

	/**
	 * @return le tour de Passage du monstre
	 */
	public int getTourPassage() {
		return tourPassage;
	}

	/**
	 * @return le nombre de fois o� le chasseur est pass� 
	 */
	public int getNbPassageChass() {
		return nbPassageChass;
	}

	/**
	 * @return la liste d'Item sur la case
	 */
	public List<Item> getDedans() {
		return dedans;
	}
	/**
	 * Ajouter � la liste d'Item un nouvel Item
	 * @param l'item � ajouter
	 */
	public void ajouterItem(Item item) {
		this.dedans.add(item);
	}
	/**
	 * Quand le chasseur passe sur une case
	 */
	public void incrNbPassageChass() {
		nbPassageChass++;
	}
	/**
	 * Mets � jour le tour de passage du monstre sur la case
	 */
	public void setTourPassage() {
		tourPassage = Engine.getInstance().getTourActuel();
	}
}