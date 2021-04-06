package Model;

import java.util.ArrayList;

public class SousMarin {


    private ArrayList<ArrayList<Point>> pointsCylindre;
    private Point[] centreCercle;
    private int nbMeridienCylindre;
    private int nbPartieFaceCylindre;

    public SousMarin(){
        init();
    }

    public void  init(){
        initCylindre();
    }

    public void initCylindre(){
        pointsCylindre = new ArrayList<ArrayList<Point>>() ;
        centreCercle = new Point[] { new Point(0, 0, 3), new Point(0, 0, -3) };
        nbMeridienCylindre = 100;
        nbPartieFaceCylindre = 2;

        double delta = 2.0*Math.PI/nbMeridienCylindre;
        double h = 6;
        double r = 1;

        double angle = 0.0;
        double x;
        double y;
        double z;

        for(int j=0;j<nbPartieFaceCylindre;j++) {
            pointsCylindre.add(new ArrayList<Point>());

            for(int i=0;i<=nbMeridienCylindre;i++){
                x=r*Math.cos(angle);
                y=r*Math.sin(angle);
                z=h/2-((h/nbPartieFaceCylindre)*j);

                pointsCylindre.get(j).add(new Point(x,y,z));

                angle+=delta;
            }
            angle=0.0;
            for(int i=0;i<=nbMeridienCylindre;i++){
                x=r*Math.cos(angle);
                y=r*Math.sin(angle);
                z=h/2-((h/nbPartieFaceCylindre)*j);

                pointsCylindre.get(j).add(new Point(x,y,z-((h/nbPartieFaceCylindre))));
                if (i!=nbMeridienCylindre){
                    angle+=delta;
                }
            }
        }
    }

    public ArrayList<ArrayList<Point>> getPointsCylindre() {
        return pointsCylindre;
    }

    public int getNbMeridienCylindre() {
        return nbMeridienCylindre;
    }

    public int getNbPartieFaceCylindre() {
        return nbPartieFaceCylindre;
    }

    public Point[] getCentreCercle() {
        return centreCercle;
    }
}
