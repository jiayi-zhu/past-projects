package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class LightSquareWorld {
    private TETile[][] originalWorld;
    private Point startP;
    private TETile[][] lightSight;

    public LightSquareWorld(TETile[][] world, Point startP) {
        this.originalWorld = world;
        this.lightSight = world;
        this.startP = startP;
    }

    public TETile[][] setWorld() {
        for (int i = 0; i < originalWorld.length; i+=1) {
            for (int j = 0; j < originalWorld[0].length; j+=1) {
                if (startP.withinSquare(i,j)) {
                    lightSight[i][j] = originalWorld[i][j];
                } else {
                    lightSight[i][j] = Tileset.NOTHING;
                }
            }
        }
        return lightSight;
    }

}
