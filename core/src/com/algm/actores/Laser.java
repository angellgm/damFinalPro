package com.algm.actores;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Laser extends Actor {
	// Área de la textura
	private TextureRegion laser;
	private Vector2 vectorLaser;

	public Laser() {
		// Inicializar textura
		laser = new TextureRegion(SarsCovKiller.ASSETMANAGER.get("laser.png", Texture.class), 120, 80);
		// Tamaño igual al de la textura
		setSize(laser.getRegionWidth(), laser.getRegionHeight());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(laser, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
				getScaleX(), getScaleY(), getRotation());
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		//Se mueve solo en el eje x
		moveBy(500 * delta, 0);
		//Eliminar actor al llegar al final de la pantalla
		if (getX() > getStage().getWidth()) {
			remove();
		}
		
		
	}
	
}
