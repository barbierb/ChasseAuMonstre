package testsGestionPersonnage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entites.personnage.Personnage;
import entites.personnage.chasseur.Chasseur;
import entites.personnage.monstre.Monstre;
import plateau.Plateau;
import plateau.Position;

public class MainTest {
	
	public Plateau p;
	public Personnage chasseur,monstre;
	
	@BeforeClass
	public static void beforeAll() {
		System.out.println("D�but des tests");
	}	
	
	@Before
	public void beforeEach() {
		System.out.println("Initialisation...");
		chasseur = new Chasseur(new Position(0, 0));
		monstre = new Monstre(new Position(5, 5));
		System.out.println("D�but du test");
	}

	@Test
	public void testPosition() {
		Position p1 = new Position(0, 0);
		assertEquals(0,p1.getX());
		assertEquals(0,p1.getY());
		assertTrue(p1.equals(p1));
		assertTrue(new Position(0,0).equals(p1));
		assertFalse(new Position(1,1).equals(p1));
	}
	@Test
	public void testPersonnage() {
		assertFalse(chasseur.aEtoile());
	}
	@After
	public void afterEach() {
		System.out.println("Suivant...");
	}
	@AfterClass
	public static void endTests() {
		System.out.println("Fin des tests");
	}
}
