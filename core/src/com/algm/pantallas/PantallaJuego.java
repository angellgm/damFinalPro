/**
 * 
 */
package com.algm.pantallas;

import java.util.ArrayList;

import javax.management.Query;

import com.algm.actorcontrol.BarraEnergia;
import com.algm.actorcontrol.BotonDisparo;
import com.algm.actorcontrol.MBarraEnergia;
import com.algm.actorcontrol.NivelPuntuacion;
import com.algm.actorcontrol.Pad;
import com.algm.actorcontrol.Pausa;
import com.algm.actores.Adn;
import com.algm.actores.BonusAdn;
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
	private Virus virus;
	private Virus2 virus2;
	private Virus3 virus3;
	private Virus4 virus4;
	private Virus5 virus5;
	private Virus6 virus6;
	private Virus7 virus7;
	private Virus8 virus8;
	private Adn adn;
	private BonusAdn bonusAdn;
	private BonusEnergia bonusEnergia;
	private BonusPuntos bonusPuntos;
	private BonusVelocidad bonusVelocidad;
	private Pad control;
	private Pausa pausa;
	private MBarraEnergia mBarraEnergia;
	private BarraEnergia barraEnergia;
	private BotonDisparo btDisparo;
	private ArrayList<Actor> listVirus;
//	private ArrayList<Virus2> listVirus2;
//	private ArrayList<Virus3> listVirus3;
//	private ArrayList<Virus4> listVirus4;
//	private ArrayList<Virus5> listVirus5;
//	private ArrayList<Virus6> listVirus6;
//	private ArrayList<Virus7> listVirus7;
//	private ArrayList<Virus8> listVirus8;
	private ArrayList<Adn> listAdns;
	static NivelPuntuacion puntos;
	private float velocidadNanoBot;
	private float virusSpawn;
	private Texture btPausa, btMenu;
	private Image imagePausa, imageMenu;
	private State state;
	private String jugador;
	public Preferences preferences;
	private int virusKill;
	private int maxPuntos;
	private int nivel;
	private double spawnIncre;
	private int maxSpawn;

	public enum State {
		PAUSE, RUN,
	}

	public PantallaJuego(SarsCovKiller juego) {
		super(juego);
		velocidadNanoBot = 500;
		// Rango ideal entre 0.975 (Facil) y 0.965 (Dificil)
		spawnIncre = 1;
		// "Maximos" enemigos en pantalla
		maxSpawn = 6;
	}

	@Override
	public void show() {

		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stageJuego = new Stage(viewport, juego.sckBatch);

		// Modo debug gráfico
		//stageJuego.setDebugAll(true);

		// Estado inicial RUN
		state = State.RUN;

		SarsCovKiller.ASSETMANAGER.get("sonido/fondo.ogg", Sound.class).loop();

		fondoPJuego = new FondoPantallaJuego();
		fondoPJuego.setPosition(0, 0);
		fondoPJuego.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		puntos = new NivelPuntuacion(new BitmapFont(Gdx.files.internal("fuentes/fuenteNormal.fnt"),
				Gdx.files.internal("fuentes/fuenteNormal.png"), false));
		puntos.setPosition(stageJuego.getWidth() / 3.6f, stageJuego.getHeight() - (stageJuego.getHeight() / 30));
		virusKill = 0;
		nivel = 1;
		maxPuntos = 0;

		listAdns = new ArrayList<Adn>();
		listVirus = new ArrayList<Actor>();

		adn = new Adn();
		virus = new Virus();
		virus2 = new Virus2();
		virus3 = new Virus3();
		virus4 = new Virus4();
		virus5 = new Virus5();
		virus6 = new Virus6();
		virus7 = new Virus7();
		virus8 = new Virus8();
		bonusAdn = new BonusAdn();
		bonusEnergia = new BonusEnergia();
		bonusPuntos = new BonusPuntos();
		bonusVelocidad = new BonusVelocidad();

		nanoBot = new NanoBot();
		nanoBot.setPosition(stageJuego.getWidth() / 14, stageJuego.getHeight() / 2);
		nanoBot.setEnergia((float) 1);

		control = new Pad();
		control.setPosition(stageJuego.getWidth() / 14, 15);

		cargarBarraEnergia();

		stageJuego.addActor(fondoPJuego);
		stageJuego.addActor(puntos);
		stageJuego.addActor(mBarraEnergia);
		stageJuego.addActor(barraEnergia);
		stageJuego.addActor(nanoBot);
		stageJuego.addActor(virus);
		stageJuego.addActor(virus2);
		stageJuego.addActor(virus3);
		stageJuego.addActor(virus4);
		stageJuego.addActor(virus5);
		stageJuego.addActor(virus6);
		stageJuego.addActor(virus7);
		stageJuego.addActor(virus8);
		stageJuego.addActor(control);
		pausa();

		// Cargar boton de disparo, pausa y menu solo en Android o iOS
		if ((Gdx.app.getType() == ApplicationType.Android) || (Gdx.app.getType() == ApplicationType.iOS)) {
			btTactilDisparo();
			btTactilMenu();
			btTactilPausa();
		}
		// Cargar datos del slot seleccionado si no es partida nueva.
		preferences = Gdx.app.getPreferences("sckPersist");
		if (SarsCovKiller.esContinuarPartida) {
			cargarDatosPersistencia();
		}
		// Settear datos de partida al hud
		puntos.setNivel(nivel);
		puntos.setMarcador(maxPuntos);
		puntos.setSck(virusKill);
		// cargar listener del teclado si es Desktop
//		if (Gdx.app.getType() == ApplicationType.Desktop) {
//			stageJuego.setKeyboardFocus(nanoBot);
//			nanoBot.addListener(new ImputListener());
//		}
		Gdx.input.setInputProcessor(stageJuego);
		stageJuego.setKeyboardFocus(nanoBot);
		nanoBot.addListener(new ImputListener());

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
			if (listVirus.size() < maxSpawn) {
				virusVerdeAzulRojoSpawn(delta);
			}
			controlPad(control.getKnobPercentX(), control.getKnobPercentY());
			adnSpawnClick();

			accionColisionesListas();
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
		// TODO Auto-generated method stub

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

	private void cargarDatosPersistencia() {
		nivel = preferences.getInteger("Nivel ".concat(SarsCovKiller.slotGuardado), 1);
		maxPuntos = preferences.getInteger("MaxPuntos ".concat(SarsCovKiller.slotGuardado), 0);
		virusKill = preferences.getInteger("VirusKill ".concat(SarsCovKiller.slotGuardado), 0);

	}

	private void persistirDatosPartida() {
		// ANULADO
//		if ((preferences.getInteger("MaxPuntos ".concat(SarsCovKiller.slotGuardado)) > puntos.getMarcador())) {
//			System.out.println("menos puntos que lo guardado!");
//		}
		preferences.putInteger("Nivel ".concat(SarsCovKiller.slotGuardado), puntos.getNivel());
		preferences.putInteger("MaxPuntos ".concat(SarsCovKiller.slotGuardado), puntos.getMarcador());
		preferences.putInteger("VirusKill ".concat(SarsCovKiller.slotGuardado), puntos.getSck());
		preferences.flush();
	}

	private void cargarBarraEnergia() {
		mBarraEnergia = new MBarraEnergia();
		mBarraEnergia.setPosition(5, stageJuego.getHeight() - mBarraEnergia.getHeight() - 7);
		barraEnergia = new BarraEnergia(nanoBot);
		barraEnergia.setPosition(87, stageJuego.getHeight() - mBarraEnergia.getHeight() - 7);
	}

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
	 * @category Elimina objetos Adn y Virus que ya no son visibles en pantalla para
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
		for (int j = 0; j < listVirus.size(); j++) {
			if (listVirus.get(j).getX() <= 0 || listVirus.get(j).getX() >= Gdx.graphics.getWidth()
					|| listVirus.get(j).getY() <= 0 || listVirus.get(j).getY() >= Gdx.graphics.getHeight()) {
				// System.out.println(listVirus.get(j).getClass().getSimpleName());
				listVirus.remove(j);
			}
		}
	}

	
	private void adnVirusColision(int j, int i) {
		virusKill++;
		puntos.setMarcador(puntos.getMarcador() + 100);
		puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
		puntos.setSck(virusKill);
		SarsCovKiller.ASSETMANAGER.get("sonido/impactoVirus.ogg", Sound.class).play();
		adn = listAdns.get(i);
		listVirus.remove(j);
		listAdns.remove(i);
		adn.remove();
		virus.remove();
	}

	private void virusBotColision(float IMPACTO, int j) {
		virusKill++;
		if (nanoBot.getEnergiaRango() <= 0.25) {
			nanoBot.setEnergia(0);
			SarsCovKiller.ASSETMANAGER.get("sonido/botKill.ogg", Sound.class).play();
			SarsCovKiller.ASSETMANAGER.get("sonido/GameOver1.ogg", Sound.class).play();

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

			SarsCovKiller.ASSETMANAGER.get("sonido/impactoBot.ogg", Sound.class).play();

		}
		listVirus.remove(j);
		virus.remove();
	}

	public final class ImputListener extends InputListener {
		/**
		 * @param InputEvent
		 * @param keycode
		 * @category Referente al imput listener del teclado (pulsar la tecla). Modifica
		 *           la velocidad y dirección del actor principal (NanoBot) y otras
		 *           funciones como disparar.
		 * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/InputListener.html
		 */

		private boolean keyDownW;
		private boolean keyDownS;
		private boolean keyDownA;
		private boolean keyDownD;

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
				nanoBot.setEnergia(nanoBot.getEnergiaRango() - 0.003f);

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

	private void btTactilMenu() {
		btMenu = SarsCovKiller.ASSETMANAGER.get("ui/menuIcon60.png", Texture.class);
		imageMenu = new Image(btMenu);
		imageMenu.setPosition((float) (stageJuego.getWidth() / 2 - (imageMenu.getWidth() * 1.5)), 10);
		stageJuego.addActor(imageMenu);

		imageMenu.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// PANTALLA MENU
				SarsCovKiller.ASSETMANAGER.get("sonido/fondo.ogg", Sound.class).stop();
				juego.setScreen(juego.P_MENU);
				return true;
			}
		});
	}

	private void btTactilPausa() {
		btPausa = SarsCovKiller.ASSETMANAGER.get("ui/pausaIcon60.png", Texture.class);
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
			SarsCovKiller.ASSETMANAGER.get("sonido/adn.ogg", Sound.class).play();
			// Generar adn resta energía
			nanoBot.setEnergia(nanoBot.getEnergiaRango() - 0.001f);
			// Añadir a lista de adn en pantalla
			listAdns.add(adn);
		}
	}

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
	 * @category Genera enemigos "VirusVerde/Azul/Rojo/Azul-Rojo " Spawn determinado
	 *           por virusSpawn (la suma de delta y número aleatorio
	 *           (Math.random())). Cuando virusSpawn es superior a 1 hay un nuevo
	 *           spawn.
	 * @see deltaTime: Tiempo que tarda en procesar y renderizar un frame del
	 *      videojuego.
	 */
	private void virusVerdeAzulRojoSpawn(float delta) {
		// si delta es inferior (problemas renderizado) se induce menos SPAWN
		virusSpawn = delta + (float) Math.random();

		if (virusSpawn > spawnIncre) {
			// System.out.println("virusSpawn " + virusSpawn + ">SpawnI " + spawnIncre);
			Virus virus = new Virus();
			virus.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus);
			// Añadir a lista de virus en pantalla
			listVirus.add(virus);
		}
		if (virusSpawn > spawnIncre + 0.004) {
			Virus2 virus2 = new Virus2();
			virus2.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus2);
			// Añadir a lista de virus en pantalla
			listVirus.add(virus2);
		}
		if (virusSpawn > spawnIncre + 0.006) {
			Virus3 virus3 = new Virus3();
			virus3.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus3);
			// Añadir a lista de virus en pantalla
			listVirus.add(virus3);
		}
		if (virusSpawn > spawnIncre + 0.008) {
			Virus4 virus4 = new Virus4();
			virus4.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus4);
			// Añadir a lista de virus en pantalla
			listVirus.add(virus4);
		}
		if (virusSpawn > spawnIncre + 0.0010) {
			Virus5 virus5 = new Virus5();
			virus5.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus5);
			// Añadir a lista de virus en pantalla
			listVirus.add(virus5);
		}
		if (virusSpawn > spawnIncre + 0.012) {
			Virus6 virus6 = new Virus6();
			virus6.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus6);
			// Añadir a lista de virus en pantalla
			listVirus.add(virus6);
		}
		if (virusSpawn > spawnIncre + 0.014) {
			Virus7 virus7 = new Virus7();
			virus7.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus7);
			// Añadir a lista de virus en pantalla
			listVirus.add(virus7);
		}
		if (virusSpawn > spawnIncre + 0.016) {
			Virus8 virus8 = new Virus8();
			virus8.setPosition(stageJuego.getWidth(), stageJuego.getHeight() * (float) Math.random());
			stageJuego.addActor(virus8);
			// Añadir a lista de virus en pantalla
			listVirus.add(virus8);
		}

	}

	/**
	 * @category Comprueba las colisiones entre actores Virus-Nanobot y Virus-Adn y
	 *           aplica remove
	 */
	private void accionColisionesListas() {
		// Se que es una cutrada..... pero no doy con la clave para que funcionen
		// algunos métodos necesarios (getRectangle()) mediante la herencia con Actor.
		adn = new Adn();
		// Hay virus que son letales xD
		float IMPACTO = 0.25f;
		float IMPACTOLETAL = 1f;
		
		// Si hay colision entre Virus - Nanobot. Se elimina alien, energia y puntuación
		// Si hay colision entre Virus - Adn. Se elimina alien, adn y marcador++
		for (int j = 0; j < listVirus.size(); j++) {
			if (listVirus.get(j).getClass().getSimpleName().equals("Virus")) {
				Virus virus = new Virus();
				virus = (Virus) listVirus.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get("sonido/botKill.ogg", Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get("sonido/GameOver1.ogg", Sound.class).play();
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
						SarsCovKiller.ASSETMANAGER.get("sonido/impactoBot.ogg", Sound.class).play();
					}
					listVirus.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							virusKill++;
							puntos.setMarcador(puntos.getMarcador() + 100);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
							SarsCovKiller.ASSETMANAGER.get("sonido/impactoVirus.ogg", Sound.class).play();
							adn = listAdns.get(i);
							listVirus.remove(j);
							listAdns.remove(i);
							adn.remove();
							virus.remove();
						}
					}
			} else if (listVirus.get(j).getClass().getSimpleName().equals("Virus2")) {
				Virus2 virus = new Virus2();
				virus = (Virus2) listVirus.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;					
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get("sonido/botKill.ogg", Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get("sonido/GameOver1.ogg", Sound.class).play();
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
						SarsCovKiller.ASSETMANAGER.get("sonido/impactoBot.ogg", Sound.class).play();
					}
					listVirus.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							virusKill++;
							puntos.setMarcador(puntos.getMarcador() + 100);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
							SarsCovKiller.ASSETMANAGER.get("sonido/impactoVirus.ogg", Sound.class).play();
							adn = listAdns.get(i);
							listVirus.remove(j);
							listAdns.remove(i);
							adn.remove();
							virus.remove();
						}
					}
			} else if (listVirus.get(j).getClass().getSimpleName().equals("Virus3")) {
				Virus3 virus = new Virus3();
				virus = (Virus3) listVirus.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get("sonido/botKill.ogg", Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get("sonido/GameOver1.ogg", Sound.class).play();
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
						SarsCovKiller.ASSETMANAGER.get("sonido/impactoBot.ogg", Sound.class).play();
					}
					listVirus.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							virusKill++;
							puntos.setMarcador(puntos.getMarcador() + 100);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
							SarsCovKiller.ASSETMANAGER.get("sonido/impactoVirus.ogg", Sound.class).play();
							adn = listAdns.get(i);
							listVirus.remove(j);
							listAdns.remove(i);
							adn.remove();
							virus.remove();
						}
					}
			} else if (listVirus.get(j).getClass().getSimpleName().equals("Virus4")) {
				Virus4 virus = new Virus4();
				virus = (Virus4) listVirus.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get("sonido/botKill.ogg", Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get("sonido/GameOver1.ogg", Sound.class).play();
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
						SarsCovKiller.ASSETMANAGER.get("sonido/impactoBot.ogg", Sound.class).play();
					}
					listVirus.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							virusKill++;
							puntos.setMarcador(puntos.getMarcador() + 100);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
							SarsCovKiller.ASSETMANAGER.get("sonido/impactoVirus.ogg", Sound.class).play();
							adn = listAdns.get(i);
							listVirus.remove(j);
							listAdns.remove(i);
							adn.remove();
							virus.remove();
						}
					}
			} else if (listVirus.get(j).getClass().getSimpleName().equals("Virus5")) {
				Virus5 virus = new Virus5();
				virus = (Virus5) listVirus.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get("sonido/botKill.ogg", Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get("sonido/GameOver1.ogg", Sound.class).play();
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
						SarsCovKiller.ASSETMANAGER.get("sonido/impactoBot.ogg", Sound.class).play();
					}
					listVirus.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							virusKill++;
							puntos.setMarcador(puntos.getMarcador() + 100);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
							SarsCovKiller.ASSETMANAGER.get("sonido/impactoVirus.ogg", Sound.class).play();
							adn = listAdns.get(i);
							listVirus.remove(j);
							listAdns.remove(i);
							adn.remove();
							virus.remove();
						}
					}
			} else if (listVirus.get(j).getClass().getSimpleName().equals("Virus6")) {
				Virus6 virus = new Virus6();
				virus = (Virus6) listVirus.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get("sonido/botKill.ogg", Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get("sonido/GameOver1.ogg", Sound.class).play();
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
						SarsCovKiller.ASSETMANAGER.get("sonido/impactoBot.ogg", Sound.class).play();
					}
					listVirus.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							virusKill++;
							puntos.setMarcador(puntos.getMarcador() + 100);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
							SarsCovKiller.ASSETMANAGER.get("sonido/impactoVirus.ogg", Sound.class).play();
							adn = listAdns.get(i);
							listVirus.remove(j);
							listAdns.remove(i);
							adn.remove();
							virus.remove();
						}
					}
			} else if (listVirus.get(j).getClass().getSimpleName().equals("Virus7")) {
				Virus7 virus = new Virus7();
				virus = (Virus7) listVirus.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get("sonido/botKill.ogg", Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get("sonido/GameOver1.ogg", Sound.class).play();
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
						SarsCovKiller.ASSETMANAGER.get("sonido/impactoBot.ogg", Sound.class).play();
					}
					listVirus.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							virusKill++;
							puntos.setMarcador(puntos.getMarcador() + 100);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
							SarsCovKiller.ASSETMANAGER.get("sonido/impactoVirus.ogg", Sound.class).play();
							adn = listAdns.get(i);
							listVirus.remove(j);
							listAdns.remove(i);
							adn.remove();
							virus.remove();
						}
					}
			} else if (listVirus.get(j).getClass().getSimpleName().equals("Virus8")) {
				Virus8 virus = new Virus8();
				virus = (Virus8) listVirus.get(j);
				if (virus.getRectangle().overlaps(nanoBot.getRectangle()) && virus.getX() != stageJuego.getWidth()) {
					virusKill++;
					if (nanoBot.getEnergiaRango() <= 0.25) {
						nanoBot.setEnergia(0);
						SarsCovKiller.ASSETMANAGER.get("sonido/botKill.ogg", Sound.class).play();
						SarsCovKiller.ASSETMANAGER.get("sonido/GameOver1.ogg", Sound.class).play();
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
						SarsCovKiller.ASSETMANAGER.get("sonido/impactoBot.ogg", Sound.class).play();
					}
					listVirus.remove(j);
					virus.remove();
				} else
					for (int i = 0; i < listAdns.size(); i++) {
						// Colision entre Virus - Adn. Se elimina alien, adn y marcador++
						if (listAdns.get(i).getRectangle().overlaps(virus.getRectangle())) {
							virusKill++;
							puntos.setMarcador(puntos.getMarcador() + 100);
							puntos.setNivel(((int) puntos.getMarcador() / 10000) + 1);
							puntos.setSck(virusKill);
							SarsCovKiller.ASSETMANAGER.get("sonido/impactoVirus.ogg", Sound.class).play();
							adn = listAdns.get(i);
							listVirus.remove(j);
							listAdns.remove(i);
							adn.remove();
							virus.remove();
						}
					}
			}

		}

	}


	public Stage getStage() {
		return stageJuego;
	}

	public void setStage(Stage stage) {
		this.stageJuego = stage;
	}

	public FondoPantallaJuego getFondo() {
		return fondoPJuego;
	}

	public void setFondo(FondoPantallaJuego fondo) {
		this.fondoPJuego = fondo;
	}

	public NanoBot getNanoBot() {
		return nanoBot;
	}

	public void setNanoBot(NanoBot nanoBot) {
		this.nanoBot = nanoBot;
	}

	/**
	 * @return the virus
	 */
	public Virus getVirus() {
		return virus;
	}

	/**
	 * @param virus the virus to set
	 */
	public void setVirus(Virus virus) {
		this.virus = virus;
	}

	public Adn getAdn() {
		return adn;
	}

	public void setAdn(Adn adn) {
		this.adn = adn;
	}

	public Pad getControl() {
		return control;
	}

	public void setControl(Pad control) {
		this.control = control;
	}

	public MBarraEnergia getmBarraEnergia() {
		return mBarraEnergia;
	}

	public void setmBarraEnergia(MBarraEnergia mBarraEnergia) {
		this.mBarraEnergia = mBarraEnergia;
	}

	public BarraEnergia getBarraEnergia() {
		return barraEnergia;
	}

	public void setBarraEnergia(BarraEnergia barraEnergia) {
		this.barraEnergia = barraEnergia;
	}

	public BotonDisparo getBtDisparo() {
		return btDisparo;
	}

	public void setBtDisparo(BotonDisparo btDisparo) {
		this.btDisparo = btDisparo;
	}

	public ArrayList<Actor> getListVirus() {
		return listVirus;
	}

	public void setListVirus(ArrayList<Actor> listVirus) {
		this.listVirus = listVirus;
	}

	public ArrayList<Adn> getListAdns() {
		return listAdns;
	}

	public void setListAdns(ArrayList<Adn> listAdns) {
		this.listAdns = listAdns;
	}

	public NivelPuntuacion getPuntos() {
		return puntos;
	}

	public void setPuntos(NivelPuntuacion puntos) {
		PantallaJuego.puntos = puntos;
	}

	public float getVelocidadNanoBot() {
		return velocidadNanoBot;
	}

	public void setVelocidadNanoBot(float velocidadNanoBot) {
		this.velocidadNanoBot = velocidadNanoBot;
	}

	public float getVirusSpawn() {
		return virusSpawn;
	}

	public void setVirusSpawn(float virusSpawn) {
		this.virusSpawn = virusSpawn;
	}

	public State getState() {
		return state;
	}

	public void setGameState(State s) {
		this.state = s;
	}

	/**
	 * @return the jugador
	 */
	public String getJugador() {
		return jugador;
	}

	/**
	 * @param jugador the jugador to set
	 */
	public void setJugador(String jugador) {
		this.jugador = jugador;
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
