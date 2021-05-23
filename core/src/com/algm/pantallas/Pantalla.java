package com.algm.pantallas;

import com.algm.sck.SarsCovKiller;
import com.badlogic.gdx.Screen;

/**
 * @author Angel
 * 
 * Clase Pantalla abstracta: Para crear un modelo de pantalla y poder reutilizarla para las pantallas hijas
 * se pueden eliminar algunos m�todos (render, show, hide, dispose), ya que la pantalla hija deber�n implementarlos
 * por oligaci�n. 
 * 
 */
public abstract class Pantalla implements Screen {
	// Conexi�n con la clase principal
	protected SarsCovKiller juego;

	
	public Pantalla(SarsCovKiller juego) {
		super();
		this.juego = juego;
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}


	public SarsCovKiller getJuego() {
		return juego;
	}


	public void setJuego(SarsCovKiller juego) {
		this.juego = juego;
	}

}
