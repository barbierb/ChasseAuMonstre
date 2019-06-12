package reseau;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
/**
 * Classe qui gère le broadcast sur le réseau local
 * @author Sylvain
 *
 */
public class BroadcastTask extends Thread implements Runnable {

	private DatagramSocket socket;
	private final String nom;
	private final String user;
	
	public BroadcastTask(String nom, String user) {
		this.nom = nom;
		this.user = user;
	}
	
	@Override
	public void run() {
		try {
			socket = new DatagramSocket(Serveur.PORT_BRD, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);

			while(true) {

				byte[] buffer = new byte[1500];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				
				String message = new String(packet.getData()).trim();
				if (message.equals(Serveur.ASK_BRD)) {
					byte[] sendData = ("SERVEUR "+nom+" "+user).getBytes();

					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
					socket.send(sendPacket);
				}
			}
		} catch (IOException ex) {}
	}

}