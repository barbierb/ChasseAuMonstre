package gestionpersonnage;

public class Etoile extends Item{
	
	/**
	 * Constructeur de la classe Etoile qui fait appel à sa superclass et la définit comme récupérable
	 */
	public Etoile() {
		super(true);
	}
	
	/**
	 * Méthode toString informant que l'étoile a été récupérée par le joueur
	 */
	public String toString() {
		return "Etoile récupérée";
	}
}