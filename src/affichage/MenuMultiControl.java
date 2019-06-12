package affichage;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import reseau.Client;
import reseau.Serveur;
/**
 * Menu de sélection du serveur ou de choix du nom du serveur que l'on veut héberger
 * @author Sylvain
 *
 */
public class MenuMultiControl {

	public static final Image serv = new Image(Affichage.chargerImg("../img/serv.png"), 400, 80, true, true);
	public static final Image serv_selected = new Image(Affichage.chargerImg("../img/serv_selected.png"), 400, 80, true, true);
    @FXML
    private Pane screen;

    @FXML
    private ImageView fond;

    @FXML
    private VBox listeserv;

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
    private ImageView quitter;
    
    @FXML
    private ImageView titre;

	private ArrayList<PingServeur> serveurs;

	public static MenuMultiControl instance;
	public static String ip = "127.0.0.1";
	
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
	void initialize() {
		heberger.setImage(img.CONTENEUR);
		rejoindre.setImage(img.CONTENEUR);
		quitter.setImage(img.CONTENEUR);
		fond.setImage(img.FOND);
		titre.setImage(img.CONTENEUR_TITRE);
		
		instance = this;
		serveurs = new ArrayList<PingServeur>();
		rejoindretext.setFill(Color.BLACK);
		hebergertext.setFill(Color.BLACK);
		rejoindretext.setMouseTransparent(true);
		hebergertext.setMouseTransparent(true);
		
		hebergertext.setDisable(true);
		rejoindre.setDisable(true);

		nomserv.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(newValue.length()>3 && newValue.length()<12 && newValue.matches("[a-zA-Z0-9_]*")) {
					hebergertext.setFill(Color.LIGHTGREEN);
					heberger.setDisable(false);
				} else {
					hebergertext.setFill(Color.BLACK);
					heberger.setDisable(true);
				}
			}	
		});
		
		heberger.setOnMouseClicked(e -> {
			Affichage.stage.setScene(Menus.getSceneHebergement(nomserv.getText()));
		});
		rejoindre.setOnMouseClicked(e -> {
			Client.connecter(ip, Serveur.PORT_JEU);
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
		Client.pingServeurs();
		
	}

	public static class PingServeur extends StackPane {
		public String ip;
		public String nom;
		public String user;
		private ImageView img;
		public PingServeur(String ip, String nom, String user) {
			this.ip = ip;
			this.nom = nom;
			this.user = user;
			this.img = new ImageView(serv);
			this.img.setOnMouseClicked(e->{
				for(PingServeur ps : MenuMultiControl.instance.serveurs) {
					ps.img.setImage(serv);
				}
				this.img.setImage(serv_selected);
				MenuMultiControl.instance.rejoindretext.setFill(Color.LIGHTGREEN);
				MenuMultiControl.instance.rejoindre.setDisable(false);
				MenuMultiControl.ip = ip;
			});
			Text t = new Text(nom+" - "+user);
			t.setFont(new Font("NewsgeekSerif", 18));
			t.setMouseTransparent(true);
			getChildren().addAll(img, t);
		}

	}

	public void addServeur(String nom, String user, String ip) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for(PingServeur ps : serveurs) {
					if(ps.ip.equals(ip)) {
						return;
					}
				}
				PingServeur ps = new PingServeur(ip,nom,user);
				serveurs.add(ps);
				listeserv.getChildren().add(ps);
			}

		});
	}

}
