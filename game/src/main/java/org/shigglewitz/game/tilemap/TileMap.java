package org.shigglewitz.game.tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import org.shigglewitz.game.config.Config;
import org.shigglewitz.game.tilemap.Tile.Type;

public class TileMap {
    private double x;
    private double y;

    private int xmin;
    private int xmax;
    private int ymin;
    private int ymax;

    private final double tween;

    private int[][] map;
    private final int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;

    private BufferedImage tileSet;
    private int numTilesAcross;
    private Tile[][] tiles;

    private int rowOffset;
    private int colOffset;
    private final int numRowsToDraw;
    private final int numColsToDraw;

    public TileMap(int tileSize) {
        this.tileSize = tileSize;

        numRowsToDraw = Config.HEIGHT / tileSize + 2;
        numColsToDraw = Config.WIDTH / tileSize + 2;

        tween = 0.07;
    }

    public void loadTiles(String s) {
        try {
            tileSet = ImageIO.read(getClass().getResourceAsStream(s));
            numTilesAcross = tileSet.getWidth() / tileSize;
            tiles = new Tile[2][numTilesAcross];

            BufferedImage subImage;
            for (int col = 0; col < numTilesAcross; col++) {
                subImage = tileSet.getSubimage(col * tileSize, 0, tileSize,
                        tileSize);
                tiles[0][col] = new Tile(subImage, Type.NORMAL);
                subImage = tileSet.getSubimage(col * tileSize, tileSize,
                        tileSize, tileSize);
                tiles[1][col] = new Tile(subImage, Type.BLOCKED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String s) {
        try {
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            numCols = Integer.parseInt(br.readLine());
            numRows = Integer.parseInt(br.readLine());
            map = new int[numRows][numCols];
            width = numCols * tileSize;
            height = numRows * tileSize;

            xmin = Config.WIDTH - width;
            xmax = 0;
            ymin = Config.HEIGHT - height;
            ymax = 0;

            String delims = "\\s+";
            for (int row = 0; row < numRows; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for (int col = 0; col < numCols; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public Type getType(int row, int col) {
        int rc = map[row][col];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;

        return tiles[r][c].getType();
    }

    @SuppressWarnings("unused")
    public void setPosition(double x, double y) {
        if (Config.CAMERA_MODE == 0) {
            this.x = x;
            this.y = y;
        } else {
            this.x += (x - this.x) * tween;
            this.y += (y - this.y) * tween;
        }

        fixBounds();

        colOffset = (int) -this.x / tileSize;
        rowOffset = (int) -this.y / tileSize;
    }

    private void fixBounds() {
        if (x < xmin) {
            x = xmin;
        }
        if (x > xmax) {
            x = xmax;
        }
        if (y < ymin) {
            y = ymin;
        }
        if (y > ymax) {
            y = ymax;
        }
    }

    public void draw(Graphics2D g) {
        for (int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
            if (row >= numRows) {
                break;
            }
            for (int col = colOffset; col < colOffset + numColsToDraw; col++) {
                if (col >= numCols) {
                    break;
                }
                if (map[row][col] == 0) {
                    continue;
                }

                int rc = map[row][col];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;

                g.drawImage(tiles[r][c].getImage(), (int) x + col * tileSize,
                        (int) y + row * tileSize, null);
            }
        }
    }
}
