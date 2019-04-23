package gestionpersonnage;

public class TestItPlateau {
	static Plateau plat = new Plateau(3, 3);
	public static void main(String[] args) {
		for (Case c: plat) {
			System.out.println(c.numCase);
		}
	}
}