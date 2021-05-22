package com.algm.actorcontrol;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * @author Angel
 *
 */
public class Energia extends Actor implements NivelEnergia {
	private float energia;
	private float autoIncrementoEnergia;

	public Energia() {
		energia = 2;
		autoIncrementoEnergia = 0;

	}
	
	
	/**
	 * La energia no puede ser inferior a 0 ni superior a 2
	 * 
	 * @return Energía del nanoBot
	 */
	
	@Override
	public float getEnergiaRango() {
		if (energia < 0) {
			energia = 0;
		} else if (energia > 2) {
			energia = 2;
		}
		return energia;
	}

	@Override
	public void setEnergia(float energia) {
		this.energia = energia;
	}

	@Override
	public void subirEnergia(float energia) {
		this.energia += energia;
	}

	@Override
	public void act(float delta) {
		// Incrementar energia cada x tiempo
		super.act(delta);
		autoIncrementoEnergia += delta;
		if (energia < 2 && autoIncrementoEnergia > 2) {
			energia+= 0.5f;
			System.out.println("energia++");
		}
	}

}
