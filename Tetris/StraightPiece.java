package Tetris;

import java.awt.*;

/**
 * @author ADKN
 * @version 13 Jan 2016, 11:37 PM
 */
public class StraightPiece extends SuperPiece {
    /**
     * Create a straight piece. [3]
     *
     *          Sq Sq Sq Sq<br>
     *
     * @param r row location for this piece
     * @param c column location for this piece
     * @param g the grid for this game piece
     */
    public StraightPiece(int r, int c, Grid g) {
        super(r, c, g);

        //Create the squares
        square[0] = new Square(g, r-1, c - 1, Color.cyan, true);
        square[1] = new Square(g, r-1, c, Color.cyan, true);
        square[2] = new Square(g, r-1, c + 1, Color.cyan, true);
        square[3] = new Square(g, r-1, c + 2, Color.cyan, true);
    }

    public void rotatePiece() {
        if (canRotate()) {
            switch (orientation) {
                case 0:
                    square[0].move(Game.UPRIGHT);
                    square[2].move(Game.DOWNLEFT);
                    square[3].move(Game.DOWNLEFT2);

                    break;

                case 1:
                    square[0].move(Game.DOWNRIGHT);
                    square[2].move(Game.UPLEFT);
                    square[3].move(Game.UPLEFT2);

                    break;

                case 2:
                    square[0].move(Game.DOWNLEFT);
                    square[2].move(Game.UPRIGHT);
                    square[3].move(Game.UPRIGHT2);

                    break;

                case 3:
                    square[0].move(Game.UPLEFT);
                    square[2].move(Game.DOWNRIGHT);
                    square[3].move(Game.DOWNRIGHT2);

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
                if (square[0].canMove(Game.UPRIGHT)) {
                    if (square[2].canMove(Game.DOWNLEFT)) {
                        if (square[3].canMove(Game.DOWNLEFT2)) {
                            return true;
                        }
                    }
                }

            case 1:
                if (square[0].canMove(Game.DOWNRIGHT)) {
                    if (square[2].canMove(Game.UPLEFT)) {
                        if (square[3].canMove(Game.UPLEFT2)) {
                            return true;
                        }
                    }
                }

            case 2:
                if (square[0].canMove(Game.UPRIGHT)) {
                    if (square[2].canMove(Game.DOWNLEFT)) {
                        if (square[3].canMove(Game.DOWNLEFT2)) {
                            return true;
                        }
                    }
                }


            case 3:
                if (square[0].canMove(Game.UPLEFT)) {
                    if (square[2].canMove(Game.DOWNRIGHT)) {
                        if (square[3].canMove(Game.DOWNRIGHT2)) {
                            return true;
                        }
                    }
                }

        }
        return false;
    }
}
