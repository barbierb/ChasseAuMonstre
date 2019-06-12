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
		((ImageView)event.getTarget()).setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
	}

	@FXML
	private void mouseExited(MouseEvent event) {
		((ImageView)event.getTarget()).setImage(new Image(Affichage.chargerImg("../conteneur.png")));
	}
    
    @FXML
    void initialize() {
    	fond.setImage(ImagesConstantes.getInstance().FOND);
    	solo.setImage(new Image(getClass().getClassLoader().getResourceAsStream("img/conteneur.png")));
    	multi.setImage(new Image(getClass().getClassLoader().getResourceAsStream("img/conteneur.png")));
    	aide.setImage(new Image(getClass().getClassLoader().getResourceAsStream("img/conteneur.png")));
    	quitter.setImage(new Image(getClass().getClassLoader().getResourceAsStream("img/conteneur.png")));
    	imgtitre.setImage(new Image(getClass().getClassLoader().getResourceAsStream("img/conteneur_titre.png")));
    	
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
