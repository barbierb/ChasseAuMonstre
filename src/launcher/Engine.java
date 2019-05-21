package launcher;

import java.net.InetAddress;

import affichage.Affichage;
import entites.personnage.Personnage;
import plateau.Plateau;
import reseau.Client;
import reseau.Serveur;

public class Engine {
	
	private static Engine instance;

	private Plateau plateau;
	private Personnage chasseur;
	private Personnage monstre;

	private boolean tourMonstre;
	private int tourActuel; // TODO A BOUGER DANS PLATEAU
	
	private Engine() {
		
		while(getWinner() == null) {
			
			System.out.println("affichage");
			
			sleep(3000);
		}
	}

	public static Engine getInstance() {
		if(Engine.instance == null)
			Engine.instance = new Engine();
		return instance;
	}

	private void sleep(long millisecondes) {
		try {
			Thread.sleep(millisecondes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private Personnage getWinner() {
		/*if(this.monstre.getPosition().equals(this.chasseur.getPosition()))
			return this.chasseur;
		Iterator<Case> itr = plateau.iterator();
		int nbParcoursMonstre = 0;
		while(itr.hasNext()) {
			Case c = itr.next();
			if(c.getTourPassage() != -1)
				nbParcoursMonstre++;
		}
		if(nbParcoursMonstre >= plateau.getNbCases()*0.75)
			return this.chasseur;*/
		return null;
	}

	
	public static void start() {
		
		Affichage.getInstance();
		
		// Affichage.changerInterface(Menu.PRINCIPAL);
		boolean isHost = !true;
		if(isHost) {
			Serveur.demarrerServeur("Jeuj", System.getProperty("user.name"));
		} else {
			Client.pingServeurs();
			Client.connecter("127.0.0.1", Serveur.PORT_JEU);
		}
	}
	
	public static void main(String[] args) {
		Engine.start();
	}
}