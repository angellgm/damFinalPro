package com.algm.actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Pad extends Touchpad {

	private static float zonaMuerta = 5;
	private static Skin controllerSkin;

	public Pad(float controllerSkin, Skin skin) {
		super(controllerSkin, skin);
		// TODO Auto-generated constructor stub
	}

	public Pad(float controllerSkin, TouchpadStyle style) {
		super(controllerSkin, style);
		// TODO Auto-generated constructor stub
	}

	public Pad(float controllerSkin, Skin skin, String styleName) {
		super(controllerSkin, skin, styleName);
		// TODO Auto-generated constructor stub
	}

	public Pad() {
		super(zonaMuerta, Pad.getTouchPadStyle());
		//setBounds(getOriginX(), getOriginY(), getWidth(), getHeight());
		setBounds(getOriginX(), getOriginY(), getWidth(), getHeight());
		setPosition(getX(), getY());
		setStyle(getTouchPadStyle());

	}

	private static TouchpadStyle getTouchPadStyle() {

		Skin controllerSkin = new Skin();
		controllerSkin.add("touchFondo", new Texture("ui/touchFondo.png"));
		controllerSkin.add("touchPalanca", new Texture("ui/touchPad.png"));

		TouchpadStyle touchpadStyle = new TouchpadStyle();
		touchpadStyle.background = controllerSkin.getDrawable("touchFondo");
		touchpadStyle.knob = controllerSkin.getDrawable("touchPalanca");
		return touchpadStyle;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (isTouched()) {
			//System.out.println("getKnobPercentX() " + getKnobPercentX());
			//System.out.println("getKnobPercentY() " + getKnobPercentY());
			//System.out.println("getKnobX() " + getKnobX());
			//System.out.println("getKnobY() " + getKnobY()+"\n");
			//System.out.println("getPrefHeight() " + getPrefHeight());
			//System.out.println("getPrefWidth() " + getPrefWidth());
			//System.out.println("getResetOnTouchUp() " + getResetOnTouchUp());
		}
	}
}
