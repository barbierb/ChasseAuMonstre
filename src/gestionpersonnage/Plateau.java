package gestionpersonnage;

public class Plateau {
	
	private static Plateau instance;
	public int tailleX, tailleY;
	public Case[][] cases;

	public int tour;
	
	// tab longue vue

	public Personnage monstre;
	public Personnage chasseur;
	
	public Plateau(int x, int y) {
		instance = this;
		this.tailleX = x;
		this.tailleY = y;
		this.cases = new Case[x][y];

		for(int i=0; i<x; i++)
			for(int j=0; j<y; j++)
				cases[x][y] = new Case();
	}
	
	public void start() {
		while(!isPartyFinished()) {
			
			Direction next;
			do {
				next = monstre.getDirectionVoulue();
			} while(verifCases(monstre, next));
			
			//deplacer dans la direction voulue le monstre
			
			
		}
		// win 
	}
	
	private boolean isPartyFinished() {
		return false;
	}
	
	public boolean deplacer(Entite e, Direction d) {
		
		return false;
	}

	public Case getCase(Position p) {
		return getCase(p.getX(), p.getY());
	}
	
	public Case getCase(int x, int y) {
		if(x > tailleX || y > tailleY || x < 0 || y < 0) return null;
		return this.cases[x][y];
	}

	public static Plateau getInstance() {
		return instance;
	}
	
	private boolean verifCases(Personnage p, Direction d) {
		
		Case tmp = getCase(p.getPosition().getX() + d.getX(), p.getPosition().getY() + d.getY());
		
		if(tmp == null)
			return false;
		
		//TODO verif si c un chasseur et du coup verif si il est pas deja passé. 
		
		return true;
	}
}
