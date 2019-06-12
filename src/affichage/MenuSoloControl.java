package affichage;

import javafx.fxml.FXML;
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

	public static Integer taille = 10;
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
	public void initialize() {
		monstre.setImage(img.CONTENEUR);
		chasseur.setImage(img.CONTENEUR);
		cinq.setImage(img.CONTENEUR);
		/**
		 * Taille de plateau de base
		 */
		dix.setImage(img.CONTENEUR_FONCE);
		quinze.setImage(img.CONTENEUR);
		fond.setImage(img.FOND);
		
		monstre.setOnMouseClicked(e -> {
			Serveur.demarrerServeur("monstrelocal", System.getProperty("user.name"), true);
			Affichage.stage.setScene(Menus.getSceneJeu());
		});
		
		
		
		cinq.setOnMouseClicked(e -> {
			taille = 5;
			cinq.setImage(img.CONTENEUR_FONCE);
			dix.setImage(img.CONTENEUR);
			quinze.setImage(img.CONTENEUR);
		});
		dix.setOnMouseClicked(e -> {
			taille = 10;
			cinq.setImage(img.CONTENEUR);
			dix.setImage(img.CONTENEUR_FONCE);
			quinze.setImage(img.CONTENEUR);
		});
		quinze.setOnMouseClicked(e -> {
			taille = 15;
			cinq.setImage(img.CONTENEUR);
			dix.setImage(img.CONTENEUR);
			quinze.setImage(img.CONTENEUR_FONCE);
		});
		
		cinq.setOnMouseEntered(e -> {
			if(taille!=5) cinq.setImage(img.CONTENEUR_HOVER);
			
		});
		cinq.setOnMouseExited(e -> {
			if(taille!=5) cinq.setImage(img.CONTENEUR);
		});
		
		dix.setOnMouseEntered(e -> {
			if(taille!=10) dix.setImage(img.CONTENEUR_HOVER);
			
		});
		dix.setOnMouseExited(e -> {
			if(taille!=10) dix.setImage(img.CONTENEUR);
		});
		
		quinze.setOnMouseEntered(e -> {
			if(taille!=15) quinze.setImage(img.CONTENEUR_HOVER);
		});
		quinze.setOnMouseExited(e -> {
			if(taille!=15) quinze.setImage(img.CONTENEUR);
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