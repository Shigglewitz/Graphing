package org.dkeeney.graphing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.dkeeney.graphing.equations.Equation;
import org.dkeeney.graphing.equations.exceptions.InsufficientVariableInformationException;
import org.dkeeney.graphing.equations.exceptions.InvalidEquationException;

public class Grapher {
    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;
    private static final double DEFAULT_MIN_X = -10.0;
    private static final double DEFAULT_MAX_X = 10.0;
    private static final double DEFAULT_MIN_Y = -10.0;
    private static final double DEFAULT_MAX_Y = 10.0;

    private final Equation e;
    private double[] values;

    public Grapher(String equation) throws InvalidEquationException {
        this.e = new Equation(equation);
        this.values = null;
    }

    public void calculateValues(BigDecimal minRange, BigDecimal maxRange,
            BigDecimal delta, String var)
            throws InsufficientVariableInformationException {
        Map<String, BigDecimal> vars = new HashMap<>();
        this.values = new double[(int) Math.floor(maxRange.subtract(minRange)
                .divide(delta, RoundingMode.CEILING).add(new BigDecimal(0.5))
                .doubleValue())];

        int i = 0;
        for (BigDecimal eval = minRange; eval.compareTo(maxRange) < 0; eval = eval
                .add(delta), i++) {
            vars.put(var, eval);
            this.values[i] = this.e.solve(vars);
        }
    }

    public double[] getValues() {
        return this.values;
    }

    public BufferedImage getGraph() {
        return this.getGraph(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_MIN_X,
                DEFAULT_MAX_X, DEFAULT_MIN_Y, DEFAULT_MAX_Y);
    }

    public BufferedImage getGraph(int width, int height) {
        return this.getGraph(width, height, DEFAULT_MIN_X, DEFAULT_MAX_X,
                DEFAULT_MIN_Y, DEFAULT_MAX_Y);
    }

    public BufferedImage getGraph(double minX, double maxX, double minY,
            double maxY) {
        return this.getGraph(DEFAULT_WIDTH, DEFAULT_HEIGHT, minX, maxX, minY,
                maxY);
    }

    private final Color axisColor = Color.BLACK;
    private final Color gridColor = new Color(200, 200, 200);

    private final boolean drawGrid = false;
    private final boolean drawTicks = true;

    private final int pixelsPerTick = 3;
    private final int unitsBetweenTicks = 1;

    public BufferedImage getGraph(int width, int height, double minX,
            double maxX, double minY, double maxY) {
        BufferedImage image = ImageMaker.baseImage(width, height);
        Graphics2D graphics = image.createGraphics();
        int xAxisPosition = -1;
        int yAxisPosition = -1;

        if (minY == 0) {
            xAxisPosition = image.getHeight();
        } else if (maxY == 0) {
            xAxisPosition = 0;
        } else if (minY < 0 && maxY > 0) {
            xAxisPosition = Math.abs((int) (minY / (maxY - minY) * image
                    .getHeight()));
        }
        if (minX == 0) {
            yAxisPosition = image.getWidth();
        } else if (maxX == 0) {
            yAxisPosition = 0;
        } else if (minX < 0 && maxX > 0) {
            yAxisPosition = Math.abs((int) (minX / (maxX - minX) * image
                    .getWidth()));
        }

        graphics.setColor(this.gridColor);
        if (this.drawGrid) {
            // draw grid
            int gridPosition = -1;
            for (int y = (int) Math.ceil(minY); y <= maxY; y += this.unitsBetweenTicks) {
                gridPosition = Math
                        .abs((int) ((y - minY) / (maxY - minY) * image
                                .getHeight()));
                graphics.drawLine(0, gridPosition, image.getWidth(),
                        gridPosition);
            }
            for (int x = (int) Math.ceil(minX); x <= maxX; x += this.unitsBetweenTicks) {
                gridPosition = Math
                        .abs((int) ((x - minX) / (maxX - minX) * image
                                .getWidth()));
                graphics.drawLine(gridPosition, 0, gridPosition,
                        image.getHeight());
            }
        } else if (this.drawTicks) {
            // draw ticks
            int tickPosition = -1;
            if (minY <= 0 && maxY >= 0) {
                for (int y = (int) Math.ceil(minY); y <= maxY; y += this.unitsBetweenTicks) {
                    tickPosition = Math
                            .abs((int) ((y - minY) / (maxY - minY) * image
                                    .getHeight()));
                    graphics.drawLine(yAxisPosition - this.pixelsPerTick,
                            tickPosition, yAxisPosition + this.pixelsPerTick,
                            tickPosition);
                }
            }
            if (minX <= 0 && maxX >= 0) {
                for (int x = (int) Math.ceil(minX); x <= maxX; x += this.unitsBetweenTicks) {
                    tickPosition = Math
                            .abs((int) ((x - minX) / (maxX - minX) * image
                                    .getWidth()));
                    graphics.drawLine(tickPosition, xAxisPosition
                            - this.pixelsPerTick, tickPosition, xAxisPosition
                            + this.pixelsPerTick);
                }
            }
        }

        // draw axes
        graphics.setColor(this.axisColor);
        graphics.drawLine(0, xAxisPosition, image.getWidth(), xAxisPosition);
        graphics.drawLine(yAxisPosition, 0, yAxisPosition, image.getHeight());

        graphics.dispose();

        return image;
    }
}
