package org.dkeeney.graphing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.dkeeney.utils.ImageMaker;

public class LightningGrapher implements Grapher {

    private final Color backgroundColor = Color.BLACK;

    @Override
    public void draw(BufferedImage image) {
        Graphics2D graphics = image.createGraphics();

        graphics.dispose();
    }

    @Override
    public void adjustWindow(double minX, double maxX, double minY, double maxY) {
        return;
    }

    @Override
    public BufferedImage getGraph(int width, int height) {
        BufferedImage image = ImageMaker.baseImage(width, height,
                this.backgroundColor);
        this.draw(image);
        return image;
    }

}
