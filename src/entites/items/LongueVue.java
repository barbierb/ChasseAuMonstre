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
	 * Constructeur de la classe LongueVue qui d�finit la variable recup�rable � false car une longue vue n'est pas r�cup�rable
	 */
	public LongueVue(Position p) {
		this.recuperable=false;
		LongueVue.NB_MAX = 5;
		LongueVue.cptLongueVue++;
		this.pos = p;
	}
	
	/**
	 * M�thode toString informant que le monstre a �t� d�tect� par la longue-vue
	 */
	public String toString() {
		return "Monstre d�tect�";
	}
	
	/**
	 * Le nombre de longue vues pos�es
	 * @return un entier repr�sentant le nombre de longue vues pos�es
	 */
	public int getCptLongueVue() {
		return cptLongueVue;
	}
	
	/**
	 * M�thode qui d�cr�mente le nombre de longue vues pos�es et s'enl�ve de la liste des items de sa case
	 */
	public void supprLongueVue() {
		Engine.getInstance().getPlateau().getCase(pos).getDedans().remove(this);
		cptLongueVue--;
	}
}
