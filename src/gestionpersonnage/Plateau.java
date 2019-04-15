package gestionpersonnage;

public class Plateau {
	
	public int tailleX, tailleY;
	public Case[][] cases;
	
	// tab longue vue 

	public Personnage chasseur;
	public Personnage monstre;
	
	private static Plateau instance;
	
	public int tourActuel;
	
	public Plateau(int x, int y) {
		this.tailleX = x;
		this.tailleY = y;
		this.cases = new Case[x][y];
		instance=this;
		tourActuel=0;
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

	public void afficherPlateau() {
		//AFFICHAGE BORD HAUT
		for(int i=0; i<tailleX;i++){
			System.out.print("-");
		}
		System.out.print("\n");
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
	
	private boolean verifCases(Personnage p, Direction d) {
		
		Case tmp = getCase(p.getPosition().getX() + d.getX(), p.getPosition().getY() + d.getY());
		
		if(tmp == null)
			return false;
		
		//TODO verif si c un chasseur et du coup verif si il est pas deja pass. 
		
		return true;
	}

}
