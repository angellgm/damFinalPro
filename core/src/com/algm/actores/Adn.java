package com.algm.actores;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Adn extends Actor {

	private int velX, velY;
	private Texture texture;
	private TextureRegion adn;
	private TextureRegion[] texturaRegionMov;
	private Vector2 vector;
	private float tiempo;
	private Animation animacion;

	public Adn() {
		texture = new Texture(Gdx.files.internal("adn.png"));

		// Crear array temporal para dividir textura (9 subtexturas)
		TextureRegion[][] temporalRegions = TextureRegion.split(texture, texture.getWidth() / 9, texture.getHeight());
		// Crear regiones en movimiento para TextureRegion;
		texturaRegionMov = new TextureRegion[9];
		for (int j = 0; j < 9; j++) {
			texturaRegionMov[j] = (temporalRegions[0][j]);
		}
		// Crear animación (Tiempo del Frame (0.10seg), TextureRegions)
		animacion = new Animation(0.1f, texturaRegionMov);
		// Objeto para poder variar animación
		// animacionActual = animacion;
		// Velocidad inicial 0
		vector = new Vector2(0, 0);
		tiempo = 0f;
		adn = temporalRegions[0][0];
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(adn, getX(), getY(), adn.getRegionWidth(), adn.getRegionHeight());
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		// Tiempo que pasa desde el último frame render.
		tiempo += Gdx.graphics.getDeltaTime();
		adn = (TextureRegion) animacion.getKeyFrame(tiempo, true);
		//Se mueve solo en el eje x
		moveBy(600 * delta, 0);
		//Eliminar actor al llegar al final de la pantalla
		if (getX() > getStage().getWidth()) {
			remove();
		}
		
	}
	
}
