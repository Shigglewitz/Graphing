package org.shigglewitz.game.tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.shigglewitz.game.main.Config;

public class Background {
    private BufferedImage image;
    private double x;
    private double y;
    private double dx;
    private double dy;
    private final double moveScale;

    public Background(String s, double moveScale) {
        try {
            image = ImageIO.read(this.getClass().getResourceAsStream(s));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        this.moveScale = moveScale;
    }

    public void setPosition(double x, double y) {
        this.x = (x * moveScale) % Config.WIDTH;
        this.y = (y * moveScale) % Config.HEIGHT;
    }

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        x += dx;
        y += dy;
        x %= Config.WIDTH;
        y %= Config.HEIGHT;
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, null);
        if (x < 0) {
            g.drawImage(image, (int) x + Config.WIDTH, (int) y, null);
        } else if (x > 0) {
            g.drawImage(image, (int) x - Config.WIDTH, (int) y, null);
        }
    }
}
