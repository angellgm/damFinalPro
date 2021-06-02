/**
 * 
 */
package com.algm.pantallas;


import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * @author Angel
 *
 */
public class PantallaCargaAssets extends Pantalla {


	public PantallaCargaAssets(SarsCovKiller juego) {
		super(juego);

	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		//Inicia el menú después de cargar los assets
		if (SarsCovKiller.ASSETMANAGER.update()) {
			//System.out.println("is loader ALL");
			juego.setScreen(juego.P_MENU);
		}
		
		//Inicia la pantalla de carga después de cargar sus assets
		if (SarsCovKiller.ASSETMANAGER.isLoaded("fondo/sckCarga.png")) {
			//System.out.println("is loader sck carga");
			juego.sckBatch.begin();
			juego.sckBatch.draw(SarsCovKiller.ASSETMANAGER.get("fondo/sckCarga.png", Texture.class), 0, 0, Gdx.graphics.getWidth(),
					Gdx.graphics.getHeight());
			juego.sckBatch.end();
		}
	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

}
