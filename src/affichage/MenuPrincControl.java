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
    
    @FXML
	private void mouseEntered(MouseEvent event) {
		((ImageView)event.getTarget()).setImage(ImagesConstantes.getInstance().CONTENEUR_HOVER);
	}

	@FXML
	private void mouseExited(MouseEvent event) {
		((ImageView)event.getTarget()).setImage(ImagesConstantes.getInstance().CONTENEUR);
	}
    
    @FXML
    void initialize() {
    	fond.setImage(ImagesConstantes.getInstance().FOND);
    	solo.setImage(ImagesConstantes.getInstance().CONTENEUR);
    	multi.setImage(ImagesConstantes.getInstance().CONTENEUR);
    	aide.setImage(ImagesConstantes.getInstance().CONTENEUR);
    	quitter.setImage(ImagesConstantes.getInstance().CONTENEUR);
    	imgtitre.setImage(ImagesConstantes.getInstance().CONTENEUR_TITRE);
    	
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
