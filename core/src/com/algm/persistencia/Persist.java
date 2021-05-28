package com.algm.persistencia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Persist {
	private Preferences preferences;

	public Persist() {
		super();
		this.preferences = Gdx.app.getPreferences(null);
	}
	

	
}
