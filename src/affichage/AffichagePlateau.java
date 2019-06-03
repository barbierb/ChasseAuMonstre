package affichage;

import java.util.Random;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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

    @FXML
    public void initialize() {
    	c = Client.getInstance();
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
        
        /*endTurn.setOnAction(e -> {
        	if(c.monTour) {
        		if(c.getPlateau().getMonstre() == null) {
        			
        		}
        	}
        });*/
        endTurn.setVisible(false);
        
        grille.setOnMouseClicked(e -> {
        	if(c.monTour && c.getPlateau().tour == 0) {
        		Position pmonstre = new Position((int)e.getX()/tailleBaseImg, (int)e.getY()/tailleBaseImg);
        		c.getPlateau().setMonstre(new Monstre(pmonstre));
        		Position pchass = new Position(pmonstre.getX(), pmonstre.getY());
        		while(pmonstre.equals(pchass)) {
        			pchass = new Position(new Random().nextInt(c.getPlateau().getTaille()), new Random().nextInt(c.getPlateau().getTaille()));
        		}
        		c.getPlateau().setChasseur(new Chasseur(pchass));
        		c.getPlateau().placerEtoiles();
        		c.getPlateau().tour++;
        		update();
        		c.envoyerPlateau();
        	}
        });
        
        update();
    }
    
    public void update() {
    	//affichage tour
    	tour.setText("Tour "+c.getPlateau().tour);
    	
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
    	if(c.getPlateau().tour % 2 == 0) {
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


}
