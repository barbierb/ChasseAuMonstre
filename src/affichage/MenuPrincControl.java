package affichage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
<<<<<<< HEAD

public class MenuPrincControl {

    @FXML
    private Button exit;

    @FXML
    private Button controls;

    @FXML
    private Button solo;

    @FXML
    private Button multi;

    @FXML
    void setSceneSolo(ActionEvent event) {
    	Affichage.stage.setScene(Menus.getSceneSolo());
    }

    @FXML
    void setSceneMulti(ActionEvent event) {
    	Affichage.stage.setScene(Menus.getSceneMulti());
    }

    @FXML
    void setSceneControls(ActionEvent event) {
    	Affichage.stage.setScene(Menus.getSceneControls());
=======
import reseau.Client;
import reseau.Serveur;

public class MenuPrincControl {

    @FXML
    private Button exit;

    @FXML
    private Button controls;

    @FXML
    private Button solo;

    @FXML
    private Button multi;

    @FXML
    void setSceneSolo(ActionEvent event) {
    	Client.connecter("127.0.0.1", Serveur.PORT_JEU);
    }

    @FXML
    void setSceneMulti(ActionEvent event) {
    	Serveur.demarrerServeur("Jeuj", System.getProperty("user.name"));
    }

    @FXML
    void setSceneControls(ActionEvent event) {
    	System.out.println("scene controls");
    	Affichage.stage.setScene(Affichage.getSceneControls());
>>>>>>> refs/remotes/origin/Vincent
    }

    @FXML
    void exitou(ActionEvent event) {
    	System.exit(0);
    }

}
