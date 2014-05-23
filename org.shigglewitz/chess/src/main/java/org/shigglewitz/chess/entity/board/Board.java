package org.shigglewitz.chess.entity.board;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "BOARDS")
public class Board {
    public static final int DEFAULT_SIZE = 8;

    private final Square[][] board;
    private int size;
    private UUID id;

    public Board() {
        this(DEFAULT_SIZE);
    }

    public Board(int size) {
        this.board = new Square[size][size];
        this.size = size;
    }

    @Transient
    public Square getSquare(int file, int rank) {
        validateFileAndRank(file, rank, this.size);

        return this.board[file - 1][rank - 1];
    }

    @Transient
    public Square getSquare(String descr) {
        int[] fileAndRank = Square.getRankAndFileFromDescr(descr);

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
}