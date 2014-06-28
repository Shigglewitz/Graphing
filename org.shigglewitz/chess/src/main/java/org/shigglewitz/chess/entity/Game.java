package org.shigglewitz.chess.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "GAMES")
public class Game implements Serializable {
    private static final long serialVersionUID = -4377523928633766665L;

    public enum Color {
        LIGHT, DARK
    }

    private UUID id;
    private Player lightPlayer;
    private Player darkPlayer;
    private Color nextMove;
    private Board board;

    /**
     * this should only be used by hibernate
     */
    protected Game() {
    }

    protected static String emptyStartingString(int size) {
        return String.format(String.format("%%-%ds", size), "");
    }

    public Game(int size) {
        this(size, emptyStartingString(size), emptyStartingString(size));
    }

    public Game(int size, String lightStart, String darkStart) {
        this.setBoard(new Board(size, lightStart, darkStart));
    }

    public static Game createDefaultGame() {
        Game ret = new Game();
        ret.setBoard(Board.createDefaultBoard());
        ret.setNextMove(Color.LIGHT);

        return ret;
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

    @ManyToOne
    @JoinColumn(name = "lightplayer_id")
    public Player getLightPlayer() {
        return this.lightPlayer;
    }

    public void setLightPlayer(Player lightPlayer) {
        this.lightPlayer = lightPlayer;
    }

    @ManyToOne
    @JoinColumn(name = "darkplayer_id")
    public Player getDarkPlayer() {
        return this.darkPlayer;
    }

    public void setDarkPlayer(Player darkPlayer) {
        this.darkPlayer = darkPlayer;
    }

    @Column
    public Color getNextMove() {
        return this.nextMove;
    }

    public void setNextMove(Color nextMove) {
        this.nextMove = nextMove;
    }

    @OneToOne
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
}
