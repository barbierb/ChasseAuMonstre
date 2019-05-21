package affichage;

public class Affichage {
	
	private static Affichage affichage;
	//TODO VINCENT & SYLVAIN: STOCKER ET CHARGER LES SCENES

	private Affichage() {
		//TODO VINCENT & SYLVAIN: CHARGER LE MENU PRINCIPAL 1
	}
	
	public static Affichage getInstance() {
		if(Affichage.affichage == null)
			Affichage.affichage = new Affichage();
		return Affichage.affichage;
	}

}
