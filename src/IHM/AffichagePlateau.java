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
        
        /*ImageView herbe = new ImageView(new Image("/chasseaumonstre/img/grass_tile_1.png"));
        grille.add(herbe, 0, 0);*/
    }
}
