package actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Controller extends Touchpad {

	private static float deadzoneRadius;
	private static Skin touchpadSkin;

	public Controller(float deadzoneRadius, Skin skin) {
		super(deadzoneRadius, skin);
		// TODO Auto-generated constructor stub
	}

	public Controller(float deadzoneRadius, TouchpadStyle style) {
		super(deadzoneRadius, style);
		// TODO Auto-generated constructor stub
	}

	public Controller(float deadzoneRadius, Skin skin, String styleName) {
		super(deadzoneRadius, skin, styleName);
		// TODO Auto-generated constructor stub
	}
	
	public Controller(){ 
	    super(deadzoneRadius, Controller.getTouchPadStyle()); 
	    setBounds(getOriginX(), getOriginY(), getWidth(), getHeight());	
	}
	
	private static TouchpadStyle getTouchPadStyle(){
		
		Skin touchpadSkin = new Skin(); 
	    touchpadSkin.add("touchFondo", new Texture("touchFondo.png")); 
	    touchpadSkin.add("touchPalanca", new Texture("touchPalanca.png")); 

	    TouchpadStyle touchpadStyle = new TouchpadStyle(); 
	    touchpadStyle.background = touchpadSkin.getDrawable("touchFondo"); 
	    touchpadStyle.knob = touchpadSkin.getDrawable("touchPalanca"); 
	    return touchpadStyle; 
	}
	
	@Override 
	public void act (float delta) { 
	    super.act(delta); 
	    if(isTouched()){ 
	        // Mover al personaje
	    } 
	}
}
