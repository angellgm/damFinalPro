/**
 * 
 */
package com.algm.sck;

import com.badlogic.gdx.scenes.scene2d.Stage;
import actores.Laser;
import actores.NanoBot;
import actores.Virus;

/**
 * @author Angel
 *
 */
public class PantallaJuego extends Pantalla {
	/**
	 *  https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/Stage.html
	 *  Un gráfico de escena 2D que contiene jerarquías de actors. Stage maneja la ventana gráfica y distribuye eventos de entrada.
	 *  setViewport(Viewport) controla las coordenadas utilizadas dentro del escenario y configura la cámara utilizada para convertir
	 *  entre las coordenadas del escenario y las coordenadas de la pantalla.
	 *  Un escenario debe recibir eventos de entrada para que pueda distribuirlos a los actores.
	 *  Por lo general, esto se hace pasando el escenario a Gdx.input.setInputProcessor.
	 *  Se InputMultiplexerpuede usar para manejar eventos de entrada antes o después de que lo haga la etapa.
	 *  Si un actor maneja un evento devolviendo verdadero desde el método de entrada, entonces el método de entrada de la etapa 
	 *  también devolverá verdadero, lo que provocará que los procesadores de entrada posteriores no reciban el evento.
	 *  El escenario y sus componentes (como Actores y Oyentes) no son seguros para subprocesos y solo deben actualizarse 
	 *  y consultarse desde un solo subproceso (presumiblemente el subproceso principal de procesamiento).
	 *  Los métodos deben ser reentrantes, por lo que puede actualizar Actores y Etapas desde devoluciones de llamada y controladores.
	 *  Stagepuede ser extremadamente útil si necesita controles en pantalla (botones, joystick, etc.)
	 *  puede usar clases de scene2d.ui
	 */
	
	private Stage stage;
	private Laser laser;
	private NanoBot nanoBot;
	private Virus virus;


	public PantallaJuego(SarsCovKiller juego) {
		super(juego);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		stage = new Stage();
		
		laser = new Laser();
		virus = new Virus();
		nanoBot = new NanoBot();
		
		nanoBot.setPosition(50, 50);
		laser.setPosition(100, 100);
		virus.setPosition(200, 200);
		
		stage.addActor(laser);
		stage.addActor(virus);
		stage.addActor(nanoBot);
		
	}

	@Override
	public void render(float delta) {
		stage.act();	// Actulizar
		stage.draw();	// Dibujar
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();	//Destruir pantalla
	}

}
