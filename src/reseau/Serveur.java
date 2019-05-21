package reseau;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;

import launcher.Engine;

public class Serveur {
	
	public static final int 	PORT_JEU = 12345;
	public static final int 	PORT_BRD = 4812;
	public static final String 	ASK_BRD = "GETSERVEUR";
	
	private static Serveur instance;

	private Client hote;
	private Client opposant;

	private ServerSocket serveurListener;
	private Thread brdTask;
	
	private Serveur(String nomServeur, String nomHote) {
		
		try {
			System.out.println("démarrage du serveur");
			
			serveurListener = new ServerSocket(Serveur.PORT_JEU);
			System.out.println("serveur démarré");

			System.out.println("connexion de l'hote");
			Client.connecter(InetAddress.getLocalHost(), Serveur.PORT_JEU);
			this.hote = new Client(serveurListener.accept()); // TODO WHAT THE FUCK COMMENT C POSSIBLE
			System.out.println("hote connecté");
		} catch(BindException e) {
			System.out.println("Un serveur est déjà démarré.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.brdTask = new BroadcastTask(nomServeur, nomHote);
		this.brdTask.start();
		
		System.out.println("Attente d'un opposant");
		try {
			this.opposant = new Client(serveurListener.accept());
			System.out.println("opposant connecté ");
			//this.brdTask = null; //TODO ARETTER LE BROADCAST QD QQUN CONNECTE
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.hote.out().println(MessageReseau.READY.toString());
		this.opposant.out().println(MessageReseau.READY.toString());
		System.out.println("messages sent");
	}
	
	public static void demarrerServeur(String nomServeur, String nomHote) {
		Serveur.instance = new Serveur(nomServeur, nomHote);
	}
	
	public static Serveur getInstance() {
		return Serveur.instance;
	}
}
