package plateau;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

import entites.items.LongueVue;
import entites.personnage.Personnage;
import entites.personnage.chasseur.Chasseur;
import entites.personnage.monstre.Monstre;

/**
 * Classe du plateau, itérable sur ses cases 
 * @author Benoit
 */
public class Plateau implements Iterable<Case>  {

	private int tailleX, tailleY;
	private Case[][] cases;
	private int nbCases;
	private Queue<LongueVue> longueVues;

	/**
	 * Constructeur en fonction de la configuration passée en paramètre
	 * @param cfg : la configuration de la parties
	 */
	public Plateau(int x, int y) {
		this.tailleX = x;
		this.tailleY = y;
		this.longueVues = new PriorityQueue<LongueVue>();
		this.cases = new Case[this.tailleX][this.tailleY];
		for (int i = 0; i < this.tailleX; i++) {
			for (int j = 0; j < this.tailleY; j++) {
				cases[i][j] = new Case();
				nbCases++;
			}
		}
	}
	
	public void afficherPlateau(Personnage perso) {
		int nbCasesLigne = 0;
		
		//AFFICHAGE LETTRES
		this.afficherLettresCases();
		
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
				System.out.print("|\n");
			}
		}
		//AFFICHAGE BORD BAS
		this.afficherLignes();
	}
	
	private void afficherLettresCases() {
		for(int i = 0; i < tailleX; i++) {
			System.out.print("  "+(char)(65+i));
		}
		System.out.print("\n");
	}
	
	private void afficherLignes() {
		System.out.print(" ");
		for(int i=0; i<tailleX*3+1;i++){
			System.out.print("-");
		}
		System.out.print("\n");
	}
	
	private void afficherContenuCase(Case c, Personnage perso) {
		System.out.print("|");
		//AFFICHAGE CONTENU LIGNES
		if(perso.getPosition().getX() == c.numCase % tailleX && perso.getPosition().getY() == c.numCase / tailleY) {
			if(perso instanceof Chasseur) {
				System.out.print("C");
			}
		}
		
		this.afficherEspaceManquant(c, perso);
		
	}
	
	private void afficherEspaceManquant(Case c, Personnage perso) {
		if((c.getDedans().size() == 1 && !(perso.getPosition().getX() == c.numCase % tailleX && perso.getPosition().getY() == c.numCase / tailleY) && c.getTourPassage() == -1)
				|| (perso.getPosition().getX() == c.numCase % tailleX && perso.getPosition().getY() == c.numCase / tailleY && c.getDedans().isEmpty())
				|| (c.getDedans().isEmpty() && c.getTourPassage() != -1 && perso instanceof Monstre)
				|| (c.getDedans().size() == 1 && perso instanceof Chasseur && !(perso.getPosition().getX() == c.numCase % tailleX && perso.getPosition().getY() == c.numCase / tailleY))){
			System.out.print(" ");
		}
	}

	
	/**
	 * @return le tableau de cases
	 */
	public Case[][] getCases() {
		return cases;
	}

	public Queue<LongueVue> getLongueVues() {
		return longueVues;
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
	 * Donne le nombre de cases total du tableau
	 * @return un entier représentant le nb de cases
	 */
	public int getNbCases() {
		return nbCases;
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

