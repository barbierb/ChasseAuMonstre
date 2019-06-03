package affichage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import reseau.Client;
import reseau.Serveur;

public class MenuMultiControl {
	
    @FXML
    private Pane screen;

	@FXML
	private ImageView fond;

	@FXML
	private ImageView heberger;

	@FXML
	private Text hebergertext;

	@FXML
	private ImageView rejoindre;

	@FXML
	private Text rejoindretext;

	@FXML
	private TextField nomserv;

	@FXML
	void initialize() {
		rejoindretext.setFill(Color.RED);
		hebergertext.setFill(Color.RED);
		rejoindretext.setMouseTransparent(true);
		hebergertext.setMouseTransparent(true);

		nomserv.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(newValue.length()>0) {
					hebergertext.setFill(Color.LIGHTGREEN);
					heberger.setDisable(false);
				} else {
					hebergertext.setFill(Color.RED);
					heberger.setDisable(true);
				}
			}	
		});
		heberger.setOnMouseClicked(e -> {
			Affichage.stage.setScene(Menus.getSceneHebergement(hebergertext.getText()));
		});
		heberger.setOnMouseEntered(e -> {
			heberger.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
		});
		heberger.setOnMouseExited(e -> {
			heberger.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
		});

		rejoindre.setOnMouseClicked(e -> {
			//Affichage.stage.setScene(getSceneJeu());
			Client.connecter("127.0.0.1", Serveur.PORT_JEU);
		});
		rejoindre.setOnMouseEntered(e -> {
			rejoindre.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
		});
		rejoindre.setOnMouseExited(e -> {
			rejoindre.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
		});

		screen.setOnKeyPressed(e -> {
			try {
				Affichage.getInstance().start(Affichage.stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}

}
