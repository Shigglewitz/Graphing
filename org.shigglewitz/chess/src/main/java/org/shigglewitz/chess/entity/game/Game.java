package org.shigglewitz.chess.entity.game;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "GAMES")
public class Game {
    public enum Color {
        WHITE, BLACK
    }

    private UUID id;

    public Game() {
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
