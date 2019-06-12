package affichage;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
public class MenuPrincControl {
	
    @FXML
    private ImageView imgtitre;

    @FXML
    private ImageView fond;

    @FXML
    private ImageView solo;

    @FXML
    private ImageView multi;

    @FXML
    private ImageView aide;

    @FXML
    private ImageView quitter;
    private ImagesConstantes img = ImagesConstantes.getInstance();
    @FXML
	private void mouseEntered(MouseEvent event) {
		((ImageView)event.getTarget()).setImage(img.CONTENEUR_HOVER);
	}

	@FXML
	private void mouseExited(MouseEvent event) {
		((ImageView)event.getTarget()).setImage(img.CONTENEUR);
	}
    
    @FXML
    void initialize() {
    	// OBLIGATOIRE POUR L'EXPORT .JAR
    	fond.setImage(img.FOND);
    	solo.setImage(img.CONTENEUR);
    	multi.setImage(img.CONTENEUR);
    	aide.setImage(img.CONTENEUR);
    	quitter.setImage(img.CONTENEUR);
    	imgtitre.setImage(img.CONTENEUR_TITRE);
    	// 
    	
    	solo.setOnMouseClicked(e -> {
        	Affichage.stage.setScene(Menus.getSceneSolo());
    	});
    	
    	multi.setOnMouseClicked(e -> {
        	Affichage.stage.setScene(Menus.getSceneMulti());
    	});
    	
    	
    	aide.setOnMouseClicked(e -> {
        	Affichage.stage.setScene(Menus.getSceneAide());
    	});
    
    	
    	quitter.setOnMouseClicked(e -> {
        	System.exit(0);
    	});

    }
}
