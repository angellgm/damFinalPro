package com.algm.sck;

import com.algm.movimiento.MovNave;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SarsCovKiller extends ApplicationAdapter {
	SpriteBatch batch;
	Texture imgNave;
	MovNave nave;
 


	
	@Override
	public void create () {
		batch = new SpriteBatch();
		imgNave = new Texture("nave.png");
		nave = new MovNave();
	}

	@Override
	public void render () {
		if (nave.getNavePosicion()<0) {
			nave.setNaveDireccion(1);
		}
		
		if (nave.getNavePosicion()>500) {
			nave.setNaveDireccion(-1);
		}
		int navep=nave.getNavePosicion();
		nave.setNavePosicion(navep += (3 * nave.getNaveDireccion()));
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(imgNave, nave.getNavePosicion(), 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		imgNave.dispose();
	}
}
