package com.algm.actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BonusAdn extends Actor {

	private int velX, velY;
	private Texture texture;
	private TextureRegion bonusAdn;
	private TextureRegion[] texturaRegionMov;
	private Vector2 vector;
	private float tiempo;
	private Animation animacion;
	private Rectangle rectangle;

	public BonusAdn() {
		texture = new Texture(Gdx.files.internal("bonusAdn.png"));

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
		bonusAdn = temporalRegions[0][0];
		setSize(bonusAdn.getRegionWidth(), bonusAdn.getRegionHeight());

		// Rectangle: Encapsula un rectángulo 2D definido por su punto de esquina en la
		// parte inferior izquierda y sus extensiones en x (ancho) e y (alto)
		rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(bonusAdn, getX(), getY(), bonusAdn.getRegionWidth(), bonusAdn.getRegionHeight());
		//System.out.println("Rectangle ADN x: "+rectangle.getX()+" y: "+rectangle.getY()+ " "+rectangle.getHeight()+" "+rectangle.getWidth());

	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		// Tiempo que pasa desde el último frame render.
		tiempo += Gdx.graphics.getDeltaTime();
		bonusAdn = (TextureRegion) animacion.getKeyFrame(tiempo, true);
		// Se mueve solo en el eje x
		moveBy(200 * delta, 0);

		// Posición y tamaño del rectangle igual al actor
		rectangle.x = getX();
		rectangle.y = getY();
		rectangle.width = getWidth();
		rectangle.height = getHeight();

		// Elimina actor cuando sale de la pantalla visible
		if (getX() > getStage().getWidth()) {
			remove();
			//System.out.println("eliminado actor adn");
		}

	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public TextureRegion getAdn() {
		return bonusAdn;
	}

	public void setAdn(TextureRegion adn) {
		this.bonusAdn = adn;
	}

	public TextureRegion[] getTexturaRegionMov() {
		return texturaRegionMov;
	}

	public void setTexturaRegionMov(TextureRegion[] texturaRegionMov) {
		this.texturaRegionMov = texturaRegionMov;
	}

	public Vector2 getVector() {
		return vector;
	}

	public void setVector(Vector2 vector) {
		this.vector = vector;
	}

	public float getTiempo() {
		return tiempo;
	}

	public void setTiempo(float tiempo) {
		this.tiempo = tiempo;
	}

	public Animation getAnimacion() {
		return animacion;
	}

	public void setAnimacion(Animation animacion) {
		this.animacion = animacion;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

}
