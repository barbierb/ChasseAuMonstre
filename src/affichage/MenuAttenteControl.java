package affichage;

import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
/**
 * Menu d'attente d'un joueur opposant en réseau
 * @author Sylvain
 */
public class MenuAttenteControl {

	private final String[] ATT = new String[] {
			"En attente d'un joueur   ",
			"En attente d'un joueur.  ",
			"En attente d'un joueur.. ",
			"En attente d'un joueur...",
		};
	public static int att_id = 0;


    @FXML
    private Pane screen;

    @FXML
    private Text textattente;

    @FXML
    private ImageView quitter;
    
    @FXML
    private ImageView fond;
    
	public static Timer timer;
	
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
		
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(MenuAttenteControl.att_id == 4)
					MenuAttenteControl.att_id = 0;
				textattente.setText(ATT[att_id++]);
			}
		}, 0, 1000);
		
		screen.setOnKeyPressed(e -> {
			if(!e.getCode().equals(KeyCode.ESCAPE)) return;
			timer.cancel();
			try {
				Affichage.getInstance().start(Affichage.stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		quitter.setOnMouseClicked(e->{
			timer.cancel();
			try {
				Affichage.getInstance().start(Affichage.stage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}

}