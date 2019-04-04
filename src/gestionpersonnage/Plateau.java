package gestionpersonnage;

public class Plateau {
	
	public int tailleX, tailleY;
	public Case[][] cases;
	
	// tab longue vue 

	public Personnage chasseur;
	public Personnage monstre;
	
	public Plateau(int x, int y) {
		this.tailleX = x;
		this.tailleY = y;
		this.cases = new Case[x][y];
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
