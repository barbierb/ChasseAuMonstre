package affichage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import personnage.Personnage;
import personnage.chasseur.Chasseur;
import personnage.monstre.Monstre;
import plateau.Case;
import plateau.Position;
import testsPlateau.testAffichagePlateau;

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
    private Image chasseur;
    private Image monstre;
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
    
    public void initialize() throws FileNotFoundException {
        assert grille != null : "fx:id=\"grille\" was not injected: check your FXML file 'AffichagePlateau.fxml'.";
        System.out.println("Initilisation...");
        
        gc = grille.getGraphicsContext2D();
        tailleBaseImg = (int) grille.getWidth() / testAffichagePlateau.p.getTaille();
        
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
        chasseur = new Image("File:img/Chasseur templerun/Idle__000.png", tailleBaseImg, tailleBaseImg, true, true);
        monstre = new Image("File:img/Monstre zombie/Idle (1).png",  tailleBaseImg, tailleBaseImg, true, true);
        
        update();
    }
    
    private void afficherAttente() {
    	blocage.setOpacity(0.7);
    	labelBlocage.setOpacity(1);
    	labelBlocage.setText("L'ENNEMI JOUE...");
    }
    
    public void update() {
    	blocage.setOpacity(0);
    	labelBlocage.setOpacity(0);
    	
    	//affichage tour
    	tour.setText("Tour "+testAffichagePlateau.p.getTour());
    	
    	//affichage etoiles que le joueur a
        afficherEtoilesJoueur();
        
        if(distanceMonstreChasseur() < 3 && testAffichagePlateau.estMonstre) {
        	if(distanceMonstreChasseur() == 1) {
        		attention.setText("Attention ! Le chasseur est a "+distanceMonstreChasseur()+" case");
        	}
        	else {
        		attention.setText("Attention ! Le chasseur est a "+distanceMonstreChasseur()+" cases");
        	}
        }
    	
        for(int i = 0; i < testAffichagePlateau.p.getTaille(); i++) { //changer par taille plateau Client
        	for(int j = 0; j < testAffichagePlateau.p.getTaille(); j++) { //idem
        		if(testAffichagePlateau.p.getCase(i, j).getTourPassage() > -1 && testAffichagePlateau.estMonstre) {
        			gc.drawImage(bleEcrase, i*ble.getWidth(), j*ble.getHeight());
        		}
        		else {
        			gc.drawImage(ble, i*ble.getWidth(), j*ble.getHeight());
        		}
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
        if(testAffichagePlateau.gagnant != null) {
        	affichageFin(testAffichagePlateau.gagnant);
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
    
    /**
     * calcule la distance entre le monstre et le chasseur
     * @return la distance calculée
     */
    private int distanceMonstreChasseur() {
    		return (int) Math.sqrt(Math.pow(testAffichagePlateau.p.monstre.getPosition().getX() - testAffichagePlateau.p.chasseur.getPosition().getX(), 2) + Math.pow(testAffichagePlateau.p.monstre.getPosition().getY() - testAffichagePlateau.p.chasseur.getPosition().getY(), 2));
    }//Pythagore
    
    private void affichageFin(Personnage gagnant) {
    	blocage.setOpacity(0.7);
    	labelBlocage.setOpacity(1);
    	if(testAffichagePlateau.estMonstre) {
    		if(gagnant instanceof Monstre) {
    			labelBlocage.setText("Vous avez gagné !!");
    		}
    		else {
    			labelBlocage.setText("Vous avez Perdu");
    		}
    	}
    	else {
    		if(gagnant instanceof Chasseur) {
    			labelBlocage.setText("Vous avez gagné !!");
    		}
    		else {
    			labelBlocage.setText("Vous avez perdu");
    		}
    	}
    }
}
