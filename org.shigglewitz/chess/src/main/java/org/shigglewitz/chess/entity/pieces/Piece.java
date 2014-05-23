package org.shigglewitz.chess.entity.pieces;

import org.shigglewitz.chess.entity.game.Game;

public interface Piece {
    public Game.Color getColor();

    public String getName();

    public boolean hasMoved();
}
