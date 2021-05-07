/**
 * 
 */
package com.algm.sck;

import java.awt.event.KeyEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import actores.Controller;
import actores.Laser;
import actores.NanoBot;
import actores.Virus;

/**
 * @author Angel
 *
 */
public class PantallaJuego extends Pantalla {
	/**
	 * https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/Stage.html
	 * Un gr�fico de escena 2D que contiene jerarqu�as de actors. Stage maneja la
	 * ventana gr�fica y distribuye eventos de entrada. setViewport(Viewport)
	 * controla las coordenadas utilizadas dentro del escenario y configura la
	 * c�mara utilizada para convertir entre las coordenadas del escenario y las
	 * coordenadas de la pantalla. Un escenario debe recibir eventos de entrada para
	 * que pueda distribuirlos a los actores. Por lo general, esto se hace pasando
	 * el escenario a Gdx.input.setInputProcessor. Se InputMultiplexerpuede usar
	 * para manejar eventos de entrada antes o despu�s de que lo haga la etapa. Si
	 * un actor maneja un evento devolviendo verdadero desde el m�todo de entrada,
	 * entonces el m�todo de entrada de la etapa tambi�n devolver� verdadero, lo que
	 * provocar� que los procesadores de entrada posteriores no reciban el evento.
	 * El escenario y sus componentes (como Actores y Oyentes) no son seguros para
	 * subprocesos y solo deben actualizarse y consultarse desde un solo subproceso
	 * (presumiblemente el subproceso principal de procesamiento). Los m�todos deben
	 * ser reentrantes, por lo que puede actualizar Actores y Etapas desde
	 * devoluciones de llamada y controladores. Stagepuede ser extremadamente �til
	 * si necesita controles en pantalla (botones, joystick, etc.) puede usar clases
	 * de scene2d.ui
	 * 
	 * 1200*600 Res. Original
	 */

	Stage stage;
	private Laser laser;
	private NanoBot nanoBot;
	private Virus virus;
	private Controller control;

	public PantallaJuego(SarsCovKiller juego) {
		super(juego);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		control = new Controller();
		laser = new Laser();
		virus = new Virus();
		nanoBot = new NanoBot();

		nanoBot.setPosition(20, 250);
		stage.setKeyboardFocus(nanoBot);

		stage.addActor(nanoBot);
//		stage.addActor(laser);
//		stage.addActor(virus);
//		stage.addActor(control);

		nanoBot.addListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				switch (keycode) {
				case Input.Keys.S:
					nanoBot.vectorNanoBot.y = -300;
					return true;

				case Input.Keys.W:
					nanoBot.vectorNanoBot.y = 300;
					// nanoBot.vectorNanoBot.x = 0;
					return true;

				case Input.Keys.A:
					nanoBot.vectorNanoBot.x = -300;
					// nanoBot.vectorNanoBot.y = 0;
					return true;

				case Input.Keys.D:
					nanoBot.vectorNanoBot.x = 300;
					// nanoBot.vectorNanoBot.y = 0;
					return true;

				default:
					return false;
				}

			}


			@Override
			public boolean keyUp(InputEvent event, int keycode) {

//				if(keycode != Input.Keys.SPACE) {
//					return false;
//				}
				switch (keycode) {
				case Input.Keys.S: // 47
					//if (keycode != 51)
						
						nanoBot.vectorNanoBot.y = 0;
					return true;
					

				case Input.Keys.W: // 51
					if (keycode != 47)
						nanoBot.vectorNanoBot.y = 0;
					return true;

				case Input.Keys.A: // 29
					if (keycode != 32)
						nanoBot.vectorNanoBot.x = 0;
					return true;

				case Input.Keys.D: // 32
					if (keycode != 29)
						nanoBot.vectorNanoBot.x = 0;
					return true;

				case Input.Keys.SPACE: // 62
					Laser laser = new Laser();
					laser.setPosition(nanoBot.getX() + nanoBot.getWidth(), nanoBot.getY());
					stage.addActor(laser);
					return true;

				default:
					return false;

				}
			}

		});

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
		// stage.setViewport
	}

	@Override
	public void render(float delta) {
		// stage.clear();
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(); // Actulizar
		stage.draw(); // Dibujar
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose(); // Destruir pantalla
	}

}
