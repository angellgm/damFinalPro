package com.algm.actores;

import com.algm.actorcontrol.NivelEnergia;
import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class NanoBot extends Actor implements NivelEnergia {
	// Área de la textura
	private TextureRegion nanoBot;
	private Vector2 vector;
	private float energiaBot;
	private Rectangle rectangle;

	public NanoBot() {
		// Inicializar textura
		nanoBot = new TextureRegion(SarsCovKiller.ASSETMANAGER.get("nanobot.png", Texture.class), 120, 80);
		// Tamaño igual al de la textura
		setSize(nanoBot.getRegionWidth(), nanoBot.getRegionHeight());
		// Velocidad inicial 0
		vector = new Vector2(0, 0);
		// Rectangle: Encapsula un rectángulo 2D definido por su punto de esquina en la
		// parte inferior izquierda y sus extensiones en x (ancho) e y (alto)
		rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(nanoBot, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}

	// Movimiento según tiempo de actualización y la velocidad del actor
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		moveBy(vector.x * delta, vector.y * delta);
		// Mapear limites de escenario para el actor
		if (getX() < 0) {
			setX(0);
			vector.x = 0;

		} else if (getRight() > getStage().getWidth()) {
			setX(getStage().getWidth() - getWidth());
			vector.x = 0;
		}

		if (getY() < 0) {
			setY(0);
			vector.y = 0;

		} else if (getTop() > getStage().getHeight()) {
			setY(getStage().getHeight() - getHeight());
			vector.y = 0;
		}
		
		rectangle.x = getX();
		rectangle.y = getY();
		rectangle.width = getWidth();
		rectangle.height = getHeight();
				
		
		

	}

	public TextureRegion getNanoBot() {
		return nanoBot;
	}

	public void setNanoBot(TextureRegion nanoBot) {
		this.nanoBot = nanoBot;
	}

	public Vector2 getVectorNanoBot() {
		return vector;
	}

	public void setVectorNanoBot(Vector2 vectorNanoBot) {
		this.vector = vectorNanoBot;
	}

	@Override
	public float getEnergiaRango() {
		// TODO Auto-generated method stub
		return energiaBot;
	}

	@Override
	public void setEnergia(float energiaBot) {
		// TODO Auto-generated method stub
		this.energiaBot = energiaBot;

	}

	@Override
	public void subirEnergia(float energia) {
		energiaBot += energia;
	}

	public Vector2 getVector() {
		return vector;
	}

	public void setVector(Vector2 vector) {
		this.vector = vector;
	}

	public float getEnergiaBot() {
		return energiaBot;
	}

	public void setEnergiaBot(float energiaBot) {
		this.energiaBot = energiaBot;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

}
