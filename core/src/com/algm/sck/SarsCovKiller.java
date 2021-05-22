package com.algm.sck;

import com.algm.actorcontrol.MovNave;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * @author Angel
 * 
 * Alt-Shift-J -> Gen coments
 * NOTAS sobre el cambio a Game vs ApplicationAdapter
 * La clase Game tiene un poco más de sobrecarga con el uso de pantallas. Sin embargo, esta sobrecarga está diseñada para facilitar
 * la implementación de diferentes etapas / niveles en su juego. Es importante tener en cuenta que esta sobrecarga es mínima.
 * El ApplicationAdapter no tiene ninguna sobrecarga adicional (es una aplicación recta de un ApplicationListener).
 * 1200*600 Res. Original
 */

public class SarsCovKiller extends Game implements Screen {
	/**
	 * ASSETMANAGER
	 * https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/assets/AssetManager.html
	 * clase que se usa para cargar y descargar recursos para el proyecto.
	 * Almacenará una cantidad de referencias que tiene un activo y lo mantendrá cargado hasta que ya no sea necesario.
	 */
	public static final AssetManager ASSETMANAGER = new AssetManager();

	
	@Override
	public void create() {
		
		ASSETMANAGER.load("laser.png", Texture.class);
		ASSETMANAGER.load("virus.png", Texture.class);
		ASSETMANAGER.load("nanobot.png", Texture.class);
		ASSETMANAGER.load("ui/mEnergia.png", Texture.class);
		ASSETMANAGER.load("ui/bEnergia.png", Texture.class);
		ASSETMANAGER.load("ui/botonDisparo.png", Texture.class);
		
		//Pantalla de carga
		while(!ASSETMANAGER.update()) {
			
		}
		//Primera pantalla Test
		setScreen(new PantallaJuego(this));
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		super.dispose();
		//Eliminar ASSETMANAGER despues de salir del juego
		ASSETMANAGER.dispose();
	}


}
