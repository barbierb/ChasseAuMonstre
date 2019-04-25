package plateau;

import java.util.Iterator;

import entites.Entite;
import entites.personnages.Chasseur;
import entites.personnages.ChasseurIA;
import entites.personnages.Direction;
import entites.personnages.Monstre;
import entites.personnages.MonstreIA;
import entites.personnages.Personnage;
import launcher.ConfigurationPartie;

public class Plateau implements Iterable<Case>  {
	
	public int tailleX, tailleY;
	public Case[][] cases;
	
	public Personnage chasseur;
	public Personnage monstre;
	
	private static Plateau instance;
	
	public int tourActuel;
	
	public Plateau(ConfigurationPartie cfg) {
		instance = this;
		this.tourActuel = 0;
		this.tailleX = cfg.getTailleX();
		this.tailleY = cfg.getTailleY();
		if(cfg.isJoueur1Chasseur()) {
			this.chasseur = new Chasseur(new Position(0,0));
			this.monstre = cfg.isJoueur2IA()?new MonstreIA(new Position(0,0)):new Monstre(new Position(0,0));
		} else {
			this.monstre = new Monstre(new Position(0,0));
			this.chasseur = cfg.isJoueur2IA()?new ChasseurIA(new Position(0,0)):new Chasseur(new Position(0,0));
		}
		this.cases = new Case[this.tailleX][this.tailleY];
		for (int i = 0; i < this.tailleX; i++) {
			for (int j = 0; j < this.tailleY; j++) {
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

	@Override
	public Iterator<Case> iterator() {
		return new ItPlateau(cases);
	}

}

