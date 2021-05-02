package com.algm.sck.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.algm.sck.SarsCovKiller;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "SarsCovKiller";
		config.width = 1200;
		config.height = 600;
		//config.fullscreen = true;
		
		
		new LwjglApplication(new SarsCovKiller(), config);
	}
}
