package byowTools.TileEngine;

package byow.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class FrameRender {
    private int width;
    private int height;
    public FrameRender(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void drawMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(width / 2 , height*0.8, "CS61B: THE GAME");
        Font fontSmall = new Font("Monaco", Font.ROMAN_BASELINE, 20);
        StdDraw.setFont(fontSmall);
        StdDraw.text(width / 2 , height*0.4, "NEW GAME (N)");
        StdDraw.text(width / 2 , height*0.35, "LOAD GAME (L)");
        StdDraw.text(width / 2 , height*0.3, "QUIT (Q)");
        StdDraw.show();
    }

    public void drawFrame(String s) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.show();
    }

    public String menuKeyInput() {
        System.out.println("YES");
        String userInput = "";
        while (userInput.length() < 1) {
            if (StdDraw.hasNextKeyTyped()) {
                char current = StdDraw.nextKeyTyped();
                userInput += current;
            }
        }
        return userInput;
    }

    public String seedInput(int n) {
        drawFrame("Please enter a seed");
        String userInput = "";
        while (userInput.length() < n) {
            if (StdDraw.hasNextKeyTyped()) {
                char current = StdDraw.nextKeyTyped();
                if (current == "s".charAt(0) | current == "S".charAt(0)) {
                    break;
                }
                userInput += current;
                drawFrame(userInput);
            }
        }
        return userInput;
    }



}