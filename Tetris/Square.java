package Tetris;
import java.awt.*;
/**
 * One Square on our Tetris Grid or one square in our Tetris game piece
 * 
 * @author csc143
 * @author Modified by Andrew Nguyen, Jan 13, 2015
 */
public class Square
{
    private Grid theGrid;      // the environment where this Square is
    private int row, col;       // the grid location of this Square
    private boolean ableToMove; // true if this Square can move
    private Color color;       // the color of this Square
    
    // possible move directions are defined by the Game class
    
    // dimensions of a Square
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    
    /**
     * Constructor for objects of class Square
     * @param g  the Grid for this Square
     * @param row the row of this Square in the Grid 
     * @param col the column of this Square in the Grid 
     * @param c the Color of this Square
     * @param mobile  true if this Square can move
     * 
     * @throws IllegalArgumentException if row and col not within the Grid
     */
    public Square(Grid g, int row, int col, Color c, boolean mobile){
       if (row < 0 || row > g.HEIGHT-1)
            throw new IllegalArgumentException("Invalid row =" + row);
       if (col < 0 || col > g.WIDTH-1)
            throw new IllegalArgumentException("Invalid column  = " + col);
       
       // initialize instance variables
       theGrid = g;
       this.row = row;
       this.col = col;
       color = c;
       ableToMove = mobile;
    }

    /**
     * Return the row for this Square
     */
    public int getRow() {
        return row;
    }
    
    /** 
     * Return the column for this Square
     */
    public int getCol() {
        return col;
    }
    
    /**
     * Return true if this Square can move 1 spot in direction d
     * @param direction the direction to test for possible move
     * @throws IllegalArgumentException if direction is not valid
     */
    public boolean canMove(int direction){
        if (!ableToMove)
            return false;
        
        boolean move = true;
        // if the given direction is blocked, we can't move
        // remember to check the edges of the grid
        try {
            switch (direction) {
            /*
            Consider adding a left and right that are col += 2 and -= 2.
            This would be to check for blocks 2,3,4,6 which have blocks in them that need to move right and left
            twice. Required so that a block does not accidentally move off the grid and commit suicide.
             */

            /*
            Standard 4 move directions.
             */
                case Game.DOWN:
                    if (row == (theGrid.HEIGHT - 1) || theGrid.isSet(row + 1, col))  //If the piece is at the bottom or the area beneath it is filled
                        move = false;
                    break;
                case Game.LEFT:
                    //When is the piece unable to move to the left?
                    if (col == 0 || theGrid.isSet(row, col - 1))
                        move = false;
                    break;
                case Game.RIGHT:
                    //When is the piece unable to move to the right?
                    if (col == theGrid.WIDTH - 1 || theGrid.isSet(row, col + 1))
                        move = false;
                    break;
                case Game.UP:
                    //When is the piece unable to move up?
                    if (row == 0 || theGrid.isSet(row - 1, col))
                        move = false;
                    break;

                /*
                double moves in standard 4 directions. Contains a callback to single directions to check the block
                next to it, and the block two moves away. Same is applied to the two diagonal move blocks.
                 */
                case Game.LEFT2:
                    if (!canMove(Game.LEFT)) {
                        move = false;
                    }
                    if (col == 1 || theGrid.isSet(row, col - 2))
                        move = false;
                    break;
                case Game.RIGHT2:
                    if (!canMove(Game.RIGHT)) {
                        move = false;
                    }
                    if (col == theGrid.WIDTH - 2 || theGrid.isSet(row, col + 2))
                        move = false;
                    break;
                case Game.DOWN2:
                    if (!canMove(Game.DOWN)) {
                        move = false;
                    }
                    if (row == (theGrid.HEIGHT - 2) || theGrid.isSet(row + 2, col))
                        move = false;
                    break;
                case Game.UP2:
                    if (!canMove(Game.UP)) {
                        move = false;
                    }
                    if (row == 1 || theGrid.isSet(row - 2, col))
                        move = false;
                    break;

                /*
                single moves in the 4 diagonal directions
                 */
                case Game.UPLEFT:
                    if (!canMove(Game.LEFT) || !canMove(Game.UP)) {
                        move = false;
                    }
                    if (theGrid.isSet(row - 1, col - 1))
                        move = false;
                    break;
                case Game.UPRIGHT:
                    if (!canMove(Game.RIGHT) || !canMove(Game.UP)) {
                        move = false;
                    }
                    if (theGrid.isSet(row - 1, col + 1))
                        move = false;
                    break;
                case Game.DOWNLEFT:
                    if (!canMove(Game.LEFT) || !canMove(Game.DOWN)) {
                        move = false;
                    }
                    if (theGrid.isSet(row + 1, col - 1))
                        move = false;
                    break;
                case Game.DOWNRIGHT:
                    if (!canMove(Game.RIGHT) || !canMove(Game.DOWN)) {
                        move = false;
                    }
                    if (theGrid.isSet(row + 1, col + 1))
                        move = false;
                    break;

                /*
                double moves in the 4 diagonal directions
                ISSUES:
                    Since these are used mostly for rotating the straight piece, they will sometimes throw and array idx out of bounds
                    Ex, particularly when the Straight piece is one block away from the left edge, vertical, and after rotating only ONCE!
                 */
                case Game.UPLEFT2:
                    if (!canMove(Game.UPLEFT)) {
                        move = false;
                    }
                    if (theGrid.isSet(row - 2, col - 2))
                        move = false;

                    break;
                case Game.UPRIGHT2:
                    if (!canMove(Game.UPRIGHT)) {
                        move = false;
                    }
                    if (theGrid.isSet(row - 2, col + 2))
                        move = false;
                    break;
                case Game.DOWNLEFT2:
                    if (!canMove(Game.DOWNLEFT)) {
                        move = false;
                    }
                    if (theGrid.isSet(row + 1, col - 1))
                        move = false;
                    break;
                case Game.DOWNRIGHT2:
                    if (!canMove(Game.DOWNRIGHT)) {
                        move = false;
                    }
                    if (theGrid.isSet(row + 2, col + 2))
                        move = false;
                    break;

                default:
                    throw new IllegalArgumentException("Bad direction to Square.canMove()");
            }
        } catch (ArrayIndexOutOfBoundsException err) {}
        return move;
    }
    
    /** move Square in the given direction if possible
     * Square will not move if direction is blocked, or Square is unable to move
     * If attempt to move DOWN and it can't, the Square is frozen
     * and cannot move anymore
     * @param direction the direction to move
     */
    public void move(int direction) {
        if (canMove(direction)) {
            switch(direction) {

                /*
                standard 4 direction moves
                 */
                case Game.DOWN:
                    row += 1;
                    break;
                case Game.LEFT:
                    col -= 1;
                    break;
                case Game.RIGHT:
                    col += 1;
                    break;
                case Game.UP:
                    row -= 1;
                    break;

                /*
                double moves in standard 4 directions
                 */
                case Game.LEFT2:
                    col -= 2;
                    break;
                case Game.RIGHT2:
                    col += 2;
                    break;
                case Game.DOWN2:
                    row += 2;
                    break;
                case Game.UP2:
                    row -= 2;
                    break;

                /*
                single moves in 4 diagonal directions
                 */
                case Game.UPLEFT:
                    col -= 1;
                    row -= 1;
                    break;
                case Game.UPRIGHT:
                    col += 1;
                    row -= 1;
                    break;
                case Game.DOWNLEFT:
                    row += 1;
                    col -= 1;
                    break;
                case Game.DOWNRIGHT:
                    row += 1;
                    col += 1;
                    break;

                /*
                double moves in the 4 diagonal directions
                 */
                case Game.UPLEFT2:
                    col -= 2;
                    row -= 2;
                    break;
                case Game.UPRIGHT2:
                    col += 2;
                    row -= 2;
                    break;
                case Game.DOWNLEFT2:
                    row += 2;
                    col -= 2;
                    break;
                case Game.DOWNRIGHT2:
                    row += 2;
                    col += 2;
                    break;
            }
        } 
    }
    
    /** Change the color of the Square
     * @param c the new color
     */
    public void setColor(Color c) {
        color = c;
    }
    
    /** Get the color of this Square
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Draw this square on the given Graphics context
     */
    public void draw(Graphics g) {
    
        // calculate the upper left (x,y) coordinate of this square
        int actualX = theGrid.LEFT + col * WIDTH;
        int actualY = theGrid.TOP + row * HEIGHT;
        g.setColor(color);
        g.fillRect(actualX, actualY, WIDTH, HEIGHT);
    }
}
