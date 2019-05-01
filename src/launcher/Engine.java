package launcher;

import java.util.Iterator;
import java.util.Random;

import entites.items.LongueVue;
import entites.items.LongueVueTemp;
import entites.personnage.Personnage;
import entites.personnage.Type;
import entites.personnage.chasseur.Chasseur;
import entites.personnage.chasseur.ChasseurIA;
import entites.personnage.monstre.Monstre;
import entites.personnage.monstre.MonstreIA;
import plateau.Case;
import plateau.Plateau;
import plateau.Position;
import util.Clavier;
/**
 * Classe qui gère le déroulement de la partie
 * @author Sylvain
 */
public class Engine {

	public final static String CHOIX_JOUEUR1 = 
			"\n        Chasse Au Monstre\n\n"
					+ "Quel personnage voulez-vous jouer ?\n"
					+ "     (1) Chasseur (2) Monstre\n";
	public final static String CHOIX_ADVERSAIRE =
			"\nVoulez vous jouer contre une IA ? (Merci de ne pas jouer contre une IA elles ne sont pas encore fonctionnelles) \n"
					+ "     (1) IA (2) Autre Joueur\n";
	public final static String UTILISER_ETOILE =
			"\nVoulez vous utiliser une étoile ?\n"
					+ "     (1) Oui (2) Non\n";
	public final static String PLACER_LONGUEVUE =
			"\nEntrer une position pour placer une longue vue. (A5,C3,F8...)";
	public final static String PLACER_LONGUEVUE_TMP =
			"\nEntrer une position pour placer une longue vue temporaire. (A5,C3,F8...)";
	public final static String CHOIX_POS_DEBUT =
			"\nVeuillez entrer votre position de départ. (A5,C3,F8...)";
	public final static String RESET = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
	private static final String WIN_MONSTRE = "\nLe monstre a remporté cette partie.\n";
	private static final String WIN_CHASSEUR = "\nLe Chasseur a remporté cette partie.\n";

	private static Engine instance;

	private Plateau plateau;

	private Personnage chasseur;
	private Personnage monstre;

	private int tourActuel;
	private boolean tourMonstre;
	/**
	 * Constructeur de l'engine 
	 * @param config = configuration de la partie
	 */
	public Engine(ConfigurationPartie config) {

		Engine.instance = this;
		this.tourActuel = 0;
		this.tourMonstre = true;
		this.plateau = new Plateau(config.getTailleX(), config.getTailleY());

		Random r = new Random();
		if(config.getJoueur1().equals(Type.MONSTRE)) {
			this.plateau.afficherPlateau();
			Position base = saisiePosition(CHOIX_POS_DEBUT);
			this.plateau.getCase(base).setTourPassage();
			this.monstre = new Monstre(base);
			if(config.getJoueur2().equals(Type.CHASSEUR)) {
				this.chasseur = new Chasseur(new Position(r.nextInt(plateau.getCases().length),r.nextInt(plateau.getCases()[0].length)));
			} else {
				this.chasseur = new ChasseurIA(new Position(r.nextInt(plateau.getCases().length),r.nextInt(plateau.getCases()[0].length)));
			}
		} else {
			this.chasseur = new Chasseur(new Position(r.nextInt(plateau.getCases().length),r.nextInt(plateau.getCases()[0].length)));
			if(config.getJoueur2().equals(Type.MONSTRE)) {
				Position base = saisiePosition(CHOIX_POS_DEBUT);
				this.monstre = new Monstre(base);
			} else {
				this.monstre = new MonstreIA(new Position(r.nextInt(plateau.getCases().length),r.nextInt(plateau.getCases()[0].length)));
			}
		}
		start();
	}
	/**
	 * Lancement de la partie
	 */
	public void start() {
		System.out.println(Engine.RESET);
		while(getWinner() == null) {
			if(this.tourMonstre) {

				if(this.monstre instanceof MonstreIA) {
					System.out.println("TOUR "+this.tourActuel+" - MONSTRE IA");
					//TODO BOSSER LES IA ICI
				} else {
					System.out.println("TOUR "+this.tourActuel+" - MONSTRE");
					this.plateau.afficherPlateau((Monstre)this.monstre);
					if(this.monstre.aEtoile()) {
						char choix = 0;
						do {
							System.out.println(UTILISER_ETOILE);
							String tmp = Clavier.lireString();
							if(tmp != null && tmp.length()>0)
								choix = tmp.charAt(0);
						} while(choix!='1' && choix!='2');
						if(choix == '1') {
							this.monstre.utiliseEtoile();
						}
					}
				}
				this.monstre.deplace();

			} else {

				if(this.plateau.getLongueVues().size() > LongueVue.NB_MAX) {
					this.plateau.getLongueVues().poll().supprLongueVue();
				}

				if(this.chasseur instanceof ChasseurIA) {
					System.out.println("TOUR "+this.tourActuel+" - CHASSEUR IA");
					//TODO BOSSER LES IA 
				} else {
					
					if(this.chasseur.isEtoile()) {
						LongueVueTemp.checkLVTemp();
					}
					System.out.println("TOUR "+this.tourActuel+" - CHASSEUR");
					this.plateau.afficherPlateau((Chasseur)this.chasseur);

					Position lvpos = saisiePosition(PLACER_LONGUEVUE);
					this.plateau.getCase(lvpos).ajouterItem(new LongueVue(lvpos));
					this.plateau.afficherPlateau((Chasseur)this.chasseur);

					if(!this.chasseur.isEtoile() && this.chasseur.aEtoile()) {

						char choix = 0;
						do {
							System.out.println(UTILISER_ETOILE);
							String tmp = Clavier.lireString();
							if(tmp != null && tmp.length()>0)
								choix = tmp.charAt(0);
						} while(choix!='1' && choix!='2');
						if(choix == '1') {
							this.monstre.utiliseEtoile();
							Position lvtmp1 = saisiePosition(PLACER_LONGUEVUE_TMP);
							this.plateau.getCase(lvtmp1).ajouterItem(new LongueVueTemp(lvtmp1));
							Position lvtmp2 = saisiePosition(PLACER_LONGUEVUE_TMP);
							this.plateau.getCase(lvtmp2).ajouterItem(new LongueVueTemp(lvtmp2));
							Position lvtmp3 = saisiePosition(PLACER_LONGUEVUE_TMP);
							this.plateau.getCase(lvtmp3).ajouterItem(new LongueVueTemp(lvtmp3));
						}
					}
				}
				this.chasseur.deplace();
			}

			sleep(100);

			this.tourMonstre = !this.tourMonstre;
			this.tourActuel++;
		}
		
		
		if(getWinner() instanceof Monstre) {
			System.out.println(WIN_MONSTRE);
		} else {
			System.out.println(WIN_CHASSEUR);
		}
		
		
	}

	/**
	 * Retourne le gagnant de la partie
	 * @return 
	 */
	private Personnage getWinner() {

		if(this.monstre.getPosition().equals(this.chasseur.getPosition())) {
			return this.chasseur;
		}

		Iterator<Case> itr = getPlateau().iterator();
		int nbParcoursMonstre = 0;
		while(itr.hasNext()) {
			Case c = itr.next();
			if(c.getTourPassage() != -1) {
				nbParcoursMonstre++;
			}
		}
		if(nbParcoursMonstre >= getPlateau().getNbCases()*0.75) {
			return this.chasseur;
		}

		return null;
	}

	/**
	 * Retourne l'instance de l'engine
	 * @return l'instance de l'engine
	 */
	public static Engine getInstance() {
		return instance;
	}
	/**
	 * Retourne le tour actuel pendant la partie
	 * @return le tour actuel
	 */
	public int getTourActuel() {
		return tourActuel;
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
	 * @param monstre qui va jouer
	 */
	public void setMonstre(Personnage monstre) {
		if(this.monstre == null) this.monstre = monstre;
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

	public Plateau getPlateau() {
		return plateau;
	}

	private void sleep(long millisecondes) {
		try {
			Thread.sleep(millisecondes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Position saisiePosition(String s) {
		String choix = null;
		do {
			System.out.println(s);
			System.out.println("     A->"+(char)(getPlateau().getCases().length+'A')+" 0->"+(getPlateau().getCases()[0].length-1));
			choix = Clavier.lireString();
		} while(choix.length()!=2 
				|| (choix.charAt(0)<'A')
				|| (choix.charAt(0)>(getPlateau().getCases().length+'A')) 
				|| (choix.charAt(1)<'0') 
				|| (choix.charAt(1)>=(getPlateau().getCases()[0].length+'0')));
		return new Position(Integer.parseInt(""+choix.charAt(1)),Integer.parseInt(""+(choix.charAt(0)-'A')));
	}
}