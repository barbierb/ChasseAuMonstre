package gestionpersonnage;

import launcher.ConfigurationPartie;

public class TestItPlateau {
	static Plateau plat = new Plateau(new ConfigurationPartie(true, true));
	public static void main(String[] args) {
		for (Case c: plat) {
			System.out.println(c.numCase);
		}
	}
}