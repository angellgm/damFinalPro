package actores;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Laser extends Actor {
	// Área de la textura
	private TextureRegion laser;

	public Laser() {
		// Inicializar textura
		laser = new TextureRegion(SarsCovKiller.ASSETMANAGER.get("laser.png", Texture.class), 120, 80);
		// Tamaño igual al de la textura
		setSize(laser.getRegionWidth(), laser.getRegionHeight());
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(laser, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
				getScaleX(), getScaleY(), getRotation());
	}
	
}
