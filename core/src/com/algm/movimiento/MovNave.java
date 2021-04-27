package com.algm.movimiento;

public class MovNave {
	static int ANCHO;
	static int ALTO;
	private int navePosicion; 
	private int naveDireccion;
	
	public MovNave() {
		super();
		this.navePosicion = 0;
		this.naveDireccion = 1;
	}

	public static int getAncho() {
		return ANCHO;
	}

	public static void setAncho(int ancho) {
		MovNave.ANCHO = ANCHO;
	}

	public static int getAlto() {
		return ALTO;
	}

	public static void setAlto(int alto) {
		MovNave.ALTO = ALTO;
	}

	public int getNavePosicion() {
		return navePosicion;
	}

	public void setNavePosicion(int navePosicion) {
		this.navePosicion = navePosicion;
	}

	public int getNaveDireccion() {
		return naveDireccion;
	}

	public void setNaveDireccion(int naveDireccion) {
		this.naveDireccion = naveDireccion;
	}
	
	
}
