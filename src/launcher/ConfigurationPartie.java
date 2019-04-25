package launcher;

public class ConfigurationPartie {

	private boolean joueur1isChasseur;

	private boolean joueur2isIA;
	
	private int tailleX = 10;
	private int tailleY = 10;
	
	public ConfigurationPartie(boolean j1isChasseur, boolean j2isIA) {
		this.joueur1isChasseur = j1isChasseur;
		this.joueur2isIA = j2isIA;
	}

	public ConfigurationPartie() {
	}

	public boolean isJoueur1Chasseur() {
		return joueur1isChasseur;
	}

	public void setJoueur1isChasseur(boolean joueur1isChasseur) {
		this.joueur1isChasseur = joueur1isChasseur;
	}
	
	public boolean isJoueur2IA() {
		return joueur2isIA;
	}

	public void setJoueur2isIA(boolean joueur2isA) {
		this.joueur2isIA = joueur2isA;
	}

	public int getTailleX() {
		return tailleX;
	}

	public void setTailleX(int tailleX) {
		this.tailleX = tailleX;
	}

	public int getTailleY() {
		return tailleY;
	}

	public void setTailleY(int tailleY) {
		this.tailleY = tailleY;
	}
}
