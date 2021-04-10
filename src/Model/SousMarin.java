package Model;

import java.util.ArrayList;

public class SousMarin {


    private final float longueur;
    private final float rayon;
    private ArrayList<ArrayList<Point>> pointsCylindre;
    private Point[] centreCercle;
    private int nbMeridienCylindre;
    private int nbParalleleCylindre;

    private ArrayList<ArrayList<Point>> pointsSphere;
    private int nbMeridienSphere;
    private int nbParalleleSphere;


    public SousMarin(float longueur, float rayon){
        this.longueur = longueur;
        this.rayon = rayon;
        init();
    }

    public void  init(){
        initCylindre();
        initSphere();
    }

    public void initCylindre(){

        pointsCylindre = new ArrayList<ArrayList<Point>>() ;
        centreCercle = new Point[] { new Point(0, 0, longueur/2), new Point(0, 0, -longueur/2) };
        nbMeridienCylindre = 50;
        nbParalleleCylindre = 20;

        double theta;
        double x;
        double y;
        double z;

        for(int j = 0; j <= nbParalleleCylindre; j++) {
            pointsCylindre.add(new ArrayList<Point>());
            for(int i=0;i<=nbMeridienCylindre;i++){
                theta = 2.0*Math.PI*((double) i/nbMeridienCylindre);
                x=rayon*Math.cos(theta);
                y=rayon*Math.sin(theta);
                z = (longueur / 2) - ((longueur / nbParalleleCylindre) * j);
                pointsCylindre.get(j).add(new Point(x,y,z));
            }
        }
    }

    public void initSphere() {

        pointsSphere = new ArrayList<ArrayList<Point>>() ;
        nbMeridienSphere = 50;
        nbParalleleSphere = 20;

        double theta;
        double lambda;
        double x;
        double y;
        double z;

        for(int j = 0; j< nbParalleleSphere; j++) {
            pointsSphere.add(new ArrayList<Point>());
            lambda = (Math.PI/2) - Math.PI*((double) j/nbParalleleSphere);
            z=rayon*Math.sin(lambda);
            for(int i = 0; i<= nbMeridienSphere; i++){
                theta = 2.0*Math.PI*((double) i/nbMeridienSphere);
                x=(rayon*Math.cos(lambda))*Math.cos(theta);
                y=(rayon*Math.cos(lambda))*Math.sin(theta);
                pointsSphere.get(j).add(new Point(x,y,z));

            }
        }
    }

    public ArrayList<ArrayList<Point>> getPointsCylindre() {
        return pointsCylindre;
    }

    public int getNbMeridienCylindre() {
        return nbMeridienCylindre;
    }

    public int getNbParalleleCylindre() {
        return nbParalleleCylindre;
    }

    public Point[] getCentreCercle() {
        return centreCercle;
    }

    public ArrayList<ArrayList<Point>> getPointsSphere() {
        return pointsSphere;
    }

    public int getNbMeridienSphere() {
        return nbMeridienSphere;
    }

    public int getNbParalleleSphere() {
        return nbParalleleSphere;
    }
}
