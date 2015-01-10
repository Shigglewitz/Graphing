package org.shigglewitz.game.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.shigglewitz.game.config.Config;
import org.shigglewitz.game.tilemap.TileMap;

public class Explosion extends MapObject {

    private BufferedImage[] sprites;
    private boolean remove;

    public Explosion(TileMap tm, int x, int y) {
        super(tm);

        this.x = x;
        this.y = y;

        width = 30;
        height = 30;

        try {
            BufferedImage spriteSheet = ImageIO.read(getClass()
                    .getResourceAsStream(Config.ENEMY_EXPLOSION_SPRITE));

            sprites = new BufferedImage[6];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spriteSheet.getSubimage(i * width, 0, width,
                        height);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(70);

        remove = false;
    }

    public void update() {
        animation.update();
        if (animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    public boolean shouldRemove() {
        return remove;
    }

    @Override
    public void draw(Graphics2D g) {
        setMapPosition();

        super.draw(g);
    }
}
