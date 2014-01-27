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
                .divide(delta, RoundingMode.CEILING).add(new BigDecimal(1))
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

    private Color graphBackground = Color.WHITE;
    private Color axisColor = Color.BLACK;
    private Color gridColor = new Color(200, 200, 200);
    private Color graphColor = Color.GREEN;

    private boolean drawGrid = false;
    private boolean drawTicks = true;

    private int pixelsPerTick = 3;
    private int unitsBetweenTicks = 1;

    private int extraGraphPadding = 0;

    public BufferedImage getGraph(int width, int height, double minX,
            double maxX, double minY, double maxY) {
        BufferedImage image = ImageMaker.baseImage(width, height,
                this.graphBackground);
        Graphics2D graphics = image.createGraphics();
        int xAxisPosition = -1;
        int yAxisPosition = -1;

        if (minY == 0) {
            xAxisPosition = image.getHeight();
        } else if (maxY == 0) {
            xAxisPosition = 0;
        } else if (minY < 0 && maxY > 0) {
            xAxisPosition = image.getHeight()
                    - Math.abs(-(int) (minY / (maxY - minY) * image.getHeight()));
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
                if (y != 0) {
                    gridPosition = image.getHeight()
                            - Math.abs((int) ((y - minY) / (maxY - minY) * image
                                    .getHeight()));
                    graphics.drawLine(0, gridPosition, image.getWidth(),
                            gridPosition);
                }
            }
            for (int x = (int) Math.ceil(minX); x <= maxX; x += this.unitsBetweenTicks) {
                if (x != 0) {
                    gridPosition = Math
                            .abs((int) ((x - minX) / (maxX - minX) * image
                                    .getWidth()));
                    graphics.drawLine(gridPosition, 0, gridPosition,
                            image.getHeight());
                }
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
        // x axis
        graphics.drawLine(0, xAxisPosition, image.getWidth(), xAxisPosition);
        // y axis
        graphics.drawLine(yAxisPosition, 0, yAxisPosition, image.getHeight());

        // calculate values
        if (this.values == null) {
            try {
                this.calculateValues(new BigDecimal(minX),
                        new BigDecimal(maxX), new BigDecimal((maxX - minX)
                                / image.getWidth()), "x");
            } catch (InsufficientVariableInformationException e) {
                e.printStackTrace();
                graphics.drawString("Exception calculating values", 1, 10);
            }
        }
        // draw graphs
        int y = image.getHeight()
                - ((int) ((this.values[0] - minY) / (maxY - minY) * image
                        .getHeight()));
        int pastY = y;
        int pastX = 0;
        int temp = -1;
        graphics.setColor(this.graphColor);
        for (int x = 0; x < image.getWidth(); x++) {
            y = image.getHeight()
                    - ((int) ((this.values[x] - minY) / (maxY - minY) * image
                            .getHeight()));
            if ((y >= 0 && y < image.getHeight())
                    || (pastY >= 0 && pastY < image.getHeight())) {
                temp = y;
                if (y < 0) {
                    y = 0;
                }
                if (y >= image.getHeight()) {
                    y = image.getHeight() - 1;
                }
                if (pastY < 0) {
                    pastY = 0;
                }
                if (pastY >= image.getHeight()) {
                    pastY = image.getHeight() - 1;
                }
                graphics.drawLine(pastX, pastY, x, y);
                for (int i = 0; i < this.extraGraphPadding; i++) {
                    if (pastX > 0) {
                        graphics.drawLine(pastX - 1, pastY, x - 1, y);
                    }
                    if (x < image.getWidth() - 1) {
                        graphics.drawLine(pastX + 1, pastY, x + 1, y);
                    }
                }

                y = temp;
            }
            pastY = y;
            pastX = x;
        }

        graphics.dispose();

        return image;
    }

    public void setGraphBackground(Color graphBackground) {
        this.graphBackground = graphBackground;
    }

    public void setAxisColor(Color axisColor) {
        this.axisColor = axisColor;
    }

    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }

    public void setGraphColor(Color graphColor) {
        this.graphColor = graphColor;
    }

    public void setDrawGrid(boolean drawGrid) {
        this.drawGrid = drawGrid;
    }

    public void setDrawTicks(boolean drawTicks) {
        this.drawTicks = drawTicks;
    }

    public void setPixelsPerTick(int pixelsPerTick) {
        this.pixelsPerTick = pixelsPerTick;
    }

    public void setUnitsBetweenTicks(int unitsBetweenTicks) {
        this.unitsBetweenTicks = unitsBetweenTicks;
    }

    public void setExtraGraphPadding(int extraGraphPadding) {
        this.extraGraphPadding = extraGraphPadding;
    }
}
