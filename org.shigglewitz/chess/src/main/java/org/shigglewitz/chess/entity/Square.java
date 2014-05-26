package org.shigglewitz.chess.entity;

import java.io.Serializable;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.shigglewitz.chess.entity.Game.Color;
import org.shigglewitz.chess.entity.Square.SquarePk;
import org.shigglewitz.chess.entity.pieces.Piece;

@Entity
@Table(name = "SQUARES")
@IdClass(SquarePk.class)
public class Square implements Serializable {
	private static final long serialVersionUID = -4666913760303484707L;

	public static final String DESCR_REGEX = "([a-z]+)([1-9][0-9]*)";
	public static final Pattern DESCR_PATTERN = Pattern.compile(DESCR_REGEX);
	public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	private int rank;
	private int file;
	private Piece piece;
	private Board board;

	/**
	 * should only used by hibernate
	 * 
	 */
	protected Square() {
	}

	public Square(int rank, int file, Board board) {
		this(rank, file, Board.DEFAULT_SIZE, board);
	}

	public Square(int file, int rank, int boardSize, Board board) {
		Board.validateFileAndRank(file, rank, boardSize);

		this.rank = rank;
		this.file = file;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
		if (piece != null) {
			System.out.println("setting " + piece.getColor() + " "
					+ piece.getName() + " at square " + this.getDescr());
		}
	}

	@Transient
	public Piece getPiece() {
		return this.piece;
	}

	@Id
	public int getRank() {
		return this.rank;
	}

	/**
	 * should only be used by hibernate
	 * 
	 * @param rank
	 */
	protected void setRank(int rank) {
		this.rank = rank;
	}

	@Id
	public int getFile() {
		return this.file;
	}

	/**
	 * should only be used by hibernate
	 * 
	 * @param file
	 */
	protected void setFile(int file) {
		this.file = file;
	}

	@Id
	@ManyToOne
	// @JoinColumns({
	// @JoinColumn(name="squares_rank"),
	// @JoinColumn(name="squares_file"),
	// @JoinColumn(name="board_id")
	// })
	@JoinColumn(name = "board_id")
	public Board getBoard() {
		return this.board;
	}

	/**
	 * should only be used by hibernate
	 * 
	 * @param board
	 */
	protected void setBoard(Board board) {
		this.board = board;
	}

	@Transient
	public Game.Color getColor() {
		if (this.file + this.rank % 2 == 0) {
			return Color.DARK;
		} else {
			return Color.LIGHT;
		}
	}

	@Transient
	public String getDescr() {
		return Character.toString((char) (this.file - 1 + 'a'))
				+ Integer.toString(this.rank);
	}

	/**
	 * 
	 * @return [0] = file, [1] = rank
	 */
	public static int[] getFileAndRankFromDescr(String descr) {
		int[] ret = new int[2];

		if (!descr.matches(DESCR_REGEX)) {
			throw new IllegalArgumentException("Descr " + descr
					+ " does not match regex!");
		}

		Matcher matcher = DESCR_PATTERN.matcher(descr);
		matcher.find();

		String letters = matcher.group(1).toLowerCase();
		int fileValue = 0;

		for (int i = 0; i < letters.length(); i++) {
			fileValue *= 26;
			fileValue += (letters.charAt(i) - 'a' + 1);
		}

		ret[0] = fileValue;
		ret[1] = Integer.parseInt(matcher.group(2));

		return ret;
	}

	@Override
	public String toString() {
		return this.getDescr() + this.piece.toString();
	}

	public static class SquarePk implements Serializable {
		private static final long serialVersionUID = 3706773330379490018L;
		protected int rank;
		protected int file;
		protected UUID board_id;

		public SquarePk() {
		}

		public SquarePk(int rank, int file, UUID board_id) {
			this.rank = rank;
			this.file = file;
			this.board_id = board_id;
		}

		public boolean equals(SquarePk other) {
			return rank == other.rank && file == other.file
					&& board_id == other.board_id;
		}

		public int hashCode() {
			return board_id.hashCode() + (rank * 31) + (file * 37);
		}
	}
}
