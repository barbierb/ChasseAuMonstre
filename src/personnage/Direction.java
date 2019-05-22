package personnage;

/**
 * Enumeration des différentes directions disponibles pour les personnages avec les changements de coordonnées qui correspondent
 * @author Sylvain
 *
 */
public enum Direction {

    N("Nord", false, -1, 0),
    NE("Nord-Est", true, -1, 1),
    E("Est", false, 0, 1),
    SE("Sud-Est", true, 1, 1),
    S("Sud", false, 1, 0),
    SO("Sud-Ouest", true, 1, -1),
    O("Ouest", false, 0, -1),
    NO("Nord-Ouest", true, -1, -1);


    private int x;
    private int y;
    private String label;
    private boolean diagonale;

    private Direction(String l, boolean d, int x, int y) {
        this.label = l;
        this.diagonale = d;
        this.x = x;
        this.y = y;
    }

    public String getLabel() {
        return label;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    /**
     * Si la direction est en diagonale retourne true, false sinon
     * @return true si la direction est NE ou SE ou SO ou NO
     */
    public boolean estDiagonale() {
        return diagonale;
    }
    /**
     * Donne une direction en fonction du numéro, qui correspondent au pavé numérique
     * @param i : le numéro sur le pavé numérique (de 1 à 9 sans 5)
     * @return la direction correspondante
     */
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