package affichage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
    }

    @FXML
    void exitou(ActionEvent event) {
    	System.exit(0);
    }

}
