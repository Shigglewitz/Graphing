package org.shigglewitz.chess.entity.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.shigglewitz.chess.entity.game.Game.Color;
import org.shigglewitz.chess.entity.pieces.Piece;
import org.shigglewitz.chess.entity.pieces.impl.Bishop;
import org.shigglewitz.chess.entity.pieces.impl.King;
import org.shigglewitz.chess.entity.pieces.impl.Knight;
import org.shigglewitz.chess.entity.pieces.impl.Pawn;
import org.shigglewitz.chess.entity.pieces.impl.Queen;
import org.shigglewitz.chess.entity.pieces.impl.Rook;

public class BoardTest {
	@Test
	public void testGetSquare() {
		Board board = new Board(5, "     ", "     ");

		assertEquals("a1", board.getSquare("a1").getDescr());
		assertEquals("a1", board.getSquare(1, 1).getDescr());
		assertEquals("b2", board.getSquare("b2").getDescr());
		assertEquals("b2", board.getSquare(2, 2).getDescr());
		assertEquals("c1", board.getSquare("c1").getDescr());
		assertEquals("c1", board.getSquare(3, 1).getDescr());
		assertEquals("a3", board.getSquare("a3").getDescr());
		assertEquals("a3", board.getSquare(1, 3).getDescr());
	}

	@Test
	public void testDefaultSetup() {
		Board board = Board.createDefaultBoard();

		assertEquals(Board.DEFAULT_SIZE, board.getSize());
		
		// check pawns
		for (int i = 0; i < Board.DEFAULT_SIZE; i++) {
			assertSquarePieceProperties(board.getSquare(i + 1, 2), Pawn.class,
					Color.LIGHT);
		}

		for (int i = 0; i < Board.DEFAULT_SIZE; i++) {
			assertSquarePieceProperties(board.getSquare(i + 1, 7), Pawn.class,
					Color.DARK);
		}

		// check light back rank
		assertSquarePieceProperties(board.getSquare("a1"), Rook.class,
				Color.LIGHT);
		assertSquarePieceProperties(board.getSquare("b1"), Knight.class,
				Color.LIGHT);
		assertSquarePieceProperties(board.getSquare("c1"), Bishop.class,
				Color.LIGHT);
		assertSquarePieceProperties(board.getSquare("d1"), Queen.class,
				Color.LIGHT);
		assertSquarePieceProperties(board.getSquare("e1"), King.class,
				Color.LIGHT);
		assertSquarePieceProperties(board.getSquare("f1"), Bishop.class,
				Color.LIGHT);
		assertSquarePieceProperties(board.getSquare("g1"), Knight.class,
				Color.LIGHT);
		assertSquarePieceProperties(board.getSquare("h1"), Rook.class,
				Color.LIGHT);

		// check dark back rank
		assertSquarePieceProperties(board.getSquare("a8"), Rook.class,
				Color.DARK);
		assertSquarePieceProperties(board.getSquare("b8"), Knight.class,
				Color.DARK);
		assertSquarePieceProperties(board.getSquare("c8"), Bishop.class,
				Color.DARK);
		assertSquarePieceProperties(board.getSquare("d8"), Queen.class,
				Color.DARK);
		assertSquarePieceProperties(board.getSquare("e8"), King.class,
				Color.DARK);
		assertSquarePieceProperties(board.getSquare("f8"), Bishop.class,
				Color.DARK);
		assertSquarePieceProperties(board.getSquare("g8"), Knight.class,
				Color.DARK);
		assertSquarePieceProperties(board.getSquare("h8"), Rook.class,
				Color.DARK);

		// rest of the board is null
		for (int i = 3; i < 7; i++) {
			for (int j = 1; j <= 8; j++) {
				assertNull(board.getSquare(j, i).getPiece());
			}
		}
	}

	private void assertSquarePieceProperties(Square square,
			Class<? extends Piece> clazz, Color color) {
		Piece piece = square.getPiece();
		assertNotNull("Square " + square.getDescr() + " has no piece", piece);
		assertEquals(clazz, piece.getClass());
		assertEquals(color, piece.getColor());
	}
}
