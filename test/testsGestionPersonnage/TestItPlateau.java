package testsGestionPersonnage;

import launcher.ConfigurationPartie;
import plateau.Case;
import plateau.Plateau;

public class TestItPlateau {
	
	static Plateau plat = new Plateau(new ConfigurationPartie(true, true));
	
	public static void main(String[] args) {
		for(Case c: plat) {
			System.out.println(c);
		}
	}
}