package org.shigglewitz.chess.entity.dao;

import java.util.UUID;

import org.shigglewitz.chess.entity.game.Game;
import org.shigglewitz.chess.entity.player.Player;

public interface ChessDao {
    public Player getPlayer(UUID id);

    public Game getGame(UUID id);

    public void savePlayer(Player player);

    public void saveGame(Game game);
}
