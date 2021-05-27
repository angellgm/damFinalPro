/**
 * 
 */
package com.algm.pantallas;

import com.algm.actorcontrol.NivelPuntuacion;
import com.algm.actorpantalla.FondoGameOver;
import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
public class PantallaGameOver extends Pantalla {
	private Stage stageGameOver;
	private FondoGameOver fondoGameOver;
	private Viewport viewport;
	private NivelPuntuacion puntos;
	private Texture btMenu;
	private Texture btContinuar;
	private Texture btSalir;
	private Texture btMenuTecla;
	private Texture btContinuarTecla;
	private Texture btSalirTecla;
	private Image imageMenu;
	private Image imageContinuar;
	private Image imageSalir;
	private Image imageMenuTecla;
	private Image imageContinuarTecla;
	private Image imageSalirTecla;

	public PantallaGameOver(SarsCovKiller juego) {
		super(juego);
		// juego.setScreen(juego.P_GAMEOVER);
	}

	@Override
	public void show() {

		// Cargar escena para actores
		// camera = new PerspectiveCamera();
		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stageGameOver = new Stage(viewport, juego.sckBatch);

		fondoGameOver = new FondoGameOver();
		fondoGameOver.setPosition(0, 0);

		puntos = PantallaJuego.puntos;
		puntos.setPosition(stageGameOver.getWidth() / 3f, stageGameOver.getHeight() / 1.2f);

		stageGameOver.addActor(fondoGameOver);
		stageGameOver.addActor(puntos);

		btContinuar();
		btMenu();
		btSalir();

		if (Gdx.app.getType() == ApplicationType.Desktop) {
			btContinuarTecla();
			btMenuTecla();
			btSalirTecla();
		}

		Gdx.input.setInputProcessor(stageGameOver);
		stageGameOver.setKeyboardFocus(fondoGameOver);
		fondoGameOver.addListener(new InpLGameOver());

	}

	@Override
	public void render(float delta) {
		// Limpiar pantalla para evitar trazos fantasma de los actores
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stageGameOver.act(Gdx.graphics.getDeltaTime()); // Actulizar
		stageGameOver.draw(); // Dibujar

	}

	@Override
	public void hide() {
		// Se destrulle la pantalla en el hide para que no se acumulen al cambiar de
		// pantalla
		stageGameOver.dispose();
	}

	@Override
	public void dispose() {
	}

	private void btSalir() {
		btSalir = SarsCovKiller.ASSETMANAGER.get("ui/btSalir.png", Texture.class);
		imageSalir = new Image(btSalir);
		imageSalir.setPosition((imageContinuar.getX() + imageContinuar.getWidth()) + (stageGameOver.getWidth() / 50),
				stageGameOver.getHeight() / 6f);
		stageGameOver.addActor(imageSalir);

		imageSalir.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				SarsCovKiller.ASSETMANAGER.get("sonido/fondo.ogg", Sound.class).stop();
				// Salir de la app
				Gdx.app.exit();
				// System.out.println("Touch SALIR");
				return true;
			}
		});
	}

	private void btMenu() {
		btMenu = SarsCovKiller.ASSETMANAGER.get("ui/btMenu.png", Texture.class);
		imageMenu = new Image(btMenu);
		imageMenu.setPosition(imageContinuar.getX() - (imageMenu.getWidth() + (stageGameOver.getWidth() / 50)),
				stageGameOver.getHeight() / 6f);
		stageGameOver.addActor(imageMenu);

		imageMenu.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				SarsCovKiller.ASSETMANAGER.get("sonido/fondo.ogg", Sound.class).stop();
				// PANTALLA MENU
				juego.setScreen(juego.P_MENU);
				// System.out.println("Touch MENU");
				return true;
			}
		});
	}

	private void btContinuar() {
		btContinuar = SarsCovKiller.ASSETMANAGER.get("ui/btContinuar.png", Texture.class);
		imageContinuar = new Image(btContinuar);
		imageContinuar.setPosition(stageGameOver.getWidth() / 2f - (imageContinuar.getWidth() / 2),
				stageGameOver.getHeight() / 6f);
		stageGameOver.addActor(imageContinuar);

		imageContinuar.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				SarsCovKiller.ASSETMANAGER.get("sonido/fondo.ogg", Sound.class).stop();
				// PANTALLA JUEGO
				juego.setScreen(juego.P_JUEGO);
				// System.out.println("Touch CONTINUAR");
				return true;
			}
		});
	}

	private void btMenuTecla() {
		btMenuTecla = SarsCovKiller.ASSETMANAGER.get("ui/m.png", Texture.class);
		imageMenuTecla = new Image(btMenuTecla);
		imageMenuTecla.setPosition((imageMenu.getX() + imageMenu.getWidth() / 2) - (imageMenuTecla.getWidth() / 2),
				imageMenu.getY() - imageMenuTecla.getHeight());
		stageGameOver.addActor(imageMenuTecla);
	}

	private void btContinuarTecla() {
		btContinuarTecla = SarsCovKiller.ASSETMANAGER.get("ui/c.png", Texture.class);
		imageContinuarTecla = new Image(btContinuarTecla);
		imageContinuarTecla.setPosition(
				(imageContinuar.getX() + imageContinuar.getWidth() / 2) - (imageContinuarTecla.getWidth() / 2),
				imageContinuar.getY() - imageContinuarTecla.getHeight());
		stageGameOver.addActor(imageContinuarTecla);
	}

	private void btSalirTecla() {
		btSalirTecla = SarsCovKiller.ASSETMANAGER.get("ui/esc.png", Texture.class);
		imageSalirTecla = new Image(btSalirTecla);
		imageSalirTecla.setPosition((imageSalir.getX() + imageSalir.getWidth() / 2) - (imageSalirTecla.getWidth() / 2),
				imageSalir.getY() - imageSalirTecla.getHeight());
		stageGameOver.addActor(imageSalirTecla);
	}

	private final class InpLGameOver extends InputListener {
		/**
		 * @param InputEvent
		 * @param keycode
		 * @category Referente al imput listener del teclado
		 * @see https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/InputListener.html
		 */

		@Override
		public boolean keyDown(InputEvent event, int keycode) {

			switch (keycode) {
			case Input.Keys.ESCAPE:
				SarsCovKiller.ASSETMANAGER.get("sonido/fondo.ogg", Sound.class).stop();
				// Salir de la app
				Gdx.app.exit();
				return true;

			case Input.Keys.C:
				SarsCovKiller.ASSETMANAGER.get("sonido/fondo.ogg", Sound.class).stop();
				// PANTALLA JUEGO
				juego.setScreen(juego.P_JUEGO);
				return true;

			case Input.Keys.M:
				SarsCovKiller.ASSETMANAGER.get("sonido/fondo.ogg", Sound.class).stop();
				// PANTALLA MENU
				juego.setScreen(juego.P_MENU);
				return true;

			default:
				return false;
			}
		}
	}
}

/*
 * 
 */