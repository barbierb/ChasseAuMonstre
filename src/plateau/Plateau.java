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
		String contenu = "║";

		//AFFICHAGE CONTENU LIGNES
		if(this.estPositionPersonnage(c, perso)) {
			if(perso instanceof Chasseur) {
				contenu+="C";
			}
			else {
				contenu+="M";
			}
		}
		else if(c.getDedans().isEmpty() && !(c.getTourPassage() != -1 && perso instanceof Monstre)) {
			contenu+="  ";
		}
		
		if(c.getTourPassage() != -1 && perso instanceof Monstre) {
			contenu+="-";
		}
		
		for(Item h:c.getDedans()) {
			if(h instanceof Etoile) {
				contenu+="E";
			}
			else if(h instanceof LongueVue) {
				if(perso instanceof Chasseur) {
					if(c.getTourPassage() != -1) {
						contenu+=c.getTourPassage();
					}
					else {
						contenu+="L";
					}
				}
				else if(c.getTourPassage() == -1 || perso instanceof Chasseur){
					contenu+=" ";
				}
			}
		}
		
		contenu = this.afficherEspaceManquant(contenu);
		
		System.out.print(contenu);
	}
	
	/**
	 * Affichage de l'espace manquant pour combler la case si besoin
	 * @param contenu: le contenu de la case actuel
	 * @return le contenu de la case modifié
	 */
	private String afficherEspaceManquant(String contenu) {
		for(int i = 0; i < 3 - contenu.length(); i++) {
			contenu+=" ";
		}
		
		return contenu;
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

