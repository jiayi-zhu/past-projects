package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import static java.lang.Math.abs;

public class Hallway {
    private Point begin;
    private Point end;
    private TETile[][] world;


    public Hallway(TETile[][] tiles, Room b, Room e) {
        this.world = tiles;
        this.begin = b.selectRandomTile();
        this.end = e.selectRandomTile();
    }



    // drawing from lower(startP) to upper (endP). startP and endP must have the same x-value
    private void drawVerticalLine(Point startP, Point endP) {
        for (int i = startP.getY(); i <= endP.getY(); i++) {
            world[startP.getX()][i] = Tileset.FLOOR;
        }
    }

    // drawing from left(startP) to right(endP). startP and endP must have the same y-value
    private void drawHorizontalLine(Point startP, Point endP) {
        for (int i = startP.getX(); i <= endP.getX(); i++) {
            world[i][startP.getY()] = Tileset.FLOOR;
        }
    }

    public void drawHallWay() {
        int xDist  = abs(begin.getX() - end.getX());
        if (begin.getX() < end.getX()) { // begin is on the left of end
            drawHorizontalLine(begin, begin.shift(xDist, 0));
            if (begin.getY() < end.getY()) { // begin is on the bottom left of end
                drawVerticalLine(begin.shift(xDist,0), end);
            } else { // begin is on the upper left of end
                drawVerticalLine(end, begin.shift(xDist, 0));
            }
        } else { // end is on the left of begin
            drawHorizontalLine(end, end.shift(xDist, 0));
            if (end.getY() < begin.getY()) { // end is on the bottom left of begin
                drawVerticalLine(end.shift(xDist,0), begin);
            } else { // end is on the upper left of begin
                drawVerticalLine(begin, end.shift(xDist, 0));
            }
        }
    }
}