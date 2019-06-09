package reseau;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import personnage.chasseur.Chasseur;
import personnage.chasseur.ChasseurIA;
import personnage.monstre.Monstre;
import personnage.monstre.MonstreIA;
import plateau.Case;
import plateau.Plateau;
import plateau.Position;

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
			if(p.getMonstre() != null)
				if(p.getMonstre() instanceof MonstreIA)
					pnew.setMonstre(new MonstreIA(p.getMonstre().getPosition()));
				else
					pnew.setMonstre(new Monstre(p.getMonstre().getPosition()));
			if(p.getChasseur() != null)
				if(p.getChasseur() instanceof ChasseurIA)
					pnew.setChasseur(new ChasseurIA(p.getChasseur().getPosition()));	
				else
					pnew.setChasseur(new Chasseur(p.getChasseur().getPosition()));
			
			for(int i = 0; i < p.getTaille(); i++) {
                for(int j = 0; j < p.getTaille(); j++) {
                    Position pos = new Position(i,j);
                    Case tmp = p.getCase(pos);
                    if(tmp.hasEtoile()) {
                    	pnew.getCase(i,j).placerEtoile();
                    }
                    if(tmp.hasLV()) {
                    	pnew.getCase(i,j).placerLV(tmp.getLongueVue());
                    }
                }
            }
			
			this.out.writeObject(pnew);
			this.out.flush();
			this.out.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Plateau recevoirPlateau() {
		//System.out.println("Connexion: en attente de plateau");
		try {
			Plateau p = (Plateau) this.in.readObject();
			return p;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}