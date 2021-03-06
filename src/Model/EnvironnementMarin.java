package Model;

import java.util.ArrayList;

/**
 * Classe permettant l'initialisation de l'environnement marin
 */
public class EnvironnementMarin {
    private ArrayList<Point> points;
    private double longueur;
    private double nbpoints;
    private ArrayList<ArrayList<Point>> maillage = new ArrayList<>();
    private ArrayList<Double> couleur = new ArrayList<>();

    /**
     * @param l         longueur du cube fond marin
     * @param nbpoints  Nombre de points du maillage du fond marin
     * Constructeur d'EnvironnementMarin
     */
    public EnvironnementMarin(int l, double nbpoints) {
        this.longueur = l;
        this.points = new ArrayList<>();
        this.nbpoints = nbpoints;
        this.init();
    }

    /**
     * Initialise tous les éléments de l'environnement marin
     */
    public void init() {
        Point p1 = new Point(this.longueur, this.longueur, this.longueur);
        Point p2 = new Point(this.longueur, this.longueur, -this.longueur);
        Point p3 = new Point(this.longueur, -this.longueur, -this.longueur);
        Point p4 = new Point(-this.longueur, -this.longueur, -this.longueur);
        Point p5 = new Point(-this.longueur, this.longueur, this.longueur);
        Point p6 = new Point(-this.longueur, this.longueur, -this.longueur);
        Point p7 = new Point(-this.longueur, -this.longueur, this.longueur);
        Point p8 = new Point(this.longueur, -this.longueur, this.longueur);

        //Face 1 - Toit
        this.points.add(p1);
        this.points.add(p5);
        this.points.add(p6);
        this.points.add(p2);

        //Face 2
        this.points.add(p1);
        this.points.add(p2);
        this.points.add(p3);
        this.points.add(p8);

        //Face 3
        this.points.add(p4);
        this.points.add(p6);
        this.points.add(p5);
        this.points.add(p7);

        //Face 4
        this.points.add(p1);
        this.points.add(p8);
        this.points.add(p7);
        this.points.add(p5);

        //Face 5
        this.points.add(p4);
        this.points.add(p3);
        this.points.add(p2);
        this.points.add(p6);

        //Fond marin
        this.constructionSol();
    }

    /**
     * Construction du fond marin
     */
    public void constructionSol(){
        for (int i = 0; i<=this.nbpoints*2; i++){
            ArrayList<Point> a = new ArrayList<>();
            for (int j = 0; j<=this.nbpoints*2; j++){
                double c = 0.2+Math.random()*0.33;
                couleur.add(c);
                a.add(new Point(-this.longueur+(this.longueur/nbpoints)*i, -this.longueur + ((Math.random()*2)-1)*(float) (this.longueur/5),-this.longueur+(this.longueur/nbpoints)*j));
            }
            maillage.add(a);
        }
    }

    //GETTER
    public ArrayList<ArrayList<Point>> getMaillage() {
        return maillage;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Double> getCouleur() {
        return couleur;
    }

}
