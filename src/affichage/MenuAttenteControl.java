package affichage;

import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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
    
	public static Timer timer;

	@FXML
	void initialize() {
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
		
		quitter.setOnMouseEntered(e -> {
			quitter.setImage(new Image(Affichage.chargerImg("../conteneur_hover.png")));
		});
		quitter.setOnMouseExited(e -> {
			quitter.setImage(new Image(Affichage.chargerImg("../conteneur.png")));
		});
	}

}