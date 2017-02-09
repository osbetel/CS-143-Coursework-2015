package Tetris;

/**
 * Create and control the game Tetris.  
 * 
 * NOTE: when putting a Tetris object in a top-level container, make sure the Tetris
 * object has the focus (use requestFocus())
 * 
 * @author CSC 143 Based on a C++ version written by the 
 * University of Washington CSE department
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Tetris extends JPanel
{
   
    private Game theGame;
    
    /** Set up the parts for the Tetris game, display and user control
     */
    public Tetris() {
      
        theGame = new Game(this);
        GameController ec = new GameController(theGame);
        addKeyListener(ec);       
        setBackground(Color.yellow);
    }
    
    /**
     * Update the display
     */
    public void update() {
        repaint();
    }
    
    /**
     * Paint the current state of the game
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        theGame.draw(g);  
        if (theGame.isGameOver() ) {
            g.setFont(new Font("Palatino", Font.BOLD, 40));
            g.setColor(Color.BLACK);
            g.drawString("GAME OVER", 80, 300);
        }
    }
    
    /*  Create a game and play it*/
    public static void play()
    {
        JFrame f = new JFrame("The Tetris Game");
        Tetris t = new Tetris();
        f.getContentPane().add(t);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400,600);
        f.validate();
        f.setVisible(true);
        t.requestFocus();       // so that Tetris (the JPanel) fires the keyboard events.
     }
    /**
     * To be able to run from the command line or desktop
     */
    
    public static void main(String[] args)
    {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          play();
        }
      });
    }
    
}
