package org.shigglewitz.game.main;

public interface Config {
    public static final String WINDOW_TITLE = "TITLE";

    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    public static final int TARGET_FPS = 60;
    public static final int TARGET_TIME = 1000 / TARGET_FPS;

    public static final String BACKGROUND_FOLDER = "/backgrounds/";
    public static final String MENU_BACKGROUND = BACKGROUND_FOLDER
            + "menubg.gif";
}
