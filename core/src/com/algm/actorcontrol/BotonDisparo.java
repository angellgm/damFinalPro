package com.algm.actorcontrol;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public class BotonDisparo extends Button {

	private TextureRegion btDisparo;

	public BotonDisparo() {
		// Inicializar textura
		btDisparo = new TextureRegion(SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("ui/botonDisparo.png"), Texture.class), 90, 90);

		// Tamaño igual al de la textura
		setSize(btDisparo.getRegionWidth(), btDisparo.getRegionHeight());

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(btDisparo, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());

	}

	private ButtonStyle ButtonStyle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

}
