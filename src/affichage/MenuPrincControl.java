package affichage;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class MenuPrincControl {

    @FXML
    private ImageView solo;

    @FXML
    private ImageView multi;

    @FXML
    private ImageView controles;

    @FXML
    private ImageView quitter;
    
    @FXML
    void initialize() {
    	solo.setOnMouseClicked(e -> {
        	Affichage.stage.setScene(Menus.getSceneSolo());
    	});
    	multi.setOnMouseClicked(e -> {
        	Affichage.stage.setScene(Menus.getSceneMulti());
    	});
    	controles.setOnMouseClicked(e -> {
        	Affichage.stage.setScene(Menus.getSceneControls());
    	});
    	quitter.setOnMouseClicked(e -> {
        	System.exit(0);
    	});
    }

}
