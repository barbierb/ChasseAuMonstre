package plateau;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Classe du plateau, itérable sur ses cases 
 * @author Sylvain
 */
public class Plateau implements Iterable<Case>  {

	private int tailleX, tailleY;
	private Case[][] cases;
	private int nbCases;

	/**
	 * Constructeur en fonction de la configuration passée en paramètre
	 * @param cfg : la configuration de la parties
	 */
	public Plateau(int x, int y) {
		this.tailleX = x;
		this.tailleY = y;
		this.cases = new Case[this.tailleX][this.tailleY];
		for (int i = 0; i < this.tailleX; i++) {
			for (int j = 0; j < this.tailleY; j++) {
				cases[i][j] = new Case();
				nbCases++;
			}
		}
		
		ArrayList<Position> tmp = new ArrayList<Position>();
		int nbEtoile = 0;
		while(nbEtoile != 3) {
			Position p = new Position(new Random().nextInt(this.tailleX), new Random().nextInt(this.tailleY));
			if(!tmp.contains(p)) {
				tmp.add(p);
				cases[p.getX()][p.getY()].placerEtoile();
				nbEtoile++;
			}
		}
		
	}
	
	
	/**
	 * Affichage du plateau vide
	 */
	/*
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
	*/
	/**
	 * Affichage du plateau
	 * @param perso : le personnage pour lequel il faut afficher la vue du plateau
	 */
	/*
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
	*/
	/**
	 * Fonction d'affichage des lettres désignant les colonnes du plateau
	 */
	/*
	private void afficherLettresColonnes() {
		for(int i = 0; i < tailleX; i++) {
			System.out.print("  "+(char)(65+i));
		}
		System.out.print("\n");
	}
	*/
	/**
	 * Fonction d'affichage des lignes constituant la structure du plateau
	 * @param c : Case de début de la ligne
	 */
	/*
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
	*/
	/**
	 * Affichage de la dernière ligne de structure du plateau
	 */
	/*
	private void afficherDerniereLigne() {
		System.out.print(" ╚");
		for(int i=0; i<tailleX-1;i++){
			System.out.print("══╩");
		}
		System.out.println("══╝");
	}
	*/
	/**
	 * Affichage du contenu d'une case
	 * @param c: la case dont il faut afficher le contenu
	 * @param perso: le personnage pour lequel il faut afficher la vue de cette case
	 */
	/*
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
	*/
	/**
	 * Affichage de l'espace manquant pour combler la case si besoin
	 * @param contenu: le contenu de la case actuel
	 * @return le contenu de la case modifié
	 */
	/*
	private String afficherEspaceManquant(String contenu) {
		for(int i = 0; i < 3 - contenu.length(); i++) {
			contenu+=" ";
		}
		
		return contenu;
	}
	*/
	/**
	 * Fonction qui teste si le personnage se situe sur la case passée en paramètre
	 * @param c: la case à tester
	 * @param perso: le personnage à tester
	 * @return true si le personnage se situe sur la case passée en paramètre, false sinon
	 */
	/*
	private boolean estPositionPersonnage(Case c, Personnage perso) {
		if(perso.getPosition().getX() == c.numCase / tailleY && perso.getPosition().getY() == c.numCase % tailleY) {
			return true;
		}
		return false;
	}
	*/


	/**
	 * @return le tableau de cases
	 */
	public Case[][] getCases() {
		return cases;
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
		if(x >= tailleX || y >= tailleY) return null;
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
	 * Retourne l'itérateur du plateau qui passe par toutes les cases
	 */
	@Override
	public Iterator<Case> iterator() {
		return new ItPlateau(cases);
	}


	public double getNbCases() {
		return nbCases;
	}




	//TODO faire les emplacements de base du chasseur et monstre

}

