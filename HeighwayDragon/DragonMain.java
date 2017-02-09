package HeighwayDragon;

import java.awt.*;

/**
 * @author ADKN.
 * @version 04 Feb 2016.
 */


/**
 * Stores a List of <Point> of where to draw the dragons, what color they are, and their iteration number.
 * For this assignment, the Model is this class and the Dragon class, the View which draws the dragons and creates the
 * primary JFrame is DragonViewing, and the Controller and class to show the iteration count is IterationCommand.
 */
public class DragonMain {
    private Dragon[] dragons;
    private DragonViewing view;
    private static final int DRAGON_COUNT = 4;

    /**
     * Constructor for the Model aspect; stores all the data. This class stores the Dragons which all
     * individually store their own data.
     */
    public DragonMain() {
        dragons = new Dragon[DRAGON_COUNT];

        //All start at the origin. All dragons have a color, tail, direction, and list of points.
        dragons[0] = new Dragon(Dragon.NORTH, Color.RED); //Top, Red
        dragons[1] = new Dragon(Dragon.EAST, Color.GREEN); //Right, Green
        dragons[2] = new Dragon(Dragon.SOUTH, Color.BLUE); //Bottom, Blue
        dragons[3] = new Dragon(Dragon.WEST, Color.BLACK); //Left, Black

        IterationCommand control = new IterationCommand(this);  //Creates the controller with this model passed in
        view = new DragonViewing(dragons, control);  //Creates the GUI and display with starting data and the controller passed in.
    }

    /**
     * Called to notify viewer classes of updates to the data.
     */
    public void notifyViewers() {
        view.update(dragons);
        /*
        In true MVC paradigm, it would notify the view and then the view would request data and update itself. Since I
        only have one class to update, I elected to directly update it from the model instead.
         */
    }

    /**
     * Method to increment the iteration count and calculate the new image.
     * Controller has no direct access to command the Dragons, must go through this class as an intermediary.
     */
    public void increment() {
        for (Dragon d : dragons ) {
            d.increment();
        }
        notifyViewers();
    }

    /**
     * Method to decrement the iteration count and calculate the new image.
     * Controller has no direct access to command the Dragons, must go through this class as an intermediary.
     */
    public void decrement() {
        for (Dragon d : dragons ) {
            d.decrement();
        }
        notifyViewers();
    }

    /**
     * Sends a request for the current data, in this case, the Array of Dragons.
     * @return Returns the current Array of Dragon objects.
     */
    public Dragon[] requestInfo() {
        return dragons;
    }

    public static void main(String[] args) {
        DragonMain dragonTime = new DragonMain();
    }
}
