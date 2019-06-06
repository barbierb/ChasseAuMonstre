package affichage;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MenuPrincControl {

    @FXML
    private ImageView solo;
    @FXML
    private Text solotext;

    @FXML
    private ImageView multi;
    @FXML
    private Text multitext;

    @FXML
    private ImageView controles;
    @FXML
    private Text controlestext;
    
    @FXML
    private ImageView fond;

    @FXML
    private ImageView quitter;
    @FXML
    private Text quittertext;
    
    @FXML
    void initialize() {
    	
    	solo.setOnMouseClicked(e -> {
        	Affichage.stage.setScene(Menus.getSceneSolo());
    	});
    	solo.setOnMouseEntered(e -> {
    		solo.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
    	});
    	solo.setOnMouseExited(e -> {
    		solo.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
    	});
    	solotext.setMouseTransparent(true);
    	
    	
    	
    	multi.setOnMouseClicked(e -> {
        	Affichage.stage.setScene(Menus.getSceneMulti());
    	});
    	multi.setOnMouseEntered(e -> {
    		multi.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
    	});
    	multi.setOnMouseExited(e -> {
    		multi.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
    	});
    	multitext.setMouseTransparent(true);
    	
    	
    	
    	
    	controles.setOnMouseClicked(e -> {
        	Affichage.stage.setScene(Menus.getSceneControls());
    	});
    	controles.setOnMouseEntered(e -> {
    		controles.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
    	});
    	controles.setOnMouseExited(e -> {
    		controles.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
    	});
    	controlestext.setMouseTransparent(true);
    	
    	
    	
    	quitter.setOnMouseClicked(e -> {
        	System.exit(0);
    	});
    	quitter.setOnMouseEntered(e -> {
    		quitter.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
    	});
    	quitter.setOnMouseExited(e -> {
    		quitter.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
    	});
    	quittertext.setMouseTransparent(true);
    }
}
