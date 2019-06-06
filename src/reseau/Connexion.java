package reseau;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import plateau.Plateau;

public class Connexion {
	
	private Socket socket;
	public ObjectInputStream in;
	public ObjectOutputStream out;

	public Connexion(Socket socket) {
		this.socket = socket;
		try {
			socket.setKeepAlive(true);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Connexion: Exception IO, fin de la partie.");
			try {
				socket.close();
				System.exit(1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public Socket getSocket() {
		return this.socket;
	}

	public void envoyer(String s) {
		try {
			this.out.writeObject(s);
		} catch (IOException e) {
			System.out.println("Connexion: Exception IO, fin de la partie.");
			try {
				socket.close();
				System.exit(1);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void envoyer(Plateau p) {
		try {
			Plateau pnew = new Plateau(p.getTaille());
			pnew.setTour(p.getTour());
			this.out.writeObject(pnew);
			this.out.flush();
			this.out.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Plateau recevoirPlateau() {
		System.out.println("Connexion: en attente de plateau");
		try {
			Plateau p = (Plateau) this.in.readObject();
			return p;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}