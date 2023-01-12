package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;


public class HUD {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private TETile[][] world;
    private TETile avatar;
    public HUD(TETile[][] w, TETile avatar) {
        this.world = w;
        this.avatar = avatar;
    }

    public void drawInfo() {
        int x = (int)StdDraw.mouseX();
        int y = (int)StdDraw.mouseY();
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(fontBig);
        if (x >= 0 & x < WIDTH & y >= 0 & y < HEIGHT) {
            if (world[x][y].equals(Tileset.NOTHING)) {
                StdDraw.text(3, HEIGHT - 1, "Nothing");
            } else if (world[x][y].equals(Tileset.FLOOR)) {
                StdDraw.text(3, HEIGHT - 1, "Floor");
            } else if (world[x][y].equals(Tileset.WALL)) {
                StdDraw.text(3, HEIGHT - 1, "Wall");
            } else if (world[x][y].equals(avatar)) {
                StdDraw.text(3, HEIGHT - 1, "Avatar");
            }
        }
        StdDraw.show();
    }

    public String getHUD() {
        int x = (int)StdDraw.mouseX();
        int y = (int)StdDraw.mouseY();
        if (x >= 0 & x < WIDTH & y >= 0 & y < HEIGHT) {
            if (world[x][y].equals(Tileset.NOTHING)) {
                return "Nothing";
            } else if (world[x][y].equals(Tileset.FLOOR)) {
                return "Floor";
            } else if (world[x][y].equals(Tileset.WALL)) {
                return "Wall";
            } else if (world[x][y].equals(avatar)) {
                return "Avatar";
            }
        }
        return " ";
    }


}
