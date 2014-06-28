package org.shigglewitz.chess.entity.pieces;

import org.shigglewitz.chess.entity.Game;

public interface Piece {
	Game.Color getColor();

	void setColor(Game.Color color);

	String getName();

	void setName(String name);

	boolean isMoved();

	void setMoved(boolean moved);

	boolean isCaptured();

	void setCaptured(boolean captured);
}
