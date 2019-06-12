package personnage.chasseur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import affichage.Affichage;
import affichage.AffichagePlateau;
import personnage.Direction;
import plateau.Case;
import plateau.Position;
import reseau.Client;
/**
 * Classe chasseur pour son IA qui étend la classe Chasseur et qui gère les déplacements calculés par l'IA
 * @author Cantin
 */
public class ChasseurIA extends Chasseur {
	private static final long serialVersionUID = 42;
	private static Direction[] directions_chasseur = new Direction[4];
	private boolean monstre_detecte = false;
	private Position position_monstre = null;
	private int taillePlateau = Client.getInstance().getPlateau().getTaille();




	public ChasseurIA(Position p) {
		super(p);
		int index_tab = 0;
		int i = 1;

		while(index_tab != 3){
			if(!Direction.byNumero(i).estDiagonale()) {
				directions_chasseur[index_tab] = Direction.byNumero(i);
				index_tab++;
			}
			i++;
		}

	}

	@Override
	public Direction getDirectionVoulue() {

		if(monstre_detecte) {
			System.out.println("dans traque");
			return getDirectionTraque();
		}else {

			Direction direc = null;
			Position nextPos = null;
			System.out.println("dans random");
			while(direc == null){
				direc = directions_chasseur[new Random().nextInt(directions_chasseur.length - 1)];
				System.out.println(directions_chasseur.toString());
				System.out.println(direc);

				nextPos = new Position(this.pos.getX() + direc.getX(), this.pos.getY()+ direc.getY());

				if(nextPos.getX() > taillePlateau -1 || nextPos.getX() < 0  || nextPos.getY() > taillePlateau -1 || nextPos.getY() < 0) {
					direc = null;
					System.out.println("positon out of bounds");
				}else {
					if(!this.peutPasser(nextPos)) {
						direc = null;
					}
				}
				System.out.println("Dans while");
			}
			return direc;
		}


	}

	private Direction getDirectionTraque(){

		List<Direction> directionsTraque = new ArrayList<Direction>();

		if(this.pos.getX() == position_monstre.getX()) {

		}else if(this.pos.getX() < position_monstre.getX()) {
			directionsTraque.add(Direction.E);
		}else {
			directionsTraque.add(Direction.O);
		}

		if(this.pos.getY() == position_monstre.getY()) {

		}else if(this.pos.getY() < position_monstre.getY()){
			directionsTraque.add(Direction.S);
		}else {
			directionsTraque.add(Direction.N);
		}

		System.out.println(directionsTraque);
		if(directionsTraque.size() > 0) {
			return directionsTraque.get(new Random().nextInt(directionsTraque.size()));
		}else {
			monstre_detecte = false;
			return getDirectionVoulue();
		}
	}

	public void placerLongueVue() {
		System.out.println("fonction placer appelée");
		Case meilleureCase = null;

		Affichage.placerLongueVue=true;

		Random genAlea = new Random();
		int x;
		int y;
		Position positionCourante;
		Case caseCourante; 

		if(!monstre_detecte) {
			
			x = genAlea.nextInt(taillePlateau - 1);
			y = genAlea.nextInt(taillePlateau - 1);
			positionCourante = new Position(x, y);
			caseCourante = Client.getInstance().getPlateau().getCase(positionCourante);
			caseCourante.placerLV();
			
			if(caseCourante.getTourPassage() != -1) {

				if(meilleureCase == null || meilleureCase.getTourPassage() < caseCourante.getTourPassage()) {
					meilleureCase = caseCourante;
					position_monstre = positionCourante;
					monstre_detecte = true;
				}
			}else {
				
			}
		}

		AffichagePlateau.getInstance().update();
		Affichage.placerLongueVue=false;
	}
}

