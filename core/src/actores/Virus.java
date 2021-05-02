package actores;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Virus extends Actor {

	// Área de la textura
	private TextureRegion virus;

	public Virus() {
		// Inicializar textura
		virus = new TextureRegion(SarsCovKiller.ASSETMANAGER.get("virus.png", Texture.class), 120, 120);
		// Tamaño igual al de la textura
		setSize(virus.getRegionWidth(), virus.getRegionHeight());
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(virus, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
				getScaleX(), getScaleY(), getRotation());
	}

}
