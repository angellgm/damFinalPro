package com.algm.actorpantalla;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FondoPantallaJuego extends Actor {

	private int velX, velY;
	private Texture texture;
	private TextureRegion fondo;
	private TextureRegion[] texturaRegionMov;
	private Vector2 vector;
	private float tiempo;
	private Animation animacion;
	private String png;

	public FondoPantallaJuego() {

//		// Crear regiones en movimiento para TextureRegion;
		texturaRegionMov = new TextureRegion[47];
		for (int i = 0; i < 47; i++) {
            png ="fondo/pJuego/".concat(i+"").concat(".png");
			texturaRegionMov[i] = new TextureRegion(new Texture(Gdx.files.internal(png)));
		}
		// Crear animaci?n (Tiempo del Frame (0.04seg), TextureRegions)
		animacion = new Animation(0.04f, texturaRegionMov);
		// Objeto para poder variar animaci?n
		// animacionActual = animacion;
		// Velocidad inicial 0
		vector = new Vector2(0, 0);
		tiempo = 0f;
		//adn = temporalRegions[0][0];
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		//batch.draw(fondo, getX(), getY(), fondo.getRegionWidth(), fondo.getRegionHeight());
		batch.draw(fondo, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		// Tiempo que pasa desde el ?ltimo frame render.
		tiempo += Gdx.graphics.getDeltaTime();
		fondo = (TextureRegion) animacion.getKeyFrame(tiempo, true);
		
	}
	
}
