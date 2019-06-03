package affichage;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import plateau.Case;
import plateau.Position;
import reseau.Client;

public class AffichagePlateau {
	
    @FXML
    private Canvas grille;
    
    @FXML
    private Label tour;
    
    @FXML
    private Label tourDeQui;
    
    private GraphicsContext gc;
    private int tailleBaseImg;
    
    public void initialize() {
        assert grille != null : "fx:id=\"grille\" was not injected: check your FXML file 'AffichagePlateau.fxml'.";
        System.out.println("Initilisation...");
        Client c = Client.getInstance();
        
        gc = grille.getGraphicsContext2D();
        tailleBaseImg = (int) grille.getWidth() / c.getPlateau().getTaille();
        
        Image herbe = new Image("File:img/grass_tile_1.png", tailleBaseImg, tailleBaseImg, true, true); //taille dynamique en fonction de taille plateau Client
        Image etoile = new Image("File:img/etoile.png", tailleBaseImg, tailleBaseImg, true, true);
        Image longueVue = new Image("File:img/longue-vue.jpg", tailleBaseImg, tailleBaseImg, true, true);
        Image chasseur = new Image("File:img/Chasseur templerun/Idle__000.png", tailleBaseImg, tailleBaseImg, true, true);
        Image monstre = new Image("File:img/Monstre zombie/Idle (1).png",  tailleBaseImg, tailleBaseImg, true, true);
        Image img = herbe;
        
        //affichage tour
        tour.setFont(new Font("Arial", 28));
        tour.setText("Tour "+c.getPlateau().tour);
        tour.setAlignment(Pos.CENTER);
        
        //affichage tour de qui
        tourDeQui.setFont(new Font("Arial", 28));
        changerTourDeQui();
        tourDeQui.setAlignment(Pos.CENTER);

        
        gc.setFill(Color.YELLOW);
        gc.setFont(new Font(tailleBaseImg/3));
        
      for(int i = 0; i < c.getPlateau().getTaille(); i++) { //changer par taille plateau Client
        	for(int j = 0; j < c.getPlateau().getTaille(); j++) { //idem
        		gc.drawImage(herbe, i*herbe.getWidth(), j*herbe.getHeight());
        		int nbImg = 0;
        		
        		if(c.getPlateau().getCase(i, j).hasEtoile()) {
        			img = etoile;
        			afficherImg(img, getNbEntites(c.getPlateau().getCase(i, j), i, j), i, j, nbImg);
        			nbImg++;
        		}	
        		if(c.getPlateau().getCase(i, j).getLongueVue() > 0 && !c.estMonstre) {
        			img = longueVue;
        			afficherImg(img, getNbEntites(c.getPlateau().getCase(i, j), i, j), i, j, nbImg);
        			nbImg++;
        		}
        		if(c.getPlateau().getChasseur() != null) {
	        		if(c.getPlateau().getChasseur().getPosition().equals(new Position(i,j)) && !c.estMonstre) {
	        	    	img = chasseur;
	        	    	afficherImg(img, getNbEntites(c.getPlateau().getCase(i, j), i, j), i, j, nbImg);
	        	    	nbImg++;
	        	   	}
        		}
        		if(c.getPlateau().getMonstre() != null) {
	        		if(c.getPlateau().getMonstre().getPosition().equals(new Position(i,j)) && c.estMonstre) {
	        	   		img = monstre;
	        	   		afficherImg(img, getNbEntites(c.getPlateau().getCase(i, j), i, j), i, j, nbImg);
	        	   		nbImg++;
	        	   	}
        		}	
        	}
		}
    }
    
    private int getNbEntites(Case c, int x, int y) {
    	int nb = 0;
    	Client cl = Client.getInstance();
    	
    	if(c.hasEtoile()) {
    		nb++;
    	}
    	if(c.getLongueVue() > 0 && !cl.estMonstre) {
    		nb++;
    	}
    	if(cl.getPlateau().getMonstre() != null) {
	    	if(cl.getPlateau().getMonstre().getPosition().equals(new Position(x,y)) && cl.estMonstre) {
	    		nb++;
	    	}
    	}
    	if(cl.getPlateau().getChasseur() != null) {
	    	if(cl.getPlateau().getChasseur().getPosition().equals(new Position(x,y)) && !cl.estMonstre) {
	    		nb++;
	    	}
    	}
    	
    	return nb;
    }
    
    private void changerTourDeQui() {
    	Client c = Client.getInstance();
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

    
    private void afficherImg(Image img, int nbEntites, int x, int y, int nbImg) {
   		if(nbEntites > 1) {
			gc.drawImage(img, x*tailleBaseImg + nbImg*(tailleBaseImg/2), y*tailleBaseImg, tailleBaseImg/2, tailleBaseImg/2);
		}
		else {
			gc.drawImage(img, x*tailleBaseImg, y*tailleBaseImg);
		}
    }
}
