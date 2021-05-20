package com.algm.actores;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BarraEnergia extends Actor {
	private TextureRegion barraEnergia;

	public BarraEnergia() {
		// Inicializar textura
		barraEnergia = new TextureRegion(SarsCovKiller.ASSETMANAGER.get("/SarsCovKiller-android/assets/ui/barraEnergia.png", Texture.class), 500, 250);
		// marcoBarraEnergia = new
		// TextureRegion(SarsCovKiller.ASSETMANAGER.get("/ui/marcoBarraEnergia.png",
		// Texture.class), 500, 250);

		// Tamaño igual al de la textura
		setSize(barraEnergia.getRegionWidth(), barraEnergia.getRegionHeight());
		// setSize(marcoBarraEnergia.getRegionWidth(),
		// marcoBarraEnergia.getRegionHeight());
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(barraEnergia, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

}
