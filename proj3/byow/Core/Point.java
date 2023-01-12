package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Point {
    private int x;
    private int y;
    private TETile[][] world;


    public Point(int x, int y, TETile[][] world) {
        this.x = x;
        this.y = y;
        this.world = world;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Point shift(int dx, int dy) {
        return new Point(this.x + dx, this.y + dy, world);
    }

    public void moveRight(TETile avatar) {
        if (world[x+1][y] == Tileset.FLOOR) {
            world[x+1][y] = avatar;
            world[x][y] = Tileset.FLOOR;
        }
    }
    public void moveLeft(TETile avatar) {
        if (world[x-1][y] == Tileset.FLOOR) {
            world[x-1][y] = avatar;
            world[x][y] = Tileset.FLOOR;
        }
    }

    public void moveUp(TETile avatar) {
        if (world[x][y+1] == Tileset.FLOOR) {
            world[x][y+1] = avatar;
            world[x][y] = Tileset.FLOOR;
        }
    }

    public void moveDown(TETile avatar) {
        if (world[x][y-1] == Tileset.FLOOR) {
            world[x][y-1] = avatar;
            world[x][y] = Tileset.FLOOR;
        }
    }

    public boolean withinSquare(int xVal, int yVal) {
        if (xVal == x & yVal == y) {
            return true;
        } else if (xVal == x) {
            if (yVal == y - 1 | yVal == y + 1) {
                return true;
            } else {return false;}
        } else if (yVal == y) {
            if (xVal == x - 1 | xVal == x + 1) {
                return true;
            } else {return false;}
        } else if (xVal == x - 1 | xVal == x + 1) {
            if (yVal == y - 1 | yVal == y + 1) {
                return true;
            } else {return false;}
        } else {return false;}
    }

}
