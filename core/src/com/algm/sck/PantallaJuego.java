/**
 * 
 */
package com.algm.sck;

import com.algm.actores.Pad;
import com.algm.actores.Adn;
import com.algm.actores.BarraEnergia;
import com.algm.actores.EnergiaBot;
import com.algm.actores.NanoBot;
import com.algm.actores.Virus;
import com.algm.actores.Fondo;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * @author Angel
 *
 */
public class PantallaJuego extends Pantalla {

	Stage stage;
	private Fondo fondo;
	private NanoBot nanoBot;
	private Virus virus;
	private Adn adn;
	private Pad control;
	private BarraEnergia barraEnergia;
	
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

		fondo= new Fondo();
		fondo.setPosition(0, 0);
		
		adn = new Adn();
		virus = new Virus();

		nanoBot = new NanoBot();
		nanoBot.setPosition(20, 250);

		control = new Pad();
		control.setPosition(15, 15);
			
		barraEnergia = new BarraEnergia();
		barraEnergia.setPosition(50, 15);

		if (Gdx.app.getType() == ApplicationType.Desktop) {
			stage.setKeyboardFocus(nanoBot);
			nanoBot.addListener(new ImputListener());
		}
		// Usar touchPad solo en Android
		if (Gdx.app.getType() == ApplicationType.Android) {
			stage.addActor(control);
		}

		stage.addActor(fondo);
		stage.addActor(nanoBot);
		stage.addActor(virus);
		stage.addActor(control);
		stage.addActor(barraEnergia);
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
		virusVerdeSpawn(delta);
		controlPad(control.getKnobPercentX(), control.getKnobPercentY());
		adnSpawnClick();

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

	private final class ImputListener extends InputListener {
		/**
		 * @param InputEvent
		 * @param keycode
		 * @category Referente al imput listener del teclado (pulsar la tecla). Modifica
		 *           la velocidad y dirección del actor principal (NanoBot) y otras
		 *           funciones como disparar.
		 * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/InputListener.html
		 */
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

		/**
		 * @param InputEvent
		 * @param keycode
		 * @category Referente al imput listener del teclado (soltar la tecla). Modifica
		 *           la velocidad y dirección del actor principal (NanoBot) y otras
		 *           funciones como disparar.
		 * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/InputListener.html
		 */
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
				// Genera disparos al soltar la tecla
				Adn adn = new Adn();
				adn.setPosition(nanoBot.getX() + nanoBot.getWidth(), nanoBot.getY() + (nanoBot.getHeight() / 3));
				stage.addActor(adn);
				return true;

			default:
				return false;
			}
		}
	}

	/**
	 * @param knobPercentX
	 * @param knobPercentY
	 * @category Referente al movimiento del TouchPad. Modifica la velocidad y
	 *           dirección del actor principal (NanoBot) de forma analógica.
	 * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/ui/Touchpad.html
	 */
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

	/**
	 * @category Referente al imput listener del mouse (soltar la tecla derecha).
	 *           Función que genera disparos (adn)
	 * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/InputListener.html
	 */
	private void adnSpawnClick() {
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			Adn adn = new Adn();
			adn.setPosition(nanoBot.getX() + nanoBot.getWidth(), nanoBot.getY() + (nanoBot.getHeight() / 3));
			stage.addActor(adn);
		}
	}

	/**
	 * @param delta
	 * @category Genera enemigos "VirusVerdes". Spawn determinado por virusSpawn (la
	 *           suma de delta y número aleatorio (Math.random())). Cuando
	 *           virusSpawn es superior a 1 hay un nuevo spawn.
	 * @see deltaTime: Tiempo que tarda en procesar y renderizar un frame del videojuego.
	 */
	private void virusVerdeSpawn(float delta) {
		virusSpawn = delta + (float) Math.random();
		if (virusSpawn > 1) {
			Virus virus = new Virus();
			virus.setPosition(stage.getWidth(), stage.getHeight() * (float) Math.random());
			stage.addActor(virus);
			// Hay nuevo spawn
			virusSpawn = virusSpawn + (float) Math.random();
		}
	}
}

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
 * 1200*600 Res. Original auto documentar: Shift-Alt-J
 */
