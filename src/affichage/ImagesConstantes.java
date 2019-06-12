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

}
