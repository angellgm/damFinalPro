package com.algm.actores;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class EnergiaBot extends Actor {
	private float energia;
	private float autoIncrementoEnergia;

	public EnergiaBot() {
		energia = 2;
		autoIncrementoEnergia = 0;
	}

	public float getEnergiaRango() {
		if (energia < 0) {
			energia = 0;
		} else if (energia > 2) {
			energia = 2;
		}
		return energia;
	}

	public void setEnergia(float energia) {
		this.energia = energia;
	}

	public void subirEnergia(float energia) {
		this.energia += energia;
	}

	@Override
	public void act(float delta) {
		// Incrementar energia cada x tiempo
		super.act(delta);
		autoIncrementoEnergia += delta;
		if (energia < 2 && autoIncrementoEnergia <= 5) {
			energia++;
		}
	}

}
