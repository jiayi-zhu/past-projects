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

    public void moveRight() {
        if (world[x+1][y] == Tileset.FLOOR) {
            world[x+1][y] = Tileset.FLOWER;
            world[x][y] = Tileset.FLOOR;
        } else {return;}
    }
    public void moveLeft() {
        if (world[x-1][y] == Tileset.FLOOR) {
            world[x-1][y] = Tileset.FLOWER;
            world[x][y] = Tileset.FLOOR;
        } else {return;}
    }

    public void moveUp() {
        if (world[x][y+1] == Tileset.FLOOR) {
            world[x][y+1] = Tileset.FLOWER;
            world[x][y] = Tileset.FLOOR;
        }
    }

    public void moveDown() {
        if (world[x][y-1] == Tileset.FLOOR) {
            world[x][y-1] = Tileset.FLOWER;
            world[x][y] = Tileset.FLOOR;
        }
    }

}


