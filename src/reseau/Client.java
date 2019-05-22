package reseau;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

public class Client extends Thread {
	
	private static Client instance;

	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;

	public Client(Socket init) {
		socket = init;
		try {
			input = new BufferedReader(
						new InputStreamReader(
							socket.getInputStream()
						)
					);
			output = new PrintWriter(
						socket.getOutputStream(), true
					);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
	}

    public void run() {
    	while(true) {
    		try {
    			System.out.println("en attente de message rsx");
    			String recu = input.readLine();
    			System.out.println(recu);
    			
    		} catch (IOException e) {
                System.out.println("Déconnecté ! " + e);
            } finally {
                try {
                	socket.close();
                } catch (IOException e) {}
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
    	try {
			Client.instance = new Client(new Socket(ip, port));
			System.out.println("connecté au serveur "+ip+":"+port);
		} catch (IOException e) {
			System.out.println("Ce serveur est innacessible.");
		} 
    }
    
    public static void pingServeurs() {
		
    	new Timer().schedule(new TimerTask() {
    		private int occurence = 1;
			@Override
			public void run() {
				System.out.println("pingServeurs()");
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
				System.out.println(occurence+" ---> "+serv);
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
    

	public PrintWriter out() {
		return output;
	}

	public BufferedReader in() {
		return input;
	}

	public static Client getInstance() {
		return instance;
	}
}
