package gestionpersonnage;

import java.util.Iterator;

public class Plateau implements Iterator<Case>,Iterable<Case> {
	
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
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				cases[i][j] = new Case();
			}
		}
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
		
		//TODO verif si c un chasseur et du coup verif si il est pas deja pass et si il est dans la zone autour du monstre. 
		//TODO verif si c un monstre et si il marche sur sa propre case
		
		return true;
	}

	private int nbCases = cases[0].length * cases.length ;
	private int numCases = 1;
	private int i = 0;
	private int j = 0;
	@Override
	public boolean hasNext() {
		
		return numCases < nbCases  ;
	}

	@Override
	public Case next() {
		numCases++;
		i++;
		if(i > cases[0].length) {
			i = 0;
			j++;
		}
		
		return cases[i][j]  ;
	}

	@Override
	public Iterator<Case> iterator() {
	
		return this;
	}

}
