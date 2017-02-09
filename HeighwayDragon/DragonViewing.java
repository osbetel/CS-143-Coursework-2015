package HeighwayDragon;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author ADKN.
 * @version 07 Feb 2016.
 */


/**
 * Class to manage the "view" aspect of the Heighway Dragon Program. This class draws the primary JFrame,
 * the dragons inside as they change with each iteration, and is passed in the GUI portions of the control aspect
 * so that it can display them in the frame as well.
 */
public class DragonViewing extends JPanel {
    private Dragon[] dragons;
    IterationCommand control;
    JFrame frame;

    protected static int FRAME_SIZE = 500;

    /**
     *
     * @param d An Array of Dragon objects. This is for drawing the initial state which looks like a +.
     * @param cont The controller aspect passed into the view. Controller object is not actually used, only its JPanel
     *             buttonPane is, but the call could not be static because it must also update the count of iterations.
     */
    public DragonViewing(Dragon[] d, IterationCommand cont) {
        dragons = d;

         //Set up of the primary frame, its layout manager, background, etc.
        frame = new JFrame("Heighway Fractal");
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(FRAME_SIZE, FRAME_SIZE);
        frame.setBackground(Color.WHITE);
        frame.setVisible(true);

        //This adds the buttonPane which is comprised of two buttons to increment and decrement, but also a count of iterations.
        control = cont;
        frame.getContentPane().add(control.buttonPane, BorderLayout.SOUTH);

        frame.validate();
    }

    /**
     * Updates the display
     * @param dragons New dragon objects, with new points to draw, are passed in and the display is updated.
     */
    public void update(Dragon[] dragons) {
        this.dragons = dragons;
        repaint();
    }

    /**
     * Paints the current state of the game.
     * @param g Graphics object; used to paint the initial state and any updated state.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Dragon d : dragons) {
            for (int i = 1; i < d.getPointList().size(); i++) {
                ArrayList<Point> pointList = d.getPointList();
                g.setColor(d.getColor());
                g.drawLine(pointList.get(i-1).x, pointList.get(i-1).y, pointList.get(i).x, pointList.get(i).y);
            }
        }
    }
}
