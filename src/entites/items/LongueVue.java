package entites.items;

import launcher.Engine;
import plateau.Position;

/**
 * Une longue vue permet au chasseur de voir sur NB_MAX cases 
 * @author Sylvain
 *
 */
public class LongueVue extends Item {
	public static int NB_MAX;
	private static int cptLongueVue;
	
	private Position pos;
	
	/**
	 * Constructeur de la classe LongueVue qui définit la variable recupérable à false car une longue vue n'est pas récupérable
	 */
	public LongueVue(Position p) {
		this.recuperable=false;
		LongueVue.NB_MAX = 5;
		LongueVue.cptLongueVue++;
		this.pos = p;
	}
	
	/**
	 * Méthode toString informant que le monstre a été détecté par la longue-vue
	 */
	public String toString() {
		return "Monstre détecté";
	}
	
	/**
	 * Le nombre de longue vues posées
	 * @return un entier représentant le nombre de longue vues posées
	 */
	public int getCptLongueVue() {
		return cptLongueVue;
	}
	
	/**
	 * Méthode qui décrémente le nombre de longue vues posées et s'enlève de la liste des items de sa case
	 */
	public void supprLongueVue() {
		Engine.getInstance().getPlateau().getCase(pos).getDedans().remove(this);
		cptLongueVue--;
	}
}
