package affichage;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
/**
 * Menu qui détaille les différentes règles du jeu
 * @author Sylvain
 */
public class MenuAideControl {
    @FXML
    private Pane screen;

    @FXML
    private ImageView quitter;
    
    @FXML
    private ImageView fond;
    
    @FXML
    private ImageView aide;
    
    @FXML
    private ImageView controlMons;
    
    @FXML
    private ImageView controlChass;
    
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
		quitter.setImage(img.CONTENEUR);
		fond.setImage(img.FOND);
		aide.setImage(img.AIDE);
		controlChass.setImage(img.CONTROL_CHASSEUR);
		controlMons.setImage(img.CONTROL_MONSTRE);
		
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
