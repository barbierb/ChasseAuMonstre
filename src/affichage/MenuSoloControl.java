package affichage;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import reseau.Client;
import reseau.Serveur;

public class MenuSoloControl {

    @FXML
    private Pane screen;

    @FXML
    private ImageView fond;

    @FXML
    private VBox listeserv;

    @FXML
    private ImageView monstre;

    @FXML
    private Text hebergertext;

    @FXML
    private ImageView chasseur;

    @FXML
    private Text rejoindretext;

    @FXML
    private ImageView quitter;

    @FXML
    public void initialize() {
    	monstre.setOnMouseClicked(e -> {
    		Serveur.demarrerServeur("monstrelocal", System.getProperty("user.name"), true);
    		Affichage.stage.setScene(Menus.getSceneJeu());
    		
		});
    	monstre.setOnMouseEntered(e -> {
    		monstre.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
		});
    	monstre.setOnMouseExited(e -> {
    		monstre.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
		});

    	chasseur.setOnMouseClicked(e -> {
    		Serveur.demarrerServeur("chasseurlocal", System.getProperty("user.name"), true);
    		//Affichage.stage.setScene(Menus.getSceneJeu());
		});
    	chasseur.setOnMouseEntered(e -> {
    		chasseur.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
		});
    	chasseur.setOnMouseExited(e -> {
    		chasseur.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
		});
		quitter.setOnMouseClicked(e->{
			if(Client.brdTask != null) Client.brdTask.cancel();
			try {
				Affichage.getInstance().start(Affichage.stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		quitter.setOnMouseEntered(e -> {
			quitter.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
		});
		quitter.setOnMouseExited(e -> {
			quitter.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
		});

		screen.setOnKeyPressed(e -> {
			if(!e.getCode().equals(KeyCode.ESCAPE)) return;
			if(Client.brdTask != null) Client.brdTask.cancel();
			try {
				Affichage.getInstance().start(Affichage.stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
    }
}