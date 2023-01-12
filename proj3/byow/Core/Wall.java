package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Wall {
    private TETile[][] world;

    public Wall(TETile[][] world) {
        this.world = world;
    }

    public void buildWall() {
        //iterate over all the tiles in our world
        for (int x = 0; x < world.length; x+=1) {
            for (int y = 0; y <world[0].length; y += 1) {

                if (world[x][y] == Tileset.NOTHING) { // the tile itself must be NOTHING to become a valid candidate for a wall

                    // Check if the tile is adjacent to a FLOOR; if so, turn it into a wall
                    // Adjacency is measured in terms of its 8 surrounding tiles.
                    // upper, lower, left, right; upper left, upper right, lower left, lower right;
                    if ((x - 1 >= 0 && world[x - 1][y] == Tileset.FLOOR) |
                            (x + 1 < world.length && world[x + 1][y] == Tileset.FLOOR) |
                            (y + 1 < world[0].length && world[x][y + 1] == Tileset.FLOOR) |
                            (y - 1 >= 0 && world[x][y - 1] == Tileset.FLOOR) |
                            (y - 1 >= 0 && x - 1 >= 0 && world[x - 1][y - 1] == Tileset.FLOOR) |
                            (y + 1 < world[0].length && x + 1 < world.length && world[x + 1][y + 1] == Tileset.FLOOR) |
                            (y - 1 >= 0 && x + 1 < world.length && world[x + 1][y - 1] == Tileset.FLOOR) |
                            (y + 1 < world[0].length && x - 1 >= 0 && world[x - 1][y + 1] == Tileset.FLOOR)) {
                        world[x][y] = Tileset.WALL;
                    }

                }
            }
        }
    }

}
