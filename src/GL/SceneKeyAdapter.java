package GL;

import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;

/**
 * Classe permettant de récupérer les infos d'input clavier
 */
class SceneKeyAdapter extends KeyAdapter {
	private float view_rotx, view_roty;
	private float zoom = 1;
    float x;
    float delta;
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
		x = myGLEventListener.getX();
        delta = myGLEventListener.getDelta();

        if(KeyEvent.VK_LEFT == kc) {
            myGLEventListener.setDelta((float) (Math.PI/32));
            myGLEventListener.setX((float) Math.sin(myGLEventListener.getDelta()));
            myGLEventListener.setZ((float) Math.cos(myGLEventListener.getDelta()));
            myGLEventListener.setTheta((float) Math.PI/6);

            if (myGLEventListener.getLambda()<= Math.PI/8){
                myGLEventListener.setLambda((float) Math.PI/64);
            }
        } 
        
        else if(KeyEvent.VK_RIGHT == kc) {
            myGLEventListener.setDelta((float) -(Math.PI/32));
            myGLEventListener.setX((float) Math.sin(myGLEventListener.getDelta()));
            myGLEventListener.setZ((float) Math.cos(myGLEventListener.getDelta()));
            myGLEventListener.setTheta((float) Math.PI/6);
            if (myGLEventListener.getLambda()>= -Math.PI/8){
                myGLEventListener.setLambda((float) -Math.PI/64);
            }
        }
        
        else if(KeyEvent.VK_UP == kc) {
            myGLEventListener.setX((float) Math.sin(myGLEventListener.getDelta()));
            myGLEventListener.setZ((float) Math.cos(myGLEventListener.getDelta()));
            myGLEventListener.setTheta((float) Math.PI/6);

            if (myGLEventListener.getLambda()>0){
                myGLEventListener.setLambda((float) -Math.PI/64);
            } else if (myGLEventListener.getLambda()<0){
                myGLEventListener.setLambda((float) Math.PI/64);
            }
        }
        
        else if(KeyEvent.VK_DOWN == kc) {
            myGLEventListener.setX((float) -Math.sin(myGLEventListener.getDelta()));
            myGLEventListener.setZ((float) -Math.cos(myGLEventListener.getDelta()));
            myGLEventListener.setTheta((float) -Math.PI/6);

            if (myGLEventListener.getLambda()>0){
                myGLEventListener.setLambda((float) -Math.PI/64);
            } else if (myGLEventListener.getLambda()<0){
                myGLEventListener.setLambda((float) Math.PI/64);
            }
        }
        else {
        	//System.out.println(e.getKeyCode());
        }
        
        myGLEventListener.setView_rotx(view_rotx);
        myGLEventListener.setView_roty(view_roty);
    }
	
	public float getScale() {
		return zoom;
	}
	
	public void setScale(float scale) {
		this.zoom = scale;
	}

  }