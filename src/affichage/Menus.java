package affichage;

import java.io.IOException;

import org.hamcrest.core.Is;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import reseau.Client;
import reseau.Serveur;

public class Menus {
	private static boolean isMonstre;
	protected static Scene getSceneSolo() {
		BorderPane root = new BorderPane();
		background(root, Color.GRAY);
		
		
		VBox top = new VBox();
		root.setTop(top);
		
		HBox top0 = new HBox();
		top.getChildren().add(top0);
		top0.getChildren().add(boutonRetour());
		
		HBox top1 = new HBox();
		top.getChildren().add(top1);
		top1.setAlignment(Pos.CENTER);
		
		Label solo = new Label("Partie contre l'ordinateur");
		solo.setFont(getFontTitre());
		top1.getChildren().add(solo);
		
		HBox center = new HBox();
		root.setCenter(center);
		center.setAlignment(Pos.CENTER);
		
		VBox centergauche = new VBox();
		centergauche.setAlignment(Pos.CENTER);
		center.getChildren().add(centergauche);
		CheckBox monstre = new CheckBox("Jouer en tant que monstre");
		CheckBox chasseur = new CheckBox("Jouer en tant que chasseur");
		centergauche.getChildren().addAll(monstre,chasseur);
		monstre.addEventHandler(ActionEvent.ACTION, e -> {
			isMonstre = true;
		});
		chasseur.addEventHandler(ActionEvent.ACTION, e -> {
			isMonstre = false;
		});
		
		HBox bottom = new HBox();
		bottom.setAlignment(Pos.CENTER);
		Button lancer = new Button("Lancer la partie");
		lancer.addEventHandler(ActionEvent.ACTION, e -> {
			// lancer le jeu avec la bonne config
			Affichage.stage.setScene(getSceneJeu());
		});
		
		return new Scene(root,1280,720);
	}
	
	
	protected static Scene getSceneControls() {
		BorderPane root = new BorderPane();
		VBox vboxtop = new VBox();
		HBox top = new HBox();
		top.setAlignment(Pos.CENTER);

		vboxtop.getChildren().add(boutonRetour());
		vboxtop.getChildren().add(top);
		root.setTop(vboxtop);
		
		background(root,Color.GRAY);
		Label titre = new Label("Controls");
		titre.setFont(getFontTitre());
		top.getChildren().add(titre);
		
		HBox hboxcenter = new HBox();
		hboxcenter.setAlignment(Pos.CENTER);
		VBox center1 = new VBox();
		center1.setAlignment(Pos.CENTER);
		hboxcenter.getChildren().add(center1);
		
		ImageView touchesM = new ImageView(new Image("file:data/ten-keysv1.png"));
		touchesM.setFitWidth(420);
		touchesM.setFitHeight(420);
		Label monstre = new Label("Monstre");
		monstre.setFont(getFontTitre());
		center1.getChildren().add(monstre);
		center1.getChildren().add(touchesM);
		
		VBox center2 = new VBox();
		center2.setAlignment(Pos.CENTER);
		ImageView touchesC = new ImageView(new Image("file:data/ten-keysv12.png"));
		touchesC.setFitWidth(420);
		touchesC.setFitHeight(420);
		Label chasseur = new Label("Chasseur");
		chasseur.setFont(getFontTitre());
		center2.getChildren().add(chasseur);
		center2.getChildren().add(touchesC);
		hboxcenter.getChildren().add(center2);

		root.setCenter(hboxcenter);
		
		return new Scene(root,1280,720);
	}

	private static String nomServeur="";
	
	protected static Scene getSceneMulti() {
		BorderPane root = new BorderPane();
		VBox top = new VBox();
		HBox top0 = new HBox();
		HBox top1 = new HBox();
		HBox top2 = new HBox();
		HBox top3 = new HBox();
		
		root.setTop(top);

		top.getChildren().add(boutonRetour());
		top.getChildren().add(top0);
		top.getChildren().add(top1);
		top.getChildren().add(top2);
		top.getChildren().add(top3);
		
		
		background(root,Color.GRAY);
		
		top0.setAlignment(Pos.CENTER);
		top1.setAlignment(Pos.CENTER);
		top2.setAlignment(Pos.CENTER);
		top3.setAlignment(Pos.CENTER);
		
		Label titre =  new Label("Multijoueur");
		titre.setFont(getFontTitre(45));
		top0.getChildren().add(titre);
		
		Label hosting =  new Label("Heberger un serveur");
		
		hosting.setFont(getFontTitre());
		top1.getChildren().add(hosting);
		
		Button hebergement = new Button("Héberger ma partie");
		top3.getChildren().add(hebergement);
		hebergement.addEventHandler(ActionEvent.ACTION, e -> {
			Affichage.stage.setScene(getSceneHebergement(nomServeur));
		});
		
		TextField tf = new TextField();
		top2.getChildren().add(tf);
		tf.setPromptText("Nom du serveur");
		tf.addEventHandler(KeyEvent.KEY_TYPED, e -> {
			nomServeur+=e.getCharacter();
		});
		
		BorderPane center = new BorderPane();
		ListView<String> listServeurs = new ListView<String>(); // aucune idée de comment gérer ça
		center.setCenter(listServeurs); // c'est moche c'est pas important
		center.setPadding(new Insets(30,300,30,300));
		
		HBox centreHaut = new HBox();
		centreHaut.setAlignment(Pos.CENTER);
		Label listeServ = new Label("Liste des serveurs");
		listeServ.setFont(getFontTitre());
		centreHaut.getChildren().add(listeServ);
		center.setTop(centreHaut);
		
		Button join = new Button("Rejoindre ce serveur");
		HBox bot = new HBox();
		center.setBottom(bot);
		bot.setAlignment(Pos.CENTER);
		bot.getChildren().add(join);
		join.addEventHandler(ActionEvent.ACTION, e -> {
			// rejoindre ce serveur = listServeurs.getSelectionModel().getSelectedItem();
			// Affichage.stage.setScene(getSceneJeu());
	    	Client.connecter("127.0.0.1", Serveur.PORT_JEU);
		});
		
		root.setCenter(center);

		return new Scene(root,1280,720);
	}
	

	public static Scene getSceneJeu() {
		Parent root = null;
		try {
			root = FXMLLoader.load(Affichage.class.getResource("AffichagePlateau.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(root,1280,720);
	}
	
	private static Scene getSceneHebergement(String nom) {
		Serveur.demarrerServeur(nom, System.getProperty("user.name"));
		BorderPane root = new BorderPane();
		VBox center = new VBox();
		root.setCenter(center);
		center.setAlignment(Pos.CENTER);
		Label wait = new Label("En attente d'un opposant...");
		center.getChildren().add(wait);
		Button exit = new Button("Exit");
		Button cancel = new Button("Cancel");
		HBox bot = new HBox();
		root.setBottom(bot);
		bot.setAlignment(Pos.CENTER);
		bot.getChildren().add(cancel);
		bot.getChildren().add(exit);
		
		exit.addEventHandler(ActionEvent.ACTION, e -> {
			System.exit(0);
		});
		cancel.addEventHandler(ActionEvent.ACTION, e -> {
			Affichage.stage.setScene(getSceneMulti());
		});
		
		return new Scene(root,1280,720);
	}
		
	public static void listerFontsConsole() {
		for(String s: Font.getFontNames()) {
			System.out.println(s);
		}
	}
	
	private static void background(Region r,Color c ) {
		r.setBackground(new Background(new BackgroundFill(
				c, 
				new CornerRadii(0), 
				new Insets(0)))
				);
	}
	
	private static ImageView boutonRetour() {
		ImageView retour = new ImageView(new Image("file:data/robot.png"));
		retour.setFitWidth(96);
		retour.setFitHeight(60.6);
		
		retour.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			try {
				Affichage.getInstance().start(Affichage.stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return retour;
	}

	private static Font getFontTitre(int i)	{
		return new Font("LobsterTwo",i);
	}
	private static Font getFontTitre() {
		return getFontTitre(35);
	}


}
