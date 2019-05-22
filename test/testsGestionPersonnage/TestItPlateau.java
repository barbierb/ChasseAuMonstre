package testsGestionPersonnage;

import plateau.Case;
import plateau.Plateau;

public class TestItPlateau {
	
	static Plateau plat;
	
	public static void main(String[] args) {
		for(Case c: plat) {
			System.out.println(c);
		}
	}
}