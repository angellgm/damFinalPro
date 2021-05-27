package com.algm.actorpantalla;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FondoMenu extends Actor {

	private Texture texture;
	private TextureRegion fondo;
	private TextureRegion[] texturaRegionMov;
	private Vector2 vector;
	private float tiempo;
	private Animation animacion;
	private String png;

	public FondoMenu() {

//		Crear regiones en movimiento para TextureRegion;
		texturaRegionMov = new TextureRegion[202];
		for (int i = 0; i < 202; i++) {
			png = "fondo/pMenu/".concat("fondo-" + i + "").concat(".png");
			texturaRegionMov[i] = new TextureRegion(new Texture(Gdx.files.internal(png)));
		}
		// Crear animación (Tiempo del Frame (0.04seg), TextureRegions)
		animacion = new Animation(0.05f, texturaRegionMov);
		// Objeto para poder variar animación
		// animacionActual = animacion;
		// Velocidad inicial 0
		vector = new Vector2(0, 0);
		tiempo = 0f;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(fondo, getX(), getY(), fondo.getRegionWidth(), fondo.getRegionHeight());
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		// Tiempo que pasa desde el último frame render.
		tiempo += Gdx.graphics.getDeltaTime();
		fondo = (TextureRegion) animacion.getKeyFrame(tiempo, true);

	}

}
