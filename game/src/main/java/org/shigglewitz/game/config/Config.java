package org.shigglewitz.game.config;

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

    public static final String HUD_FOLDER = "/hud/";
    public static final String HUD_SPRITE = HUD_FOLDER + "hud.gif";

    public static final String MAP_FOLDER = "/maps/";
    public static final String LEVEL_1_1_MAP = MAP_FOLDER + "level1-1.map";

    public static final String SPRITES_FOLDER = "/sprites/";
    public static final String ENEMY_SPRITES_FOLDER = SPRITES_FOLDER + "enemy/";
    public static final String SLUGGER_SPRITE = ENEMY_SPRITES_FOLDER
            + "slugger.gif";
    public static final String ARACHNIK_SPRITE = ENEMY_SPRITES_FOLDER
            + "arachnik.gif";
    public static final String ENEMY_EXPLOSION_SPRITE = ENEMY_SPRITES_FOLDER
            + "explosion.gif";
    public static final String PLAYER_SPRITES_FOLDER = SPRITES_FOLDER
            + "player/";
    public static final String FIREBALL_SPRITE = PLAYER_SPRITES_FOLDER
            + "fireball.gif";
    public static final String PLAYER_SPRITE = PLAYER_SPRITES_FOLDER
            + "playersprites.gif";

    public static final String TILESET_FOLDER = "/tilesets/";
    public static final String LEVEL_1_TILESET = TILESET_FOLDER
            + "grasstileset.gif";
}
