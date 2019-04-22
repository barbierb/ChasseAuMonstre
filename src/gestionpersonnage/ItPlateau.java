package gestionpersonnage;

import java.util.Iterator;

/**
 * Itérateur pour le plateau
 * @author Sylvain
 *
 */
public class ItPlateau implements Iterator<Case>{
	
	private Case[][] cases;

	private int nbCases;
	private int numCases;
	private int i;
	private int j;
	
	public ItPlateau(Case[][] cases) {
		this.cases=cases;
		this.nbCases = cases[0].length * cases.length;
		this.numCases = 1;
		this.i=0;
		this.j=0;
	}
	@Override
	public boolean hasNext() {	
		return numCases < nbCases  ;
	}

	@Override
	public Case next() {
		Case actuel= cases[i][j];
		numCases++;
		i++;
		if(i > cases[0].length) {
			i = 0;
			j++;
		}
		
		return actuel;
	}

}
