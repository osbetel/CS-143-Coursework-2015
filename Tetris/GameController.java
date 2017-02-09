package Tetris;

/**
 * Handles events for the Tetris Game.  User events (key strokes) as well as periodic timer
 * events.
 * 
 * @author CSC 143
 * @author Modified by Andrew Nguyen, Jan 19, 2016
 */
import java.awt.event.*;
import javax.swing.*;

public class GameController extends KeyAdapter implements ActionListener {
    private Game theGame;
    private Timer timer;
    private static final double PIECE_MOVE_TIME = 0.8;  //controls time between
                                                   //piece moving down
                                                   //increase to slow it down
    private boolean gameOver;
    
    /**
     * Constructor for objects of class EventController
     * @param g the game this is controlling
     */
    public GameController(Game g) {
        theGame = g;
        gameOver = false;
        double delay = 1000 * PIECE_MOVE_TIME;  // in milliseconds
        timer = new Timer((int)delay, this);
        timer.setCoalesce(true);    // if multiple events pending, bunch them to 1 event
        timer.start();
    }

    /*
     * Respond to special keys being pressed
     * Left, Right, and Down arrowkeys move the piece in their respective directions, space key moves the piece down.
     * the r key will rotate the piece.
     */
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    handleMove(Game.DOWN);
                    break;
                case KeyEvent.VK_RIGHT:
                    handleMove(Game.RIGHT);
                    break;
                case KeyEvent.VK_DOWN:
                    handleMove(Game.DOWN);
                    break;
                case KeyEvent.VK_LEFT:
                    handleMove(Game.LEFT);
                    break;
                case KeyEvent.VK_R:
                    theGame.rotateCommand();    // Sometimes causes a NullPointerException when there is no piece
                    break;                      // eg: the gap between landing a piece and generation of a new piece
                                                // Issue is non critical, rare, and does not break the operation of the program.

            }
        }
    }
   
   /** Update the game periodically based on a timer event*/
    public void actionPerformed(ActionEvent e) {
        handleMove(Game.DOWN);
     }
     
     /** Update the game by moving in the given direction
      */
     private void handleMove(int direction){
        theGame.movePiece(direction);
        gameOver = theGame.isGameOver();
        if (gameOver)
            timer.stop();
     }
}
