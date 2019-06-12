package reseau;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import affichage.MenuAttenteControl;
/**
 * Classe qui initialise et stocke les connexions entre les clients
 * Acceptation des différents cas de figure de connexions en réseau
 * @author Sylvain
 */
public class Serveur {

	public static final int 	PORT_JEU = 12345;
	public static final int 	PORT_BRD = 4812;
	public static final String 	ASK_BRD = "GETSERVEUR";

	private static Serveur instance;

	public Connexion hote;
	public Connexion opposant;

	private ServerSocket serveurListener;
	private Thread brdTask;
	public boolean solo;
	public String nomServeur;

	private Serveur(String nomServeur, String nomHote, boolean solo) {
		Serveur.instance = this;
		this.solo = solo;
		this.nomServeur = nomServeur;
		try {
			serveurListener = new ServerSocket(Serveur.PORT_JEU);

			Client.connecter("127.0.0.1", Serveur.PORT_JEU);

			this.hote = new Connexion(serveurListener.accept());
		} catch(BindException e) {
			System.out.println("SRV Un serveur est déjà démarré.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.brdTask = new BroadcastTask(nomServeur, nomHote);
		this.brdTask.start();
		try {
			if(this.solo) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							new Client(new Socket("127.0.0.1", Serveur.PORT_JEU), false);
							while(true) {}
							
						} catch (IOException e) {
							System.out.println("IA Ce serveur est innacessible.");
						}
					}
				}).start();
				this.opposant = new Connexion(serveurListener.accept());
			} else {
				this.opposant = new Connexion(serveurListener.accept());
				MenuAttenteControl.timer.cancel();
			}
			this.brdTask.interrupt();
			
			new Engine().start(); // démarrage d'une nouvelle partie.
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void demarrerServeur(String nomServeur, String nomHote, boolean solo) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				new Serveur(nomServeur, nomHote, solo);
			}
		}).start();;
	}

	public static Serveur getInstance() {
		return Serveur.instance;
	}
}
