package com.gridofsums.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gridofsums.game.GridOfSums;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GridOfSums.WIDTH;
		config.height = GridOfSums.HEIGHT;
		config.title = GridOfSums.TITLE;
		new LwjglApplication(new GridOfSums(), config);
	}
}
