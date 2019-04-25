package plateau;

import java.util.Iterator;

/**
 * Itérateur pour le plateau
 * @author Sylvain
 *
 */
public class ItPlateau implements Iterator<Case>{
	
	private Case[][] cases;
	private int i;
	private int j;
	
	public ItPlateau(Case[][] cases) {
		this.cases = cases;
		this.i = 0;
		this.j = 0;
	}
	@Override
	public boolean hasNext() {	
		return i < cases[0].length && j < cases.length;
	}

	@Override
	public Case next() {
		Case actuel= cases[i][j];
		j++;
		if(j == cases[0].length) {
			j = 0;
			i++;
		}		
		return actuel;
	}

}
