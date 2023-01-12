package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.io.File;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class Engine {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    private static final int MENU_WIDTH = 30;
    private static final int MENU_HEIGHT = 40;

    public TETile[][] finalWorldFrame;
    private Random RANDOM;
    private TERenderer ter;
    private MenuRenderer mr;
    private boolean squareSightMode;
    private TETile avatar;
    private Point myPosition;
    private boolean initialLoad;
    private HUD hud;

    static File join(String first,String...others){
        return Paths.get(first,others).toFile();
    }

    public Engine() {
        finalWorldFrame = new TETile[WIDTH][HEIGHT];
        squareSightMode = false;
        avatar = Tileset.AVATAR;
        initialLoad = true;
    }

    private String getAvatarName() {
        if (avatar.equals(Tileset.AVATAR)) {
            return "DEFAULT";
        } else if (avatar.equals(Tileset.FLOWER)) {
            return "FLOWER";
        } else if (avatar.equals(Tileset.SAND)) {
            return "SAND";
        } return null;
    }

    private TETile mapAvatarNameToTile(String s) {
        if (s.equals("DEFAULT")) {
            return Tileset.AVATAR;
        } else if (s.equals("FLOWER")) {
            return Tileset.FLOWER;
        } else if (s.equals("SAND")) {
            return Tileset.SAND;
        } else {return null;}
    }



    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        ter = new TERenderer();
        mr = new MenuRenderer();
        mr.initialize(MENU_WIDTH,MENU_HEIGHT);
        mr.drawMenu(getAvatarName());
        String menuKey;
        String mySeed;
        menuKey = mr.menuInput();
        if (menuKey.equals("N") | menuKey.equals("n")) {
            mr.drawFrame("");
            mySeed = mr.seedInput();
            ter.initialize(WIDTH, HEIGHT);
            TETile[][] wf = interactWithInputString("N" + mySeed + "S");
            ter.renderFrame(wf);
            subsequentInput(mySeed,false);
        } else if (menuKey.equals("L") | menuKey.equals("l")) {
            ter.initialize(WIDTH, HEIGHT);
            TETile[][] wf = interactWithInputString("L");
            if (!squareSightMode) {
                ter.renderFrame(wf);
            } else {
                LightSquareWorld squareWorld = new LightSquareWorld(finalWorldFrame,myPosition);
                ter.renderFrame(squareWorld.setWorld());
            }
            subsequentInput(null, true);
        } else if (menuKey.equals("Q") | menuKey.equals("q")) {
            System.exit(0);
        } else if (menuKey.equals("A") | menuKey.equals("a")) {
            mr.drawAvatarMenu();
            String avatarKey = mr.avatarInput();
            if ((avatarKey.equals("D") | avatarKey.equals("d"))) {
                avatar = Tileset.AVATAR;
            }
            if (avatarKey.equals("F") | avatarKey.equals("f")) {
                avatar = Tileset.FLOWER;
            } else if (avatarKey.equals("S") | avatarKey.equals("s")) {
                avatar = Tileset.SAND;
            }
            interactWithKeyboard();
        }
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
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        if (String.valueOf(input.charAt(0)).equals("L")) { //begin with L, so no new seed.
            loadHelper(input.substring(1));
        } else {
            long SEED = findSeed(input);
            String allMoves = findMoves(input);
            regularHelper(SEED, allMoves);
        }
        return finalWorldFrame;
    }


    private boolean endsWithSave(String s) {
        if (s == null) {
            return false;
        } else if (s.length() < 2) {
            return false;
        }
        String s1 = String.valueOf(s.charAt(s.length() - 1));
        if ((s1.equals("Q") | s1.equals("q")) &
                String.valueOf(s.charAt(s.length() - 2)).equals(":")) {
            return true;
        } else {return false;}
    }

    private String excludeColonQ(String s) {
        return s.substring(0,s.length() - 2);
    }

    private void loadHelper(String input) {
        File f = join("savedFile.txt");
        if (!f.exists()) {
            System.exit(0);
        } else {
            In savedFile = new In(f.getName());
            String[] allLines = savedFile.readAllLines();
            long prevSeed = Long.parseLong(allLines[0]);
            String prevMoves;
            if (allLines.length < 4) {
                prevMoves = "";
            } else {prevMoves = allLines[1];}
            if (initialLoad) {
                this.squareSightMode = Boolean.parseBoolean(allLines[allLines.length - 2]);
                this.avatar = mapAvatarNameToTile(allLines[allLines.length - 1]);
                initialLoad = false;
            }
            if (!endsWithSave(input)) {
                generateWorldArray(prevSeed,prevMoves+input);
            } else {
                try {
                    FileWriter writer = new FileWriter(f.getName());
                    writer.write(String.format("%d", prevSeed));
                    writer.write("\r\n");
                    String movesSoFar = prevMoves+excludeColonQ(input);
                    writer.write(movesSoFar);
                    writer.write("\r\n");
                    String lightMode = String.format("%s", squareSightMode);
                    writer.write(lightMode);
                    writer.write("\r\n");
                    writer.write(getAvatarName());
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        }
    }

    private void regularHelper(long seed, String input) {
        if (!endsWithSave(input)) {
            generateWorldArray(seed,input);
        } else {
            try {
                File f = join("savedFile.txt");
                FileWriter writer = new FileWriter(f.getName());
                writer.write(String.format("%d",seed));
                writer.write("\r\n");
                writer.write(excludeColonQ(input));
                writer.write("\r\n");
                String lightMode = String.format("%s", squareSightMode);
                writer.write(lightMode);
                writer.write("\r\n");
                writer.write(getAvatarName());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

    private long findSeed(String input) {
        long seed = 0;
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(input);
        if (m.find()) {
            seed = Long.parseLong(m.group());
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


    private void generateWorldArray(long SEED, String moves) {
        fillWithNothing(finalWorldFrame);
        RANDOM = new Random(SEED);
        RandomGenerator rg = new RandomGenerator(finalWorldFrame, RANDOM, moves, avatar);
        this.myPosition = rg.getStarP();
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


    /**
     press space button to change to square light mode
     */

    private void subsequentInput(String seed, boolean loaded) {
        String moves = "";
        hud = new HUD(finalWorldFrame, avatar);
        hud.drawInfo();
        String prevHUD = hud.getHUD();
        while (true) {
            String currHUD = hud.getHUD();
            if (!currHUD.equals(prevHUD)) {
                prevHUD = currHUD;
                ter.renderFrame(finalWorldFrame);
                hud.drawInfo();
            }
            if (StdDraw.hasNextKeyTyped()) {
                String nextKey = String.format("%s", StdDraw.nextKeyTyped());
                moves += nextKey;
                if (nextKey.equals(" ")) {
                    this.squareSightMode = !this.squareSightMode;
                }
                if (!loaded) {
                    interactWithInputString("N" + seed + "S" + moves);
                } else {
                    interactWithInputString("L" + moves);
                }
                if (!squareSightMode) {
                    ter.renderFrame(finalWorldFrame);
                } else {
                    LightSquareWorld squareWorld = new LightSquareWorld(finalWorldFrame,myPosition);
                    ter.renderFrame(squareWorld.setWorld());
                }
                hud.drawInfo();
                prevHUD = hud.getHUD();
            }


        }
    }
}
