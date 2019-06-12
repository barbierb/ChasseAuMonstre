package affichage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import personnage.Direction;
import personnage.chasseur.Chasseur;
import personnage.chasseur.ChasseurIA;
import personnage.monstre.Monstre;
import plateau.Case;
import plateau.Plateau;
import plateau.Position;
import reseau.Client;
import reseau.Serveur;

public class AffichagePlateau{
	
    @FXML
    private Canvas grille;
    private GraphicsContext gc;
    private int tailleBaseImg;
 
    @FXML
    private Canvas nbEtoiles;
    private GraphicsContext afficheEtoiles;
    @FXML
    private Label tour;
    
    private Image ble;
    private Image bleEcrase;
    private Image etoile;
    private Image longueVue;
    private Image chasseurHaut;
    private Image chasseurBas;
    private Image chasseurDroite;
    private Image chasseurGauche;
    private Image img;
    
    @FXML
    private Canvas controles;
    private GraphicsContext afficheControles;
    
    @FXML
    private Pane blocage;
    @FXML
    private Label labelBlocage;
    
    @FXML
    private Label competence;
    
    private Font police;
    
    @FXML
    private Label attention;
    
	private Client c;

	private static AffichagePlateau ap;
	
	public static boolean solo;
	private Direction lastdir = Direction.S;
	
	private Image monstreHaut;
	private Image monstreBas;
	private Image monstreGauche;
	private Image monstreDroite;
    
    public void initialize() throws FileNotFoundException {

    	labelBlocage.setTextAlignment(TextAlignment.CENTER);
    	labelBlocage.setFont(new Font("NewsgeekSerif", 42));
    	labelBlocage.setText("L'ENNEMI JOUE...");
        
		c = Client.getInstance();
		ap = this;
        
        gc = grille.getGraphicsContext2D();
        tailleBaseImg = (int) grille.getWidth() / c.getPlateau().getTaille();
        
        //preparation affichage étoiles
        afficheEtoiles = nbEtoiles.getGraphicsContext2D();
        afficheEtoiles.drawImage(new Image("File:img/aide.png"), 0, 0, nbEtoiles.getWidth(), nbEtoiles.getHeight());
        
        //taille et couleur de l'écriture dans les cases
        gc.setFill(Color.YELLOW);
        gc.setFont(new Font(tailleBaseImg/3));
        
        //chargement de la police d'écriture
        police = Font.loadFont(new FileInputStream(new File("data/NewsgeekSerif.ttf")), 30);
        
        //paramètres tour
        tour.setFont(police);
        tour.setAlignment(Pos.CENTER);
        
        //paramètres competence
        competence.setFont(police);
        competence.setAlignment(Pos.CENTER);
        
        //paramètre attention
        attention.setFont(police);
        attention.setAlignment(Pos.CENTER);
        attention.setTextFill(Color.web("E30A0A"));
        
        //affichage controles
        afficheControles = controles.getGraphicsContext2D();
        afficherControles();
        
        ble = new Image("File:img/ble.png", tailleBaseImg, tailleBaseImg, true, true); //taille dynamique en fonction de taille plateau Client
        bleEcrase = new Image("File:img/bleEcrase.png", tailleBaseImg, tailleBaseImg, true, true);
        etoile = new Image("File:img/etoile.png", tailleBaseImg, tailleBaseImg, true, true);
        longueVue = new Image("File:img/longuevue.png", tailleBaseImg, tailleBaseImg, true, true);

        chasseurHaut = new Image("File:img/chasseur_haut.png", tailleBaseImg, tailleBaseImg, true, true);
        chasseurBas = new Image("File:img/chasseur_bas.png", tailleBaseImg, tailleBaseImg, true, true);
        chasseurGauche = new Image("File:img/chasseur_gauche.png", tailleBaseImg, tailleBaseImg, true, true);
        chasseurDroite = new Image("File:img/chasseur_droite.png", tailleBaseImg, tailleBaseImg, true, true);

        monstreHaut = new Image("File:img/monstre_haut.png", tailleBaseImg, tailleBaseImg, true, true);
        monstreBas = new Image("File:img/monstre_bas.png", tailleBaseImg, tailleBaseImg, true, true);
        monstreGauche = new Image("File:img/monstre_gauche.png", tailleBaseImg, tailleBaseImg, true, true);
        monstreDroite = new Image("File:img/monstre_droite.png", tailleBaseImg, tailleBaseImg, true, true);
        
		grille.setOnMouseClicked(e -> {
			if(c.estMonstre && c.getPlateau().getTour() == 0) {
				Position pmonstre = new Position((int)e.getX()/tailleBaseImg, (int)e.getY()/tailleBaseImg);
				c.getPlateau().setMonstre(new Monstre(pmonstre));
				Position pchass = new Position(pmonstre.getX(), pmonstre.getY());
				while(pmonstre.equals(pchass)) {
					pchass = new Position(new Random().nextInt(c.getPlateau().getTaille()), new Random().nextInt(c.getPlateau().getTaille()));
				}
				c.getPlateau().setChasseur(Serveur.getInstance().solo?
						new ChasseurIA(pchass):new Chasseur(pchass));
				c.getPlateau().placerEtoiles();
				c.getPlateau().getCase(pmonstre).setTourPassage(0);
				c.getPlateau().setTour(1);
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
					update();
					Affichage.placerLongueVue=false;
				}
			}
		});

		Affichage.stage.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			System.out.println("event");
			if(c.getPlateau().getTour() == 0) {
				return;
			}
			if(!c.monTour) {
				System.out.println("pas ton tour :')");
				return;
			}
			if(!Affichage.placerLongueVue)
				if(e.getCode().equals(KeyCode.NUMPAD2) 
						|| e.getCode().equals(KeyCode.NUMPAD4) || e.getCode().equals(KeyCode.NUMPAD6) 
						|| e.getCode().equals(KeyCode.NUMPAD8) 
						|| (Client.getInstance().estMonstre && 
								(e.getCode().equals(KeyCode.NUMPAD1) ||  e.getCode().equals(KeyCode.NUMPAD3) 
										|| e.getCode().equals(KeyCode.NUMPAD7)|| e.getCode().equals(KeyCode.NUMPAD9))) ) {

					Direction d = Direction.byNumero(Integer.parseInt(e.getText()));
					lastdir = d;
					if(Client.getInstance().estMonstre)  {
						Client.getInstance().getPlateau().getMonstre().setDirection(d);
					} else Client.getInstance().getPlateau().getChasseur().setDirection(d);	  
				}
			if(e.getCode().equals(KeyCode.ASTERISK) || e.getCode().equals(KeyCode.MULTIPLY)) {
				Plateau p = Client.getInstance().getPlateau();
				if(Client.getInstance().estMonstre) {
					Monstre m = p.getMonstre();
					if(m.aEtoile()) m.utiliseEtoile();
				} else {
					Chasseur c = p.getChasseur();
					if(c.aEtoile()) c.utiliseEtoile();
				}
				update();
			}
			
            if(e.getCode().equals(KeyCode.ESCAPE)) {
                try {
                    Affichage.getInstance().start(Affichage.stage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
		});
        
        update();
    }
    
    private void afficherAttente() {
    	blocage.toFront();
    	labelBlocage.toFront();
    }
    
    public void update() {	
    	Platform.runLater(new Runnable() {
    		@Override
			public void run() {
    			labelBlocage.toBack();
    			blocage.toBack();
    			
    			afficheEtoiles.clearRect(0, 0, nbEtoiles.getWidth(), nbEtoiles.getHeight());
       			gc.clearRect(0, 0, grille.getWidth(), grille.getHeight());
		    	
		    	//affichage tour
		    	tour.setText("Tour "+c.getPlateau().getTour());
		    	
		    	//affichage etoiles que le joueur a
		    	if(c.getPlateau().getMonstre() != null) {
		    		afficherEtoilesJoueur();
		    	}
		        
		    	if(c.getPlateau().getMonstre() != null && c.getPlateau().getChasseur() != null) {
			        if(distanceMonstreChasseur() < 3 && c.estMonstre) {
			        	if(distanceMonstreChasseur() == 1) {
			        		attention.setText("Attention ! Le chasseur est a "+distanceMonstreChasseur()+" case");
			        	} else {
			        		attention.setText("Attention ! Le chasseur est a "+distanceMonstreChasseur()+" cases");
			        	}
			        }
		    	}
		    	
		        for(int i = 0; i < c.getPlateau().getTaille(); i++) { //changer par taille plateau Client
		        	for(int j = 0; j < c.getPlateau().getTaille(); j++) { //idem
		        		if(c.getPlateau().getCase(i, j).getTourPassage() > -1 && c.estMonstre) {
		        			gc.drawImage(bleEcrase, i*ble.getWidth(), j*ble.getHeight());
		        		} else {
		        			gc.drawImage(ble, i*ble.getWidth(), j*ble.getHeight());
		        		}
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
			        			if(lastdir.equals(Direction.N)) {
			        				img = chasseurHaut;
			        			} else if(lastdir.equals(Direction.O)) {
			        				img = chasseurGauche;
			        			} else if(lastdir.equals(Direction.E)) {
			        				img = chasseurDroite;
			        			} else {
			        				img = chasseurBas;
			        			}
			        	    	afficherImg(img, getNbEntites(c.getPlateau().getCase(i, j), i, j), i, j, nbImg, gc);
			        	    	nbImg++;
			        	   	}
		        		}
		        		if(c.getPlateau().getMonstre() != null) {
			        		if(c.getPlateau().getMonstre().getPosition().equals(new Position(i,j)) && c.estMonstre) {
			        			if(lastdir.equals(Direction.N) || lastdir.equals(Direction.NE) || lastdir.equals(Direction.NO)) {
			        				img = monstreHaut;
			        			} else if(lastdir.equals(Direction.O)) {
			        				img = monstreGauche;
			        			} else if(lastdir.equals(Direction.E)) {
			        				img = monstreDroite;
			        			} else {
			        				img = monstreBas;
			        			}
			        	   		afficherImg(img, getNbEntites(c.getPlateau().getCase(i, j), i, j), i, j, nbImg, gc);
			        	   		nbImg++;
			        	   	}
		        		}	
		        	}
				}
		        
		        if(!c.monTour && !solo) {
		        	afficherAttente();
		        }
    		}
    	});
    }
    
    private int getNbEntites(Case c, int x, int y) {
    	int nb = 0;
    	
    	if(c.hasEtoile()) {
    		nb++;
    	}
    	if(c.getLongueVue() > 0 && !this.c.estMonstre) {
    		nb++;
    	}
    	if(this.c.getPlateau().getMonstre() != null) {
	    	if(this.c.getPlateau().getMonstre().getPosition().equals(new Position(x,y)) && this.c.estMonstre) {
	    		nb++;
	    	}
    	}
    	if(this.c.getPlateau().getChasseur() != null) {
	    	if(this.c.getPlateau().getChasseur().getPosition().equals(new Position(x,y)) && !this.c.estMonstre) {
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
    
    private void afficherEtoilesJoueur() {
    	afficheEtoiles.drawImage(new Image("File:img/aide.png"), 0, 0, nbEtoiles.getWidth(), nbEtoiles.getHeight());
    	
    	if(c.estMonstre) {
    		if(c.getPlateau().getMonstre().getNbEtoiles() > 0) {
    			competence.setText("\"E\" pour activer");
    		}
    		for(int i = 0; i < c.getPlateau().getMonstre().getNbEtoiles(); i++) {
    			afficheEtoiles.drawImage(etoile, i*(nbEtoiles.getWidth()/3),0, nbEtoiles.getWidth()/3, nbEtoiles.getWidth()/3);
    		}
    	}
    	else {
    		if(c.getPlateau().getChasseur().getNbEtoiles() > 0) {
    			competence.setText("\"E\" pour activer");
    		}
    		for(int i = 0; i < c.getPlateau().getChasseur().getNbEtoiles(); i++) {
    			afficheEtoiles.drawImage(etoile, i*(nbEtoiles.getWidth()/3),0, nbEtoiles.getWidth()/3, nbEtoiles.getWidth()/3);
    		}
    	}
    }
    
    private void afficherControles() {
    	if(c.estMonstre) {
    		afficheControles.drawImage(new Image("File:data/ten-keysMv2.png"), 0, 0, controles.getWidth(), controles.getHeight());
    	}
    	else {
    		afficheControles.drawImage(new Image("File:data/ten-keysCv2.png"), 0, 0, controles.getWidth(), controles.getHeight());
    	}
    }
    
    /**
     * calcule la distance entre le monstre et le chasseur
     * @return la distance calculée
     */
    private int distanceMonstreChasseur() {
    		return (int) Math.sqrt(Math.pow(c.getPlateau().getMonstre().getPosition().getX() - c.getPlateau().getChasseur().getPosition().getX(), 2) + Math.pow(c.getPlateau().getMonstre().getPosition().getY() - c.getPlateau().getChasseur().getPosition().getY(), 2));
    }//Pythagore
    
    public void affichageFin(boolean gagne) {
    	Platform.runLater(new Runnable() {
			@Override
			public void run() {
				blocage.toFront();
		    	labelBlocage.toFront();
		    	blocage.setOpacity(0.7);
		    	labelBlocage.setOpacity(1);
		    	if(gagne) {
		    		labelBlocage.setText("Vous avez gagné !");
		    	} else {
		    		labelBlocage.setText("Vous avez perdu.");
		    	}
			}
    	});
    }
    
	public static AffichagePlateau getInstance() {
		return ap;
	}
}
