package Tetris;

import java.awt.*;

/**
 * @author ADKN
 * @version 13 Jan 2016, 11:55 PM
 */
public class ZigPiece extends SuperPiece{
    /**
     * Create a Zig Shape piece. [2]
     *
     *              Sq Sq<br>
     *                 Sq Sq<br>
     *
     * @param r row location for this piece
     * @param c column location for this piece
     * @param g the grid for this game piece
     */
    public ZigPiece(int r, int c, Grid g) {
        super(r, c, g);

        //Create the squares
        square[0] = new Square(g, r - 1, c - 1, Color.red, true);
        square[1] = new Square(g, r, c, Color.red, true);
        square[2] = new Square(g, r - 1, c, Color.red, true);
        square[3] = new Square(g, r, c + 1, Color.red, true);
    }

    public void rotatePiece() {
        if (canRotate()) {
            switch (orientation) {
                case 0:
                    square[0].move(Game.RIGHT2);
                    square[2].move(Game.DOWNRIGHT);
                    square[3].move(Game.DOWNLEFT);

                    break;

                case 1:
                    square[0].move(Game.DOWN2);
                    square[2].move(Game.DOWNLEFT);
                    square[3].move(Game.UPLEFT);

                    break;

                case 2:
                    square[0].move(Game.LEFT2);
                    square[2].move(Game.UPLEFT);
                    square[3].move(Game.UPRIGHT);

                    break;

                case 3:
                    square[0].move(Game.UP2);
                    square[2].move(Game.UPRIGHT);
                    square[3].move(Game.DOWNRIGHT);

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
                if(square[0].canMove(Game.RIGHT2)) {
                    if(square[2].canMove(Game.DOWNRIGHT)) {
                        if(square[3].canMove(Game.DOWNLEFT)) {
                            return true;
                        }
                    }
                }

            case 1:
                if(square[0].canMove(Game.DOWN2)) {
                    if(square[2].canMove(Game.DOWNLEFT)) {
                        if(square[3].canMove(Game.UPLEFT)) {
                            return true;
                        }
                    }
                }

            case 2:
                if(square[0].canMove(Game.LEFT2)) {
                    if(square[2].canMove(Game.UPLEFT)) {
                        if(square[3].canMove(Game.UPRIGHT)) {
                            return true;
                        }
                    }
                }

            case 3:
                if(square[0].canMove(Game.UP2)) {
                    if(square[2].canMove(Game.UPRIGHT)) {
                        if(square[3].canMove(Game.DOWNRIGHT)) {
                            return true;
                        }
                    }
                }
        }
        return false;
    }
}
