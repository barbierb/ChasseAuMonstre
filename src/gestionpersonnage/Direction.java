package gestionpersonnage;

public enum Direction {
	
	N("Nord", false),
	NE("Nord-Est", true),
	E("Est", false),
	SE("Sud-Est", true),
	S("Sud", false),
	SO("Sud-Ouest", true),
	O("Ouest", false),
	NO("Nord-Ouest", true);
	
	private String label;
	private boolean diagonale;
	
	private Direction(String n, boolean d) {
		this.label = n;
		this.diagonale = d;
	}

	public String getLabel() {
		return label;
	}

	public boolean estDiagonale() {
		return diagonale;
	}
	
}
