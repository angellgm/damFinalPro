package com.algm.actorcontrol;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BarraEnergia extends Actor {

	private TextureRegion bEnergia;
	private NivelEnergia nivelEnergia;

	public BarraEnergia(NivelEnergia nivelEnergia) {
		this.nivelEnergia = nivelEnergia;

		// Inicializar textura
		bEnergia = new TextureRegion(SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("ui/bEnergia.png"), Texture.class), 166,
				70);


		// Tamaño igual al de la textura
		setSize(bEnergia.getRegionWidth(), bEnergia.getRegionHeight());

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		batch.draw(bEnergia, getX(), getY(), getOriginX(), getOriginY(), getWidth() * nivelEnergia.getEnergiaRango(),
				getHeight(), getScaleX(), getScaleY(), getRotation());
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
	}

}
