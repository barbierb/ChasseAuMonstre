package affichage;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import reseau.Serveur;

public class Menus {

	protected static Scene getSceneControls() {
		Pane labels = new Pane();
		labels.getChildren().add(new Label("Titre"));
		Scene controls = new Scene(labels);
		return controls;
	}

	private static String nomServeur="";
	protected static Scene getSceneMulti() {
		BorderPane root = new BorderPane();
		VBox top = new VBox();
		HBox top1 = new HBox();
		HBox top2 = new HBox();
		HBox top3 = new HBox();
		
		root.setTop(top);
		
		top.getChildren().add(top1);
		top.getChildren().add(top2);
		top.getChildren().add(top3);
		
		root.setBackground(new Background(new BackgroundFill(
				Color.GRAY, 
				new CornerRadii(0), 
				new Insets(0)))
				);
		
		top1.setAlignment(Pos.CENTER);
		top2.setAlignment(Pos.CENTER);
		top3.setAlignment(Pos.CENTER);
		
		Label titre =  new Label("Multijoueur");
		titre.setFont(new Font("LobsterTwo",45));
		top1.getChildren().add(titre);
		
		Button hebergement = new Button("Héberger ma partie");
		top3.getChildren().add(hebergement);
		hebergement.addEventHandler(ActionEvent.ACTION, e -> {
			Affichage.stage.setScene(getSceneHebergement(nomServeur));
		});
		
		TextField tf = new TextField();
		top2.getChildren().add(tf);
		tf.setPromptText("Entrez ici le nom du serveur");
		tf.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			nomServeur+=e.getCharacter();
		});
		

		AnchorPane center = new AnchorPane();
		root.setCenter(center);

		return new Scene(root,1280,720);
	}
	
	private static Scene getSceneHebergement(String nom) {
		//Serveur.demarrerServeur(nom, System.getProperty("user.name"));
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
}
