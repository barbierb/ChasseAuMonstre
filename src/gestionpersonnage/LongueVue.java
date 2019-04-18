package gestionpersonnage;

public class LongueVue extends Item{
	public final int NB_MAX = 5;
	
	/**
	 * Constructeur de la classe LongueVue qui définit la variable recupérable à false car une longue vue n'est pas récupérable
	 */
	public LongueVue() {
		this.recuperable=false;
	}
	
	/**
	 * Méthode toString informant que le monstre a été détecté par la longue-vue
	 */
	public String toString() {
		return "Monstre détecté";
	}
}
