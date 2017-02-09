package Tetris;

import java.awt.*;

/**
 * @author ADKN
 * @version 13 Jan 2016, 11:11 PM
 */

/**
 * Super class of all the pieces. Contains the basic functionality and basic methods, but
 * is abstract and only used as a data type for dynamic dispatch.
 */
public abstract class SuperPiece {
    private boolean ableToMove;     // can this piece move
    protected Square[] square;      // the Squares that make up this piece
                                    // Made up of PIECE_COUNT squares
    protected int orientation;      // 0 = base state, 1 = rotated once, 2 rotated twice, 3 = rotated thrice

    private Grid theGrid;           // the board this piece is on

    // number of squares in 1 Tetris game piece
    private static final int PIECE_COUNT = 4;


    public SuperPiece(int r, int c, Grid g) {
        theGrid = g;
        square = new Square[PIECE_COUNT];
        ableToMove = true;
        orientation = 0;

    }


    /**
     * Draw the piece on the given Graphics context
     */
    public void draw(Graphics g) {
        for (int i = 0; i < PIECE_COUNT; i++)
            square[i].draw(g);
    }

    /**
     * Move the piece if possible
     * Freeze the piece if it cannot move down anymore
     * @param direction the direction to move
     * @throws IllegalArgumentException if direction is not Square.DOWN,
     * Square.LEFT, or Square.RIGHT
     */
    public void move(int direction) {
        if (canMove(direction)){
            for (int i = 0; i < PIECE_COUNT; i++)
                square[i].move(direction);
        }
        // if we couldn't move, see if because we're at the bottom
        else if (direction == Game.DOWN) {
            ableToMove = false;
        }
    }

    /** Return the (row,col) grid coordinates occupied by this Piece
     * @return an Array of (row,col) Points
     */
    public Point[] getLocations() {
        Point[] points = new Point[PIECE_COUNT];
        for(int i = 0; i < PIECE_COUNT; i++) {
            points[i] = new Point(square[i].getRow(), square[i].getCol());
        }
        return points;
    }

    /**
     * Return the color of this piece
     */
    public Color getColor() {
        // all squares of this piece have the same color
        return square[0].getColor();
    }

    /**
     * Returns if this piece can move in the given direction
     * @throws IllegalArgumentException if direction is not a valid direction, or if the piece has already been "frozen."
     */
    public boolean canMove(int direction) {
        if (!ableToMove)
            return false;

        //Each square must be able to move in that direction
        boolean answer = true;
        for (int i = 0; i < PIECE_COUNT; i++) {
            answer = answer && square[i].canMove(direction);
        }

        return answer;
    }

    /**
     * Method to determine whether a piece can rotate.
     * @return True or false, depending on whether or not the piece can rotate and has space to do so.
     */
    public abstract boolean canRotate();
    /**
     * The implementation of where the individual squares of a piece should move upon executing a rotation command.
     */
    public abstract void rotatePiece();

}
