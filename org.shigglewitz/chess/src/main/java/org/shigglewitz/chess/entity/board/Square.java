package org.shigglewitz.chess.entity.board;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.shigglewitz.chess.entity.game.Game;
import org.shigglewitz.chess.entity.game.Game.Color;
import org.shigglewitz.chess.entity.pieces.Piece;

public class Square {
    private static final String DESCR_REGEX = "([a-z]+)([1-9][0-9]*)";
    public static final Pattern DESCR_PATTERN = Pattern.compile(DESCR_REGEX);
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    private final int rank;
    private final int file;
    private Piece piece;

    public Square(int rank, int file) {
        this(rank, file, Board.DEFAULT_SIZE);
    }

    public Square(int file, int rank, int boardSize) {
        Board.validateFileAndRank(file, rank, boardSize);

        this.rank = rank;
        this.file = file;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public Game.Color getColor() {
        if (this.file + this.rank % 2 == 0) {
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }

    public String getDescr() {
        return Character.toString((char) (this.file - 1 + 'a'))
                + Integer.toString(this.rank);
    }

    /**
     * 
     * @return [0] = file, [1] = rank
     */
    public static int[] getRankAndFileFromDescr(String descr) {
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
}
