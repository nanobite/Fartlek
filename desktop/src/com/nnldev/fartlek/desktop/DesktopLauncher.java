package com.nnldev.fartlek.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nnldev.fartlek.Fartlek;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Fartlek.WIDTH;
        config.height = Fartlek.HEIGHT;
        config.title = Fartlek.TITLE;
        new LwjglApplication(new Fartlek(), config);
    }
}
