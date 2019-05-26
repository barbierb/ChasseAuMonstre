package IHM;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class AffichagePlateau{
	
    @FXML
    private GridPane grille;
    
    public void initialize() {
        assert grille != null : "fx:id=\"grille\" was not injected: check your FXML file 'AffichagePlateau.fxml'.";
        System.out.println("Initilisation...");
        
        for(int i = 0; i < 10; i++) {
        	for(int j = 0; j < 10; j++) {
        		grille.add(creerAffichageImg(new Image("File:img/grass_tile_1.png"), 72), i, j);
        	}
        }
    }
    
    public ImageView creerAffichageImg(Image img, int taille) {
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(taille);
        imgView.setFitWidth(taille);
        
        return imgView;
    }
}
