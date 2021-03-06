package com.algm.actorcontrol;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class NivelPuntuacion extends Actor {
	private int marcador;
	private int sck;
	private int nivel;
	private BitmapFont fuente;

	public NivelPuntuacion(BitmapFont bitmapFont) {
		super();
		this.sck = 0;
		this.nivel=1;
		this.fuente = bitmapFont;
		//bitmapFont.setColor(255,255,0,100); 		
		//bitmapFont.getData().setScale(1.2f);		
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		fuente.draw(batch,"NIVEL: "+Integer.toString(nivel)+"    "
				+ "PUNTOS: "+Integer.toString(marcador)+"    "
				+ "SCK: "+Integer.toString(sck), getX(), getY());
		
	}


	public int getMarcador() {
		return marcador;
	}

	public void setMarcador(int marcador) {
		this.marcador = marcador;
	}



	/**
	 * @return the sck
	 */
	public int getSck() {
		return sck;
	}

	/**
	 * @param sck the sck to set
	 */
	public void setSck(int sck) {
		this.sck = sck;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public BitmapFont getBitmapFont() {
		return fuente;
	}

	public void setBitmapFont(BitmapFont bitmapFont) {
		this.fuente = bitmapFont;
	}
	
	
	
	
}
