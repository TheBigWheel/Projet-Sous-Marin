package Model;

/**
 * Classe permettant la création et modification de point (x,y,z)
 */
public class Point {
    private double x;
    private double y;
    private double z;

    /**
     * @param x Coordonnée en x du point
     * @param y Coordonnée en y du point
     * @param z Coordonnée en z du point
     */
    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //GETTER AND SETTER
    public double getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}

