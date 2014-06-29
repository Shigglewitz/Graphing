package org.shigglewitz.chess.maven;

public interface Properties {
    String WAR_NAME = "${war.name}";
    String VIEW_FILE_PATH = "${path.to.view.files}";
    String VIEW_EXTENSION = "${view.extension}";
    String GAME_CONTROLLER_PATH = "${controller.game.path}";
    String PLAYER_CONTROLLER_PATH = "${controller.player.path}";
}
