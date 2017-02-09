package Tetris;

import java.awt.*;

/**
 * An LPiece item in the Tetris Game.
 * 
 * This piece is made up of 4 squares in the following configuration
 *              Sq<br>
 *              Sq<br>
 *              Sq Sq<br>
 * 
 * The game piece "floats above" the Grid.  The (row, col) coordinates 
 * are the location of the middle Square on the side within the Grid
 * 
 * @author CSC 143
 */
public class LPiece extends SuperPiece {
    /**
     * Create an L-Shape piece. [0]
     * @param r row location for this piece
     * @param c column location for this piece
     * @param g the grid for this game piece
     */
    public LPiece(int r, int c, Grid g) {
        super(r,c,g);

        //Create the squares
        square[0] = new Square(g, r - 1, c, Color.magenta, true);
        square[1] = new Square(g, r, c , Color.magenta, true);
        square[2] = new Square(g, r + 1, c, Color.magenta, true);
        square[3] = new Square(g, r + 1, c + 1, Color.magenta, true);
    }

    public void rotatePiece() {
        if (canRotate()) {
            switch (orientation) {
                case 0:
                    square[0].move(Game.DOWNRIGHT);
                    square[2].move(Game.UPLEFT);
                    square[3].move(Game.LEFT2);

                    break;

                case 1:
                    square[0].move(Game.DOWNLEFT);
                    square[2].move(Game.UPRIGHT);
                    square[3].move(Game.UP2);

                    break;

                case 2:
                    square[0].move(Game.UPLEFT);
                    square[2].move(Game.DOWNRIGHT);
                    square[3].move(Game.RIGHT2);

                    break;

                case 3:
                    square[0].move(Game.UPRIGHT);
                    square[2].move(Game.DOWNLEFT);
                    square[3].move(Game.DOWN2);

                    break;
            }
            if (orientation < 3) {
                orientation++;
            } else {orientation = 0;}
        }
    }

    public boolean canRotate() {
        switch (orientation) {
            case 0:
                if(square[0].canMove(Game.DOWNRIGHT)) {
                    if(square[2].canMove(Game.UPLEFT)) {
                        if(square[3].canMove(Game.LEFT2)) {
                            return true;
                        }
                    }
                }

            case 1:
                if(square[0].canMove(Game.DOWNLEFT)) {
                    if(square[2].canMove(Game.UPRIGHT)) {
                        if(square[3].canMove(Game.UP2)) {
                            return true;
                        }
                    }
                }

            case 2:
                if(square[0].canMove(Game.UPLEFT)) {
                    if(square[2].canMove(Game.DOWNRIGHT)) {
                        if(square[3].canMove(Game.RIGHT2)) {
                            return true;
                        }
                    }
                }

            case 3:
                if(square[0].canMove(Game.UPRIGHT)) {
                    if(square[2].canMove(Game.DOWNLEFT)) {
                        if(square[3].canMove(Game.DOWN2)) {
                            return true;
                        }
                    }
                }
        }
        return false;
    }
}
