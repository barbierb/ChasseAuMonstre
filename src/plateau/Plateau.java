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
	 * Affichage du plateau du point de vue du chasseur
	 * @param chasseur
	 */
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
				} else {
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
	/**
	 * Affichage du plateau du point de vue du monstre
	 * @param monstre
	 */
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
	/**
	 * Fonction de vérification pour les mouvements des personnages
	 * @param p = le personnage qui souhaite se déplacer
	 * @param d = la direction dans laquelle il souhaite se déplacer
	 * @return true si le mouvement est possible, false sinon
	 */
	private boolean verifCases(Personnage p, Direction d) {

		Case tmp = getCase(p.getPosition().getX() + d.getX(), p.getPosition().getY() + d.getY());

		if(tmp == null)
			return false;

		//TODO verif si c un chasseur et du coup verif si il est pas deja pass et si il est dans la zone autour du monstre. 
		//TODO verif si c un monstre et si il marche sur sa propre case

		return true;
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

