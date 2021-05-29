/**
 * 
 */
package com.algm.pantallas;

import com.algm.actorpantalla.FondoMenu;
import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Application.ApplicationType;
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
	private Texture btTexture1, btTexture1a, btTexture2, btTexture2a, btTexture3, btTexture3a, btTexture4, btTexture4a,
			btTexture5, btTexture5a;
	private Image image1, image1a, image2, image2a, image3, image3a, image4, image4a, image5, image5a;
	private Image imageJugar;
	private Image imageComoJugar;
	private Image imageContinuar;
	private Image imageSalir;
	private Image imageJugarTecla;
	private Image imageComoJugarTecla;
	private Image imageContinuarTecla;
	private Image imageSalirTecla;
	//public static String slotGuardado;
	private Image imageLogoSCK;
	

	public PantallaMenu(SarsCovKiller juego) {
		super(juego);
	}

	@Override
	public void show() {

		// Cargar escena para actores
		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stageMenu = new Stage(viewport, juego.sckBatch);

		//SarsCovKiller.ASSETMANAGER.get("sonido/fondoMenu.ogg", Sound.class).loop();

		fondoMenu = new FondoMenu();
		fondoMenu.setPosition(0, 0);
		fondoMenu.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stageMenu.addActor(fondoMenu);
		SarsCovKiller.slotGuardado = "SLOT1";

		btContinuar();
		btJugar();
		btSalir();
		btComoJugar();
		logoSCK();
		btSlot3();
		btSlot2();
		btSlot4();
		btSlot5();
		btSlot1();

		if (Gdx.app.getType() == ApplicationType.Desktop) {
			btJugarTecla();
			btContinuarTecla();
			btSalirTecla();
			btComoJugarTecla();
		}

		Gdx.input.setInputProcessor(stageMenu);
		stageMenu.setKeyboardFocus(fondoMenu);
		fondoMenu.addListener(new InpLGameOver());
	}

	@Override
	public void resize(int width, int height) {
		// juego.sckBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		super.resize(width, height);
	}

	// Bucle principal
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

	private void btJugar() {
		btJugar = SarsCovKiller.ASSETMANAGER.get("ui/btJugar.png", Texture.class);
		imageJugar = new Image(btJugar);
		imageJugar.setPosition(imageContinuar.getX() - (imageJugar.getWidth() + (stageMenu.getWidth() / 50)),
				stageMenu.getHeight() / 7f);
		stageMenu.addActor(imageJugar);

		imageJugar.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// PANTALLA JUEGO
				SarsCovKiller.esContinuarPartida = false;
				juego.setScreen(juego.P_JUEGO);
				SarsCovKiller.ASSETMANAGER.get("sonido/fondoMenu.ogg", Sound.class).stop();
				
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
				SarsCovKiller.esContinuarPartida = true;
				juego.setScreen(juego.P_JUEGO);
				SarsCovKiller.ASSETMANAGER.get("sonido/fondoMenu.ogg", Sound.class).stop();
				return true;
			}

		});
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
				return true;
			}
		});
	}

	private void btComoJugar() {
		btComoJugar = SarsCovKiller.ASSETMANAGER.get("ui/btComoJugar.png", Texture.class);
		imageComoJugar = new Image(btComoJugar);
		imageComoJugar.setPosition(stageMenu.getWidth() - (imageComoJugar.getWidth() + 15),
				stageMenu.getHeight() - (imageComoJugar.getHeight() + 15));
		stageMenu.addActor(imageComoJugar);

		imageComoJugar.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// PANTALLA COMO JUGAR?
				juego.setScreen(juego.P_COMOJUGAR);
				return true;
			}
		});
	}

	private void btSlot3() {
		btTexture3 = SarsCovKiller.ASSETMANAGER.get("ui/3.png", Texture.class);
		image3 = new Image(btTexture3);
		image3.setPosition(imageContinuar.getX() + imageContinuar.getWidth() / 2 - image3.getWidth() / 2,
				imageContinuar.getY() + imageContinuar.getHeight() + 5);
		stageMenu.addActor(image3);

		btTexture3a = SarsCovKiller.ASSETMANAGER.get("ui/3a.png", Texture.class);
		image3a = new Image(btTexture3a);
		image3a.setPosition(imageContinuar.getX() + imageContinuar.getWidth() / 2 - image3.getWidth() / 2,
				imageContinuar.getY() + imageContinuar.getHeight() + 5);
		stageMenu.addActor(image3a);
		image3a.setVisible(false);

		image3.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				esSlot3();
				return true;
			}
		});
	}

	private void esSlot3() {
		SarsCovKiller.slotGuardado = "SLOT3";
		image1a.setVisible(false);
		image2a.setVisible(false);
		image3a.setVisible(true);
		image4a.setVisible(false);
		image5a.setVisible(false);
	}

	private void btSlot2() {
		btTexture2 = SarsCovKiller.ASSETMANAGER.get("ui/2.png", Texture.class);
		image2 = new Image(btTexture2);
		image2.setPosition(image3.getX() - image3.getWidth(), imageContinuar.getY() + imageContinuar.getHeight() + 5);
		stageMenu.addActor(image2);

		btTexture2a = SarsCovKiller.ASSETMANAGER.get("ui/2a.png", Texture.class);
		image2a = new Image(btTexture2a);
		image2a.setPosition(image3.getX() - image3.getWidth(), imageContinuar.getY() + imageContinuar.getHeight() + 5);
		stageMenu.addActor(image2a);
		image2a.setVisible(false);

		image2.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				esSlot2();
				return true;
			}
		});
	}

	private void esSlot2() {
		SarsCovKiller.slotGuardado = "SLOT2";
		image1a.setVisible(false);
		image2a.setVisible(true);
		image3a.setVisible(false);
		image4a.setVisible(false);
		image5a.setVisible(false);
	}

	private void btSlot4() {
		btTexture4 = SarsCovKiller.ASSETMANAGER.get("ui/4.png", Texture.class);
		image4 = new Image(btTexture4);
		image4.setPosition(image3.getX() + image3.getWidth(), imageContinuar.getY() + imageContinuar.getHeight() + 5);
		stageMenu.addActor(image4);

		btTexture4a = SarsCovKiller.ASSETMANAGER.get("ui/4a.png", Texture.class);
		image4a = new Image(btTexture4a);
		image4a.setPosition(image3.getX() + image3.getWidth(), imageContinuar.getY() + imageContinuar.getHeight() + 5);
		stageMenu.addActor(image4a);
		image4a.setVisible(false);

		image4.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				esSlot4();
				return true;
			}
		});
	}

	private void esSlot4() {
		SarsCovKiller.slotGuardado = "SLOT4";
		image1a.setVisible(false);
		image2a.setVisible(false);
		image3a.setVisible(false);
		image4a.setVisible(true);
		image5a.setVisible(false);
	}

	private void btSlot5() {
		btTexture5 = SarsCovKiller.ASSETMANAGER.get("ui/5.png", Texture.class);
		image5 = new Image(btTexture5);
		image5.setPosition(image4.getX() + image4.getWidth(), imageContinuar.getY() + imageContinuar.getHeight() + 5);
		stageMenu.addActor(image5);

		btTexture5a = SarsCovKiller.ASSETMANAGER.get("ui/5a.png", Texture.class);
		image5a = new Image(btTexture5a);
		image5a.setPosition(image4.getX() + image4.getWidth(), imageContinuar.getY() + imageContinuar.getHeight() + 5);
		stageMenu.addActor(image5a);
		image5a.setVisible(false);

		image5.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				esSlot5();
				return true;
			}
		});
	}

	private void esSlot5() {
		SarsCovKiller.slotGuardado = "SLOT5";
		image1a.setVisible(false);
		image2a.setVisible(false);
		image3a.setVisible(false);
		image4a.setVisible(false);
		image5a.setVisible(true);
	}

	private void btSlot1() {
		btTexture1 = SarsCovKiller.ASSETMANAGER.get("ui/1.png", Texture.class);
		image1 = new Image(btTexture1);
		image1.setPosition(image2.getX() - image2.getWidth(), imageContinuar.getY() + imageContinuar.getHeight() + 5);
		stageMenu.addActor(image1);

		btTexture1a = SarsCovKiller.ASSETMANAGER.get("ui/1a.png", Texture.class);
		image1a = new Image(btTexture1a);
		image1a.setPosition(image2.getX() - image2.getWidth(), imageContinuar.getY() + imageContinuar.getHeight() + 5);
		stageMenu.addActor(image1a);
		image1a.setVisible(true);

		image1.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				esSlot1();
				return true;
			}
		});
	}

	private void esSlot1() {
		SarsCovKiller.slotGuardado = "SLOT1";
		image1a.setVisible(true);
		image2a.setVisible(false);
		image3a.setVisible(false);
		image4a.setVisible(false);
		image5a.setVisible(false);
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
				// PANTALLA JUEGO CONTINUAR
				SarsCovKiller.esContinuarPartida = true;
				juego.setScreen(juego.P_JUEGO);
				SarsCovKiller.ASSETMANAGER.get("sonido/fondoMenu.ogg", Sound.class).stop();
				return true;

			case Input.Keys.J:
				// PANTALLA JUEGO PARTIDA NUEVA
				SarsCovKiller.esContinuarPartida = false;
				juego.setScreen(juego.P_JUEGO);
				SarsCovKiller.ASSETMANAGER.get("sonido/fondoMenu.ogg", Sound.class).stop();
				return true;

			case Input.Keys.Q:
				// PANTALLA COMOJUGAR
				juego.setScreen(juego.P_COMOJUGAR);
				return true;

			case Input.Keys.NUM_1:
				esSlot1();
				return true;

			case Input.Keys.NUM_2:
				esSlot2();
				return true;

			case Input.Keys.NUM_3:
				esSlot3();
				return true;

			case Input.Keys.NUM_4:
				esSlot4();
				return true;

			case Input.Keys.NUM_5:
				esSlot5();
				return true;

			default:
				return false;
			}
		}
	}
}
