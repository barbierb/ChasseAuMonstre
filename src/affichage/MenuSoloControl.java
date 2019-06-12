package affichage;

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

	public static Integer taille = 10;
	

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
		
		cinq.setOnMouseClicked(e -> {
			taille = 5;
			cinq.setImage(new Image(Affichage.chargerImg("../conteneur_fonce.png")));
			dix.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
			quinze.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
		});
		dix.setOnMouseClicked(e -> {
			taille = 10;
			cinq.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
			dix.setImage(new Image(Affichage.chargerImg("../conteneur_fonce.png")));
			quinze.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
		});
		quinze.setOnMouseClicked(e -> {
			taille = 15;
			cinq.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
			dix.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
			quinze.setImage(new Image(Affichage.chargerImg("../conteneur_fonce.png")));
		});
		
		cinq.setOnMouseEntered(e -> {
			if(taille!=5) cinq.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
			
		});
		cinq.setOnMouseExited(e -> {
			if(taille!=5) cinq.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
		});
		
		dix.setOnMouseEntered(e -> {
			if(taille!=10) dix.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
			
		});
		dix.setOnMouseExited(e -> {
			if(taille!=10) dix.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
		});
		
		quinze.setOnMouseEntered(e -> {
			if(taille!=15) quinze.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
		});
		quinze.setOnMouseExited(e -> {
			if(taille!=15) quinze.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
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