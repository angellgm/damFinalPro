package com.algm.sck;

import com.algm.pantallas.Pantalla;
import com.algm.pantallas.PantallaCargaAssets;
import com.algm.pantallas.PantallaGameOver;
import com.algm.pantallas.PantallaMenu;
import com.algm.pantallas.PantallaComoJugar;
import com.algm.pantallas.PantallaJuego;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author Angel
 * 
 *         Alt-Shift-J -> Gen coments NOTAS sobre el cambio a Game vs
 *         ApplicationAdapter La clase Game tiene un poco más de sobrecarga con
 *         el uso de pantallas. Sin embargo, esta sobrecarga está diseñada para
 *         facilitar la implementación de diferentes etapas / niveles en su
 *         juego. Es importante tener en cuenta que esta sobrecarga es mínima.
 *         El ApplicationAdapter no tiene ninguna sobrecarga adicional (es una
 *         aplicación recta de un ApplicationListener). 1200*600 Res. Original
 */

public class SarsCovKiller extends Game {
	/**
	 * ASSETMANAGER
	 * https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/assets/AssetManager.html
	 * clase que se usa para cargar y descargar recursos para el proyecto.
	 * Almacenará una cantidad de referencias que tiene un activo y lo mantendrá
	 * cargado hasta que ya no sea necesario.
	 */

	public SpriteBatch sckBatch;// = new SpriteBatch();
	public static final AssetManager ASSETMANAGER = new AssetManager();
	public final Pantalla P_CARGA;
	public final Pantalla P_MENU;
	public final Pantalla P_JUEGO;
	public final Pantalla P_GAMEOVER;
	public final Pantalla P_COMOJUGAR;
	public static boolean esContinuarPartida = true;
	public static String slotGuardado = "";

	/**
	 * 
	 */
	public SarsCovKiller() {
		P_CARGA = new PantallaCargaAssets(this);
		P_MENU = new PantallaMenu(this);
		P_JUEGO = new PantallaJuego(this);
		P_GAMEOVER = new PantallaGameOver(this);
		P_COMOJUGAR = new PantallaComoJugar(this);
	}

	/**
	 *
	 */
	@Override
	public void create() {

		sckBatch = new SpriteBatch();
		// cargar Assets
		assetsLoad();
		// Primera pantalla
		setScreen(P_CARGA);
	}

	/**
	 *
	 */
	@Override
	public void dispose() {
		// System.out.println("Dispose SCK");
		super.dispose();

		// Eliminar ASSETMANAGER despues de salir del juego
		ASSETMANAGER.dispose();
		sckBatch.dispose();
	}

	private void assetsLoad() {
		ASSETMANAGER.load("fondo/sckCarga.png", Texture.class);
		ASSETMANAGER.load("anivirus.png", Texture.class);
		ASSETMANAGER.load("anivirus2.png", Texture.class);
		ASSETMANAGER.load("anivirus3.png", Texture.class);
		ASSETMANAGER.load("anivirus4.png", Texture.class);
		ASSETMANAGER.load("anivirus5.png", Texture.class);
		ASSETMANAGER.load("anivirus6.png", Texture.class);
		ASSETMANAGER.load("anivirus7.png", Texture.class);
		ASSETMANAGER.load("anivirus8.png", Texture.class);
		ASSETMANAGER.load("nanobot.png", Texture.class);
		ASSETMANAGER.load("adn.png", Texture.class);
		ASSETMANAGER.load("bonusAdn.png", Texture.class);
		ASSETMANAGER.load("bonusEnergia.png", Texture.class);
		ASSETMANAGER.load("bonusPuntos.png", Texture.class);
		ASSETMANAGER.load("bonusVelocidad.png", Texture.class);
		ASSETMANAGER.load("fondo/menu.png", Texture.class);
		ASSETMANAGER.load("ui/sckLogo.png", Texture.class);
		ASSETMANAGER.load("ui/pausa.png", Texture.class);
		ASSETMANAGER.load("ui/mEnergia.png", Texture.class);
		ASSETMANAGER.load("ui/bEnergia.png", Texture.class);
		ASSETMANAGER.load("ui/botonDisparo.png", Texture.class);
		ASSETMANAGER.load("ui/btSalir.png", Texture.class);
		ASSETMANAGER.load("ui/btMenu.png", Texture.class);
		ASSETMANAGER.load("ui/menuIcon60.png", Texture.class);
		ASSETMANAGER.load("ui/pausaIcon60.png", Texture.class);
		ASSETMANAGER.load("ui/btContinuar.png", Texture.class);
		ASSETMANAGER.load("ui/btOpciones.png", Texture.class);
		ASSETMANAGER.load("ui/btComoJugar.png", Texture.class);
		ASSETMANAGER.load("ui/btJugar.png", Texture.class);

		ASSETMANAGER.load("ui/esc.png", Texture.class);
		ASSETMANAGER.load("ui/m.png", Texture.class);
		ASSETMANAGER.load("ui/c.png", Texture.class);
		ASSETMANAGER.load("ui/o.png", Texture.class);
		ASSETMANAGER.load("ui/c.png", Texture.class);
		ASSETMANAGER.load("ui/q.png", Texture.class);
		ASSETMANAGER.load("ui/j.png", Texture.class);
		ASSETMANAGER.load("ui/1.png", Texture.class);
		ASSETMANAGER.load("ui/1a.png", Texture.class);
		ASSETMANAGER.load("ui/2.png", Texture.class);
		ASSETMANAGER.load("ui/2a.png", Texture.class);
		ASSETMANAGER.load("ui/3.png", Texture.class);
		ASSETMANAGER.load("ui/3a.png", Texture.class);
		ASSETMANAGER.load("ui/4.png", Texture.class);
		ASSETMANAGER.load("ui/4a.png", Texture.class);
		ASSETMANAGER.load("ui/5.png", Texture.class);
		ASSETMANAGER.load("ui/5a.png", Texture.class);

		ASSETMANAGER.load("sonido/fondoMenu.ogg", Sound.class);
		ASSETMANAGER.load("sonido/fondo.ogg", Sound.class);
		ASSETMANAGER.load("sonido/adn.ogg", Sound.class);
		ASSETMANAGER.load("sonido/botKill.ogg", Sound.class);
		ASSETMANAGER.load("sonido/GameOver1.ogg", Sound.class);
		ASSETMANAGER.load("sonido/impactoBot.ogg", Sound.class);
		ASSETMANAGER.load("sonido/impactoVirus.ogg", Sound.class);
		ASSETMANAGER.load("sonido/virusKill.ogg", Sound.class);
		ASSETMANAGER.load("sonido/metal.ogg", Sound.class);
		ASSETMANAGER.load("sonido/bonus.ogg", Sound.class);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

}
