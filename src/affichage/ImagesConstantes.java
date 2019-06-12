package affichage;

import javafx.scene.image.Image;

public class ImagesConstantes {
	
	private static ImagesConstantes instance;
	
	public static ImagesConstantes getInstance() {
		if(ImagesConstantes.instance == null)
			ImagesConstantes.instance = new ImagesConstantes();
		return ImagesConstantes.instance;
	}

	public final Image FOND = new Image(getClass().getResourceAsStream("/img/fond.jpg"));
	public final Image CONTENEUR = new Image(getClass().getResourceAsStream("/img/conteneur.png"));
	public final Image CONTENEUR_TITRE = new Image(getClass().getResourceAsStream("/img/conteneur_titre.png"));
	public final Image CONTENEUR_HOVER = new Image(getClass().getResourceAsStream("/img/conteneur_hover.png"));
	public final Image AIDE = new Image(getClass().getResourceAsStream("/img/aide.png"));
	public final Image BLE = new Image(getClass().getResourceAsStream("/img/ble.png"));
	public final Image BLE_ECRASE = new Image(getClass().getResourceAsStream("/img/bleEcrase.png"));
	public final Image CHASSEUR_BAS = new Image(getClass().getResourceAsStream("/img/chasseur_bas.png"));
	public final Image CHASSEUR_DROITE = new Image(getClass().getResourceAsStream("/img/chasseur_droite.png"));
	public final Image CHASSEUR_GAUCHE = new Image(getClass().getResourceAsStream("/img/chasseur_gauche.png"));
	public final Image CHASSEUR_HAUT = new Image(getClass().getResourceAsStream("/img/chasseur_haut.png"));
	public final Image CONTENEUR_FONCE = new Image(getClass().getResourceAsStream("/img/conteneur_fonce.png"));
	public final Image CONTROL_CHASSEUR = new Image(getClass().getResourceAsStream("/img/control_chasseur.png"));
	public final Image CONTROL_MONSTRE = new Image(getClass().getResourceAsStream("/img/control_monstre.png"));
	public final Image ETOILE = new Image(getClass().getResourceAsStream("/img/etoile.png"));
	public final Image FOND_PLATEAU = new Image(getClass().getResourceAsStream("/img/fondPlateau.png"));
	public final Image LONGUE_VUE = new Image(getClass().getResourceAsStream("/img/longuevue.png"));
	public final Image MONSTRE_BAS = new Image(getClass().getResourceAsStream("/img/monstre_bas.png"));
	public final Image MONSTRE_HAUT = new Image(getClass().getResourceAsStream("/img/monstre_haut.png"));
	public final Image MONSTRE_DROITE = new Image(getClass().getResourceAsStream("/img/monstre_droite.png"));
	public final Image MONSTRE_GAUCHE = new Image(getClass().getResourceAsStream("/img/monstre_gauche.png"));
	public final Image SERV_SELECTED = new Image(getClass().getResourceAsStream("/img/serv_selected.png"));
	public final Image SERV = new Image(getClass().getResourceAsStream("/img/serv.png"));
}
