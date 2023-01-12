package byow.Core;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class MenuRenderer {
    private int width;
    private int height;

    public void initialize(int w, int h) {
        this.width = w;
        this.height = h;
        StdDraw.setCanvasSize(width * 16, height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    public void drawMenu(String avatarName) {
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
        StdDraw.text(width / 2 , height*0.25, "SET AVATAR (A)");
        Font fontTiny = new Font("Monaco", Font.TYPE1_FONT, 15);
        StdDraw.setFont(fontTiny);
        StdDraw.text(width / 2 , height*0.2, "[current avatar: " + avatarName + "]");
        Font fontItalic = new Font("Monaco", Font.ITALIC, 12);
        StdDraw.setFont(fontItalic);
        StdDraw.text(width / 2 , height*0.15, "Note: you can ONLY set the avatar for a NEW world");
        StdDraw.text(width / 2 , height*0.1, "Press SPACE to change the sight of the world");
        StdDraw.show();
    }

    public void drawAvatarMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontMedium = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(fontMedium);
        StdDraw.text(width / 2 , height*0.6, "DEFAULT (D)");
        StdDraw.text(width / 2 , height*0.5, "FLOWER (F)");
        StdDraw.text(width / 2 , height*0.4, "SAND (S)");
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

    public String menuInput() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String current = String.format("%s",StdDraw.nextKeyTyped());
                if (current.equals("N") | current.equals("n") | current.equals("L") | current.equals("l")
                    | current.equals("Q") | current.equals("q") | current.equals("A") | current.equals("a")) {
                    return current;
                }
            }
        }
    }

    public String avatarInput() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                String current = String.format("%s",StdDraw.nextKeyTyped());
                if (current.equals("D") | current.equals("d") | current.equals("F") | current.equals("f")
                        | current.equals("S") | current.equals("s")) {
                    return current;
                }
            }
        }
    }


    public String seedInput() {
        drawFrame("Please enter a seed");
        String userInput = "";
        while (true) {
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

