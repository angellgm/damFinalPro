package com.algm.actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Controller extends Touchpad {

	private static float zonaMuerta = 50;
	private static Skin controllerSkin;

	public Controller(float controllerSkin, Skin skin) {
		super(controllerSkin, skin);
		// TODO Auto-generated constructor stub
	}

	public Controller(float controllerSkin, TouchpadStyle style) {
		super(controllerSkin, style);
		// TODO Auto-generated constructor stub
	}

	public Controller(float controllerSkin, Skin skin, String styleName) {
		super(controllerSkin, skin, styleName);
		// TODO Auto-generated constructor stub
	}
	
	public Controller(){ 
	    super(zonaMuerta, Controller.getTouchPadStyle()); 
	    setBounds(getOriginX(), getOriginY(), getWidth(), getHeight());	
	}
	
	private static TouchpadStyle getTouchPadStyle(){
		
		Skin controllerSkin = new Skin(); 
	    controllerSkin.add("touchFondo", new Texture("touchFondo.png")); 
	    controllerSkin.add("touchPalanca", new Texture("touchPalanca.png")); 

	    TouchpadStyle touchpadStyle = new TouchpadStyle(); 
	    touchpadStyle.background = controllerSkin.getDrawable("touchFondo"); 
	    touchpadStyle.knob = controllerSkin.getDrawable("touchPalanca"); 
	    return touchpadStyle; 
	}
	
	@Override 
	public void act (float delta) { 
	    super.act(delta); 
	    if(isTouched()){ 
	       System.out.println((int)getKnobPercentX());
	        System.out.println((int)getKnobPercentY());
	        //System.out.println((int)getKnobX());
	        //System.out.println((int)getKnobY());
	    } 
	}
}
