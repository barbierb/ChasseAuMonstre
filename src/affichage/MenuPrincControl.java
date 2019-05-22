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
    	System.out.println("scene solo");
    }

    @FXML
    void setSceneMulti(ActionEvent event) {
    	System.out.println("scene multi");
    }

    @FXML
    void setSceneControls(ActionEvent event) {
    	System.out.println("scene controls");
    }

    @FXML
    void exitou(ActionEvent event) {
    	System.exit(0);
    }

}
