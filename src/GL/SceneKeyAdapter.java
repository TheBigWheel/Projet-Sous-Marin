package GL;

import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;

class SceneKeyAdapter extends KeyAdapter {
	private float view_rotx, view_roty;
	private float zoom = 1;
	
	private MyGLEventListener myGLEventListener;

	
	public SceneKeyAdapter (MyGLEventListener _myGLEventListener) {
		myGLEventListener = _myGLEventListener;
		view_rotx = _myGLEventListener.getView_rotx();
		view_roty = _myGLEventListener.getView_roty();
	}


	@Override
	public void keyPressed(KeyEvent e) {
        int kc = e.getKeyCode();

        view_rotx = myGLEventListener.getView_rotx();
		view_roty = myGLEventListener.getView_roty();
		
        if(140 == kc) {
            zoom += 0.1;
            //System.out.println("Key pressed: zoom in="+zoom);
        } 
        
        if(139 == kc) {
            zoom -= 0.1;
            //System.out.println("Key pressed: zoom out");
        } 
        
        if(KeyEvent.VK_LEFT == kc) {
            view_roty -= 1;
            //System.out.println("Key pressed: view_roty="+view_roty);
        } 
        
        else if(KeyEvent.VK_RIGHT == kc) {
            view_roty += 1;
            //System.out.println("Key pressed: view_roty="+view_roty);
        } 
        
        else if(KeyEvent.VK_UP == kc) {
            view_rotx -= 1;
            //System.out.println("Key pressed: view_rotx="+view_rotx);
        } 
        
        else if(KeyEvent.VK_DOWN == kc) {
            view_rotx += 1;
            //System.out.println("Key pressed: view_rotx="+view_rotx);
        }
        else {
        	//System.out.println(e.getKeyCode());
        }
        
        myGLEventListener.setView_rotx(view_rotx);
        myGLEventListener.setView_roty(view_roty);
        myGLEventListener.setScale(zoom);
    }
	
	public float getScale() {
		return zoom;
	}
	
	public void setScale(float scale) {
		this.zoom = scale;
	}

  }