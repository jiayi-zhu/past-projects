package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Random;

public class Room {
    private Point p; // the lower-left tile of the room, which serves as the anchor point of drawing a room
    private int height;
    private int width;
    private int worldWidth;
    private int worldHeight;
    private TETile[][] world;
    private Random RANDOM;

    public Room(Point p, int height, int width, TETile[][] world, Random RANDOM) {
        this.p = p;
        this.height = height;
        this.width = width;
        this.worldWidth= world.length;
        this.worldHeight = world[0].length;
        this.world = world;
        this.RANDOM = RANDOM;
    }

    // discard the room of it either overlaps or within 1 tile of the existing rooms
    public boolean validRoom() {
        for (int x = max(p.getX() - 2,0); x <= min(min(p.getX() + width, worldWidth - 2) + 2,worldWidth - 1); x += 1) {
            for (int y = max(p.getY() - 2, 0); y <= min(min(p.getY() + height, worldHeight - 2) + 2,worldHeight - 1); y += 1) {
                if (world[x][y] == Tileset.FLOOR) {
                    return false;
                }
            }
        }
        return true;
    }


    // draw a single room
    public void drawRoom() {

        for (int x = p.getX(); x <= min(p.getX() + width, worldWidth - 2); x += 1) {
            for (int y = p.getY(); y <= min(p.getY() + height, worldHeight - 2); y += 1) {
                world[x][y] = Tileset.FLOOR;
            }
        }

    }


    // randomly select a tile in the room. min(p.x + width, worldWidth - 2)
    // this function is later used for randomly selecting the starting/ending points for the hallways
    public Point selectRandomTile() {
        int tileX = min(RANDOM.nextInt(width) + p.getX(), worldWidth - 2);
        int tileY = min(RANDOM.nextInt(height) + p.getY(), worldHeight - 2);
        Point randomTile = new Point(tileX, tileY, world);
        return randomTile;
    }



}
