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
	
	/**
	 * Affichage du plateau vide
	 */
	public void afficherPlateau() {
		//AFFICHAGE LETTRES
		this.afficherLettresColonnes();
		
		for(Case c: this) {
			if(c.numCase % tailleY == 0) {
				//AFFICHAGE LIGNES
				this.afficherLignes(c);
				//AFFICHAGE CHIFFRES
				System.out.print(c.numCase/tailleY);
			}
			
			//AFFICHAGE DES CASES
			System.out.print("║  ");
			
			//SI FIN DE LA LIGNE, AFFICHAGE DU DERNIER TRAIT VERTICAL
			if((c.numCase + 1) % tailleY == 0) {
				System.out.println("║");
			}
		}
		//AFFICHAGE BORD BAS
		this.afficherDerniereLigne();
	}
	
	/**
	 * Affichage du plateau
	 * @param perso : le personnage pour lequel il faut afficher la vue du plateau
	 */
	public void afficherPlateau(Personnage perso) {
		
		//AFFICHAGE LETTRES
		this.afficherLettresColonnes();
		
		for(Case c: this) {
			if(c.numCase % tailleY == 0) {
				//AFFICHAGE LIGNES
				this.afficherLignes(c);
				//AFFICHAGE CHIFFRES
				System.out.print(c.numCase/tailleY);
			}
			
			//AFFICHAGE DES CASES
			this.afficherContenuCase(c, perso);
			
			//SI FIN DE LA LIGNE, AFFICHAGE DU DERNIER TRAIT VERTICAL
			if((c.numCase + 1) % tailleY == 0) {
				System.out.println("║");
			}
		}
		//AFFICHAGE BORD BAS
		this.afficherDerniereLigne();
	}
	
	/**
	 * Fonction d'affichage des lettres désignant les colonnes du plateau
	 */
	private void afficherLettresColonnes() {
		for(int i = 0; i < tailleX; i++) {
			System.out.print("  "+(char)(65+i));
		}
		System.out.print("\n");
	}
	
	/**
	 * Fonction d'affichage des lignes constituant la structure du plateau
	 * @param c : Case de début de la ligne
	 */
	private void afficherLignes(Case c) {
		System.out.print(" ");
		if(c.numCase == 0) {
			System.out.print("╔");
			for(int i=0; i<tailleX-1;i++){
				System.out.print("══╦");
			}
			System.out.println("══╗");
			
		}
		else {
			System.out.print("╠");
			for(int i=0; i<tailleX-1;i++){
				System.out.print("══╬");
			}
			System.out.println("══╣");
		}
	}
	
	/**
	 * Affichage de la dernière ligne de structure du plateau
	 */
	private void afficherDerniereLigne() {
		System.out.print(" ╚");
		for(int i=0; i<tailleX-1;i++){
			System.out.print("══╩");
		}
		System.out.println("══╝");
	}
	
	/**
	 * Affichage du contenu d'une case
	 * @param c: la case dont il faut afficher le contenu
	 * @param perso: le personnage pour lequel il faut afficher la vue de cette case
	 */
	private void afficherContenuCase(Case c, Personnage perso) {
		System.out.print("║");
		//AFFICHAGE CONTENU LIGNES
		if(this.estPositionPersonnage(c, perso)) {
			if(perso instanceof Chasseur) {
				System.out.print("C");
			}
			else {
				System.out.print("M");
			}
		}
		else if(c.getDedans().isEmpty() && !(c.getTourPassage() != -1 && perso instanceof Monstre)) {
			System.out.print("  ");
		}
		
		if(c.getTourPassage() != -1 && perso instanceof Monstre) {
			System.out.print("-");
		}
		
		for(Item h:c.getDedans()) {
			if(h instanceof Etoile) {
				System.out.print("E");
			}
			else if(h instanceof LongueVue) {
				if(perso instanceof Chasseur) {
					if(c.getTourPassage() != -1) {
						System.out.print(c.getTourPassage());
					}
					else {
					System.out.print("L");
					}
				}
				else if(c.getTourPassage() == -1 || perso instanceof Chasseur){
					System.out.print(" ");
				}
			}
		}
		
		this.afficherEspaceManquant(c, perso);
		
	}
	
	/**
	 * Affichage de l'espace manquant pour combler la case si besoin
	 * @param c: la case sur laquelle il faut rajouter un espace si besoin
	 * @param perso: le personnage pour lequel il faut afficher la vue de cette case
	 */
	private void afficherEspaceManquant(Case c, Personnage perso) {
		if(condition1(c, perso) || condition2(c, perso) || condition3(c, perso)) {
			System.out.print(" ");
		}
	}
	
	/**
	 * Fonction qui teste si le personnage se situe sur la case passée en paramètre
	 * @param c: la case à tester
	 * @param perso: le personnage à tester
	 * @return true si le personnage se situe sur la case passée en paramètre, false sinon
	 */
	private boolean estPositionPersonnage(Case c, Personnage perso) {
		if(perso.getPosition().getX() == c.numCase / tailleY && perso.getPosition().getY() == c.numCase % tailleY) {
			return true;
		}
		return false;
	}
	
	/**
	 * Première condition pour l'ajout d'un espace dans une case
	 * Si il n'y a qu'un seul élément dans la case et que le personnage n'y est pas
	 * @param c: la case à tester
	 * @param perso: le personnage pour lequel il faut afficher la vue de cette case
	 * @return true si la condition est remplie, false sinon
	 */
	private boolean condition1(Case c, Personnage perso) {
		return (c.getDedans().size() == 1 && !this.estPositionPersonnage(c, perso));
	}
	
	/**
	 * Deuxième condition pour l'ajout d'un espace dans une case
	 * Si le personnage se trouve sur la case, qu'il n'y a pas d'objet sur la case et que le personnage est un chasseur
	 * @param c: la case à tester
	 * @param perso: le personnage pour lequel il faut afficher la vue de cette case
	 * @return true si la condition est remplie, false sinon
	 */
	private boolean condition2(Case c, Personnage perso) {
		return (this.estPositionPersonnage(c, perso) && c.getDedans().isEmpty() && perso instanceof Chasseur);
	}
	
	/**
	 * Troisième condition pour l'ajout d'un espace dans une case
	 * Si il n'y a qu'un seul objet sur la case, que le personnage est un chasseur et qu'il n'est pas sur la case
	 * @param c: la case à tester
	 * @param perso: le personnage pour lequel il faut afficher la vue de cette case
	 * @return true si la condition est remplie, false sinon
	 */
	private boolean condition3(Case c, Personnage perso) {
		return (c.getDedans().size() == 1 && perso instanceof Chasseur && !this.estPositionPersonnage(c, perso));
	}
	
	private boolean verifCases(Personnage p, Direction d) {
		
		Case tmp = getCase(p.getPosition().getX() + d.getX(), p.getPosition().getY() + d.getY());
		
		if(tmp == null)
			return false;
		
		//TODO verif si c un chasseur et du coup verif si il est pas deja pass et si il est dans la zone autour du monstre. 
		//TODO verif si c un monstre et si il marche sur sa propre case
		
		return true;
	}

	public void setChasseur(Personnage chasseur) {
		this.chasseur = chasseur;
	}

	public void setMonstre(Personnage monstre) {
		this.monstre = monstre;
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
	
	//TODO faire les emplacements de base du chasseur et monstre

}

