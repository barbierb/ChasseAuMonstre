package gestionpersonnage;

public class Etoile extends Item{
	
	/**
	 * Constructeur de la classe Etoile qui définit la variable recupérable à true car une étoile est récupérable
	 */
	public Etoile() {
		this.recuperable=true;
	}
	
	/**
	 * Méthode toString informant que l'étoile a été récupérée par le joueur
	 */
	public String toString() {
		return "Etoile récupérée";
	}
}