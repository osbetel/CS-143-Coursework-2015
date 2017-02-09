package Tetris;

import java.awt.*;

/**
 * This is the Tetris board represented by a (HEIGHT - by - WIDTH) matrix of Squares 
 * The upper left Square is at (0,0) The lower right Square is at (HEIGHT -1, WIDTH -1)
 * Given a Square at (x,y) the square to the left is at (x-1, y)
 *                         the square below is at (x, y+1)
 *                         
 * Each Square has a color.  A white Square is EMPTY;  any other color means that spot is 
 * occupied (i.e. a piece cannot move over/to an occupied square).  A grid will also remove 
 * completely full rows.
 * 
 * @author CSC 143
 * @author Modified by Andrew Nguyen, Jan 13, 2015
 */
public class Grid {
    private Square[][] board;
    // Width and Height of Grid in number of squares
    public static final int HEIGHT = 20;
    public static final int WIDTH = 10;
    private static final int BORDER = 5;
    
    public static final int LEFT = 100;    // pixel position of left of grid
    public static final int TOP = 75;  // pixel position of top of grid
    
    private static final Color EMPTY = Color.white;
    
    /**
     * Create the Grid
     */
    public Grid() {
        board = new Square[HEIGHT][WIDTH];
        
        //put squares in the board
        for (int row = 0; row < HEIGHT; row++)
            for(int col = 0; col < WIDTH; col++)
                board[row][col] = new Square(this, row, col, EMPTY, false);

    }

    /** 
     * Returns true if the location (row, col) on the grid is occupied
     * @param row the row in the grid 
     * @param col the column in the grid 
     */
    public boolean isSet( int row, int col){
        boolean isEmpty = board[row][col].getColor().equals(EMPTY);
        return !isEmpty;
    }
    
    /** 
     * Change the color of the Square at the given location
     * @param row the row of the Square in the Grid
     * @param col the column of the Square in the Grid
     * @param c the color to set the Square
     * @throws IndexOutOfBoundsException if row < 0 || row>= WIDTH || col < 0 || col >= HEIGHT
     */
    public void set(int row, int col, Color c) {
        board[row][col].setColor(c);
    }
 
    /**
     * Check for and remove all solid rows of squares
     * If a solid row is found and removed, all rows above
     * it are moved down and the top row set to empty
     */
    public void checkRows() {
        boolean filledRow;

        for (int row = 0; row < HEIGHT; row++) {  //Iterate through the rows, top to bottom
            filledRow = true;

            for (int col = 0; col < WIDTH; col++) {   //Iterate through the columns left to right
                if (!isSet(row,col)) {filledRow = false;}
            }
            if (filledRow) {
                for (int col = 0; col < WIDTH; col++) {
                    set(row,col,EMPTY);     //ONLY SETS COLOR BUT DOES NOT CHANGE "ableToMove"
//                    board[row][col] = new Square(this, row, col, EMPTY, false);
                    //either changing color or creating new Square works. Changing color more memory efficient...?
                }
                /*
                Issue is that the squares, even if the lower ones are painted empty, the ones
                on the top still have the status "!ableToMove" Hm...
                Need to scan for all blocks above and redraw the colored ones down by one row?
                At this point, the filled row has been removed, but the blocks haven't been redrawn. Redraw here.
                 */
                redrawAfterFilledRow();

            }
        }
    }

    /**
     * Helper method for checkRows()
     * Redraws the squares after a row is cleared to emulate the classic "falling" action.
     */
    private void redrawAfterFilledRow() {
        for (int row = HEIGHT-1; row > 0; row--) {  //Iterate through the height "rows" BOTTOM TO TOP
            /*
            It must be bottom to top because the "bottom" squares are checked for emptiness first.
            If true then the above square is erased and redrawn in the bottom square.
             */
            for (int col = 0; col < WIDTH; col++) {   //Iterate through the columns left to right
                if (isSet(row - 1, col) && !isSet(row, col)) {      //row is the "bottom" square; row - 1 is the top
                    Color temp = board[row - 1][col].getColor();    //Color needs to be stored as a temp before erasure
                    board[row][col].setColor(temp);
                    board[row - 1][col].setColor(EMPTY);
                }
            }
        }
    }
    
    /**
     * Draw the grid on the given Graphics context
     */
    public void draw(Graphics g){
    
        // draw the edges as rectangles: left, right in blue then bottom in red
        g.setColor(Color.blue);
        g.fillRect(LEFT - BORDER, TOP, BORDER, HEIGHT * Square.HEIGHT);
        g.fillRect(LEFT + WIDTH * Square.WIDTH, TOP, BORDER, HEIGHT * Square.HEIGHT);
        g.setColor(Color.red);
        g.fillRect(LEFT - BORDER, TOP + HEIGHT * Square.HEIGHT, 
                    WIDTH * Square.WIDTH + 2 * BORDER, BORDER);
        
        // draw all the squares in the grid
        for (int r = 0; r < HEIGHT; r++)
            for(int c = 0; c < WIDTH; c++)
                board[r][c].draw(g);
    }
}
