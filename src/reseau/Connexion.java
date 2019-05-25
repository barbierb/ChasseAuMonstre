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
			this.out.writeObject(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Plateau recevoirPlateau() {
		try {
			return (Plateau) this.in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}