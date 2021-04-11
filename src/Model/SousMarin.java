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

    private ArrayList<ArrayList<Point>> pointsCockpit;
    private int nbMeridienCockpit;
    private int nbParalleleCockpit;
    private Point[] faceCockpit;

    private ArrayList<ArrayList<Point>> pointsBaseHelice;
    private Point extremiteBaseHelice;
    private int nbMeridienBaseHelice;
    private int nbParalleleBaseHelice;


    public SousMarin(float longueur, float rayon){
        this.longueur = longueur;
        this.rayon = rayon;
        init();
    }

    public void  init(){
        this.initCylindre();
        this.initSphere();
        this.initCockpit();
        this.initBaseHelice();
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

        for(int j = 0; j<= nbParalleleSphere; j++) {
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

    public void initCockpit() {

        pointsCockpit = new ArrayList<ArrayList<Point>>();
        nbMeridienCockpit = 50;
        nbParalleleCockpit = 20;

        double theta;
        double x;
        double y;
        double z;

        faceCockpit = new Point[] {new Point(0, longueur/2, 0), new Point(0, -longueur/2, 0)};

        for(int j = 0; j <= nbParalleleCockpit; j++) {
            pointsCockpit.add(new ArrayList<Point>());
            for (int i = 0; i <= nbMeridienCockpit; i++) {
                theta = 2.0 * Math.PI * ((double) i / nbMeridienCockpit);
                x = rayon * Math.cos(theta);
                y = (longueur / 2) - ((longueur / nbParalleleCockpit) * j);
                z = rayon * Math.sin(theta);
                pointsCockpit.get(j).add(new Point(x, y, z));
            }
        }
    }

    /**
     * Initialise la base de l'hélice
     */
    public void initBaseHelice() {
        pointsBaseHelice = new ArrayList<ArrayList<Point>>() ;
        extremiteBaseHelice = new Point(0, 0, longueur/2-this.longueur);

        //Valeur des méridiens de l'hélice
        nbMeridienBaseHelice = 50;
        nbParalleleBaseHelice = 3;

        double theta;
        double x;
        double y;
        double z;

        for(int j = 0; j < nbParalleleBaseHelice; j++) {
            pointsBaseHelice.add(new ArrayList<Point>());
            for(int i=0;i<=nbMeridienBaseHelice;i++){
                theta = 2.0*Math.PI*((double) i/nbMeridienBaseHelice);
                x=rayon*Math.cos(theta);
                y=rayon*Math.sin(theta);
                z = (longueur / 2) - ((longueur / nbParalleleBaseHelice) * j);
                pointsBaseHelice.get(j).add(new Point(x,y,z));
            }
        }
    }

    //GETTER AND SETTER
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

    public ArrayList<ArrayList<Point>> getPointsCockpit() {
        return pointsCockpit;
    }

    public int getNbMeridienCockpit() {
        return nbMeridienCockpit;
    }

    public int getNbParalleleCockpit() {
        return nbParalleleCockpit;
    }

    public Point[] getFaceCockpit() {
        return faceCockpit;
    }

    public ArrayList<ArrayList<Point>> getPointsBaseHelice() {
        return pointsBaseHelice;
    }

    public Point getExtremiteBaseHelice() {
        return extremiteBaseHelice;
    }

    public int getNbMeridienBaseHelice() {
        return nbMeridienBaseHelice;
    }

    public int getNbParalleleBaseHelice() {
        return nbParalleleBaseHelice;
    }
}
