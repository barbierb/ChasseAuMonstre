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
import java.util.Timer;
import java.util.TimerTask;

import affichage.Affichage;
import affichage.AffichagePlateau;
import affichage.Menus;
import javafx.application.Platform;
import plateau.Plateau;

public class Client extends Thread {

	private static Client instance;

	public static Timer brdTask;

	private Connexion connexion;

	public Plateau plateau;
	public boolean estMonstre;
	public boolean monTour;
	

	public Client(Socket socket) {
		this.connexion = new Connexion(socket);
		this.start();
	}

	public void run() {
		try {
			while(true) {
				System.out.println("CLT en attente des infos de base ");
				String recu = (String) this.connexion.in.readObject();
				System.out.println("CLT infos de base="+recu);
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
				System.out.println("CLT info de base recues: estmonstre="+estMonstre+" montour="+monTour);
				break;
			}
			
			if(estMonstre) {
				this.plateau = new Plateau(11);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Affichage.stage.setScene(Menus.getSceneJeu());
						Affichage.stage.setTitle(Affichage.stage.getTitle()+" - Monstre");
					}
				});
				envoyerPlateau(); // envoi sans entites
			} else {
				System.out.println("chass 1");
				this.plateau = connexion.recevoirPlateau(); // reception sans entites
				System.out.println("chass 2 "+plateau.getTaille());
				
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Affichage.stage.setScene(Menus.getSceneJeu());
						Affichage.stage.setTitle(Affichage.stage.getTitle()+" - Chasseur");
					}
				});
				Plateau newp = connexion.recevoirPlateau();
				//this.plateau = connexion.recevoirPlateau(); // reception avec montre placé
				System.out.println("CLIENT -newp------------->>>>>>> "+(newp.getMonstre()==null)+" "+newp.getTour());
				AffichagePlateau.getInstance().update();
			}
			System.out.println("CLIENT démarrage boucle prin");
			while(true) {
				boolean b= true;
				if(b) continue;
				if(monTour) {
					if(estMonstre) {
						// MAJ AFFICHAGE POPO DEPLACE TOI
						while(plateau.getChasseur().getDirectionVoulue()==null) {
							plateau.getMonstre().deplace(); // tu peux te déplacer
						}
						// -> si NON IA 
						// --> activer un boolean dans interface: interf renvoie le plateau au serv
						// -> sinon
						// --> deplacer le monstre avec une ia

					} else {
						plateau.getChasseur().placerLongueVue();
						// methode abs permettant le placement de longue vue par ia
						// et autorise le placement dans l'interface.

						while(Affichage.placerLongueVue == false) {System.out.println("slt jatt");}
						
						while(plateau.getChasseur().getDirectionVoulue()==null) {
							plateau.getChasseur().deplace(); // tu peux te déplacer
						}
						// -> si NON IA 
						// --> activer un boolean dans interface: interf renvoie le plateau au serv
						// -> sinon
						// --> deplacer le monstre avec une ia
					}
				} else {
					this.plateau = connexion.recevoirPlateau();
					// montour = true
					// AFFICHER PLATEAU ET ATTENDRE PLATEAU
				}
			}

		} catch (IOException e) {
			System.out.println("CLT Déconnecté ! " + e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
					System.out.println("CLT connexion à "+ip+":"+port);
					instance = new Client(new Socket(ip, port));
					System.out.println("CLT connecté au serveur "+ip+":"+port);
					if(brdTask != null) brdTask.cancel();
				} catch (IOException e) {
					System.out.println("CLT Ce serveur est innacessible.");
				} 
			}
		}).start();
	}

	public static void pingServeurs() {

		brdTask = new Timer();
		brdTask.schedule(new TimerTask() {
			private int occurence = 1;
			@Override
			public void run() {
				System.out.println("CLT pingServeurs()");
				if(occurence++>=5) cancel();
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
		try {
			System.out.println("MONSTRE = null ? "+(getPlateau().getMonstre()==null));
			this.connexion.out.writeObject(getPlateau());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Client getInstance() {
		return instance;
	}

	public Plateau getPlateau() {
		return plateau;
	}

}
