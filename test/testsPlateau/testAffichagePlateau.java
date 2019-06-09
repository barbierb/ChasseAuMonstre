package testsPlateau;

import java.io.IOException;

import affichage.AffichagePlateau;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import personnage.chasseur.Chasseur;
import personnage.monstre.Monstre;
import plateau.Plateau;
import plateau.Position;

public class testAffichagePlateau extends Application{
	 public static Plateau p = new Plateau(10);
	 public static boolean estMonstre = false;
	 public static boolean monTour = true;
	
	 public void start(Stage stage) throws IOException {
		 p.getCase(4, 4).placerEtoile();
		 p.getCase(2, 2).placerLV();
		 p.monstre = new Monstre(new Position(0,0));
		 p.chasseur = new Chasseur(new Position(4,2));
		 p.getCase(4,4).placerLV();
		 p.setTour(2);
		 p.chasseur.ajouterEtoile();
		 
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("../affichage/AffichagePlateauv2.fxml"));
         Parent root = loader.load();
         
         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Chasse au monstre");
         stage.show();
	 }
	
	 public static void main(String[] args) {
	         Application.launch(args);
	 }
}