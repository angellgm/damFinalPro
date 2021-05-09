package com.algm.actores;

import java.util.Iterator;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Virus extends Actor {

	private int x, y;
	private Texture texture;
	private TextureRegion virus;
	private TextureRegion[] texturaRegionMov;
	private Vector2 vector;
	private float tiempo;
	private Animation animacion, animacionActual;

	public Virus() {
		// Velocidad inicial 0
		vector = new Vector2(0, 0);

		texture = new Texture(Gdx.files.internal("anivirus.png"));
		// Crear array temporal para dividir textura (10 subtexturas)
		TextureRegion[][] temporalRegions = TextureRegion.split(texture, texture.getWidth() / 10, texture.getHeight());
		// Crear regiones en movimiento para TextureRegion;
		texturaRegionMov = new TextureRegion[10];
		for (int j = 0; j < 10; j++) {
			texturaRegionMov[j] = (temporalRegions[0][j]);
		}
		// Crear animaci�n (Tiempo del Frame (0.10seg), TextureRegions)
		animacion = new Animation(0.1f, texturaRegionMov);
		// Objeto para poder variar animaci�n
		animacionActual = animacion;

		tiempo = 0f;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		// Animar TextureRegion en bucle (true)
		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(virus, 60, 60);
		// batch.draw(virus, getX(), getY(), getOriginX(), getOriginY(), getWidth(), //
		// getHeight(), getScaleX(), getScaleY(), getRotation());
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		// Tiempo que pasa desde el �ltimo frame render.
		tiempo += Gdx.graphics.getDeltaTime();
		virus = (TextureRegion) animacionActual.getKeyFrame(tiempo, true);

	}

	public void dispose() {
	}

}
