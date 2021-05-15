package com.algm.actores;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class NanoBot extends Actor {
	// Área de la textura
	private TextureRegion nanoBot;
	public Vector2 vector; 


	public NanoBot() {
		// Inicializar textura
		nanoBot = new TextureRegion(SarsCovKiller.ASSETMANAGER.get("nanobot.png", Texture.class), 120, 80);
		// Tamaño igual al de la textura
		setSize(nanoBot.getRegionWidth(), nanoBot.getRegionHeight());
		// Velocidad inicial 0
		vector = new Vector2(0, 0); 
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(nanoBot, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}
	
	//Movimiento según tiempo de actualización y la velocidad del actor
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		moveBy(vector.x * delta, vector.y * delta );
		//Mapear limites de escenario para el actor
		
		if(getX() < 0) {
			setX(0);
			vector.x = 0;
			
		}else if(getRight() > getStage().getWidth()) {
			setX(getStage().getWidth() - getWidth());
			vector.x = 0;
		}
		
		if(getY() < 0) {
			setY(0);
			vector.y = 0;
			
		}else if(getTop() > getStage().getHeight()) {
			setY(getStage().getHeight() - getHeight());
			vector.y = 0;
		}
		
	}
	

	public TextureRegion getNanoBot() {
		return nanoBot;
	}

	public void setNanoBot(TextureRegion nanoBot) {
		this.nanoBot = nanoBot;
	}

	public Vector2 getVectorNanoBot() {
		return vector;
	}

	public void setVectorNanoBot(Vector2 vectorNanoBot) {
		this.vector = vectorNanoBot;
	}

}
