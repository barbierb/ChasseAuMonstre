package affichage;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class MenuAideControl {
    @FXML
    private Pane screen;

    @FXML
    private ImageView quitter;
    
	@FXML
	void initialize() {
		quitter.setOnMouseClicked(e->{
			try {
				Affichage.getInstance().start(Affichage.stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		quitter.setOnMouseEntered(e -> {
			quitter.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
		});
		quitter.setOnMouseExited(e -> {
			quitter.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
		});
		screen.setOnKeyPressed(e -> {
			if(!e.getCode().equals(KeyCode.ESCAPE)) return;
			try {
				Affichage.getInstance().start(Affichage.stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}
}
