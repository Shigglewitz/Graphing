package org.shigglewitz.chess.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.shigglewitz.chess.entity.Game.Color;
import org.shigglewitz.chess.entity.pieces.Bishop;
import org.shigglewitz.chess.entity.pieces.King;
import org.shigglewitz.chess.entity.pieces.Knight;
import org.shigglewitz.chess.entity.pieces.Pawn;
import org.shigglewitz.chess.entity.pieces.Piece;
import org.shigglewitz.chess.entity.pieces.Queen;
import org.shigglewitz.chess.entity.pieces.Rook;

@Entity
@Table(name = "BOARDS")
public class Board {
    public static final int DEFAULT_SIZE = 8;
    public static final String LIGHT_START_POSITION = "PPPPPPPP"
            + System.lineSeparator() + "RNBQKBNR";
    public static final String DARK_START_POSITION = "RNBQKBNR"
            + System.lineSeparator() + "PPPPPPPP";

    private List<Square> squares;
    private int size;
    private UUID id;

    public Board() {
    }

    public Board(int size, String lightStart, String darkStart) {
        String[] lightRanks = lightStart.split(System.lineSeparator());
        String[] darkRanks = darkStart.split(System.lineSeparator());

        for (String s : lightRanks) {
            if (s.length() != size) {
                throw new IllegalArgumentException("Light starting position '"
                        + s + "' does not match size " + size);
            }
        }
        for (String s : darkRanks) {
            if (s.length() != size) {
                throw new IllegalArgumentException("Dark starting position "
                        + s + " does not match size " + size);
            }
        }

        if (darkRanks.length + lightRanks.length > size) {
            throw new IllegalArgumentException(
                    "Number of rows specified by starting positions is greater than the board size");
        }

        this.squares = new ArrayList<>();
        this.size = size;

        // create empty squares
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.squares.add(new Square(j + 1, i + 1, size, this));
            }
        }

        // create pieces and add to squares
        // light pieces
        for (int i = 0; i < lightRanks.length; i++) {
            for (int j = 0; j < size; j++) {
                this.getSquare(j + 1, lightRanks.length - i).setPiece(
                        createPiece(lightRanks[i].charAt(j), Color.LIGHT));
            }
        }

        // dark pieces
        for (int i = 0; i < darkRanks.length; i++) {
            for (int j = 0; j < size; j++) {
                this.getSquare(j + 1, size - i).setPiece(
                        createPiece(darkRanks[i].charAt(j), Color.DARK));
            }
        }
    }

    protected static Piece createPiece(char shorthand, Color color) {
        switch (shorthand) {
        case Pawn.SHORTHAND:
            return new Pawn(color);
        case Rook.SHORTHAND:
            return new Rook(color);
        case Knight.SHORTHAND:
            return new Knight(color);
        case Bishop.SHORTHAND:
            return new Bishop(color);
        case Queen.SHORTHAND:
            return new Queen(color);
        case King.SHORTHAND:
            return new King(color);
        default:
            break;
        }

        return null;
    }

    public static Board createDefaultBoard() {
        return new Board(DEFAULT_SIZE, LIGHT_START_POSITION,
                DARK_START_POSITION);
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @Type(type = "pg-uuid")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * should only be used by hibernate
     * 
     * @return
     */
    @OneToMany(mappedBy = "squareId.board", fetch = FetchType.EAGER)
    protected List<Square> getSquares() {
        return this.squares;
    }

    /**
     * should only be used by hibernate
     * 
     * @param board
     */
    protected void setSquares(List<Square> squares) {
        this.squares = squares;
    }

    @Transient
    public Square getSquare(int file, int rank) {
        validateFileAndRank(file, rank, this.size);

        return this.squares.get((file - 1) + (this.size * (rank - 1)));
    }

    @Transient
    public Square getSquare(String descr) {
        int[] fileAndRank = Square.getFileAndRankFromDescr(descr);

        return this.getSquare(fileAndRank[0], fileAndRank[1]);
    }

    public int getSize() {
        return this.size;
    }

    /**
     * should only be used by hibernate
     * 
     * @param size
     */
    protected void setSize(int size) {
        this.size = size;
    }

    public boolean validateSquareDescr(String descr) {
        if (descr == null) {
            return false;
        }

        if (!descr.matches(Square.DESCR_REGEX)) {
            return false;
        }
        int[] fileAndRank = Square.getFileAndRankFromDescr(descr);

        if (fileAndRank[0] < 1) {
            return false;
        }
        if (fileAndRank[0] > this.size) {
            return false;
        }
        if (fileAndRank[1] < 1) {
            return false;
        }
        if (fileAndRank[1] > this.size) {
            return false;
        }

        return true;
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
