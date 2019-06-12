package reseau;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import affichage.Affichage;
import affichage.AffichagePlateau;
import affichage.MenuMultiControl;
import affichage.MenuSoloControl;
import affichage.Menus;
import javafx.application.Platform;
import personnage.chasseur.Chasseur;
import personnage.monstre.MonstreIA;
import plateau.Case;
import plateau.Plateau;
import plateau.Position;
/**
 * Classe du Client, qui est considéré comme le joueur, chaque joueur est une instance de Client lié à un serveur
 * @author Sylvain
 */
public class Client extends Thread {

	private static Client instance;

	public static Timer brdTask;

	private Connexion connexion;

	public Plateau plateau;
	public boolean estMonstre;
	public boolean monTour;

	private boolean affichage;

	public Client(Socket socket, boolean affichage) {
		this.connexion = new Connexion(socket);
		this.affichage = affichage;
		this.start();
	}

	public void run() {
		try {
			while(true) {
				String recu = (String) this.connexion.in.readObject();
				if(recu!=null) {
					if(recu.equals(MessageReseau.ESTMONSTRE.toString())) {
						this.estMonstre = true;
						this.monTour = true;
					} else if(recu.equals(MessageReseau.ESTCHASSEUR.toString())) {
						this.estMonstre = false;
						this.monTour = false;
					} else {
						continue;
					}
				} else {
					continue;
				}
				break;
			}

			if(estMonstre) {
				this.plateau = new Plateau(MenuSoloControl.taille);
				if(affichage) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Affichage.stage.setScene(Menus.getSceneJeu());
							Affichage.stage.setTitle(Affichage.stage.getTitle()+" - Monstre");
						}
					});
				} else {
					sleep(100);
				}
				envoyerPlateau(); // envoi sans entites
				if(affichage)
					while(plateau.getMonstre() == null) {
						sleep(10);
						// NE SARRETTE PAS TANT QUE LE MONSTRE EST PAS PLACE
						//System.out.println(affichage?"CLIENT":"IA"+" EN ATTENTE DU PLACEMENT DU MONSTRE PAR LIHM");
					}
				else  {
					Position pmonstre = new Position(
							new Random().nextInt(plateau.getTaille()),
							new Random().nextInt(plateau.getTaille())
						);
					this.plateau.setMonstre(new MonstreIA(pmonstre));
					Position pchass = new Position(
							plateau.getMonstre().getPosition().getX(), 
							plateau.getMonstre().getPosition().getY());
					while(plateau.getMonstre().getPosition().equals(pchass)) {
						pchass = new Position(
								new Random().nextInt(plateau.getTaille()), 
								new Random().nextInt(plateau.getTaille())
							);
					}
					plateau.setChasseur(new Chasseur(pchass));
					plateau.getCase(pmonstre).setTourPassage(plateau.getTour());
					plateau.placerEtoiles();
					plateau.setTour(1);
					sleep(500);
					envoyerPlateau();
					sleep(500);
				}
			} else {
				this.plateau = connexion.recevoirPlateau(); // reception sans entites
				if(affichage)
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Affichage.stage.setScene(Menus.getSceneJeu());
							Affichage.stage.setTitle(Affichage.stage.getTitle()+" - Chasseur");
						}
					});
				this.plateau = connexion.recevoirPlateau(); // reception avec montre placé et chasseur
				if(affichage)
					AffichagePlateau.getInstance().update();
			}


			while(true) {
				if(monTour) {
					if(affichage)
						AffichagePlateau.getInstance().update();
					if(estMonstre) {
						boolean estPasse = false;
						while(!estPasse) {
							estPasse = plateau.getMonstre().deplace();
							sleep(10);
						}
					} else {
						this.plateau.getChasseur().placerLongueVue();
						while(Affichage.placerLongueVue) {
							sleep(10);
						}
						for(Case c : plateau) {
							if(c.hasLV()) c.decrLV();
						}
						boolean estPasse = false;
						System.out.println("chass deplace");
						while(!estPasse) {
							estPasse = plateau.getChasseur().deplace();
							sleep(10); // sleep obligatoire car sinon la fonction deplace ne voit pas les modifications par les évènements du jeu
						}
					}
					if(affichage)
						AffichagePlateau.getInstance().update();

					verifWinner();
					monTour = false;
					sleep(100);
					System.out.println((affichage?"CLIENT":"             IA")+" tour fini envoi du plateau");
					envoyerPlateau();
					System.out.println((affichage?"CLIENT":"             IA")+" tour fini plat envoyé.");

				} else {
					this.plateau = connexion.recevoirPlateau(); // attente déplacement autre
					if(affichage)
						AffichagePlateau.getInstance().update();
					verifWinner();
					monTour = true;
				}
			}

		} catch (IOException e) {
			System.out.println("CLT Déconnecté ! " + e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void verifWinner() throws InterruptedException {
		if(plateau.getMonstre().getPosition().equals(plateau.getChasseur().getPosition())) {

			if(estMonstre) {
				if(affichage)
					AffichagePlateau.getInstance().affichageFin(false);
				System.out.println("MONSTRE DEFAITE");
				envoyerPlateau();
				sleep(750000);
			} else {
				if(affichage)
					AffichagePlateau.getInstance().affichageFin(true);
				System.out.println("CHASSEUR VICTOIRE");
				envoyerPlateau();
				sleep(750000);
			}

		}

		if(plateau.getMonstre().getCasesEcrasees() >= plateau.getNbCases()*0.75) {
			if(estMonstre) {
				if(affichage)
				AffichagePlateau.getInstance().affichageFin(true);
				System.out.println("MONSTRE VICTOIRE");
				envoyerPlateau();
				sleep(750000);
			} else {
				if(affichage)
				AffichagePlateau.getInstance().affichageFin(false);
				System.out.println("CHASSEUR DEFAITE");
				envoyerPlateau();
				sleep(750000);
			}

		}
	}

	public static void connecter(String ip, int portJeu) {
		try {
			connecter(InetAddress.getByName(ip), portJeu);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public static void connecter(InetAddress ip, int port) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("CLIENT connexion à "+ip+":"+port);
					Client.instance = new Client(new Socket(ip, port), true);
					System.out.println("CLIENT connecté au serveur "+ip+":"+port);
					if(brdTask != null) brdTask.cancel();
				} catch (IOException e) {
					System.out.println("CLIENT Ce serveur est innacessible.");
				} 
			}
		}).start();
	}

	public static void pingServeurs() {

		brdTask = new Timer();
		brdTask.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("CLIENT pingServeurs()");
				if(Client.getInstance() != null || MenuMultiControl.instance == null) cancel();
				Enumeration<NetworkInterface> cartes = null;
				try {
					cartes = NetworkInterface.getNetworkInterfaces();
				} catch (SocketException e) {e.printStackTrace();}

				DatagramSocket c = null;
				try {
					c = new DatagramSocket();
					c.setBroadcast(true);
					c.setSoTimeout(1000);
				} catch (SocketException e1) {e1.printStackTrace();}

				while (cartes.hasMoreElements()) {
					NetworkInterface networkInterface = cartes.nextElement();
					for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
						InetAddress broadcast = interfaceAddress.getBroadcast();
						if (broadcast == null) continue;
						try {
							DatagramPacket sendPacket = new DatagramPacket(Serveur.ASK_BRD.getBytes(), Serveur.ASK_BRD.getBytes().length, broadcast, Serveur.PORT_BRD);
							c.send(sendPacket);
						} catch (Exception e) {}
					}
				}
				byte[] recvBuf = new byte[256];
				DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
				try {
					c.receive(receivePacket);
				} catch (IOException e) {
					return;
				}
				String serv = new String(receivePacket.getData()).trim();
				System.out.println("BROADCAST réponse reçue:"+serv);
				String nom = serv.split(" ")[1];
				String user = serv.split(" ")[2];
				String ip = receivePacket.getAddress().toString().substring(1);
				MenuMultiControl.instance.addServeur(nom, user, ip);
			}
		}, 0, 1000);

		//TODO ESSAYER/
		/*
MulticastSocket socket = new MulticastSocket(4446);
InetAddress group = InetAddress.getByName("203.0.113.0");
socket.joinGroup(group);

DatagramPacket packet;
for (int i = 0; i < 5; i++) {
    byte[] buf = new byte[256];
    packet = new DatagramPacket(buf, buf.length);
    socket.receive(packet);

    String received = new String(packet.getData());
    System.out.println("Quote of the Moment: " + received);
}

socket.leaveGroup(group);
socket.close();
		 */

		//TODO METTRE A JOUR LA LISTE DES SERVEURS DANS LINTERFACE
	}

	public void envoyerPlateau() {
		this.connexion.envoyer(getPlateau());
	}

	public static Client getInstance() {
		return instance;
	}

	public Plateau getPlateau() {
		return plateau;
	}

}
