package actores;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class NanoBot extends Actor {
	// Área de la textura
	private TextureRegion nanoBot;

	public NanoBot() {
		// Inicializar textura
		nanoBot = new TextureRegion(SarsCovKiller.ASSETMANAGER.get("nanobot.png", Texture.class), 120, 80);
		// Tamaño igual al de la textura
		setSize(nanoBot.getRegionWidth(), nanoBot.getRegionHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(nanoBot, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}

}
