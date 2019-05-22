package entites.items;

import launcher.Engine;
import plateau.Position;

/**
 * Une longue vue permet au chasseur de voir sur NB_MAX cases 
 * @author Sylvain
 *
 */
public class LongueVue implements Item {
	public static int NB_MAX;
	
	private Position pos;
	
	/**
	 * Constructeur de la classe LongueVue qui définit la variable recup�rable à false car une longue vue n'est pas récupérable
	 */
	public LongueVue(Position p) {
		LongueVue.NB_MAX = 5;
		this.pos = p;
	}
	
	/**
	 * Méthode toString informant que le monstre a été détecté par la longue-vue
	 */
	public String toString() {
		return "Monstre d�tect�";
	}
	
	/**
	 * Méthode qui décrémente le nombre de longue vues posées et s'enléve de la liste des items de sa case
	 */
	public void supprLongueVue() {
		Engine.getInstance().getPlateau().getCase(pos).getDedans().remove(this);
	}
}
