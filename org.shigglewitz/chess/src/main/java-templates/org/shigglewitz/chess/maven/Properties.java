package org.shigglewitz.chess.maven;

public interface Properties {
    String WAR_NAME = "${war.name}";
    String GAME_CONTROLLER_PATH = "${controller.game.path}";
}
