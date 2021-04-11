package GL;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * Classe initialisant l'affichage de la fenêtre de l'application
 */
public class MainFrame extends JFrame {

    public MainFrame(){
        setTitle("Projet Sous-marin");
        setSize(840, 640);

        //It currently contains the minimal number of routines which allow configuration on all supported window systems.
        //A GLCapabilities object specifies the OpenGL parameters for a newly-created widget, such as the color, alpha,
        //z-buffer and accumulation buffer bit depths and whether the widget is double-buffered.
        //The default capabilities are loosely specified but provide for truecolor RGB, a reasonably large depth buffer,
        //double-buffered, with no alpha, stencil, or accumulation buffers.
        GLCapabilities glCapabilities = new GLCapabilities(GLProfile.getDefault());

        //Creation du canvas OpenGL
        //Support du dessin
        //A heavyweight AWT component which provides OpenGL rendering support
        GLCanvas glCanvas = new GLCanvas(glCapabilities);


        //GLEventistener interface works along with GLCanvas class.
        //It responds to the changes in GLCanvas class and to the drawing requests made by them.
        MyGLEventListener glListener = new MyGLEventListener();

        //Callback attach�e � la surface dessinable
        glCanvas.addGLEventListener(glListener);

        //Surface dessinable attachee a la fenetre
        add(glCanvas);

        //Cr�ation de l'animator
        final FPSAnimator animator = new FPSAnimator(glCanvas, 60);


        //Gestion de la fermeture de la fenetre
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });


        //Demarrage de l'animator qui se charge d'appeler la fonction display()
        animator.start();

    }
    private static final long serialVersionUID = -1227038124975588633L;
}