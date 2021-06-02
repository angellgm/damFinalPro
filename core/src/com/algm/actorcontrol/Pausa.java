package com.algm.actorcontrol;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pausa extends Actor {

	private TextureRegion tRPausa;

	public Pausa() {
		// Inicializar textura
		tRPausa = new TextureRegion(SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("ui/pausa.png"), Texture.class), 450, 400);

		// Tamaño igual al de la textura
		setSize(tRPausa.getRegionWidth(), tRPausa.getRegionHeight());

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(tRPausa, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());

	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

}
