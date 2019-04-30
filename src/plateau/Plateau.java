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

/**
 * Classe du plateau, itérable sur ses cases 
 * @author Sylvain
 */
public class Plateau implements Iterable<Case>  {

	private int tailleX, tailleY;
	private Case[][] cases;

	private Personnage chasseur;
	private Personnage monstre;

	private static Plateau instance;

	private int tourActuel;
	/**
	 * Constructeur en fonction de la configuration passée en paramètre
	 * @param cfg : la configuration de la parties
	 */
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
	/**
	 * Boucle de jeu principale
	 */
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
	 * Affichage du plateau
	 * @param perso : le personnage pour lequel il faut afficher la vue du plateau
	 */
	public void afficherPlateau(Personnage perso) {
		int nbCasesLigne = 0;
		
		//AFFICHAGE LETTRES
		this.afficherLettresColonnes();
		
		for(Case c: this) {
			if(nbCasesLigne % tailleX == 0) {
				//AFFICHAGE LIGNES
				this.afficherLignes();
				//AFFICHAGE CHIFFRES
				System.out.print(nbCasesLigne/tailleX);
			}
			 
			this.afficherContenuCase(c, perso);
			
			nbCasesLigne++;
			
			if(nbCasesLigne % tailleX == 0) {
				System.out.print("║\n");
			}
		}
		//AFFICHAGE BORD BAS
		this.afficherLignes();
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
	 */
	private void afficherLignes() {
		System.out.print(" ");
		for(int i=0; i<tailleX*3+1;i++){
			System.out.print("═");
		}
		System.out.print("\n");
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
					System.out.print("L");
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
		if(condition1(c, perso) || condition2(c, perso) || condition3(c, perso) || condition4(c, perso)) {
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
		if(perso.getPosition().getX() == c.numCase % tailleX && perso.getPosition().getY() == c.numCase / tailleY) {
			return true;
		}
		return false;
	}
	
	/**
	 * Première condition pour l'ajout d'un espace dans une case
	 * Si il n'y a qu'un seul élément dans la case, que le personnage n'y est pas et que le monstre n'y est pas encore passé
	 * @param c: la case à tester
	 * @param perso: le personnage pour lequel il faut afficher la vue de cette case
	 * @return true si la condition est remplie, false sinon
	 */
	private boolean condition1(Case c, Personnage perso) {
		return (c.getDedans().size() == 1 && !this.estPositionPersonnage(c, perso) && c.getTourPassage() == -1);
	}
	
	/**
	 * Deuxième condition pour l'ajout d'un espace dans une case
	 * Si le personnage se trouve sur la case et qu'il n'y a pas d'objet sur la case
	 * @param c: la case à tester
	 * @param perso: le personnage pour lequel il faut afficher la vue de cette case
	 * @return true si la condition est remplie, false sinon
	 */
	private boolean condition2(Case c, Personnage perso) {
		return (this.estPositionPersonnage(c, perso) && c.getDedans().isEmpty());
	}
	
	/**
	 * Troisième condition pour l'ajout d'un espace dans une case
	 * Si il n'y a pas d'objet sur la case, que le monstre y est déjà passé et que le personnage est un monstre
	 * @param c: la case à tester
	 * @param perso: le personnage pour lequel il faut afficher la vue de cette case
	 * @return true si la condition est remplie, false sinon
	 */
	private boolean condition3(Case c, Personnage perso) {
		return (c.getDedans().isEmpty() && c.getTourPassage() != -1 && perso instanceof Monstre);
	}
	
	/**
	 * Quatrième condition pour l'ajout d'un espace dans une case
	 * Si il n'y a qu'un seul objet sur la case, que le personnage est un chasseur et qu'il n'est pas sur la case
	 * @param c: la case à tester
	 * @param perso: le personnage pour lequel il faut afficher la vue de cette case
	 * @return true si la condition est remplie, false sinon
	 */
	private boolean condition4(Case c, Personnage perso) {
		return (c.getDedans().size() == 1 && perso instanceof Chasseur && !this.estPositionPersonnage(c, perso));
	}
	/**
	 * Donne au plateau les informations sur le chasseur
	 * @param chasseur qui va jouer
	 */
	public void setChasseur(Personnage chasseur) {
		if(this.chasseur == null) this.chasseur = chasseur;
	}
	/**
	 * Donne au plateau les informations sur le chasseur
	 * @param chasseur qui va jouer
	 */
	public void setMonstre(Personnage monstre) {
		if(this.monstre == null) this.monstre = monstre;
	}
	/**
	 * @return le tableau de cases
	 */
	public Case[][] getCases() {
		return cases;
	}
	/**
	 * Retourne l'instance du plateau de jeu
	 * @return
	 */
	public static Plateau getInstance() {
		return instance;
	}
	/**
	 * Donne une case en fonction d'une Position passée en paramètre
	 * @param p = la position de la case désirée
	 * @return la case à la position demandée
	 */
	public Case getCase(Position p) {
		return getCase(p.getX(), p.getY());
	}
	/**
	 * Donne une case en fonction de ses coordonnées d'abscisse et d'ordonnée
	 * @param x = l'abscisse
	 * @param y = l'ordonnée
	 * @return la Case à la position demandée
	 */
	public Case getCase(int x, int y) {
		if(x > tailleX || y > tailleY) return null;
		return this.cases[x][y];
	}
	/**
	 * Donne la taille en abscisse du plateau
	 * @return un entier représentant la taille en abscisse du plateau
	 */
	public int getTailleX() {
		return tailleX;
	}
	/**
	 * Donne la taille en ordonnée du plateau
	 * @return un entier représentant la taille en ordonnée du plateau
	 */
	public int getTailleY() {
		return tailleY;
	}
	/**
	 * Donne le chasseur de la partie
	 * @return un chasseur
	 */
	public Personnage getChasseur() {
		return chasseur;
	}
	/**
	 * Donne le monstre de la partie
	 * @return un monstre
	 */
	public Personnage getMonstre() {
		return monstre;
	}
	/**
	 * Donne le numéro du tour de jeu actuel
	 * @return un entier représentant le tour actuel
	 */
	public int getTourActuel() {
		return tourActuel;
	}
	/**
	 * Retourne l'itérateur du plateau qui passe par toutes les cases
	 */
	@Override
	public Iterator<Case> iterator() {
		return new ItPlateau(cases);
	}

	//TODO faire les emplacements de base du chasseur et monstre

}

