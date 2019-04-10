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

    private Direction(String l, boolean d) {
        this.label = l;
        this.diagonale = d;
    }

    public String getLabel() {
        return label;
    }

    
    public boolean estDiagonale() {
        return diagonale;
    }

    public static Direction byNumero(int i) {
        switch (i) {
            case 8:
                return Direction.N;
            case 9:
                return Direction.NE;
            case 6:
                return Direction.E;
            case 3:
                return Direction.SE;
            case 2:
                return Direction.S;
            case 1:
                return Direction.SO;
            case 4:
                return Direction.O;
            default:
                return Direction.NO;
        }
    }
}