package gestionpersonnage;

public class Plateau {
	
	public int tailleX, tailleY;
	public Case[][] cases;
	
	// tab longue vue 

	public Personnage chasseur;
	public Personnage monstre;
	
	private static Plateau instance;
	
	public Plateau(int x, int y) {
		this.tailleX = x;
		this.tailleY = y;
		this.cases = new Case[x][y];
		instance=this;
	}
	
	public Case[][] getCases() {
		return cases;
	}

	public static Plateau getInstance() {
		return instance;
	}

	public boolean deplacer(Entite e, Direction d) {
		
		return false;
	}

	public Case getCase(Position p) {
		return getCase(p.getX(), p.getY());
	}
	
	public Case getCase(int x, int y) {
		if(x > tailleX || y > tailleY) return null;
		return this.cases[x][y];
	}
}
