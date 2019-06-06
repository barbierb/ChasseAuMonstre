package plateau;

import java.io.Serializable;

/**
 * Position sur un plateau avec deux coordonnées x et y
 * @author Sylvain
 */
public class Position  implements Serializable {
	private static final long serialVersionUID = 42;
	
	private int x, y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean equals(Position obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (x != obj.x)
			return false;
		if (y != obj.y)
			return false;
		return true;
	}
}
