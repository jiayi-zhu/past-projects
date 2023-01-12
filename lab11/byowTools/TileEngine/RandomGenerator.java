package byow.Core;

import java.util.ArrayList;
import java.util.Random;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;


public class RandomGenerator {

    private static final int MIN_ROOM_LEN = 3;
    private static final int MAX_ROOM_LEN = 7; //set the max possible height/width of a room to be 7

    private static final int MIN_ROOM_NUM = 5;
    private static final int MAX_ROOM_NUM = 10; //set the max possible number of rooms to be 10

    private static final int BOUNDARY_FACTOR = 5;

    private Random RANDOM;
    private byow.Core.Point startPoint;
    private int numRoom;
    private int worldWidth;
    private int worldHeight;
    private TETile[][] world;
    private ArrayList<Room> roomList;
    private ArrayList<Integer> targetTileX;
    private ArrayList<Integer> targetTileY;
    private ArrayList<byow.Core.Point> targetTilePositions;
    private ArrayList<Integer> roomHeights;
    private ArrayList<Integer> roomWidths;
    private ArrayList<byow.Core.Point> basicHallWayPoints;
    private String[] moves;



    public RandomGenerator(TETile[][] world, Random RANDOM, String moves) {
        this.RANDOM = RANDOM;
        this.worldWidth = world.length;
        this.worldHeight = world[0].length;
        this.world = world;
        if (moves == null) {
            this.moves = null;
        } else {this.moves = moves.split("");}
        roomList = new ArrayList<>();
        targetTileX = new ArrayList<>();
        targetTileY = new ArrayList<>();
        targetTilePositions = new ArrayList<>();
        roomHeights = new ArrayList<>();
        roomWidths = new ArrayList<>();
        basicHallWayPoints = new ArrayList<>();
        numRoomGenerator();
        targetTileXGenerator();
        targetTileYGenerator();
        formTargetTilePosition();
        RoomHeightsGenerator();
        RoomWidthsGenerator();
        drawListOfRooms();
        drawHallWays();
        Wall w = new Wall(world);
        w.buildWall();
        startPointGenerator();
        moveStartPoint();
    }

    public void startPointGenerator() {
        for (int i = 0; i < worldWidth; i+=1) {
            for (int j = 0; j < worldHeight; j+=1) {
                if (world[i][j] == Tileset.FLOOR) {
                    startPoint = new byow.Core.Point(i,j,world);
                    world[i][j] =Tileset.FLOWER;
                    return;
                }
            }
        }
    }
    public void moveStartPoint() {
        if (moves == null) {
            return;
        }
        for (int i = 0; i < moves.length; i += 1) {
            if (moves[i].equals("W") | moves[i].equals("w")) {
                startPoint.moveUp();
                if (world[startPoint.getX()][startPoint.getY()] != Tileset.FLOWER) {
                    startPoint = startPoint.shift(0,1);
                }
            } else if (moves[i].equals("S") | moves[i].equals("s")) {
                startPoint.moveDown();
                if (world[startPoint.getX()][startPoint.getY()] != Tileset.FLOWER) {
                    startPoint = startPoint.shift(0,-1);
                }
            } else if (moves[i].equals("A") | moves[i].equals("a")) {
                startPoint.moveLeft();
                if (world[startPoint.getX()][startPoint.getY()] != Tileset.FLOWER) {
                    startPoint = startPoint.shift(-1,0);
                }
            } else if (moves[i].equals("D") | moves[i].equals("d")) {
                startPoint.moveRight();
                if (world[startPoint.getX()][startPoint.getY()] != Tileset.FLOWER) {
                    startPoint = startPoint.shift(1,0);
                }
            }
        }
    }

    // Randomly generate the number of total rooms, which is a random number between [MIN_ROOM_NUM,MAX_ROOM_NUM]
    public void numRoomGenerator() {
        numRoom = RANDOM.nextInt(MAX_ROOM_NUM - MIN_ROOM_NUM) + MIN_ROOM_NUM;
    }


    // Generate the X coordinate of the bottom left tile for each room.
    public void targetTileXGenerator() {
        while (targetTileX.size() < numRoom * 2) {
            int newItem = RANDOM.nextInt(worldWidth - BOUNDARY_FACTOR) + 1;
            targetTileX.add(newItem);
        }
    }
    // Generate the Y coordinate of the bottom left tile for each room.
    public void targetTileYGenerator() {
        while (targetTileY.size() < numRoom * 2) {
            int newItem = RANDOM.nextInt(worldHeight - BOUNDARY_FACTOR) + 1;
            targetTileY.add(newItem);
        }
    }

    // build a list of points from the above X & Y coordinates. These are the lower-left points (which can be considered as anchor points) for each room
    public void formTargetTilePosition() {
        for (int i = 0; i < numRoom * 2; i += 1) {
            int x = targetTileX.get(i);
            int y = targetTileY.get(i);
            byow.Core.Point p = new byow.Core.Point(x,y,world);
            targetTilePositions.add(p);
        }
    }

    // Randomly generate a list of heights of rooms
    public void RoomHeightsGenerator() {
        for (int i = 0; i < numRoom * 2; i += 1) {
            int newHeight = RANDOM.nextInt(MAX_ROOM_LEN - MIN_ROOM_LEN) + MIN_ROOM_LEN; // I set the height to be in [MIN_ROOM_LEN, MAX_ROOM_LEN]
            roomHeights.add(newHeight);
        }
    }

    // Randomly generate a list of widths of rooms
    public void RoomWidthsGenerator() {
        for (int i = 0; i < numRoom * 2; i += 1) {
            int newWidth = RANDOM.nextInt(MAX_ROOM_LEN - MIN_ROOM_LEN) + MIN_ROOM_LEN; // I set the width to be in [MIN_ROOM_LEN, MAX_ROOM_LEN]
            roomWidths.add(newWidth);
        }
    }

    // draw all the rooms
    public void drawListOfRooms(){

        int currNum = numRoom;
        for (int i = 0; i < numRoom * 2; i += 1) {
            byow.Core.Point targetP = targetTilePositions.get(i);
            int roomH = roomHeights.get(i);
            int roomW = roomWidths.get(i);
            Room room = new Room(targetP,roomH,roomW, world, RANDOM);
            if (room.validRoom()) {
                roomList.add(room);
                room.drawRoom();
                currNum -= 1;
            }
            if (currNum == 0) {
                return;
            }
        }
    }

    // draw all the hallways
    public void drawHallWays() {
        for (int i = 0; i < roomList.size() - 1; i += 1) {
            byow.Core.Hallway hw = new byow.Core.Hallway(world, roomList.get(i), roomList.get(i + 1));
            hw.drawHallWay();
        }

    }


}
