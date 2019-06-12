package affichage;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    	solo.setOnMouseEntered(e -> {
    		solo.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
    	});
    	solo.setOnMouseExited(e -> {
    		solo.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
    	});
    	
    	multi.setOnMouseClicked(e -> {
        	Affichage.stage.setScene(Menus.getSceneMulti());
    	});
    	multi.setOnMouseEntered(e -> {
    		multi.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
    	});
    	multi.setOnMouseExited(e -> {
    		multi.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
    	});
    	
    	aide.setOnMouseClicked(e -> {
        	Affichage.stage.setScene(Menus.getSceneAide());
    	});
    	aide.setOnMouseEntered(e -> {
    		aide.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
    	});
    	aide.setOnMouseExited(e -> {
    		aide.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
    	});
    	
    	quitter.setOnMouseClicked(e -> {
        	System.exit(0);
    	});
    	quitter.setOnMouseEntered(e -> {
    		quitter.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
    	});
    	quitter.setOnMouseExited(e -> {
    		quitter.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
    	});
    }
}
