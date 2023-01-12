package byowTools.TileEngine;

package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Random;

public class Engine {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    private static final int MENU_WIDTH = 30;
    private static final int MENU_HEIGHT = 40;



    public TETile[][] finalWorldFrame;
    public TETile[][] previousWorldFrame;
    private Random RANDOM;
    private TERenderer ter;
    private FrameRender fr;
    private boolean endWithColonQ;

    public Engine() {
        finalWorldFrame = new TETile[WIDTH][HEIGHT];
        endWithColonQ = false;
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        this.fr = new FrameRender(MENU_WIDTH,MENU_HEIGHT);
        fr.drawMenu();
        String menuKey;
        String mySeed;
        menuKey = fr.menuKeyInput();
        if (menuKey.equals("N") | menuKey.equals("n")) {
            fr.drawFrame("");
            mySeed = fr.seedInput(19); // largest length for a long type
            interactWithInputString("N" + mySeed + "S");
        } else if (menuKey.equals("L") | menuKey.equals("l")) {
            ter.renderFrame(previousWorldFrame);
        }
        interactWithKeyboard();
    }


    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, running both of these:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        if (String.valueOf(input.charAt(0)).equals("L")) { //begin with L, so no new seed.
            loadHelper(input.substring(1));
        } else {
            int SEED = findSeed(input);
            String allMoves = findMoves(input);
            regularHelper(SEED, allMoves);
        }
        return finalWorldFrame;
    }
    private boolean endsWithSave(String s) {
        if (s == null) {
            return false;
        }
        String s1 = String.valueOf(s.charAt(s.length() - 1));
        if (s.length() > 2 & (s1.equals("Q") | s1.equals("q")) &
                String.valueOf(s.charAt(s.length() - 2)).equals(":")) {
            return true;
        } else {return false;}
    }

    private String excludeColonQ(String s) {
        return s.substring(0,s.length() - 2);
    }

    private void loadHelper(String input) {
        if (!Saving.hasSaved) {
            System.exit(0);
        } else {
            String moves = input.substring(1);
            if (!endsWithSave(moves)) {
                drawWorld(Saving.prevSeed,Saving.moveSoFar+moves);
                ter.renderFrame(finalWorldFrame);
            } else {
                Saving.moveSoFar+=excludeColonQ(moves);
                System.exit(0);
            }
        }
    }

    private void regularHelper(Integer seed, String input) {
        if (!endsWithSave(input)) {
            drawWorld(seed,input);
            ter.renderFrame(finalWorldFrame);
        } else {
            Saving.hasSaved = true;
            Saving.prevSeed = seed;
            Saving.moveSoFar = excludeColonQ(input);
            System.exit(0);
        }
    }


    private int findSeed(String input) {
        int seed = 0;
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(input);
        if (m.find()) {
            seed = Integer.parseInt(m.group());
        }
        return seed;
    }

    // for string with NseedS format
    private String findMoves(String s) {
        String allMoves = "";
        Pattern p = Pattern.compile("[a-zA-Z:]+");
        Matcher m = p.matcher(s);
        while (m.find()) {
            allMoves += m.group();
        }
        allMoves = allMoves.substring(2);
        if (allMoves.length() == 0) {
            return null;
        }
        return allMoves;
    }
    /**
     * given a seed
     * call other functions to draw the world
     */
    public void drawWorld(int SEED, String moves) {
        fillWithNothing(finalWorldFrame);
        RANDOM = new Random(SEED);
        RandomGenerator rg = new RandomGenerator(finalWorldFrame, RANDOM, moves);
        ter.renderFrame(finalWorldFrame);
    }
    /**
     * fill world with nothing*/
    private void fillWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

}

