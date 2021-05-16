package com.algm.actores;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Fondo extends Actor {

	private int velX, velY;
	private Texture texture;
	private TextureRegion fondo;
	private TextureRegion[] texturaRegionMov;
	private Vector2 vector;
	private float tiempo;
	private Animation animacion;
	private String png;

	public Fondo() {

//		// Crear regiones en movimiento para TextureRegion;
		texturaRegionMov = new TextureRegion[47];
		for (int i = 0; i < 47; i++) {
            png ="fondo/".concat(i+"").concat(".png");
			texturaRegionMov[i] = new TextureRegion(new Texture(Gdx.files.internal(png)));
		}
		// Crear animación (Tiempo del Frame (0.10seg), TextureRegions)
		animacion = new Animation(0.09f, texturaRegionMov);
		// Objeto para poder variar animación
		// animacionActual = animacion;
		// Velocidad inicial 0
		vector = new Vector2(0, 0);
		tiempo = 0f;
		//adn = temporalRegions[0][0];
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
		//Se mueve solo en el eje x
		//moveBy(600 * delta, 0);
		//Eliminar actor al llegar al final de la pantalla
//		if (getX() > getStage().getWidth()) {
//			remove();
//		}
		
	}
	
}
