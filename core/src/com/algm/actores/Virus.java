package com.algm.actores;

import com.algm.pantallas.PantallaJuego;
import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Virus extends Actor {

	private int velX, velY;
	private Texture texture;
	private TextureRegion virus;
	private TextureRegion[] texturaRegionMov;
	private Vector2 vector;
	private float tiempo;
	private Animation animacion;
	private Rectangle rectangle;
	private PantallaJuego pantallaJuego;
	private float velAnimacion;

	public Virus() {
		velX = -500;
		velY = -100;
		velAnimacion = 0.1f;

		texture = new Texture(Gdx.files.internal(SarsCovKiller.path("anivirus.png")));
		//		texture = new Texture(Gdx.files.internal(SarsCovKiller.path("anivirus7.png")));


		// Crear array temporal para dividir textura (10 subtexturas)
		TextureRegion[][] temporalRegions = TextureRegion.split(texture, texture.getWidth() / 10, texture.getHeight());
		// Crear regiones en movimiento para TextureRegion;
		texturaRegionMov = new TextureRegion[10];
		for (int j = 0; j < 10; j++) {
			texturaRegionMov[j] = (temporalRegions[0][j]);
		}
		// Crear animación (Tiempo del Frame (0.10seg), TextureRegions)
		animacion = new Animation(velAnimacion, texturaRegionMov);
		// Objeto para poder variar animación
		// animacionActual = animacion;
		// Velocidad inicial 0
		vector = new Vector2(0, 0);
		tiempo = 0f;
		virus = temporalRegions[0][0];
		setSize(virus.getRegionWidth(), virus.getRegionHeight());

		// Rectangle: Encapsula un rectángulo 2D definido por su punto de esquina en la
		// parte inferior izquierda y sus extensiones en x (ancho) e y (alto)
		rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// Color color = getColor();
		// batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		batch.draw(virus, getX(), getY(), virus.getRegionWidth(), virus.getRegionHeight());
		//System.out.println("Rectangle VIRUS x: "+rectangle.getX()+" y: "+rectangle.getY()+ " "+rectangle.getHeight()+" "+rectangle.getWidth());


	}

	@Override
	public void act(float delta) {
		super.act(delta);
		// Tiempo que pasa desde el último frame render.
		tiempo += Gdx.graphics.getDeltaTime();
		virus = (TextureRegion) animacion.getKeyFrame(tiempo, true);
		// Se mueve principalmente en el eje X solo unos grados en Y
		moveBy(velX * delta, velY * delta);

		rectangle.x = getX();
		rectangle.y = getY();
		rectangle.width = getWidth();
		rectangle.height = getHeight();

		// Elimina actor cuando sale de la pantalla visible
		if (getX() < 0 || getY() < 0 || getY() > getStage().getHeight()) {
			remove();
			// System.out.println("eliminado actor virus");
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

	public TextureRegion getVirus() {
		return virus;
	}

	public void setVirus(TextureRegion virus) {
		this.virus = virus;
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
