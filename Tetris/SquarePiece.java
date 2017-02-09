package Tetris;

import java.awt.*;
/**
 * @author ADKN
 * @version 13 Jan 2016, 11:30 PM
 *
 * This piece is made up of 4 squares in the following configuration
 *              Sq Sq<br>
 *              Sq Sq<br>
 *
 * The game piece "floats above" the Grid.  The (row, col) coordinates
 * are the location of the middle Square on the side within the Grid
 */
public class SquarePiece extends SuperPiece {
    /**
     * Create a square shaped piece. [5]
     * @param r row location for this piece
     * @param c column location for this piece
     * @param g the grid for this game piece
     */
    public SquarePiece(int r, int c, Grid g) {
        super(r,c,g);

        //Create the squares
        square[0] = new Square(g, r-1, c, Color.black, true);
        square[1] = new Square(g, r, c, Color.black, true);
        square[2] = new Square(g, r, c+1, Color.black, true);
        square[3] = new Square(g, r-1, c+1, Color.black, true);
    }

    public void rotatePiece() {}
    public boolean canRotate() {return true;}
}
