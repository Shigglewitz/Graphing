package org.shigglewitz.chess.entity.pieces.impl;

import org.shigglewitz.chess.entity.game.Game.Color;
import org.shigglewitz.chess.entity.pieces.Piece;

public class Pawn implements Piece {
    private final Color color;

    public Pawn(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasMoved() {
        // TODO Auto-generated method stub
        return false;
    }

}
