package com.algm.actores;

import java.util.Iterator;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.Game;
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

	private int velX, velY;
	private Texture texture;
	private Texture virus2;
	private TextureRegion virus;
	private TextureRegion[] texturaRegionMov;
	private Vector2 vector;
	private float tiempo;
	private Animation animacion, animacionActual;

	public Virus() {
		velX = -150;
		velY = -800;
		texture = new Texture(Gdx.files.internal("anivirus.png"));
		// virus = new TextureRegion(SarsCovKiller.ASSETMANAGER.get("anivirus.png",
		// Texture.class), 60, 60);
		// Crear array temporal para dividir textura (10 subtexturas)
		TextureRegion[][] temporalRegions = TextureRegion.split(texture, texture.getWidth() / 10, texture.getHeight());
		// Crear regiones en movimiento para TextureRegion;
		texturaRegionMov = new TextureRegion[10];
		for (int j = 0; j < 10; j++) {
			texturaRegionMov[j] = (temporalRegions[0][j]);
		}
		// Crear animación (Tiempo del Frame (0.10seg), TextureRegions)
		animacion = new Animation(0.3f, texturaRegionMov);
		// Objeto para poder variar animación
		animacionActual = animacion;
		// Velocidad inicial 0
		vector = new Vector2(0, 0);
		tiempo = 0f;
		virus = temporalRegions[0][0];

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// Color color = getColor();
		// batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(virus, getX(), getY(), virus.getRegionWidth(), virus.getRegionHeight());

	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		// Tiempo que pasa desde el último frame render.
		tiempo += Gdx.graphics.getDeltaTime();
		virus = (TextureRegion) animacionActual.getKeyFrame(tiempo, true);
		// Se mueve principalmente en el eje X solo unos grados en Y
		moveBy(velX * delta, (float) ((-velY + (float) (Math.random() * ((velY - (-velY)) + 1)))) * delta);
		//moveBy(velX * delta, (float) ((-velY + (float) (Math.random() * ((velY - (-velY)) + 1)))) * delta);
		// Eliminar actor al llegar al final de la pantalla
		if (getX() < 0) {
			remove();
		}
	}
}
