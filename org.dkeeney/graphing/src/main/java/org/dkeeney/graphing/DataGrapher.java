package org.dkeeney.graphing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.dkeeney.utils.GraphUtils;
import org.dkeeney.utils.ImageMaker;

public class DataGrapher {
    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = DEFAULT_WIDTH;

    private final List<Point> data;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    private Color graphBackground = Color.WHITE;
    private Color gridColor = Color.GRAY;
    private final Color axisColor = Color.BLACK;
    private final Color graphColor = Color.RED;
    private final boolean drawGrid = false;
    private final int pixelsPerTick = 3;
    private final int unitsBetweenTicks = 1;
    private final boolean drawTicks = true;

    public DataGrapher(List<Point> data) {
        this(data.toArray(new Point[data.size()]));
    }

    public DataGrapher(Point[] data) {
        this.data = new ArrayList<>();
        this.minX = data[0].getX();
        this.maxX = data[0].getX();
        this.minY = data[0].getY();
        this.maxY = data[0].getY();

        for (Point p : data) {
            this.addPoint(p);
        }

        Collections.sort(this.data, new PointCompare());
    }

    private void addPoint(Point p) {
        if (p.getX() > this.maxX) {
            this.maxX = p.getX();
        }
        if (p.getX() < this.minX) {
            this.minX = p.getX();
        }
        if (p.getY() > this.maxY) {
            this.maxY = p.getY();
        }
        if (p.getY() < this.minY) {
            this.minY = p.getY();
        }
        this.data.add(p);
    }

    public BufferedImage getGraph() {
        return this.getGraph(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public BufferedImage getGraph(int width, int height) {
        return this.getGraph(width, height, this.minX, this.maxX, this.minY,
                this.maxY);
    }

    public BufferedImage getGraph(int width, int height, double minX,
            double maxX, double minY, double maxY) {
        BufferedImage image = ImageMaker.baseImage(width, height,
                this.graphBackground);
        Graphics2D graphics = image.createGraphics();
        GraphUtils.drawInitialGraph(width, height, minX, maxX, minY, maxY,
                graphics, this.drawGrid, this.drawTicks, this.gridColor,
                this.axisColor, this.unitsBetweenTicks, this.pixelsPerTick);

        // draw graphs
        Point p = this.data.get(0);
        graphics.setColor(this.graphColor);
        for (int i = 1; i < this.data.size(); i++) {
            graphics.drawLine(
                    GraphUtils.getXpixel(width, minX, maxX, p.getX()),
                    GraphUtils.getYpixel(height, minY, maxY, p.getY()),
                    GraphUtils.getXpixel(width, minX, maxX, this.data.get(i)
                            .getX()), GraphUtils.getYpixel(height, minY, maxY,
                            this.data.get(i).getY()));
            p = this.data.get(i);
        }

        graphics.dispose();

        return image;
    }

    public void setGraphBackground(Color graphBackground) {
        this.graphBackground = graphBackground;
    }

    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }

    private class PointCompare implements Comparator<Point> {
        @Override
        public int compare(Point a, Point b) {
            if (a.x < b.x) {
                return -1;
            } else if (a.x > b.x) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
