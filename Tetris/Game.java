package Tetris;

import java.awt.*;
import java.util.Random;

/**
 * Manages the game Tetris. Keeps track of the current piece and the grid.
 * Updates the display whenever the state of the game has changed.
 *
 * @author CSC 143
 */
public class Game
{

    private Grid theGrid;       // the grid that makes up the Tetris board
    private Tetris theDisplay;  // the visual for the Tetris game
    protected SuperPiece piece;        // the current piece that is dropping
    private boolean isOver;     // has the game finished?

    // possible move directions
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int UP = 3;

    /*
    Added 12 different directions, mostly to deal with rotations. May be a tad overkill.
    This first block of directions moves one space in the four diagonal directions.
     */
    public static final int UPLEFT = 4;
    public static final int UPRIGHT = 5;
    public static final int DOWNLEFT = 6;
    public static final int DOWNRIGHT = 7;

    /*
    This next block moves two spaces in the four diagonal directions.
     */
    public static final int UPLEFT2 = 8;
    public static final int UPRIGHT2 = 9;
    public static final int DOWNLEFT2 = 10;
    public static final int DOWNRIGHT2 = 11;

    /*
    This final block moves two spaces in the four standard directions.
     */
    public static final int LEFT2 = 12;
    public static final int RIGHT2 = 13;
    public static final int DOWN2 = 14;
    public static final int UP2 = 15;

    /**
     * Create a Tetris game
     *
     * @param display the display as a Tetris obj.
     */
    public Game(Tetris display) {
        theGrid = new Grid();
        theDisplay = display;
       randomPiece();

        /*
        For testing. Represent the 4 corners of the useable grid.
         */
//        theGrid.set(0,0,Color.red);
//        theGrid.set(19,0,Color.red);
//        theGrid.set(19,9,Color.red);
//        theGrid.set(0,9,Color.red);

        isOver = false;
    }


    /**
     * Draw the current state of the game
     *
     * @param g the Graphics context on which to draw
     */
    public void draw(Graphics g) {
        theGrid.draw(g);
        if (piece != null)
            piece.draw(g);
    }

    /**
     * Move the piece in the given direction
     *
     * @param direction the direction to move
     * @throws IllegalArgumentException if direction is not legal
     */
    public void movePiece(int direction) {
        if (piece != null)
            piece.move(direction);
        updatePiece();
        theDisplay.update();
        theGrid.checkRows();
    }

    /**
     * Returns true if the game is over
     */
    public boolean isGameOver() {
        // game is over if the piece occupies the same space as some non-empty
        // part of the grid.  Usually happens when a new piece is made
        if (piece == null)
            return false;

        // check if game is already over
        if (isOver)
            return true;

        // check every part of the piece
        Point[] p = piece.getLocations();
        for (int i = 0; i < p.length; i++) {
            if (theGrid.isSet((int) p[i].getX(), (int) p[i].getY())) {
                isOver = true;
                return true;
            }
        }
        return false;
    }

    // Update the piece
    private void updatePiece() {
        if (piece == null) {
//            piece = new LPiece(1, Grid.WIDTH / 2 - 1, theGrid);
            randomPiece();
        }

        // set Grid positions corresponding to frozen piece
        // and then release the piece
        else if (!piece.canMove(Game.DOWN)) {
            Point[] p = piece.getLocations();
            Color c = piece.getColor();
            for (int i = 0; i < p.length; i++) {
                theGrid.set((int) p[i].getX(), (int) p[i].getY(), c);
            }
            piece = null;
        }
    }

    /**
     * Selects a random piece of of the 7 possible pieces.
     */
    private void randomPiece() {
        Random rand = new Random();
        switch (3) {
            case 0:
                piece = new LPiece(1, Grid.WIDTH / 2 - 1, theGrid);
                break;
            case 1:
                piece = new ReverseLPiece(1, Grid.WIDTH / 2 - 1, theGrid);
                break;
            case 2:
                piece = new ZigPiece(1, Grid.WIDTH / 2 - 1, theGrid);
                break;
            case 3:
                piece = new StraightPiece(1, Grid.WIDTH / 2 - 1, theGrid);
                break;
            case 4:
                piece = new TPiece(1, Grid.WIDTH / 2 - 1, theGrid);
                break;
            case 5:
                piece = new SquarePiece(1, Grid.WIDTH / 2 - 1, theGrid);
                break;
            case 6:
                piece = new ZagPiece(1, Grid.WIDTH / 2 - 1, theGrid);
                break;
        }
    }

    /**
     * Protected command used by the game controller when capturing user input of the "r" key.
     * Result is then passed to the Game class which executes rotateCommand(), which then prompts the
     * piece itself to rotate and updates the display to show this rotation.
     */
    protected void rotateCommand() {
        if (piece != null)
            this.piece.rotatePiece();
        updatePiece();
        theDisplay.update();
        theGrid.checkRows();
    }
}
