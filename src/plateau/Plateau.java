package plateau;

import java.util.Iterator;

import entites.items.Etoile;
import entites.items.Item;
import entites.items.LongueVue;
import entites.personnage.Direction;
import entites.personnage.Personnage;
import entites.personnage.chasseur.Chasseur;
import entites.personnage.chasseur.ChasseurIA;
import entites.personnage.monstre.Monstre;
import entites.personnage.monstre.MonstreIA;
import launcher.ConfigurationPartie;

public class Plateau implements Iterable<Case>  {
	
	private int tailleX, tailleY;
	private Case[][] cases;
	
	private Personnage chasseur;
	private Personnage monstre;
	
	private static Plateau instance;
	
	private int tourActuel;
	
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

	public void afficherPlateau(Chasseur chasseur) {
		
		for(int j=0; j<tailleY;j++) {
			//AFFICHAGE BORD HAUT
			for(int i=0; i<tailleX*4+1;i++){
				System.out.print("-");
			}
			System.out.print("\n");
		
			//AFFICHAGE LIGNES
			for(int i=0; i<tailleX; i++) {
				if(chasseur.getPosition().getX() == i && chasseur.getPosition().getY() == j) {
					System.out.print("| C ");
				}
				else {
					for(Item h:cases[i][j].getDedans()) {
						if(h instanceof Etoile && cases[i][j].getDedans().size() < 2) {
							System.out.print("| * ");
						}
						else if(h instanceof LongueVue) {
							System.out.print("| L ");
						}
					}
					if(cases[i][j].getDedans().isEmpty()) {
						System.out.print("|   ");
					}
				}
			}
			System.out.print("|\n");
		}
		//AFFICHAGE BORD BAS
		for(int i=0; i<tailleX*4+1;i++){
			System.out.print("-");
		}
		System.out.print("\n");
	}
	
	public void afficherPlateau(Monstre monstre) {
		
		for(int j=0; j<tailleY;j++) {
			//AFFICHAGE BORD HAUT
			for(int i=0; i<tailleX*4+1;i++){
				System.out.print("-");
			}
			System.out.print("\n");
		
			//AFFICHAGE LIGNES
			for(int i=0; i<tailleX; i++) {
				if(monstre.getPosition().getX() == i && monstre.getPosition().getY() == j) {
					System.out.print("| M ");
				}
				else if(cases[i][j].getTourPassage() != -1) {
					System.out.print("| - ");
				}
				else {
					for(Item h:cases[i][j].getDedans()) {
						if(h instanceof Etoile) {
							System.out.print("| * ");
						}
						else if(h instanceof LongueVue && cases[i][j].getDedans().size() < 2) {
							System.out.print("|   ");
						}
					}
					if(cases[i][j].getDedans().isEmpty()) {
						System.out.print("|   ");
					}
				}
			}
			System.out.print("|\n");
		}
		//AFFICHAGE BORD BAS
		for(int i=0; i<tailleX*4+1;i++){
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

	public int getTailleX() {
		return tailleX;
	}

	public int getTailleY() {
		return tailleY;
	}

	public Personnage getChasseur() {
		return chasseur;
	}

	public Personnage getMonstre() {
		return monstre;
	}

	public int getTourActuel() {
		return tourActuel;
	}

	@Override
	public Iterator<Case> iterator() {
		return new ItPlateau(cases);
	}

}

