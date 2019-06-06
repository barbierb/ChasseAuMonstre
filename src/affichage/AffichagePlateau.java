package affichage;

import java.util.Random;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import personnage.Direction;
import personnage.chasseur.Chasseur;
import personnage.monstre.Monstre;
import plateau.Case;
import plateau.Position;
import reseau.Client;

public class AffichagePlateau {
	@FXML
	private BorderPane screen;
	@FXML
	private Button endTurn;

	@FXML
	private Canvas grille;
	private GraphicsContext gc;
	private int tailleBaseImg;
	@FXML
	private Label tourDeQui;
	@FXML
	private Canvas affichageNbEtoiles;
	@FXML
	private Label tour;

	private Image herbe;
	private Image etoile;
	private Image longueVue;
	private Image chasseur;
	private Image monstre;
	private Image img;

	private GraphicsContext afficheEtoiles;


	private Client c;

	private static AffichagePlateau ap;

	@FXML
	public void initialize() {
		c = Client.getInstance();
		ap = this;
		System.out.println("Initilisation...");
		afficheEtoiles = affichageNbEtoiles.getGraphicsContext2D();

		gc = grille.getGraphicsContext2D();
		tailleBaseImg = (int) grille.getWidth() / c.getPlateau().getTaille();

		//taille et couleur de l'écriture dans les cases
		gc.setFill(Color.YELLOW);
		gc.setFont(new Font(tailleBaseImg/3));

		//paramètres tour
		tour.setFont(new Font("Arial", 28));
		tour.setAlignment(Pos.CENTER);

		//paramètres tourDeQui
		tourDeQui.setFont(new Font("Arial", 28));
		tourDeQui.setAlignment(Pos.CENTER);

		herbe = new Image("File:img/grass_tile_1.png", tailleBaseImg, tailleBaseImg, true, true); //taille dynamique en fonction de taille plateau Client
		etoile = new Image("File:img/etoile.png", tailleBaseImg, tailleBaseImg, true, true);
		longueVue = new Image("File:img/longue-vue.jpg", tailleBaseImg, tailleBaseImg, true, true);
		chasseur = new Image("File:img/Chasseur templerun/Idle__000.png", tailleBaseImg, tailleBaseImg, true, true);
		monstre = new Image("File:img/Monstre zombie/Idle (1).png",  tailleBaseImg, tailleBaseImg, true, true);
		endTurn.setVisible(false);

		grille.setOnMouseClicked(e -> {
			if(c.estMonstre && c.getPlateau().getTour() == 0) {
				Position pmonstre = new Position((int)e.getX()/tailleBaseImg, (int)e.getY()/tailleBaseImg);
				c.getPlateau().setMonstre(new Monstre(pmonstre));
				System.out.println("PTDR ICI OLALA ---->>> "+(c.getPlateau().getMonstre()==null));
				System.out.println("mons 4");
				Position pchass = new Position(pmonstre.getX(), pmonstre.getY());
				while(pmonstre.equals(pchass)) {
					pchass = new Position(new Random().nextInt(c.getPlateau().getTaille()), new Random().nextInt(c.getPlateau().getTaille()));
				}
				c.getPlateau().setChasseur(new Chasseur(pchass));
				//c.getPlateau().placerEtoiles(); //TODO VERIF ICI
				c.getPlateau().setTour(c.getPlateau().getTour()+1);
				c.monTour = true; // pas obligatoire
				update();
				c.envoyerPlateau();
			}
		});

		grille.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if(!Client.getInstance().estMonstre && Client.getInstance().monTour) {
				if(Affichage.placerLongueVue) {
					double x = e.getX();
					double y = e.getY();
					x = x/tailleBaseImg;
					y = y/tailleBaseImg;
					Position p = new Position((int)x,(int)y);
					Case caseActuelle = Client.getInstance().getPlateau().getCase(p);
					caseActuelle.placerLV();    			  
				}
			}
		});

		grille.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if(c.getPlateau().getTour() == 0)
				return;
			if(!c.monTour)
				return;
			if(e.getCode().equals(KeyCode.NUMPAD2) 
					|| e.getCode().equals(KeyCode.NUMPAD4) || e.getCode().equals(KeyCode.NUMPAD6) 
					|| e.getCode().equals(KeyCode.NUMPAD8) 
					|| (Client.getInstance().estMonstre && 
							(e.getCode().equals(KeyCode.NUMPAD1) ||  e.getCode().equals(KeyCode.NUMPAD3) 
									|| e.getCode().equals(KeyCode.NUMPAD7)|| e.getCode().equals(KeyCode.NUMPAD9))) ) {
				Direction d = Direction.byNumero(Integer.getInteger(e.getCharacter()));

				if(Client.getInstance().estMonstre)  {
					Client.getInstance().getPlateau().getMonstre().setDirection(d);
				} else Client.getInstance().getPlateau().getChasseur().setDirection(d);	  
			}

		});

		update();
	}

	public void update() {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {

				//affichage tour
				tour.setText("Tour "+c.getPlateau().getTour());

				//affichage tour de qui
				changerTourDeQui();

				//affichage etoiles que le joueur a
				afficherEtoilesJoueur();

				for(int i = 0; i < c.getPlateau().getTaille(); i++) { //changer par taille plateau Client
					for(int j = 0; j < c.getPlateau().getTaille(); j++) { //idem
						gc.drawImage(herbe, i*herbe.getWidth(), j*herbe.getHeight());
						int nbImg = 0;

						if(c.getPlateau().getCase(i, j).hasEtoile()) {
							img = etoile;
							afficherImg(img, getNbEntites(c.getPlateau().getCase(i, j), i, j), i, j, nbImg, gc);
							nbImg++;
						}	
						if(c.getPlateau().getCase(i, j).getLongueVue() > 0 && !c.estMonstre) {
							img = longueVue;
							afficherImg(img, getNbEntites(c.getPlateau().getCase(i, j), i, j), i, j, nbImg, gc);
							nbImg++;

							if(c.getPlateau().getCase(i, j).getTourPassage() > -1) {
								gc.fillText(""+c.getPlateau().getCase(i, j).getTourPassage(), 5*tailleBaseImg + tailleBaseImg*3/8, 5*tailleBaseImg + tailleBaseImg*5/6);
							}
						}
						if(c.getPlateau().getChasseur() != null) {
							if(c.getPlateau().getChasseur().getPosition().equals(new Position(i,j)) && !c.estMonstre) {
								img = chasseur;
								afficherImg(img, getNbEntites(c.getPlateau().getCase(i, j), i, j), i, j, nbImg, gc);
								nbImg++;
							}
						}
						if(c.getPlateau().getMonstre() != null) {
							if(c.getPlateau().getMonstre().getPosition().equals(new Position(i,j)) && c.estMonstre) {
								img = monstre;
								afficherImg(img, getNbEntites(c.getPlateau().getCase(i, j), i, j), i, j, nbImg, gc);
								nbImg++;
							}
						}	
					}
				}
			}

		});


	}

	private int getNbEntites(Case cas, int x, int y) {
		int nb = 0;

		if(cas.hasEtoile()) {
			nb++;
		}
		if(cas.getLongueVue() > 0 && !c.estMonstre) {
			nb++;
		}
		if(c.getPlateau().getMonstre() != null) {
			if(c.getPlateau().getMonstre().getPosition().equals(new Position(x,y)) && c.estMonstre) {
				nb++;
			}
		}
		if(c.getPlateau().getChasseur() != null) {
			if(c.getPlateau().getChasseur().getPosition().equals(new Position(x,y)) && !c.estMonstre) {
				nb++;
			}
		}

		return nb;
	}

	private void afficherImg(Image img, int nbEntites, int x, int y, int nbImg, GraphicsContext gc) {
		if(nbEntites > 1) {
			gc.drawImage(img, x*tailleBaseImg + nbImg*(tailleBaseImg/2), y*tailleBaseImg, tailleBaseImg/2, tailleBaseImg/2);
		}
		else {
			afficherImg(img, x, y, gc);
		}
	}

	private void afficherImg(Image img, int x, int y, GraphicsContext gc) {
		gc.drawImage(img, x*tailleBaseImg, y*tailleBaseImg);
	}

	private void changerTourDeQui() {
		if(c.getPlateau().getTour() % 2 == 0) {
			if(c.estMonstre) {
				tourDeQui.setText("C'est ton tour !");
			}
			else {
				tourDeQui.setText("Ce n'est pas ton tour");
			}
		}
		else {
			if(!c.estMonstre) {
				tourDeQui.setText("C'est ton tour !");
			}
			else {
				tourDeQui.setText("Ce n'est pas ton tour");
			}
		}
	}

	private void afficherEtoilesJoueur() {
		if(c.estMonstre) {
			if(c.getPlateau().getMonstre() != null) 
				for(int i = 0; i < c.getPlateau().getMonstre().getNbEtoiles(); i++) {
					afficherImg(etoile, i*tailleBaseImg, 0, afficheEtoiles);
				}
		} else {
			if(c.getPlateau().getChasseur() != null) 
				for(int i = 0; i < c.getPlateau().getChasseur().getNbEtoiles(); i++) {
					afficherImg(etoile, i*tailleBaseImg, 0, afficheEtoiles);
				}
		}
	}

	public static AffichagePlateau getInstance() {
		return ap;
	}
}
