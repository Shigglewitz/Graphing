package org.shigglewitz.chess.entity.board;

public class Board {
    public static final int DEFAULT_SIZE = 8;

    private final Square[][] board;
    private final int size;

    public Board() {
        this(DEFAULT_SIZE);
    }

    public Board(int size) {
        this.board = new Square[size][size];
        this.size = size;
    }

    public Square getSquare(int file, int rank) {
        validateFileAndRank(file, rank, this.size);

        return this.board[file - 1][rank - 1];
    }

    public Square getSquare(String descr) {
        int[] fileAndRank = Square.getRankAndFileFromDescr(descr);

        return this.getSquare(fileAndRank[0], fileAndRank[1]);
    }

    public int getSize() {
        return this.size;
    }

    public static void validateFileAndRank(int file, int rank, int boardSize) {
        if (rank < 1) {
            throw new IllegalArgumentException("Rank cannot be below 1");
        }
        if (file < 1) {
            throw new IllegalArgumentException("File cannot be below 1");
        }
        if (rank > boardSize) {
            throw new IllegalArgumentException(
                    "Rank cannot be higher than the board size");
        }
        if (file > boardSize) {
            throw new IllegalArgumentException(
                    "File cannot be higher than the board size");
        }
    }
}
