package org.shigglewitz.game.tilemap;

import java.awt.image.BufferedImage;

public class Tile {
    public enum Type {
        NORMAL, BLOCKED
    }

    private final BufferedImage image;
    private final Type type;

    public Tile(BufferedImage image, Type type) {
        this.image = image;
        this.type = type;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Type getType() {
        return type;
    }
}
