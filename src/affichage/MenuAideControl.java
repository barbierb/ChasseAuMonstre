package affichage;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MenuAideControl {
    @FXML
    private Pane screen;

    @FXML
    private ImageView quitter;
    
    @FXML
	private void mouseEntered(MouseEvent event) {
		((ImageView)event.getTarget()).setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
	}

	@FXML
	private void mouseExited(MouseEvent event) {
		((ImageView)event.getTarget()).setImage(new Image(Affichage.chargerImg("../conteneur.png")));
	}
	
	@FXML
	void initialize() {
		quitter.setOnMouseClicked(e->{
			try {
				Affichage.getInstance().start(Affichage.stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
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
