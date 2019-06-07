package affichage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import reseau.Serveur;

public class Menus {
	
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
			// lancer le jeu en començant comme un monstre
		});
		chasseur.addEventHandler(ActionEvent.ACTION, e -> {
			// lancer le jeu en commençant comme un chasseur
		});
		
		
		
		return new Scene(root);
	}
	
	
	protected static Scene getSceneAide() {
		Parent root = null;
		try {
			root = FXMLLoader.load(Affichage.class.getResource("menu_aide.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(root);
	}
	
	protected static Scene getSceneMulti() {
		Parent root = null;
		try {
			root = FXMLLoader.load(Affichage.class.getResource("menu_multi.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(root);
	}
	

	public static Scene getSceneJeu() {
		Parent root = null;
		try {
			root = FXMLLoader.load(Affichage.class.getResource("AffichagePlateau.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(root);
	}
	
	static Scene getSceneHebergement(String nom) {
		Serveur.demarrerServeur(nom, System.getProperty("user.name"));
		Parent root = null;
		try {
			root = FXMLLoader.load(Affichage.class.getResource("menu_attente.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(root);
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
		ImageView retour = new ImageView(new Image("file:img/Back-PNG-Image.png"));
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
