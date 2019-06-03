package affichage;

import javafx.fxml.FXML;
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
    	solotext.setMouseTransparent(true);
    	
    	multi.setOnMouseClicked(e -> {
        	Affichage.stage.setScene(Menus.getSceneMulti());
    	});
    	multitext.setMouseTransparent(true);
    	
    	controles.setOnMouseClicked(e -> {
        	Affichage.stage.setScene(Menus.getSceneControls());
    	});
    	controlestext.setMouseTransparent(true);
    	
    	quitter.setOnMouseClicked(e -> {
        	System.exit(0);
    	});
    	quittertext.setMouseTransparent(true);
    }

}
