/**
 * 
 */
package com.algm.pantallas;

import com.algm.actorpantalla.FondoGameOver;
import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * @author Angel
 *
 */
public class PantallaGameOver extends Pantalla {
	private Stage stage;
	private FondoGameOver fondoGameOver;

	public PantallaGameOver(SarsCovKiller juego) {
		super(juego);
		//juego.setScreen(juego.P_GAMEOVER);

	}

	@Override
	public void show() {
		stage = new Stage();
		fondoGameOver = new FondoGameOver();
		fondoGameOver.setPosition(0, 0);
		stage.addActor(fondoGameOver);

	}

	@Override
	public void render(float delta) {
		// Limpiar pantalla para evitar trazos fantasma de los actores
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw(); // Dibujar

	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		stage.dispose(); // Destruir pantalla

	}

}
