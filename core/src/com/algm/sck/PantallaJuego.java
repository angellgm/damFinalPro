/**
 * 
 */
package com.algm.sck;

import java.awt.event.KeyEvent;
import java.sql.Time;

import com.algm.actores.Pad;
import com.algm.actores.Laser;
import com.algm.actores.NanoBot;
import com.algm.actores.Virus;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author Angel
 *
 */
public class PantallaJuego extends Pantalla {

	Stage stage;
	private Laser laser;
	private NanoBot nanoBot;
	private Virus virus;
	private Pad control;
	private boolean keyDownW;
	private boolean keyDownS;
	private boolean keyDownA;
	private boolean keyDownD;
	private float velocidadNanoBot;
	private float virusSpawn;

	public PantallaJuego(SarsCovKiller juego) {
		super(juego);
		virusSpawn = 1;
		virusSpawn += (float) Math.random();
		velocidadNanoBot = 500;
	}

	@Override
	public void show() {

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		laser = new Laser();
		virus = new Virus();
		// virus.setPosition(20, 250);

		nanoBot = new NanoBot();
		nanoBot.setPosition(20, 250);

		control = new Pad();
		control.setPosition(15, 15);

		if (Gdx.app.getType() == ApplicationType.Desktop) {
			stage.setKeyboardFocus(nanoBot);
			nanoBot.addListener(new ImputListener());
		}
		// Pendiente de testeo
		if (Gdx.app.getType() == ApplicationType.Android) {
			stage.addActor(control);
		}

		stage.addActor(nanoBot);
		stage.addActor(virus);
		stage.addActor(control);
	}

	@Override
	public void resize(int width, int height) {
		// Resize en función del tamaño
		super.resize(width, height);
	}

	@Override
	public void render(float delta) {
		// Limpiar pantalla para evitar trazos fantasma de los actores
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime()); // Actulizar
		// Delta == Tiempo que tarda el ordenador en procesar y renderizar un frame del
		// videojuego
		// Al restar DELTA a VIRUSSPAWN si el tiempo es inferior a 0 ya puede respawnear
		// otro enemigo
		virusSpawn = delta + (float) Math.random();
		// System.out.println("virusSpawn == " + virusSpawn);
		if (virusSpawn > 1) {
			Virus virus = new Virus();
			virus.setPosition(stage.getWidth(), stage.getHeight() * (float) Math.random());

			stage.addActor(virus);

			// Hay nuevo spawn
			virusSpawn = virusSpawn + (float) Math.random();
			// System.out.println("virusSpawn2 == " + virusSpawn);

		}
		controlPad(control.getKnobPercentX(), control.getKnobPercentY());

		stage.draw(); // Dibujar

	}

	private void controlPad(float knobPercentX, float knobPercentY) {
		if (control.isTouched()) {

			if (knobPercentY != 0) {
				nanoBot.vector.y = velocidadNanoBot * knobPercentY;
			} else {
				nanoBot.vector.y = 0;
			}
			if (knobPercentX != 0) {
				nanoBot.vector.x = velocidadNanoBot * knobPercentX;
			} else {
				nanoBot.vector.x = 0;
			}

		}
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose(); // Destruir pantalla

	}

	private final class ImputListener extends InputListener {

		@Override
		public boolean keyDown(InputEvent event, int keycode) {
			switch (keycode) {
			case Input.Keys.S:
				keyDownS = true;
				nanoBot.vector.y = -velocidadNanoBot;
				return true;

			case Input.Keys.W:
				keyDownW = true;
				nanoBot.vector.y = velocidadNanoBot;
				return true;

			case Input.Keys.A:
				keyDownA = true;
				nanoBot.vector.x = -velocidadNanoBot;
				return true;

			case Input.Keys.D:
				keyDownD = true;
				nanoBot.vector.x = velocidadNanoBot;
				return true;

			default:
				return false;
			}

		}

		@Override
		public boolean keyUp(InputEvent event, int keycode) {
			switch (keycode) {
			case Input.Keys.S: // 47
				keyDownS = false;
				if (keyDownW) {
					return true;
				} else {
					nanoBot.vector.y = 0;
					return true;
				}

			case Input.Keys.W: // 51
				keyDownW = false;
				if (keyDownS) {
					return true;
				} else {
					nanoBot.vector.y = 0;
					return true;
				}

			case Input.Keys.A: // 29
				keyDownA = false;
				if (keyDownD) {
					return true;
				} else {
					nanoBot.vector.x = 0;
					return true;
				}

			case Input.Keys.D: // 32
				keyDownD = false;
				if (keyDownA) {
					return true;
				} else {
					nanoBot.vector.x = 0;
					return true;
				}

			case Input.Keys.SPACE: // 62
				// Genera lasers al pulsar la tecla
				Laser laser = new Laser();
				laser.setPosition(nanoBot.getX() + nanoBot.getWidth(), nanoBot.getY());
				stage.addActor(laser);

				return true;

			default:
				return false;
			}
		}

	}
}
/*
 * case Input.Keys.S: keyDownS = true; nanoBot.vector.y = -velocidadNanoBot;
 * return true;
 * 
 * case Input.Keys.W: keyDownW = true; nanoBot.vector.y = velocidadNanoBot;
 * return true;
 * 
 * case Input.Keys.A: keyDownA = true; nanoBot.vector.x = -velocidadNanoBot;
 * return true;
 * 
 * case Input.Keys.D: keyDownD = true; nanoBot.vector.x = velocidadNanoBot;
 * return true;
 */

/**
 * https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/Stage.html
 * Un gráfico de escena 2D que contiene jerarquías de actors. Stage maneja la
 * ventana gráfica y distribuye eventos de entrada. setViewport(Viewport)
 * controla las coordenadas utilizadas dentro del escenario y configura la
 * cámara utilizada para convertir entre las coordenadas del escenario y las
 * coordenadas de la pantalla. Un escenario debe recibir eventos de entrada para
 * que pueda distribuirlos a los actores. Por lo general, esto se hace pasando
 * el escenario a Gdx.input.setInputProcessor. Se InputMultiplexerpuede usar
 * para manejar eventos de entrada antes o después de que lo haga la etapa. Si
 * un actor maneja un evento devolviendo verdadero desde el método de entrada,
 * entonces el método de entrada de la etapa también devolverá verdadero, lo que
 * provocará que los procesadores de entrada posteriores no reciban el evento.
 * El escenario y sus componentes (como Actores y Oyentes) no son seguros para
 * subprocesos y solo deben actualizarse y consultarse desde un solo subproceso
 * (presumiblemente el subproceso principal de procesamiento). Los métodos deben
 * ser reentrantes, por lo que puede actualizar Actores y Etapas desde
 * devoluciones de llamada y controladores. Stagepuede ser extremadamente útil
 * si necesita controles en pantalla (botones, joystick, etc.) puede usar clases
 * de scene2d.ui
 * 
 * 1200*600 Res. Original
 */
