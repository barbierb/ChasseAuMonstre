package reseau;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import plateau.Plateau;

public class Serveur {

	public static final int 	PORT_JEU = 12345;
	public static final int 	PORT_BRD = 4812;
	public static final String 	ASK_BRD = "GETSERVEUR";

	private static Serveur instance;

	private Connexion hote;
	private Connexion opposant;

	private ServerSocket serveurListener;
	private Thread brdTask;

	private Serveur(String nomServeur, String nomHote) {
		instance = this;
		try {
			System.out.println("SRV démarrage du serveur");

			serveurListener = new ServerSocket(Serveur.PORT_JEU);
			System.out.println("SRV serveur démarré "+serveurListener.getInetAddress().toString());

			Client.connecter("127.0.0.1", Serveur.PORT_JEU);

			System.out.println("SRV connexion de l'hote");
			this.hote = new Connexion(serveurListener.accept());
			System.out.println("SRV hote connecté ");
		} catch(BindException e) {
			System.out.println("SRV Un serveur est déjà démarré.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.brdTask = new BroadcastTask(nomServeur, nomHote);
		this.brdTask.start();

		System.out.println("SRV Attente d'un opposant");
		try {
			this.opposant = new Connexion(serveurListener.accept());
			this.brdTask.interrupt();
			System.out.println("SRV opposant connecté ");
			this.hote.start();
			this.opposant.start();
			this.hote.out.writeObject(MessageReseau.ESTMONSTRE.toString());
			this.opposant.out.writeObject(MessageReseau.ESTCHASSEUR.toString());
			System.out.println("SRV messages sent");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class Connexion extends Thread {
		private Socket socket;
		private ObjectInputStream in;
		private ObjectOutputStream out;

		public Connexion(Socket socket) {
			this.socket = socket;
			try {
				socket.setKeepAlive(true);
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				System.out.println("CONN connexion démarrée");

				while(true) {
					Plateau p = null;

					p = (Plateau) in.readObject();

					if (p == null) return;

					System.out.println("CONN PLATEAU RECU");
				}
			} catch (Exception e) {

			} finally {

				try {
					if (out != null)
						out.close();
					if (in != null)
						in.close();
					socket.close();
					System.out.println("CONN Connexion stoppée");
				} catch (IOException e) {
				}
			}
		}
	}

	public static void demarrerServeur(String nomServeur, String nomHote) {
		new Serveur(nomServeur, nomHote);
	}

	public static Serveur getInstance() {
		return Serveur.instance;
	}
}
