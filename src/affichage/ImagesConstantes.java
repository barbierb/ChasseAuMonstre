package affichage;

import javafx.scene.image.Image;

public class ImagesConstantes {
	
	private static ImagesConstantes instance;
	
	public static ImagesConstantes getInstance() {
		if(ImagesConstantes.instance == null)
			ImagesConstantes.instance = new ImagesConstantes();
		return ImagesConstantes.instance;
	} 

	public final Image FOND = new Image(getClass().getClassLoader().getResourceAsStream("img/fond.jpg"));
	public final Image CONTENEUR = new Image(getClass().getClassLoader().getResourceAsStream("img/conteneur.png"));
	public final Image CONTENEUR_TITRE = new Image(getClass().getClassLoader().getResourceAsStream("img/conteneur_titre.png"));
	public final Image CONTENEUR_HOVER = new Image(getClass().getClassLoader().getResourceAsStream("img/conteneur_hover.png"));
	public final Image AIDE = new Image(getClass().getClassLoader().getResourceAsStream("img/aide.png"));
	public final Image BLE = new Image(getClass().getClassLoader().getResourceAsStream("img/ble.png"));
	public final Image BLE_ECRASE = new Image(getClass().getClassLoader().getResourceAsStream("img/bleEcrase.png"));
	public final Image CHASSEUR_BAS = new Image(getClass().getClassLoader().getResourceAsStream("img/chasseur_bas.png"));
	public final Image CHASSEUR_DROITE = new Image(getClass().getClassLoader().getResourceAsStream("img/chasseur_droite.png"));
	public final Image CHASSEUR_GAUCHE = new Image(getClass().getClassLoader().getResourceAsStream("img/chasseur_gauche.png"));
	public final Image CHASSEUR_HAUT = new Image(getClass().getClassLoader().getResourceAsStream("img/chasseur_haut.png"));
	public final Image CONTENEUR_FONCE = new Image(getClass().getClassLoader().getResourceAsStream("img/conteneur_fonce.png"));
	public final Image CONTROL_CHASSEUR = new Image(getClass().getClassLoader().getResourceAsStream("img/control_chasseur.png"));
	public final Image CONTROL_MONSTRE = new Image(getClass().getClassLoader().getResourceAsStream("img/control_monstre.png"));
	public final Image ETOILE = new Image(getClass().getClassLoader().getResourceAsStream("img/etoile.png"));
	public final Image FOND_PLATEAU = new Image(getClass().getClassLoader().getResourceAsStream("img/fondPlateau.png"));
	public final Image LONGUE_VUE = new Image(getClass().getClassLoader().getResourceAsStream("img/longuevue.png"));
	public final Image MONSTRE_BAS = new Image(getClass().getClassLoader().getResourceAsStream("img/monstre_bas.jpg"));
	public final Image MONSTRE_HAUT = new Image(getClass().getClassLoader().getResourceAsStream("img/monstre_haut.jpg"));
	public final Image MONSTRE_DROITE = new Image(getClass().getClassLoader().getResourceAsStream("img/monstre_droite.jpg"));
	public final Image MONSTRE_GAUCHE = new Image(getClass().getClassLoader().getResourceAsStream("img/monstre_gauche.jpg"));
	public final Image SERV_SELECTED = new Image(getClass().getClassLoader().getResourceAsStream("img/serv_selected.jpg"));
	public final Image SERV = new Image(getClass().getClassLoader().getResourceAsStream("img/serv.jpg"));
}
