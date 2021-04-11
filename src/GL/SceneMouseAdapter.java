package GL;

import com.jogamp.newt.Window;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLProfile;

/**
 * Classe permettant de récupérer les infos d'input souris
 */
class SceneMouseAdapter extends MouseAdapter {
	private int prevMouseX, prevMouseY;
	private float view_rotx, view_roty;
	private MyGLEventListener myGLEventListener;
	private float zoom = 0;

	public SceneMouseAdapter(MyGLEventListener _myGLEventListener) {
		myGLEventListener = _myGLEventListener;
	}

	public void mouseWheelMoved(MouseEvent e) {
	    if (e.getRotation()[1]>0){
            zoom -= 1;
        }
	    else {
	        zoom += 1;
        }

        myGLEventListener.setScale(zoom);
    }


	@Override
	public void mousePressed(MouseEvent e) {
        prevMouseX = e.getX();
        prevMouseY = e.getY();      
	}

    @Override
	public void mouseReleased(MouseEvent e) { }

    @Override
	public void mouseDragged(MouseEvent e) {
    	  
        view_rotx = myGLEventListener.getView_rotx();
  		view_roty = myGLEventListener.getView_roty();
  		
        final int x = e.getX();
        final int y = e.getY();
        int width=0, height=0;
        Object source = e.getSource();
        
        if(source instanceof Window) {
            Window window = (Window) source;
            width=window.getSurfaceWidth();
            height=window.getSurfaceHeight();
        } 
        
        else if(source instanceof GLAutoDrawable) {
        	GLAutoDrawable glad = (GLAutoDrawable) source;
            width=glad.getSurfaceWidth();
            height=glad.getSurfaceHeight();
        } 
        
        else if (GLProfile.isAWTAvailable() && source instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) source;
            width=comp.getWidth();
            height=comp.getHeight();
        } 
        
        else {
            throw new RuntimeException("Event source neither Window nor Component: "+source);
        }
        
        float thetaY = 360.0f * ( (float)(x-prevMouseX)/(float)width);
        float thetaX = 360.0f * ( (float)(prevMouseY-y)/(float)height);

        prevMouseX = x;
        prevMouseY = y;

        view_rotx += thetaX;
        view_roty += thetaY;
        
        myGLEventListener.setView_rotx(view_rotx);
        myGLEventListener.setView_roty(view_roty);
    }
}