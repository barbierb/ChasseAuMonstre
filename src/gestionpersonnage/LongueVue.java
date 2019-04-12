package gestionpersonnage;

public class LongueVue extends Item{
	private final NB_MAX = 5;
	
	/**
	 * Constructeur de la classe LongueVue qui fait appel à sa superclass et la définit comme non récupérable;
	 */
	public LongueVue() {
		super(false);
	}
	
	/**
	 * Méthode toString informant que le monstre a été détecté par la longue-vue
	 */
	public String toString() {
		return "Monstre détecté";
	}
}