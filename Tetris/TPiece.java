package Tetris;

import javax.swing.text.Caret;
import java.awt.*;

/**
 * @author ADKN
 * @version 13 Jan 2016, 11:53 PM
 */
public class TPiece extends SuperPiece {
    /**
     * Create a T-Shape piece. [4]
     *
     *                 Sq Sq Sq<br>
     *                    Sq<br>
     * @param r row location for this piece
     * @param c column location for this piece
     * @param g the grid for this game piece
     */
    public TPiece(int r, int c, Grid g) {
        super(r, c, g);

        //Create the squares
        square[0] = new Square(g, r - 1, c, Color.yellow, true);
        square[1] = new Square(g, r, c, Color.yellow, true);
        square[2] = new Square(g, r - 1, c - 1, Color.yellow, true);
        square[3] = new Square(g, r - 1, c + 1, Color.yellow, true);
    }

    public void rotatePiece() {
        if (canRotate()) {
            switch (orientation) {
                case 0:
                    square[0].move(Game.DOWNRIGHT);
                    square[2].move(Game.RIGHT2);
                    square[3].move(Game.DOWN2);

                    break;

                case 1:
                    square[0].move(Game.DOWNLEFT);
                    square[2].move(Game.DOWN2);
                    square[3].move(Game.LEFT2);

                    break;

                case 2:
                    square[0].move(Game.UPLEFT);
                    square[2].move(Game.LEFT2);
                    square[3].move(Game.UP2);

                    break;

                case 3:
                    square[0].move(Game.UPRIGHT);
                    square[2].move(Game.UP2);
                    square[3].move(Game.RIGHT2);

                    break;
            }
        }
        if (orientation < 3) {
            orientation++;
        } else {
            orientation = 0;
        }
    }

    public boolean canRotate() {
        switch (orientation) {
            case 0:
                if(square[0].canMove(Game.DOWNRIGHT)) {
                    if(square[2].canMove(Game.RIGHT2)) {
                        if(square[3].canMove(Game.DOWN2)) {
                            return true;
                        }
                    }
                }

            case 1:
                if(square[0].canMove(Game.DOWNLEFT)) {
                    if(square[2].canMove(Game.DOWN2)) {
                        if(square[3].canMove(Game.LEFT2) && square[3].getCol() > 3) {
                            return true;
                        }
                    }
                }

            case 2:
                if(square[0].canMove(Game.UPLEFT)) {
                    if(square[2].canMove(Game.LEFT2)) {
                        if(square[3].canMove(Game.UP2)) {
                            return true;
                        }
                    }
                }

            case 3:
                if(square[0].canMove(Game.UPRIGHT)) {
                    if(square[2].canMove(Game.UP2)) {
                        if(square[3].canMove(Game.RIGHT2)) {
                            return true;
                        }
                    }
                }

        }
        return false;
    }
}
