/**
 * 
 */
package com.algm.pantallas;

import java.util.ArrayList;
import com.algm.actorcontrol.BarraEnergia;
import com.algm.actorcontrol.BotonDisparo;
import com.algm.actorcontrol.MBarraEnergia;
import com.algm.actorcontrol.NivelPuntuacion;
import com.algm.actorcontrol.Pad;
import com.algm.actorcontrol.Pausa;
import com.algm.actores.Adn;
import com.algm.actores.BonusEnergia;
import com.algm.actores.BonusPuntos;
import com.algm.actores.BonusVelocidad;
import com.algm.actores.NanoBot;
import com.algm.actores.Virus;
import com.algm.actores.Virus2;
import com.algm.actores.Virus3;
import com.algm.actores.Virus4;
import com.algm.actores.Virus5;
import com.algm.actores.Virus6;
import com.algm.actores.Virus7;
import com.algm.actores.Virus8;
import com.algm.actorpantalla.FondoPantallaJuego;
import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author Angel
 *
 */
public class PantallaJuego extends Pantalla {

	private Viewport viewport;
	private Stage stageJuego;
	private FondoPantallaJuego fondoPJuego;
	private NanoBot nanoBot;
	private Adn adn;
	private Pad control;
	private Pausa pausa;
	private MBarraEnergia mBarraEnergia;
	private BarraEnergia barraEnergia;
	private BotonDisparo btDisparo;
	private ArrayList<Actor> listActores;
	private ArrayList<Adn> listAdns;
	static NivelPuntuacion puntos;
	private float velocidadNanoBot;
	private float virusSpawn;
	private Texture btPausa, btMenu;
	private Image imagePausa, imageMenu;
	private State state;
	public Preferences preferences;
	private int virusKill;
	private int maxPuntos;
	private int nivel;
	private int contadorParaBonus;
	private double spawnIncre;
	private int maxSpawn;

	public enum State {
		PAUSE, RUN,
	}

	public PantallaJuego(SarsCovKiller juego) {
		super(juego);
	}

	@Override
	public void show() {

		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stageJuego = new Stage(viewport, juego.sckBatch);
		// -------------------------------
		// Modo debug gráfico
		//stageJuego.setDebugAll(true);
		// -------------------------------
		// Estado inicial RUN
		state = State.RUN;

		SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/fondo.ogg"), Sound.class).loop();

		velocidadNanoBot = 500;
		// Rango ideal entre 5.9 (Facil lv1) y 1 (Dificil lv 50+)
		spawnIncre = 5.9;
		// "Maximos" enemigos en pantalla
		maxSpawn = 5;

		fondoPJuego = new FondoPantallaJuego();
		fondoPJuego.setPosition(0, 0);
		fondoPJuego.setSize(stageJuego.getWidth(), stageJuego.getHeight());

		puntos = new NivelPuntuacion(new BitmapFont(Gdx.files.internal("fuentes/fuenteNormal.fnt"),
				Gdx.files.internal("fuentes/fuenteNormal.png"), false));
		puntos.setPosition(stageJuego.getWidth() / 3.6f, stageJuego.getHeight() - (stageJuego.getHeight() / 30));
		virusKill = 0;
		nivel = 1;
		maxPuntos = 0;

		listAdns = new ArrayList<Adn>();
		listActores = new ArrayList<Actor>();

		nanoBot = new NanoBot();
		nanoBot.setPosition(stageJuego.getWidth() / 14, stageJuego.getHeight() / 2);
		nanoBot.setEnergia((float) 1);

		control = new Pad();
		control.setPosition(stageJuego.getWidth() / 14, 15);

		cargarBarraEnergia();

		// Cargar datos del slot seleccionado si no es partida nueva.
		preferences = Gdx.app.getPreferences("sckPersist");
		if (SarsCovKiller.esContinuarPartida) {
			cargarDatosPersistencia();
		}
		// Settear datos de partida al hud
		puntos.setNivel(nivel);
		puntos.setMarcador(maxPuntos);
		puntos.setSck(virusKill);
		contadorParaBonus = virusKill;

		stageJuego.addActor(fondoPJuego);
		stageJuego.addActor(puntos);
		stageJuego.addActor(mBarraEnergia);
		stageJuego.addActor(barraEnergia);
		stageJuego.addActor(control);
		stageJuego.addActor(nanoBot);
		
		// Cargar boton de disparo, pausa y menu solo en Android o iOS
		if (Gdx.app.getType() == ApplicationType.Android) {
			btTactilDisparo();
			btTactilMenu();
			btTactilPausa();
		}
		// cargar listener del teclado si es Desktop
		if (Gdx.app.getType() == ApplicationType.Desktop) {
			stageJuego.setKeyboardFocus(nanoBot);
			nanoBot.addListener(new ImputListener());
			
		}
		Gdx.input.setInputProcessor(stageJuego);
		
		pausa();
	}

	@Override
	public void resize(int width, int height) {
		// juego.sckBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		// fondoPJuego.setSize(stageJuego.getWidth(), stageJuego.getHeight());
		super.resize(width, height);
	}

	// Bucle principal
	@Override
	public void render(float delta) {

		switch (state) {
		case RUN:
			// Limpiar pantalla para evitar trazos fantasma de los actores
			Gdx.gl.glClearColor(0f, 0f, 0f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stageJuego.act(Gdx.graphics.getDeltaTime()); // Actualizar
			eliminarActoresNoVisibles();

			// System.out.println("LIST VIRUS " + listVirus.size());
			if (listActores.size() < maxSpawn) {
				virusVerdeAzulRojoSpawn(delta);
			}
			controlPad(control.getKnobPercentX(), control.getKnobPercentY());

			if (Gdx.app.getType() == ApplicationType.Desktop) {
				adnSpawnClick();
			}

			// Rango ideal entre 5.9 (lv1 Facil) y 1.0 (lv 50 Dificil)
			incrementarDificultad();
			generarBonus();

			try {
				accionColisionesListas();
			} catch (Exception e) {
			}
			stageJuego.draw(); // Dibujar
			break;

		case PAUSE:
			pausa.setVisible(true);
			stageJuego.draw();
		default:
			break;
		}
	}

	@Override
	public void hide() {
		// Persistir datos de la patida
		persistirDatosPartida();
		// Se destrulle la pantalla en el hide (evitar acumulación de pantallas)
		stageJuego.dispose();
		// System.out.println("hide de pJue PUNTOS:" + puntos.getMarcador());
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
		this.state = State.PAUSE;
		super.pause();
	}

	@Override
	public void resume() {
		this.state = State.RUN;
		super.resume();
	}

	/**
	 * 
	 * @category Realiza una recogida de datos persistentes en el archivo XML de
	 *           persistencia
	 */
	private void cargarDatosPersistencia() {
		nivel = preferences.getInteger("Nivel ".concat(SarsCovKiller.slotGuardado), 1);
		maxPuntos = preferences.getInteger("MaxPuntos ".concat(SarsCovKiller.slotGuardado), 0);
		virusKill = preferences.getInteger("VirusKill ".concat(SarsCovKiller.slotGuardado), 0);
	}

	/**
	 * 
	 * @category Realiza la escritura de los datos de la partida en archivo XML para
	 *           la persistencia
	 */
	private void persistirDatosPartida() {
		preferences.putInteger("Nivel ".concat(SarsCovKiller.slotGuardado), puntos.getNivel());
		preferences.putInteger("MaxPuntos ".concat(SarsCovKiller.slotGuardado), puntos.getMarcador());
		preferences.putInteger("VirusKill ".concat(SarsCovKiller.slotGuardado), puntos.getSck());
		preferences.flush();
	}

	/**
	 * @category Inicaliza Barra de energia y el marco. Le asigna una posición y
	 *           tamaño
	 */
	private void cargarBarraEnergia() {
		mBarraEnergia = new MBarraEnergia();
		mBarraEnergia.setPosition(5, stageJuego.getHeight() - mBarraEnergia.getHeight() - 7);
		barraEnergia = new BarraEnergia(nanoBot);
		barraEnergia.setPosition(87, stageJuego.getHeight() - mBarraEnergia.getHeight() - 7);
	}

	/**
	 * @category Inicaliza Boton de disparo e Imput Listerner (Touch) en
	 *           Android/IOS. Le asigna una posición y tamaño
	 */
	private void btTactilDisparo() {
		btDisparo = new BotonDisparo();
		btDisparo.setPosition((stageJuego.getWidth() - btDisparo.getWidth()) - (stageJuego.getWidth() / 14), 20);
		stageJuego.addActor(btDisparo);
		btDisparo.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				adnSpawnClick();
				return true;
			}
		});
	}

	/**
	 * @category Elimina actores Adn y Virus que ya no son visibles en pantalla para
	 *           ahorrar recursos
	 */
	private void eliminarActoresNoVisibles() {
		// Eliminar actor adn al salir de la pantalla
		for (int i = 0; i < listAdns.size(); i++) {
			if (listAdns.get(i).getX() > Gdx.graphics.getWidth()) {
				listAdns.remove(i);
			}
		}
		// Eliminar actor virus al salir de la pantalla
		for (int j = 0; j < listActores.size(); j++) {
			if (listActores.get(j).getX() <= 0 || listActores.get(j).getX() >= Gdx.graphics.getWidth()
					|| listActores.get(j).getY() <= 0 || listActores.get(j).getY() >= Gdx.graphics.getHeight()) {
				// System.out.println(listVirus.get(j).getClass().getSimpleName());
				listActores.remove(j);
			}
		}
	}

	/**
	 * @category Clase que extiende de input listerner para acciones del teclado
	 * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/InputListener.html
	 */
	public final class ImputListener extends InputListener {

		private boolean keyDownW;
		private boolean keyDownS;
		private boolean keyDownA;
		private boolean keyDownD;

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
				nanoBot.getVectorNanoBot().y = -velocidadNanoBot;
				return true;

			case Input.Keys.W:
				keyDownW = true;
				nanoBot.getVectorNanoBot().y = velocidadNanoBot;
				return true;

			case Input.Keys.A:
				keyDownA = true;
				nanoBot.getVectorNanoBot().x = -velocidadNanoBot;
				return true;

			case Input.Keys.D:
				keyDownD = true;
				nanoBot.getVectorNanoBot().x = velocidadNanoBot;
				return true;

			case Input.Keys.P:
				if (getState() == State.RUN) {
					setGameState(State.PAUSE);
				} else {
					pausa.setVisible(false);
					setGameState(State.RUN);
				}
				return true;

			case Input.Keys.ESCAPE: // 32
				// PANTALLA MENU
				SarsCovKiller.ASSETMANAGER.get("sonido/fondo.ogg", Sound.class).stop();
				juego.setScreen(juego.P_MENU);
				return true;

			default:
				return false;
			}

		}

		/**
		 * @param InputEvent
		 * @param keycode
		 * @category Referente al input listener del teclado (soltar la tecla). Modifica
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
					nanoBot.getVectorNanoBot().y = 0;
					return true;
				}

			case Input.Keys.W: // 51
				keyDownW = false;
				if (keyDownS) {
					return true;
				} else {
					nanoBot.getVectorNanoBot().y = 0;
					return true;
				}

			case Input.Keys.A: // 29
				keyDownA = false;
				if (keyDownD) {
					return true;
				} else {
					nanoBot.getVectorNanoBot().x = 0;
					return true;
				}

			case Input.Keys.D: // 32
				keyDownD = false;
				if (keyDownA) {
					return true;
				} else {
					nanoBot.getVectorNanoBot().x = 0;
					return true;
				}

			case Input.Keys.SPACE: // 62
				// Genera disparos al soltar la tecla
				Adn adn = new Adn();
				adn.setPosition(nanoBot.getX() + (nanoBot.getWidth() - nanoBot.getWidth() / 4),
						nanoBot.getY() + (nanoBot.getHeight() / 3));
				stageJuego.addActor(adn);
				SarsCovKiller.ASSETMANAGER.get("sonido/adn.ogg", Sound.class).play();
				// Generar adn resta energía
				nanoBot.setEnergia(nanoBot.getEnergiaRango() - 0.0001f);

				// Añadir a lista de adn en pantalla
				listAdns.add(adn);
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
				nanoBot.getVectorNanoBot().y = velocidadNanoBot * knobPercentY;
			} else {
				nanoBot.getVectorNanoBot().y = 0;
			}
			if (knobPercentX != 0) {
				nanoBot.getVectorNanoBot().x = velocidadNanoBot * knobPercentX;
			} else {
				nanoBot.getVectorNanoBot().x = 0;
			}
		}
	}

	/**
	 * @category Inicaliza Boton de menu e Imput Listerner (Touch) en Android/IOS.
	 *           Le asigna una posición y tamaño
	 */
	private void btTactilMenu() {
		btMenu = SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("ui/menuIcon60.png"), Texture.class);
		imageMenu = new Image(btMenu);
		imageMenu.setPosition((float) (stageJuego.getWidth() / 2 - (imageMenu.getWidth() * 1.5)), 10);
		stageJuego.addActor(imageMenu);

		imageMenu.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// PANTALLA MENU
				SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/fondo.ogg"), Sound.class).stop();
				juego.setScreen(juego.P_MENU);
				return true;
			}
		});
	}

	/**
	 * @category Inicaliza Boton de pausa e Imput Listerner (Touch) en Android/IOS.
	 *           Le asigna una posición y tamaño
	 */
	private void btTactilPausa() {
		btPausa = SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("ui/pausaIcon60.png"), Texture.class);
		imagePausa = new Image(btPausa);
		imagePausa.setPosition((float) (stageJuego.getWidth() / 2 + (imagePausa.getWidth() / 2)), 10);
		stageJuego.addActor(imagePausa);

		imagePausa.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (getState() == State.RUN) {
					setGameState(State.PAUSE);
				} else {
					pausa.setVisible(false);
					setGameState(State.RUN);
				}
				return true;
			}
		});
	}

	/**
	 * @category Referente al imput listener del mouse (soltar la tecla derecha).
	 *           Función que genera disparos (adn)
	 * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/InputListener.html
	 */
	private void adnSpawnClick() {
		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			Adn adn = new Adn();
			adn.setPosition(nanoBot.getX() + (nanoBot.getWidth() - nanoBot.getWidth() / 6),
					nanoBot.getY() + (nanoBot.getHeight() / 3));
			stageJuego.addActor(adn);
			SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/adn.ogg"), Sound.class).play();
			// Generar adn resta energía
			nanoBot.setEnergia(nanoBot.getEnergiaRango() - 0.0001f);
			// Añadir a lista de adn en pantalla
			listAdns.add(adn);
		}
	}

	/**
	 * @category Incrementa la dificultad del juego en función del nivel de la
	 *           partida
	 */
	private void incrementarDificultad() {
		int lvActual = puntos.getNivel();
		if (lvActual > 50) {
			spawnIncre = 1;
		} else {
			spawnIncre = 6 - (0.10 * puntos.getNivel());
		}
	}

	/**
	 * @category Genera items de bonus en partida de forma random y los añade a la
	 *           pantalla de juego
	 */
	private void generarBonus() {
		// Rango de muertes para spawn del bonus
		int rango = 25;
		if (contadorParaBonus + rango == puntos.getSck()) {
			// Bonus aleatorio
			contadorParaBonus = puntos.getSck();
			double bonusAzar = Math.random() * 3;
			if ((int) bonusAzar == 0) {
				BonusEnergia bonusEnergia = new BonusEnergia();
				bonusEnergia.setPosition(stageJuego.getWidth(), stageJuego.getHeight() / 2);
				stageJuego.addActor(bonusEnergia);
				listActores.add(bonusEnergia);
			}
			if ((int) bonusAzar == 1) {
				BonusPuntos bonusPuntos = new BonusPuntos();
				bonusPuntos.setPosition(stageJuego.getWidth(), stageJuego.getHeight() / 2);
				stageJuego.addActor(bonusPuntos);
				listActores.add(bonusPuntos);
			}
			if ((int) bonusAzar == 2) {
				BonusVelocidad bonusVelocidad = new BonusVelocidad();
				bonusVelocidad.setPosition(stageJuego.getWidth(), stageJuego.getHeight() / 2);
				stageJuego.addActor(bonusVelocidad);
				listActores.add(bonusVelocidad);
			}
		}
	}

	/**
	 * @category Inicaliza Boton de Pausa e Imput Listerner (Touch) en Android/IOS.
	 *           Le asigna una posición y tamaño
	 */
	private void pausa() {
		pausa = new Pausa();
		pausa.setPosition(stageJuego.getWidth() / 2 - pausa.getWidth() / 2,
				stageJuego.getHeight() / 2 - pausa.getHeight() / 2);
		stageJuego.addActor(pausa);
		pausa.setVisible(false);

		pausa.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
					if (getState() == State.RUN) {
						setGameState(State.PAUSE);
					} else {
						pausa.setVisible(false);
						setGameState(State.RUN);
					}
				}
				return true;
			}
		});
	}

	/**
	 * @param delta
	 * 
	 * @category Genera enemigos VirusVerde/Azul/Rojo/Azul-Rojo/Metálicos... "Spawn
	 *           determinado por virusSpawn (la suma de delta y número aleatorio
	 *           (Math.random())). Cuando virusSpawn es superior a 1 hay un nuevo
	 *           spawn.
	 * 
	 * @see deltaTime: Tiempo que tarda en procesar y renderizar un frame del
	 *      videojuego.
	 */
	private void virusVerdeAzulRojoSpawn(float delta) {
		// si delta es inferior (problemas renderizado) se induce menos SPAWN
		virusSpawn += delta + ((float) Math.random() / 100);
		if (virusSpawn > spawnIncre) {
			// System.out.println("virusSpawn " + virusSpawn + ">SpawnI " + spawnIncre);
			// System.out.println("diferencia " + (virusSpawn - spawnIncre));
			Virus virus = new Virus();
			virus.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus);
			// Añadir a lista de virus en pantalla
			listActores.add(virus);
		}
		if (virusSpawn > spawnIncre + 0.005) {
			Virus2 virus2 = new Virus2();
			virus2.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus2);
			// Añadir a lista de virus en pantalla
			listActores.add(virus2);
		}
		if (virusSpawn > spawnIncre + 0.007) {
			Virus3 virus3 = new Virus3();
			virus3.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus3);
			// Añadir a lista de virus en pantalla
			listActores.add(virus3);
		}
		if (virusSpawn > spawnIncre + 0.009) {
			Virus4 virus4 = new Virus4();
			virus4.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus4);
			// Añadir a lista de virus en pantalla
			listActores.add(virus4);
		}
		if (virusSpawn > spawnIncre + 0.011) {
			Virus5 virus5 = new Virus5();
			virus5.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus5);
			// Añadir a lista de virus en pantalla
			listActores.add(virus5);
		}
		if (virusSpawn > spawnIncre + 0.013) {
			Virus6 virus6 = new Virus6();
			virus6.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus6);
			// Añadir a lista de virus en pantalla
			listActores.add(virus6);
		}
		if (virusSpawn > spawnIncre + 0.016) {
			Virus7 virus7 = new Virus7();
			virus7.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus7);
			// Añadir a lista de virus en pantalla
			listActores.add(virus7);
		}
		if (virusSpawn > spawnIncre + 0.020) {
			Virus8 virus8 = new Virus8();
			virus8.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus8);
			// Añadir a lista de virus en pantalla
			listActores.add(virus8);
		}
		if (virusSpawn > spawnIncre) {
			virusSpawn = 0f;
		}
	}

	/**
	 * @category Comprueba las colisiones entre actores Virus-Nanobot y Virus-Adn y
	 *           aplica remove y otras acciones
	 */
	private void accionColisionesListas() throws Exception {
		// Se que es una cutrada..... pero no doy con la clave para que funcionen
		// algunos métodos necesarios (getRectangle()) mediante la herencia con Actor.
		adn = new Adn();
		// Hay virus que son letales xD
		float IMPACTO = 0.25f;
		float IMPACTOLETAL = 1f;

		// Si hay colision entre Virus - Nanobot. Se elimina alien, energia y puntuación
		// Si hay colision entre Virus - Adn. Se elimina alien, adn y marcador++
		for (int j = 0; j < listActores.size(); j++) {
			if (listActores.get(j).getClass().getSimpleName().equals("Virus")) {
				Virus virus = new Virus();
				virus = (Virus) listActores.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/botKill.ogg"), Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/GameOver1.ogg"), Sound.class).play();
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						// PANTALLA GAME OVER
						juego.setScreen(juego.P_GAMEOVER);

					} else {
						nanoBot.setEnergia(nanoBot.getEnergiaRango() - IMPACTO);
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/impactoBot.ogg"), Sound.class).play();
					}
					listActores.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							virusKill++;
							puntos.setMarcador(puntos.getMarcador() + 100);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
							SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/impactoVirus.ogg"), Sound.class)
									.play();
							adn = listAdns.get(i);
							listActores.remove(j);
							listAdns.remove(i);
							adn.remove();
							virus.remove();
						}
					}
			} else if (listActores.get(j).getClass().getSimpleName().equals("Virus2")) {
				Virus2 virus = new Virus2();
				virus = (Virus2) listActores.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/botKill.ogg"), Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/GameOver1.ogg"), Sound.class).play();
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						// PANTALLA GAME OVER
						juego.setScreen(juego.P_GAMEOVER);

					} else {
						nanoBot.setEnergia(nanoBot.getEnergiaRango() - IMPACTO);
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/impactoBot.ogg"), Sound.class).play();
					}
					listActores.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							virusKill++;
							puntos.setMarcador(puntos.getMarcador() + 100);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
							SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/impactoVirus.ogg"), Sound.class)
									.play();
							adn = listAdns.get(i);
							listActores.remove(j);
							listAdns.remove(i);
							adn.remove();
							virus.remove();
						}
					}
			} else if (listActores.get(j).getClass().getSimpleName().equals("Virus3")) {
				Virus3 virus = new Virus3();
				virus = (Virus3) listActores.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/botKill.ogg"), Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/GameOver1.ogg"), Sound.class).play();
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						// PANTALLA GAME OVER
						juego.setScreen(juego.P_GAMEOVER);

					} else {
						nanoBot.setEnergia(nanoBot.getEnergiaRango() - IMPACTO);
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/impactoBot.ogg"), Sound.class).play();
					}
					listActores.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							virusKill++;
							puntos.setMarcador(puntos.getMarcador() + 100);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
							SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/impactoVirus.ogg"), Sound.class)
									.play();
							adn = listAdns.get(i);
							listActores.remove(j);
							listAdns.remove(i);
							adn.remove();
							virus.remove();
						}
					}
			} else if (listActores.get(j).getClass().getSimpleName().equals("Virus4")) {
				Virus4 virus = new Virus4();
				virus = (Virus4) listActores.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/botKill.ogg"), Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/GameOver1.ogg"), Sound.class).play();
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						// PANTALLA GAME OVER
						juego.setScreen(juego.P_GAMEOVER);

					} else {
						nanoBot.setEnergia(nanoBot.getEnergiaRango() - IMPACTO);
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/impactoBot.ogg"), Sound.class).play();
					}
					listActores.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							virusKill++;
							puntos.setMarcador(puntos.getMarcador() + 100);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
							SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/impactoVirus.ogg"), Sound.class)
									.play();
							adn = listAdns.get(i);
							listActores.remove(j);
							listAdns.remove(i);
							adn.remove();
							virus.remove();
						}
					}
			} else if (listActores.get(j).getClass().getSimpleName().equals("Virus5")) {
				Virus5 virus = new Virus5();
				virus = (Virus5) listActores.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/botKill.ogg"), Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/GameOver1.ogg"), Sound.class).play();
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						// PANTALLA GAME OVER
						juego.setScreen(juego.P_GAMEOVER);

					} else {
						nanoBot.setEnergia(nanoBot.getEnergiaRango() - IMPACTO);
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/impactoBot.ogg"), Sound.class).play();
					}
					listActores.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							virusKill++;
							puntos.setMarcador(puntos.getMarcador() + 100);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
							SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/impactoVirus.ogg"), Sound.class)
									.play();
							adn = listAdns.get(i);
							listActores.remove(j);
							listAdns.remove(i);
							adn.remove();
							virus.remove();
						}
					}
			} else if (listActores.get(j).getClass().getSimpleName().equals("Virus6")) {
				Virus6 virus = new Virus6();
				virus = (Virus6) listActores.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/botKill.ogg"), Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/GameOver1.ogg"), Sound.class).play();
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						// PANTALLA GAME OVER
						juego.setScreen(juego.P_GAMEOVER);

					} else {
						nanoBot.setEnergia(nanoBot.getEnergiaRango() - IMPACTO);
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/impactoBot.ogg"), Sound.class).play();
					}
					listActores.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							virusKill++;
							puntos.setMarcador(puntos.getMarcador() + 100);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
							SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/impactoVirus.ogg"), Sound.class)
									.play();
							adn = listAdns.get(i);
							listActores.remove(j);
							listAdns.remove(i);
							adn.remove();
							virus.remove();
						}
					}
			} else if (listActores.get(j).getClass().getSimpleName().equals("Virus7")) {
				Virus7 virus = new Virus7();
				virus = (Virus7) listActores.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/botKill.ogg"), Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/GameOver1.ogg"), Sound.class).play();
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						// PANTALLA GAME OVER
						juego.setScreen(juego.P_GAMEOVER);

					} else {
						nanoBot.setEnergia(nanoBot.getEnergiaRango() - IMPACTOLETAL);
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/impactoBot.ogg"), Sound.class).play();
					}
					listActores.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/metal.ogg"), Sound.class).play();
							adn = listAdns.get(i);
							listAdns.remove(i);
							adn.remove();
						}
					}
			} else if (listActores.get(j).getClass().getSimpleName().equals("Virus8")) {
				Virus8 virus = new Virus8();
				virus = (Virus8) listActores.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/botKill.ogg"), Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/GameOver1.ogg"), Sound.class).play();
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						// PANTALLA GAME OVER
						juego.setScreen(juego.P_GAMEOVER);

					} else {
						nanoBot.setEnergia(nanoBot.getEnergiaRango() - IMPACTOLETAL);
						if (puntos.getMarcador() > 50) {
							puntos.setMarcador(puntos.getMarcador() - 50);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
						}
						SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/impactoBot.ogg"), Sound.class).play();
					}
					listActores.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/metal.ogg"), Sound.class).play();
							adn = listAdns.get(i);
							listAdns.remove(i);
							adn.remove();
						}
					}
				// Bonus Energia
			} else if (listActores.get(j).getClass().getSimpleName().equals("BonusEnergia")) {
				BonusEnergia bonusEnergia = new BonusEnergia();
				bonusEnergia = (BonusEnergia) listActores.get(j);
				if (bonusEnergia.getRectangle().overlaps(nanoBot.getRectangle())
						&& bonusEnergia.getX() != stageJuego.getWidth()) {
					SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/bonus.ogg"), Sound.class).play();
					listActores.remove(j);
					bonusEnergia.remove();
					nanoBot.setEnergia(1);
				}

				// Bonus Puntos
			} else if (listActores.get(j).getClass().getSimpleName().equals("BonusPuntos")) {
				BonusPuntos bonusPuntos = new BonusPuntos();
				bonusPuntos = (BonusPuntos) listActores.get(j);
				if (bonusPuntos.getRectangle().overlaps(nanoBot.getRectangle())
						&& bonusPuntos.getX() != stageJuego.getWidth()) {
					SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/bonus.ogg"), Sound.class).play();
					listActores.remove(j);
					bonusPuntos.remove();
					puntos.setMarcador(puntos.getMarcador() + 10000);
					puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
				}

				// Bonus Velocidad
			} else if (listActores.get(j).getClass().getSimpleName().equals("BonusVelocidad")) {
				BonusVelocidad bonusVelocidad = new BonusVelocidad();
				bonusVelocidad = (BonusVelocidad) listActores.get(j);
				if (bonusVelocidad.getRectangle().overlaps(nanoBot.getRectangle())
						&& bonusVelocidad.getX() != stageJuego.getWidth()) {
					SarsCovKiller.ASSETMANAGER.get(SarsCovKiller.path("sonido/bonus.ogg"), Sound.class).play();
					listActores.remove(j);
					bonusVelocidad.remove();
					if (velocidadNanoBot != 900) {
						velocidadNanoBot += 100;
					}
				}
			}
		}
	}

	/**
	 * @return
	 */
	public Stage getStage() {
		return stageJuego;
	}

	/**
	 * @param stage
	 */
	public void setStage(Stage stage) {
		this.stageJuego = stage;
	}

	/**
	 * @return
	 */
	public FondoPantallaJuego getFondo() {
		return fondoPJuego;
	}

	/**
	 * @param fondo
	 */
	public void setFondo(FondoPantallaJuego fondo) {
		this.fondoPJuego = fondo;
	}

	/**
	 * @return
	 */
	public NanoBot getNanoBot() {
		return nanoBot;
	}

	/**
	 * @param nanoBot
	 */
	public void setNanoBot(NanoBot nanoBot) {
		this.nanoBot = nanoBot;
	}

	/**
	 * @return
	 */
	public Pad getControl() {
		return control;
	}

	/**
	 * @param control
	 */
	public void setControl(Pad control) {
		this.control = control;
	}

	/**
	 * @return
	 */
	public MBarraEnergia getmBarraEnergia() {
		return mBarraEnergia;
	}

	/**
	 * @param mBarraEnergia
	 */
	public void setmBarraEnergia(MBarraEnergia mBarraEnergia) {
		this.mBarraEnergia = mBarraEnergia;
	}

	/**
	 * @return
	 */
	public BarraEnergia getBarraEnergia() {
		return barraEnergia;
	}

	/**
	 * @param barraEnergia
	 */
	public void setBarraEnergia(BarraEnergia barraEnergia) {
		this.barraEnergia = barraEnergia;
	}

	/**
	 * @return
	 */
	public BotonDisparo getBtDisparo() {
		return btDisparo;
	}

	/**
	 * @param btDisparo
	 */
	public void setBtDisparo(BotonDisparo btDisparo) {
		this.btDisparo = btDisparo;
	}

	/**
	 * @return
	 */
	public ArrayList<Actor> getListVirus() {
		return listActores;
	}

	/**
	 * @param listVirus
	 */
	public void setListVirus(ArrayList<Actor> listVirus) {
		this.listActores = listVirus;
	}

	/**
	 * @return
	 */
	public ArrayList<Adn> getListAdns() {
		return listAdns;
	}

	/**
	 * @param listAdns
	 */
	public void setListAdns(ArrayList<Adn> listAdns) {
		this.listAdns = listAdns;
	}

	/**
	 * @return
	 */
	public NivelPuntuacion getPuntos() {
		return puntos;
	}

	/**
	 * @param puntos
	 */
	public void setPuntos(NivelPuntuacion puntos) {
		PantallaJuego.puntos = puntos;
	}

	/**
	 * @return
	 */
	public float getVelocidadNanoBot() {
		return velocidadNanoBot;
	}

	/**
	 * @param velocidadNanoBot
	 */
	public void setVelocidadNanoBot(float velocidadNanoBot) {
		this.velocidadNanoBot = velocidadNanoBot;
	}

	/**
	 * @return
	 */
	public float getVirusSpawn() {
		return virusSpawn;
	}

	/**
	 * @param virusSpawn
	 */
	public void setVirusSpawn(float virusSpawn) {
		this.virusSpawn = virusSpawn;
	}

	/**
	 * @return
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param s
	 */
	public void setGameState(State s) {
		this.state = s;
	}
}
