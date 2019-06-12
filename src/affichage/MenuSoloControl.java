package affichage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import reseau.Client;
import reseau.Serveur;

public class MenuSoloControl {

	@FXML
	private ImageView quinze;

	@FXML
	private Text tab10;

	@FXML
	private ImageView cinq;

	@FXML
	private Pane screen;

	@FXML
	private Text tab15;

	@FXML
	private ImageView chasseur;

	@FXML
	private Text rejoindretext;

	@FXML
	private ImageView fond;

	@FXML
	private Text hebergertext;

	@FXML
	private ImageView dix;

	@FXML
	private ImageView monstre;

	@FXML
	private Text tab5;

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
	public void initialize() {
		monstre.setOnMouseClicked(e -> {
			Serveur.demarrerServeur("monstrelocal", System.getProperty("user.name"), true);
			Affichage.stage.setScene(Menus.getSceneJeu());

		});
		

		chasseur.setOnMouseClicked(e -> {
			Serveur.demarrerServeur("chasseurlocal", System.getProperty("user.name"), true);
			//Affichage.stage.setScene(Menus.getSceneJeu());
		});
		
		quitter.setOnMouseClicked(e->{
			if(Client.brdTask != null) Client.brdTask.cancel();
			try {
				Affichage.getInstance().start(Affichage.stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
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