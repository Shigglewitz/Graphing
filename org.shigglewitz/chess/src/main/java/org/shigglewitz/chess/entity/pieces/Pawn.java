package org.shigglewitz.chess.entity.pieces;

import org.shigglewitz.chess.entity.Game.Color;

public class Pawn implements Piece {
	public static final char SHORTHAND = 'P';

	private Color color;
	private boolean moved;
	private String name;
	private boolean captured;

	public Pawn(Color color) {
		this.color = color;
		this.moved = false;
		this.name = "Pawn";
		this.captured = false;
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean isMoved() {
		return this.moved;
	}

	@Override
	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	@Override
	public boolean isCaptured() {
		return this.captured;
	}

	@Override
	public void setCaptured(boolean captured) {
		this.captured = captured;
	}
}