package com.algm.actores;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BarraEnergia extends Actor {
	
	private TextureRegion bEnergia;

	public BarraEnergia() {
		// Inicializar textura
		bEnergia = new TextureRegion(SarsCovKiller.ASSETMANAGER.get("ui/bEnergia.png", Texture.class), 250, 85);

		// Tama�o igual al de la textura
		setSize(bEnergia.getRegionWidth(), bEnergia.getRegionHeight());

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub

		batch.draw(bEnergia, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

}
