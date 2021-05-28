/**
 * 
 */
package com.algm.pantallas;

import com.algm.actorcontrol.NivelPuntuacion;
import com.algm.actorpantalla.FondoMenu;
import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Application.ApplicationType;
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
public class PantallaMenu extends Pantalla {
	private Stage stageMenu;
	private FondoMenu fondoMenu;
	private Viewport viewport;
	// private NivelPuntuacion puntos;
	private Texture btJugar;
	private Texture btComoJugar;
	private Texture btContinuar;
	private Texture btSalir;
	
	private Texture btJugarTecla;
	private Texture btComoJugarTecla;
	private Texture btContinuarTecla;
	private Texture btSalirTecla;
	
	private Texture logoSCK;

	private Image imageJugar;
	private Image imageComoJugar;
	private Image imageContinuar;
	private Image imageSalir;
	
	private Image imageJugarTecla;
	private Image imageComoJugarTecla;
	private Image imageContinuarTecla;
	private Image imageSalirTecla;
	
	private Image imageLogoSCK;

	public PantallaMenu(SarsCovKiller juego) {
		super(juego);
	}

	@Override
	public void show() {

		// Cargar escena para actores
		// camera = new PerspectiveCamera();
		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stageMenu = new Stage(viewport, juego.sckBatch);

		fondoMenu = new FondoMenu();
		fondoMenu.setPosition(0, 0);
		stageMenu.addActor(fondoMenu);
		
//		puntos = PantallaJuego.puntos;
//		puntos.setPosition(stageMenu.getWidth() / 3f, stageMenu.getHeight() / 1.2f);
//		stageMenu.addActor(puntos);

		btContinuar();
		btJugar();
		btSalir();
		btComoJugar();
		logoSCK();

		if (Gdx.app.getType() == ApplicationType.Desktop) {
			btContinuarTecla();
			btJugarTecla();
			btSalirTecla();
			btComoJugarTecla();
		}

		Gdx.input.setInputProcessor(stageMenu);
		stageMenu.setKeyboardFocus(fondoMenu);
		fondoMenu.addListener(new InpLGameOver());
	}

	@Override
	public void render(float delta) {
		// Limpiar pantalla para evitar trazos fantasma de los actores
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stageMenu.act(Gdx.graphics.getDeltaTime()); // Actulizar
		stageMenu.draw(); // Dibujar

	}

	@Override
	public void hide() {
		// Se destrulle la pantalla en el hide para que no se acumulen al cambiar de
		// pantalla
		stageMenu.dispose();
	}

	@Override
	public void dispose() {
	}

	private void btSalir() {
		btSalir = SarsCovKiller.ASSETMANAGER.get("ui/btSalir.png", Texture.class);
		imageSalir = new Image(btSalir);
		imageSalir.setPosition((imageContinuar.getX() + imageContinuar.getWidth()) + (stageMenu.getWidth() / 50),
				stageMenu.getHeight() / 7f);
		stageMenu.addActor(imageSalir);

		imageSalir.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// Salir de la app
				Gdx.app.exit();
				// System.out.println("Touch SALIR");
				return true;
			}
		});
	}

	private void btJugar() {
		btJugar = SarsCovKiller.ASSETMANAGER.get("ui/btJugar.png", Texture.class);
		imageJugar = new Image(btJugar);
		imageJugar.setPosition(imageContinuar.getX() - (imageJugar.getWidth() + (stageMenu.getWidth() / 50)),
				stageMenu.getHeight() / 7f);
		stageMenu.addActor(imageJugar);

		imageJugar.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// PANTALLA MENU
				juego.setScreen(juego.P_JUEGO);
				// System.out.println("Touch MENU");
				return true;
			}
		});
	}

	private void btContinuar() {
		btContinuar = SarsCovKiller.ASSETMANAGER.get("ui/btContinuar.png", Texture.class);
		imageContinuar = new Image(btContinuar);
		imageContinuar.setPosition(stageMenu.getWidth() / 2f - (imageContinuar.getWidth() / 2),
				stageMenu.getHeight() / 7f);
		stageMenu.addActor(imageContinuar);

		imageContinuar.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// PANTALLA JUEGO
				juego.setScreen(juego.P_JUEGO);
				// System.out.println("Touch CONTINUAR");
				return true;
			}

		});
	}
	
	private void btComoJugar() {
		btComoJugar = SarsCovKiller.ASSETMANAGER.get("ui/btComoJugar.png", Texture.class);
		imageComoJugar = new Image(btComoJugar);
		imageComoJugar.setPosition(stageMenu.getWidth() - (imageComoJugar.getWidth()+15),
				stageMenu.getHeight() - (imageComoJugar.getHeight()+15));
		stageMenu.addActor(imageComoJugar);

		imageJugar.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// PANTALLA COMO JUGAR?
				juego.setScreen(juego.P_COMOJUGAR);
				return true;
			}
		});
	}

	private void btJugarTecla() {
		btJugarTecla = SarsCovKiller.ASSETMANAGER.get("ui/j.png", Texture.class);
		imageJugarTecla = new Image(btJugarTecla);
		imageJugarTecla.setPosition((imageJugar.getX() + imageJugar.getWidth() / 2) - (imageJugarTecla.getWidth() / 2),
				imageJugar.getY() - imageJugarTecla.getHeight());
		stageMenu.addActor(imageJugarTecla);
	}

	private void btContinuarTecla() {
		btContinuarTecla = SarsCovKiller.ASSETMANAGER.get("ui/c.png", Texture.class);
		imageContinuarTecla = new Image(btContinuarTecla);
		imageContinuarTecla.setPosition(
				(imageContinuar.getX() + imageContinuar.getWidth() / 2) - (imageContinuarTecla.getWidth() / 2),
				imageContinuar.getY() - imageContinuarTecla.getHeight());
		stageMenu.addActor(imageContinuarTecla);
	}

	private void btSalirTecla() {
		btSalirTecla = SarsCovKiller.ASSETMANAGER.get("ui/esc.png", Texture.class);
		imageSalirTecla = new Image(btSalirTecla);
		imageSalirTecla.setPosition((imageSalir.getX() + imageSalir.getWidth() / 2) - (imageSalirTecla.getWidth() / 2),
				imageSalir.getY() - imageSalirTecla.getHeight());
		stageMenu.addActor(imageSalirTecla);
	}
	private void btComoJugarTecla() {
		btComoJugarTecla = SarsCovKiller.ASSETMANAGER.get("ui/q.png", Texture.class);
		imageComoJugarTecla = new Image(btComoJugarTecla);
		imageComoJugarTecla.setPosition(
				(imageComoJugar.getX() + imageComoJugar.getWidth() / 2) - (imageComoJugarTecla.getWidth() / 2),
				imageComoJugar.getY() - imageComoJugarTecla.getHeight());
		stageMenu.addActor(imageComoJugarTecla);
	}
	
	private void logoSCK() {
		logoSCK = SarsCovKiller.ASSETMANAGER.get("ui/sckLogo.png", Texture.class);
		imageLogoSCK = new Image(logoSCK);
		imageLogoSCK.setPosition(20, stageMenu.getHeight() - (imageLogoSCK.getHeight() + 20));
		stageMenu.addActor(imageLogoSCK);
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
				// Salir de la app
				Gdx.app.exit();
				return true;

			case Input.Keys.C:
				// PANTALLA JUEGO
				juego.setScreen(juego.P_JUEGO);
				return true;

			case Input.Keys.J:
				// PANTALLA MENU
				juego.setScreen(juego.P_JUEGO);
				return true;
				
			case Input.Keys.Q:
				// PANTALLA MENU
				juego.setScreen(juego.P_COMOJUGAR);
				return true;

			default:
				return false;
			}
		}
	}
}
