package com.algm.actores;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MBarraEnergia extends Actor {

	private TextureRegion mBEnergia;

	public MBarraEnergia() {
		// Inicializar textura
		mBEnergia = new TextureRegion(SarsCovKiller.ASSETMANAGER.get("ui/mEnergia.png", Texture.class), 250,
				85);

		// Tamaño igual al de la textura
		setSize(mBEnergia.getRegionWidth(), mBEnergia.getRegionHeight());

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(mBEnergia, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());

	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

}
