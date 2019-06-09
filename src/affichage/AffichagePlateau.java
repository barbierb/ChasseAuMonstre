package affichage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import plateau.Case;
import plateau.Position;
import testsPlateau.testAffichagePlateau;

public class AffichagePlateau{
	
    @FXML
    private Canvas grille;
    private GraphicsContext gc;
    private int tailleBaseImg;
   /* @FXML
    private Label tourDeQui;*/
    @FXML
    private Canvas nbEtoiles;
    private GraphicsContext afficheEtoiles;
    @FXML
    private Label tour;
    
    private Image ble;
    private Image etoile;
    private Image longueVue;
    private Image chasseur;
    private Image monstre;
    private Image img;
    
    @FXML
    private Canvas controles;
    private GraphicsContext afficheControles;
    
    @FXML
    private Pane attente;
    @FXML
    private Label labelAttente;
    
    @FXML
    private Label competence;
    
    private Font police;
    
    public void initialize() throws FileNotFoundException {
        assert grille != null : "fx:id=\"grille\" was not injected: check your FXML file 'AffichagePlateau.fxml'.";
        System.out.println("Initilisation...");
        
        gc = grille.getGraphicsContext2D();
        tailleBaseImg = (int) grille.getWidth() / testAffichagePlateau.p.getTaille();
        
        //preparation affichage étoiles
        afficheEtoiles = nbEtoiles.getGraphicsContext2D();
        //afficheEtoiles.drawImage(new Image("File:img/conteneur.png",  nbEtoiles.getWidth(), nbEtoiles.getHeight(), true, true), 0, 0);
        
        //taille et couleur de l'écriture dans les cases
        gc.setFill(Color.YELLOW);
        gc.setFont(new Font(tailleBaseImg/3));
        
        //chargement de la police d'écriture
        police = Font.loadFont(new FileInputStream(new File("data/NewsgeekSerif.ttf")), 22);
        
        //paramètres tour
        tour.setFont(police);
        tour.setAlignment(Pos.CENTER);
        
       /* //paramètres tourDeQui
        tourDeQui.setFont(police);
        tourDeQui.setAlignment(Pos.CENTER);*/
        
        //paramètres competence
        competence.setFont(police);
        competence.setAlignment(Pos.CENTER);
        
        //affichage controles
        afficheControles = controles.getGraphicsContext2D();
        afficherControles();
        
        ble = new Image("File:img/grass_tile_1.png", tailleBaseImg, tailleBaseImg, true, true); //taille dynamique en fonction de taille plateau Client
        etoile = new Image("File:img/etoile.png", tailleBaseImg, tailleBaseImg, true, true);
        longueVue = new Image("File:img/longue-vue.jpg", tailleBaseImg, tailleBaseImg, true, true);
        chasseur = new Image("File:img/Chasseur templerun/Idle__000.png", tailleBaseImg, tailleBaseImg, true, true);
        monstre = new Image("File:img/Monstre zombie/Idle (1).png",  tailleBaseImg, tailleBaseImg, true, true);
        
        update();
    }
    
    private void afficherAttente() {
    	attente.setOpacity(0.7);
    	labelAttente.setOpacity(1);
    }
    
    public void update() {
    	attente.setOpacity(0);
    	labelAttente.setOpacity(0);
    	
    	//affichage tour
    	tour.setText("Tour "+testAffichagePlateau.p.getTour());
    	
    	/*//affichage tour de qui
    	changerTourDeQui();*/
    	
    	//affichage etoiles que le joueur a
        afficherEtoilesJoueur();
        
        //affichage longues-vues que le chasseur a si le joueur est un chasseur
        if(!testAffichagePlateau.estMonstre) {
        	afficherLongueVues();
        }
    	
        for(int i = 0; i < testAffichagePlateau.p.getTaille(); i++) { //changer par taille plateau Client
        	for(int j = 0; j < testAffichagePlateau.p.getTaille(); j++) { //idem
        		gc.drawImage(ble, i*ble.getWidth(), j*ble.getHeight());
        		int nbImg = 0;
        		
        		if(testAffichagePlateau.p.getCase(i, j).hasEtoile()) {
        			img = etoile;
        			afficherImg(img, getNbEntites(testAffichagePlateau.p.getCase(i, j), i, j), i, j, nbImg, gc);
        			nbImg++;
        		}	
        		if(testAffichagePlateau.p.getCase(i, j).getLongueVue() > 0 && !testAffichagePlateau.estMonstre) {
        			img = longueVue;
        			afficherImg(img, getNbEntites(testAffichagePlateau.p.getCase(i, j), i, j), i, j, nbImg, gc);
        			nbImg++;
        			
        			if(testAffichagePlateau.p.getCase(i, j).getTourPassage() > -1) {
        				gc.fillText(""+testAffichagePlateau.p.getCase(i, j).getTourPassage(), 5*tailleBaseImg + tailleBaseImg*3/8, 5*tailleBaseImg + tailleBaseImg*5/6);
        			}
        		}
        		if(testAffichagePlateau.p.chasseur != null) {
	        		if(testAffichagePlateau.p.chasseur.getPosition().equals(new Position(i,j)) && !testAffichagePlateau.estMonstre) {
	        	    	img = chasseur;
	        	    	afficherImg(img, getNbEntites(testAffichagePlateau.p.getCase(i, j), i, j), i, j, nbImg, gc);
	        	    	nbImg++;
	        	   	}
        		}
        		if(testAffichagePlateau.p.monstre != null) {
	        		if(testAffichagePlateau.p.monstre.getPosition().equals(new Position(i,j)) && testAffichagePlateau.estMonstre) {
	        	   		img = monstre;
	        	   		afficherImg(img, getNbEntites(testAffichagePlateau.p.getCase(i, j), i, j), i, j, nbImg, gc);
	        	   		nbImg++;
	        	   	}
        		}	
        	}
		}
        
        if(!testAffichagePlateau.monTour) {
        	afficherAttente();
        }
    }
    
    private int getNbEntites(Case c, int x, int y) {
    	int nb = 0;
    	
    	if(c.hasEtoile()) {
    		nb++;
    	}
    	if(c.getLongueVue() > 0 && !testAffichagePlateau.estMonstre) {
    		nb++;
    	}
    	if(testAffichagePlateau.p.monstre != null) {
	    	if(testAffichagePlateau.p.monstre.getPosition().equals(new Position(x,y)) && testAffichagePlateau.estMonstre) {
	    		nb++;
	    	}
    	}
    	if(testAffichagePlateau.p.chasseur != null) {
	    	if(testAffichagePlateau.p.chasseur.getPosition().equals(new Position(x,y)) && !testAffichagePlateau.estMonstre) {
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
    
    /*private void changerTourDeQui() {
    	if(testAffichagePlateau.p.getTour() % 2 == 0) {
    		if(testAffichagePlateau.estMonstre) {
    			tourDeQui.setText("C'est ton tour !");
    		}
    		else {
    			tourDeQui.setText("Ce n'est pas ton tour");
    		}
    	}
    	else {
    		if(!testAffichagePlateau.estMonstre) {
    			tourDeQui.setText("C'est ton tour !");
    		}
    		else {
    			tourDeQui.setText("Ce n'est pas ton tour");
    		}
    	}
    }*/
    
    private void afficherEtoilesJoueur() {
    	if(testAffichagePlateau.estMonstre) {
    		if(testAffichagePlateau.p.getMonstre().getNbEtoiles() > 0) {
    			competence.setText("\"E\" pour activer");
    		}
    		for(int i = 0; i < testAffichagePlateau.p.getMonstre().getNbEtoiles(); i++) {
    			afficheEtoiles.drawImage(etoile, i*(nbEtoiles.getWidth()/3),0, nbEtoiles.getWidth()/3, nbEtoiles.getWidth()/3);
    		}
    	}
    	else {
    		if(testAffichagePlateau.p.getChasseur().getNbEtoiles() > 0) {
    			competence.setText("\"E\" pour activer");
    		}
    		for(int i = 0; i < testAffichagePlateau.p.getChasseur().getNbEtoiles(); i++) {
    			afficheEtoiles.drawImage(etoile, i*(nbEtoiles.getWidth()/3),0, nbEtoiles.getWidth()/3, nbEtoiles.getWidth()/3);
    		}
    	}
    }
    
    private void afficherControles() {
    	if(testAffichagePlateau.estMonstre) {
    		afficheControles.drawImage(new Image("File:data/ten-keysMv2.png"), 0, 0, controles.getWidth(), controles.getHeight());
    	}
    	else {
    		afficheControles.drawImage(new Image("File:data/ten-keysCv2.png"), 0, 0, controles.getWidth(), controles.getHeight());
    	}
    }
    
    private void afficherLongueVues() {
    	
    }
}
