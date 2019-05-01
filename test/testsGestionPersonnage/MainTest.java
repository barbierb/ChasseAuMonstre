package testsGestionPersonnage;

import static org.junit.Assert.*;

import org.junit.*;

import entites.personnage.Personnage;
import entites.personnage.chasseur.Chasseur;
import entites.personnage.monstre.Monstre;
import launcher.ConfigurationPartie;
import launcher.Engine;
import plateau.Position;

public class MainTest {
	
	public Engine e;
	public Personnage chasseur,monstre;
	
	@BeforeClass
	public static void beforeAll() {
		System.out.println("Début des tests");
	}	
	
	@Before
	public void beforeEach() {
		System.out.println("Initialisation...");
		e = new Engine(new ConfigurationPartie());
		chasseur = new Chasseur(new Position(0, 0));
		monstre = new Monstre(new Position(5, 5));
		System.out.println("Début du test");
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
