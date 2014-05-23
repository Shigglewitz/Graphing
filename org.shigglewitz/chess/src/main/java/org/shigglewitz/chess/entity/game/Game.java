package org.shigglewitz.chess.entity.game;

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
import org.shigglewitz.chess.entity.board.Board;
import org.shigglewitz.chess.entity.player.Player;

@Entity
@Table(name = "GAMES")
public class Game {
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

    public Game(int size) {
        this.setBoard(new Board(size));
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
