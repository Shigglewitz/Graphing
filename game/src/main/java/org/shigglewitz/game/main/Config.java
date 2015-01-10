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
    public static final String GRASS_BACKGROUND = BACKGROUND_FOLDER
            + "grassbg1.gif";

    public static final String MAP_FOLDER = "/maps/";
    public static final String LEVEL_1_1_MAP = MAP_FOLDER + "level1-1.map";

    public static final String SPRITES_FOLDER = "/sprites/";
    public static final String PLAYER_SPRITES_FOLDER = SPRITES_FOLDER
            + "player/";
    public static final String PLAYER_SPRITE = PLAYER_SPRITES_FOLDER
            + "playersprites.gif";

    public static final String TILESET_FOLDER = "/tilesets/";
    public static final String LEVEL_1_TILESET = TILESET_FOLDER
            + "grasstileset.gif";

    public static int CAMERA_MODE = 0;
}
