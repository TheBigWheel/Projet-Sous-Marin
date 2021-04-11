package GL;

import Model.*;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.newt.event.awt.AWTMouseAdapter;

import java.util.ArrayList;

//Applications implement the GLEventListener interface to perform OpenGL drawing via callbacks.
public class MyGLEventListener implements GLEventListener {
	GLUT glut;
	GLU glu;
	
	//About the camera and the visualization
	SceneMouseAdapter objectMouse;
	SceneKeyAdapter objectKeys;
	private int longueur;
	private float camera [] = {0.0f, 0.0f, 9.0f};
	private float view_rotx = 0.0f, view_roty = 0.0f;
	private float scale = 1.0f;
	private float aspect;
	
	//Predefined colors
	float red[] = { 0.8f, 0.1f, 0.0f, 0.7f };
	float green[] = { 0.0f, 0.8f, 0.2f, 0.7f };
	float blue[] = { 0.2f, 0.2f, 1.0f, 0.7f };

	//Light properties
	float light_ambient[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	float light_diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float light_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f};
	float light_position[] = { 10.0f, 10.0f, 10.0f, 0.0f };
	float light_position2[] = { -10.0f, -10.0f, 10.0f, 0.0f };
	float light_position3[] = { 0.0f, 0.0f, -10.0f, 0.0f };

	private FondMarin fondMarin;
	private SousMarin sousMarin;
	private float x;
	private float z;
	private float longueurSousMarin;
	private float rayonSousMarin;
	private float delta;
	private float theta;
	private float lambda;

	/**
	 * The init() method is called when a new OpenGL context is created for the given GLAutoDrawable. 
	 * Any display lists or textures used during the application's normal rendering loop can be safely 
	 * initialized in init(). The GLEventListener's init() method may be called more than once during 
	 * the lifetime of the application. The init() method should therefore be kept as short as possible 
	 * and only contain the OpenGL initialization required for the display() method to run properly.
	 */
	public void init(GLAutoDrawable drawable) {
		
		GL2 gl = drawable.getGL().getGL2();
		//For the light and the material
		gl.glClearColor(0.196078f,0.858824f,0.576471f,1.0f);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, light_ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light_diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, light_specular, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light_position, 0);
		
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, light_ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, light_diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, light_specular, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, light_position2, 0);
		
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_AMBIENT, light_ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE, light_diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_SPECULAR, light_specular, 0);
		gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION, light_position3, 0);
		
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glEnable(GL2.GL_LIGHT1);
		gl.glEnable(GL2.GL_LIGHT2);
		
		//Various tests
		gl.glEnable(GL2.GL_CULL_FACE);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glShadeModel(GL2.GL_SMOOTH);
		
		objectMouse = new SceneMouseAdapter(this);
		objectKeys = new SceneKeyAdapter(this);
		
		if (drawable instanceof Window) {
			Window window = (Window) drawable;
			window.addMouseListener(objectMouse);
			window.addKeyListener(objectKeys);
		} 
		
		else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
			java.awt.Component comp = (java.awt.Component) drawable;
			new AWTMouseAdapter(objectMouse, drawable).addTo(comp);
			new AWTKeyAdapter(objectKeys, drawable).addTo(comp);
		}
		
		gl.glEnable(GL2.GL_NORMALIZE);
		
		glut =  new GLUT();
		glu =  new GLU();

		longueur = 25;
		environnementMarin = new EnvironnementMarin(longueur, 10);

		longueurSousMarin = 5;
		rayonSousMarin = 1;
		sousMarin = new SousMarin(longueurSousMarin, rayonSousMarin);
		x=0;
		z=0;
		delta = 0;
		theta = (float) Math.PI/4;
		lambda = 0;
		// Décommentez cette ligne pour un affichage squelettique qui permet de voir le maillage des objets.
		//gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
	}

	
	/**
	 * Called when the drawable has been resized
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

		GL2 gl = drawable.getGL().getGL2();

		aspect = (float)width / (float)height;
		gl.glViewport(x, y, width, height);

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		
		//glu.gluPerspective(this.longueur*1.25, (float) aspect, 0.1f, this.longueur*1.5);
		//gl.glOrtho(-(this.longueur)*1.25, (this.longueur) *1.25, -(this.longueur)*1.25,this.longueur*1.25, -this.longueur*1.25, this.longueur*1.25);
		glu.gluPerspective(60f, (float) aspect, 0.1f, 100f);
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

	
	@Override
	public void dispose(GLAutoDrawable drawable) {
	
	}


	/**
	 * Called to perform per-frame rendering.
	 */
	public void display(GLAutoDrawable drawable) {
	
		// Get the GL corresponding to the drawable we are animating
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		
		gl.glLoadIdentity();
		
		glu.gluLookAt(camera[0], camera[1], camera[2]+scale,
				0.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f);

		gl.glRotatef(view_rotx, 1.0f, 0.0f, -0.0f);
		gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
		

		//TO FILL
		gl.glPushMatrix();
			dessinerFondMarin(gl);
			gl.glTranslatef(x,0f,z);
			gl.glRotatef((float) Math.toDegrees(delta), 0f, 1f, 0f);
			dessinerSousMarin(gl);
		gl.glPopMatrix();
	}


	/**
	 * Dessine le cube de l'environnement marin
	 */
	public void dessinerEnvironnementMarin(GL2 gl) {
		for (int i = 0; i < this.environnementMarin.getPoints().size(); i += 4) {
			gl.glBegin(GL2.GL_QUADS);
			Point p1 = this.environnementMarin.getPoints().get(i);
			Point p2 = this.environnementMarin.getPoints().get(i+1);
			Point p3 = this.environnementMarin.getPoints().get(i+2);
			Point p4 = this.environnementMarin.getPoints().get(i+3);
			if (i == 0) gl.glColor3d(0,1,1);
			else gl.glColor3d(0,0.5,1);
			gl.glVertex3d(p1.getX(), p1.getY(), p1.getZ());
			gl.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
			gl.glVertex3d(p3.getX(), p3.getY(), p3.getZ());
			gl.glVertex3d(p4.getX(), p4.getY(), p4.getZ());

		}
		//TO FILL
		gl.glEnd();

		ArrayList<ArrayList<Point>> maillage = environnementMarin.getMaillage();
		for (int i = 0, maillageSize = maillage.size()-1; i <maillageSize; i++) {
			for (int j = 0; j<maillageSize; j++) {

				gl.glBegin(GL2.GL_QUADS);
				if (i == 0 || j ==0){
					gl.glColor3d(environnementMarin.getCouleur().get(i+j), environnementMarin.getCouleur().get(i+j), environnementMarin.getCouleur().get(i+j));
				}
				else gl.glColor3d(environnementMarin.getCouleur().get(i*j), environnementMarin.getCouleur().get(i*j), environnementMarin.getCouleur().get(i*j));
				gl.glVertex3d(maillage.get(i).get(j+1).getX(),maillage.get(i).get(j+1).getY(),maillage.get(i).get(j+1).getZ());
				gl.glVertex3d(maillage.get(i+1).get(j+1).getX(),maillage.get(i+1).get(j+1).getY(),maillage.get(i+1).get(j+1).getZ());
				gl.glVertex3d(maillage.get(i+1).get(j).getX(),maillage.get(i+1).get(j).getY(),maillage.get(i+1).get(j).getZ());
				gl.glVertex3d(maillage.get(i).get(j).getX(),maillage.get(i).get(j).getY(),maillage.get(i).get(j).getZ());
				gl.glEnd();
			}
		}
	}

	public void dessinerSousMarin(GL2 gl) {
		gl.glColor3d(0.3, 0.3, 0.3);
		dessinerCorpsSousMarin(gl);
		gl.glPushMatrix();
			gl.glColor3d(0,0,0);
			gl.glTranslatef(0,0,-(longueurSousMarin/2+rayonSousMarin));
			gl.glScalef(0.4f,0.4f,0.1f);
			dessinerMoteur(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
			gl.glColor3d(0.5,0.5,0.5);
			gl.glTranslatef(0,this.rayonSousMarin*1.25f,0);
			gl.glScalef(0.2f,0.1f, 0.4f);
			dessinerCockpit(gl);
		gl.glPopMatrix();
		dessinerGouvernail(gl);
	}

	public void dessinerCylindre(GL2 gl) {
		ArrayList<ArrayList<Point>> pointsCylindre = sousMarin.getPointsCylindre();
		for (int i = 0; i < pointsCylindre.size()-1; i++) {
			ArrayList<Point> pointsParallele = pointsCylindre.get(i);
			ArrayList<Point> pointsParallele1 = pointsCylindre.get(i+1);
			for (int j = 0; j < sousMarin.getNbMeridienCylindre(); j++) {
				if (i == 0){
					gl.glBegin(GL2.GL_TRIANGLES);

					gl.glVertex3d(sousMarin.getCentreCercle()[0].getX(), sousMarin.getCentreCercle()[0].getY(), sousMarin.getCentreCercle()[0].getZ());
					gl.glVertex3d(sousMarin.getPointsCylindre().get(0).get(j).getX(), sousMarin.getPointsCylindre().get(0).get(j).getY(), sousMarin.getPointsCylindre().get(0).get(j).getZ());
					gl.glVertex3d(sousMarin.getPointsCylindre().get(0).get(j + 1).getX(), sousMarin.getPointsCylindre().get(0).get(j + 1).getY(), sousMarin.getPointsCylindre().get(0).get(j + 1).getZ());

					gl.glEnd();

					gl.glBegin(GL2.GL_TRIANGLES);

					gl.glVertex3d(sousMarin.getCentreCercle()[1].getX(), sousMarin.getCentreCercle()[1].getY(), sousMarin.getCentreCercle()[1].getZ());
					gl.glVertex3d(sousMarin.getPointsCylindre().get(sousMarin.getNbParalleleCylindre()).get(j + 1).getX(), sousMarin.getPointsCylindre().get(sousMarin.getNbParalleleCylindre()).get(j + 1).getY(), sousMarin.getPointsCylindre().get(sousMarin.getNbParalleleCylindre()).get(j + 1).getZ());
					gl.glVertex3d((sousMarin.getPointsCylindre().get(sousMarin.getNbParalleleCylindre()).get(j).getX()), sousMarin.getPointsCylindre().get(sousMarin.getNbParalleleCylindre()).get(j).getY(), sousMarin.getPointsCylindre().get(sousMarin.getNbParalleleCylindre()).get(j).getZ());

					gl.glEnd();
				}

				gl.glBegin(GL2.GL_QUADS);
				Point p0 = pointsParallele.get(j);
				Point p1 = pointsParallele1.get(j);
				Point p2 = pointsParallele1.get(j + 1);
				Point p3 = pointsParallele.get(j + 1);

				gl.glVertex3d(p0.getX(), p0.getY(), p0.getZ());
				gl.glVertex3d(p1.getX(), p1.getY(), p1.getZ());
				gl.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
				gl.glVertex3d(p3.getX(), p3.getY(), p3.getZ());

				gl.glEnd();
			}
		}
	}
	public void dessinerSphere(GL2 gl) {
		ArrayList<ArrayList<Point>> pointsSphere = sousMarin.getPointsSphere();
		for (int i = 0; i <= (pointsSphere.size())/2; i++) {
			ArrayList<Point> pointsParallele = pointsSphere.get(i);
			ArrayList<Point> pointsParallele1 = pointsSphere.get(i+1);
			for (int j = 0; j < sousMarin.getNbMeridienSphere(); j++) {
				gl.glBegin(GL2.GL_QUADS);
				Point p0 = pointsParallele.get(j);
				Point p1 = pointsParallele1.get(j);
				Point p2 = pointsParallele1.get(j + 1);
				Point p3 = pointsParallele.get(j + 1);

				gl.glColor3d(1, 1, 1);
				gl.glVertex3d(p0.getX(), p0.getY(), p0.getZ());
				gl.glVertex3d(p1.getX(), p1.getY(), p1.getZ());
				gl.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
				gl.glVertex3d(p3.getX(), p3.getY(), p3.getZ());

				gl.glEnd();
			}
		}
	}

	/**
	 * Dessine le corps du sous-marin en reprenant les fonctions dessinerSphere() et dessinerCylindre()
	 */
	public void dessinerCorpsSousMarin(GL2 gl){
		dessinerCylindre(gl);
		gl.glPushMatrix();
		gl.glTranslatef(0,0,longueurSousMarin/2);
		dessinerSphere(gl);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glTranslatef(0,0,-longueurSousMarin/2);
		gl.glRotatef(180,0,1,0);
		dessinerSphere(gl);
		gl.glPopMatrix();
	}

	/**
	 * Dessine toutes les éléments du moteur du sous-marin
	 */
	public void dessinerMoteur(GL2 gl) {
        dessinerCylindreHelice(gl);
		gl.glRotatef((float) Math.toDegrees(theta), 0, 0,1);
		gl.glPushMatrix();
			gl.glPushMatrix();
				gl.glTranslatef(0,0,-this.rayonSousMarin+this.rayonSousMarin/2);
				gl.glScalef(0.05f,1f,1f);
				dessinerHelice(gl);
			gl.glPopMatrix();
			gl.glPushMatrix();
				gl.glRotatef(90,0,0,1);
				gl.glTranslatef(0,0,-this.rayonSousMarin+this.rayonSousMarin/2);
				gl.glScalef(0.05f,1f,1f);
				dessinerHelice(gl);
			gl.glPopMatrix();
		gl.glPopMatrix();
	}

	public void dessinerCylindreHelice(GL2 gl) {
		ArrayList<ArrayList<Point>> pointsCylindre = sousMarin.getPointsCylindre();
		for (int i = 0; i < pointsCylindre.size() - 1; i++) {
			ArrayList<Point> pointsParallele = pointsCylindre.get(i);
			ArrayList<Point> pointsParallele1 = pointsCylindre.get(i + 1);
			for (int j = 0; j < sousMarin.getNbMeridienCylindre(); j++) {
				Point p0 = pointsParallele.get(j);
				Point p1 = pointsParallele1.get(j);
				Point p2 = pointsParallele1.get(j + 1);
				Point p3 = pointsParallele.get(j + 1);
				gl.glBegin(GL2.GL_QUADS);
				gl.glVertex3d(p0.getX(), p0.getY(), p0.getZ());
				gl.glVertex3d(p1.getX(), p1.getY(), p1.getZ());
				gl.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
				gl.glVertex3d(p3.getX(), p3.getY(), p3.getZ());
				gl.glEnd();

				gl.glBegin(GL2.GL_QUADS);
				gl.glVertex3d(p3.getX(), p3.getY(), p3.getZ());
				gl.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
				gl.glVertex3d(p1.getX(), p1.getY(), p1.getZ());
				gl.glVertex3d(p0.getX(), p0.getY(), p0.getZ());
				gl.glEnd();
			}
		}
		gl.glPushMatrix();
		gl.glScalef(0.25f,0.25f,1);
		dessinerBaseHelice(gl);
		gl.glPopMatrix();
	}

	/**
	 * Dessine le cylindre servant de base aux hélices
	 */
	public void dessinerBaseHelice(GL2 gl) {
		ArrayList<ArrayList<Point>> pointsBaseHelice = sousMarin.getPointsBaseHelice();
		for (int i= 0; i < pointsBaseHelice.size()-1; i++){
			ArrayList<Point> pointsParallele = pointsBaseHelice.get(i);
			ArrayList<Point> pointsParallele1 = pointsBaseHelice.get(i + 1);
			for (int j = 0; j < sousMarin.getNbMeridienBaseHelice(); j++) {
				Point p0 = pointsParallele.get(j);
				Point p1 = pointsParallele1.get(j);
				Point p2 = pointsParallele1.get(j + 1);
				Point p3 = pointsParallele.get(j + 1);
				gl.glBegin(GL2.GL_QUADS);
				gl.glVertex3d(p0.getX(), p0.getY(), p0.getZ());
				gl.glVertex3d(p1.getX(), p1.getY(), p1.getZ());
				gl.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
				gl.glVertex3d(p3.getX(), p3.getY(), p3.getZ());
				gl.glEnd();

				if (i == 0){
					gl.glBegin(GL2.GL_TRIANGLES);
					gl.glVertex3d(sousMarin.getPointsBaseHelice().get(sousMarin.getNbParalleleBaseHelice()-1).get(j + 1).getX(), sousMarin.getPointsBaseHelice().get(sousMarin.getNbParalleleBaseHelice()-1).get(j + 1).getY(), sousMarin.getPointsBaseHelice().get(sousMarin.getNbParalleleBaseHelice()-1).get(j + 1).getZ());
					gl.glVertex3d(sousMarin.getPointsBaseHelice().get(sousMarin.getNbParalleleBaseHelice()-1).get(j).getX(), sousMarin.getPointsBaseHelice().get(sousMarin.getNbParalleleBaseHelice()-1).get(j).getY(), sousMarin.getPointsBaseHelice().get(sousMarin.getNbParalleleBaseHelice()-1).get(j).getZ());
					gl.glVertex3d(sousMarin.getExtremiteBaseHelice().getX(), sousMarin.getExtremiteBaseHelice().getY(), sousMarin.getExtremiteBaseHelice().getZ());
					gl.glEnd();
				}
			}
		}
	}

	/**
	 * Dessine les hélices du sous-marin
	 */
	public void dessinerHelice(GL2 gl) {
		ArrayList<ArrayList<Point>> pointsSphere = sousMarin.getPointsSphere();
		for (int i = 0; i < (pointsSphere.size()-1); i++) {
			ArrayList<Point> pointsParallele = pointsSphere.get(i);
			ArrayList<Point> pointsParallele1 = pointsSphere.get(i+1);
			for (int j = 0; j < sousMarin.getNbMeridienSphere(); j++) {
				gl.glBegin(GL2.GL_QUADS);
				Point p0 = pointsParallele.get(j);
				Point p1 = pointsParallele1.get(j);
				Point p2 = pointsParallele1.get(j + 1);
				Point p3 = pointsParallele.get(j + 1);

				gl.glColor3d(1, 1, 1);
				gl.glVertex3d(p0.getX(), p0.getY(), p0.getZ());
				gl.glVertex3d(p1.getX(), p1.getY(), p1.getZ());
				gl.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
				gl.glVertex3d(p3.getX(), p3.getY(), p3.getZ());

				gl.glEnd();
			}
		}
	}

	public void dessinerGouvernail() {

	}

	public void dessinerCockpit(GL2 gl) {
		ArrayList<ArrayList<Point>> pointsCockpit = sousMarin.getPointsCockpit();
		for (int i = 0; i < pointsCockpit.size() - 1; i++) {
			ArrayList<Point> pointsParallele = pointsCockpit.get(i);
			ArrayList<Point> pointsParallele1 = pointsCockpit.get(i + 1);
			for (int j = 0; j < sousMarin.getNbMeridienCockpit(); j++) {
				if (i == 0) {
					gl.glBegin(GL2.GL_TRIANGLES);

					gl.glVertex3d(sousMarin.getPointsCockpit().get(0).get(j + 1).getX(), sousMarin.getPointsCockpit().get(0).get(j + 1).getY(), sousMarin.getPointsCockpit().get(0).get(j + 1).getZ());
					gl.glVertex3d(sousMarin.getPointsCockpit().get(0).get(j).getX(), sousMarin.getPointsCockpit().get(0).get(j).getY(), sousMarin.getPointsCockpit().get(0).get(j).getZ());
					gl.glVertex3d(sousMarin.getFaceCockpit()[0].getX(), sousMarin.getFaceCockpit()[0].getY(), sousMarin.getFaceCockpit()[0].getZ());


					gl.glEnd();

					gl.glBegin(GL2.GL_TRIANGLES);

					gl.glVertex3d((sousMarin.getPointsCockpit().get(sousMarin.getNbParalleleCockpit()).get(j).getX()), sousMarin.getPointsCockpit().get(sousMarin.getNbParalleleCockpit()).get(j).getY(), sousMarin.getPointsCockpit().get(sousMarin.getNbParalleleCockpit()).get(j).getZ());
					gl.glVertex3d(sousMarin.getPointsCockpit().get(sousMarin.getNbParalleleCockpit()).get(j + 1).getX(), sousMarin.getPointsCockpit().get(sousMarin.getNbParalleleCockpit()).get(j + 1).getY(), sousMarin.getPointsCockpit().get(sousMarin.getNbParalleleCockpit()).get(j + 1).getZ());
					gl.glVertex3d(sousMarin.getFaceCockpit()[1].getX(), sousMarin.getFaceCockpit()[1].getY(), sousMarin.getFaceCockpit()[1].getZ());

					gl.glEnd();
				}

				gl.glBegin(GL2.GL_QUADS);
				Point p0 = pointsParallele.get(j);
				Point p1 = pointsParallele1.get(j);
				Point p2 = pointsParallele1.get(j + 1);
				Point p3 = pointsParallele.get(j + 1);

				gl.glVertex3d(p3.getX(), p3.getY(), p3.getZ());
				gl.glVertex3d(p2.getX(), p2.getY(), p2.getZ());
				gl.glVertex3d(p1.getX(), p1.getY(), p1.getZ());
				gl.glVertex3d(p0.getX(), p0.getY(), p0.getZ());

				gl.glEnd();
			}
		}
		gl.glPushMatrix();
		gl.glTranslatef(0,-this.rayonSousMarin/1.25f*3.5f,0);
		gl.glScalef(1,0.75f,0.75f);
		dessinerCylindre(gl);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslatef(0,this.rayonSousMarin*2.1f,0);
		gl.glRotatef(90,0,1,0);
		gl.glScalef(0.6f,1,1);
		dessinerCylindre(gl);
		gl.glPopMatrix();
	}

	//GETTER AND SETTER
	//*************************************************************
	public float getView_rotx() {
		return view_rotx;
	}
		
	public void setView_rotx(float view_rotx) {
		this.view_rotx = view_rotx;
	}
		
	public float getView_roty() {
		return view_roty;
	}
		
	public void setView_roty(float view_roty) {
		this.view_roty = view_roty;
	}
	
	public float getScale() {
		return scale;
	}
		
	public void setScale(float scale2) {
		this.scale = scale2;
	}
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x += x;
	}

	public float getDelta() {
		return delta;
	}

	public void setDelta(float delta) {
		this.delta = (float) ((this.delta + delta) % (2*Math.PI));
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z += z;
	}

	public void setTheta(float theta) {
		this.theta = (float) ((this.theta + theta) % (2*Math.PI));
	}

	public void setLambda(float lambda) {
		this.lambda = (float) ((this.lambda + lambda) % (2*Math.PI));
	}

	public float getLambda() {
		return lambda;
	}
}