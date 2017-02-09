package HeighwayDragon;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author ADKN.
 * @version 07 Feb 2016.
 */


/**
 * Dragon class. Stores all the necessary data to draw a single Heighway Dragon in 1/4 directions.
 */
public class Dragon {
    private ArrayList<Point> points;
    private Color color;
    private final Point tail; private final Point head;
    private int iterations;


    private static final double INITIAL_SEGMENT_LENGTH = DragonViewing.FRAME_SIZE/5; //Length of segments is based on segment/√2^n, where n is iteration

    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;


    /**
     * Constructor for a Dragon. Must be passed in an integer for direction and a Color object.
     * @param initialDirection An integer from 0 - 3 representing North, East, South, or West. In that order.
     * @param color A color object.
     */
    public Dragon(int initialDirection, Color color) {
        if (initialDirection > 3 || initialDirection < 0) {throw new IllegalArgumentException();}

        /*
        Tail point is always the origin. Head point never changes but is based on direction.
         */
        tail = new Point(); head = new Point();
        this.color = color;
        tail.setLocation(DragonViewing.FRAME_SIZE/2, DragonViewing.FRAME_SIZE/2);
        iterations = 0;

        switch (initialDirection) {
            case NORTH:
                head.setLocation(DragonViewing.FRAME_SIZE/2, DragonViewing.FRAME_SIZE/2 - INITIAL_SEGMENT_LENGTH);
                break;
            case EAST:
                head.setLocation(DragonViewing.FRAME_SIZE/2 + INITIAL_SEGMENT_LENGTH, DragonViewing.FRAME_SIZE/2);
                break;
            case SOUTH:
                head.setLocation(DragonViewing.FRAME_SIZE/2, DragonViewing.FRAME_SIZE/2 + INITIAL_SEGMENT_LENGTH);
                break;
            case WEST:
                head.setLocation(DragonViewing.FRAME_SIZE/2 - INITIAL_SEGMENT_LENGTH, DragonViewing.FRAME_SIZE/2);
                break;
        }

        points = new ArrayList<>();
        recalculate(iterations);    //Fills the list created on the prior line with the current iteration. Which is 0.
    }

    /**
     * Method to request a Dragon's current list of Points.
     * @return Returns an ArrayList of Points for the Dragon.
     */
    public ArrayList<Point> getPointList() {
        return points;
    }

    /**
     * Method to request the Dragon's current color.
     * @return Returns a color obj.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Method to request the tail point of a Dragon. (tail is always set to the same spot though.
     * Only useful if I wanted to draw some other kind's of dragons.)
     * @return Returns a Point obj of the tail.
     */
    public Point getTailPoint() {
        return tail;
    }

    /**
     * Method to request the head point of a Dragon.
     * @return Returns Point obj of the head.
     */
    public Point getHeadPoint() {
        return head;
    }

    /**
     * Method to request the current iteration number.
     * @return Returns an int of the current iteration.
     */
    public int getIteration() {
        return iterations;
    }

    /**
     * Increments the iteration by 1, then recalculates the new set of points based on that iteration number,
     * which eventually makes its way to the viewer classes updating the display.
     */
    public void increment() {
        iterations++;
        points = recalculate(iterations);
    }

    /**
     * Decrements the iteration by 1, then recalculates the new set of points based on that iteration number,
     * which eventually makes its way to the viewer classes updating the display.
     */
    public void decrement() {
        if (iterations == 0) {return;}
        iterations--;
        points = recalculate(iterations);
    }

    /**
     * Calculates the set of points to draw for a particular iteration. Calls recursively until hitting the base case.
     * @param iterations Number of the iteration you wish to calculate points for.
     * @return Returns an ArrayList of Points to draw for the desired iteration.
     */
    private ArrayList<Point> recalculate(int iterations) {
        if(iterations == 0) {
            //Base case 0, just a line.
            points.clear();
            points.add(tail);
            points.add(head);
        } else if(iterations == 1) {
            //Base case 1 iteration, a single right angle.
            points.clear();
            points.add(tail);
            points.add(nextPoint(tail,head));
            points.add(head);
        } else {
            //all other cases
            points = recalculate(iterations - 1); //Recursively calls all the iterations back to 1,
            return makeFinalList();             //makeFinalList() creates a list of current iteration's points + new points.
        }
        return points;  //Returns in the event of base case being reached.
    }

    /**
     * Constructs a list from the new points to be drawn and the old points
     * of the prior iteration, combining them into a single list.
     * @return Returns an ArrayList of the current iteration's Points with the new ones inserted into their proper places.
     */
    private ArrayList<Point> makeFinalList() {
        /*
        tempList will have all the newly calculated points added,
        finalList will have the old points and new points combined into a single new list.
         */
        ArrayList<Point> tempList = new ArrayList<>();
        ArrayList<Point> finalList = new ArrayList<>();

        //Loop for calculating a list of the next points from a given list. Only calculates the new points.
        for (int i = 1; i < points.size(); i++) {
            /*
            Every other point is calculated with the "start" and "end" points reversed, such that if you have point A,B,C,D
            it will calculate nextPoint(A,B); nextPoint(C,B); nextPoint(C,D); this ensures that the image does not mirror
            itself by drawing all the lines in the same direction.
             */
            if (i % 2 != 0) {
                tempList.add(nextPoint(points.get(i - 1), points.get(i)));
            } else {
                tempList.add(nextPoint(points.get(i), points.get(i - 1)));
            }
        }

        //Adds the old points and the new points together, in the proper order, into a single final list.
        for (int i = 0; i < points.size(); i++) {
            finalList.add(points.get(i));
            if(i < tempList.size()){    //Avoids index out of bounds and adds the "new" points in the proper order, one after each old point.
                finalList.add(tempList.get(i));
            }
        }
        return finalList;
    }

    /**
     * Calculates the next point to be drawn from a starting point and an end point.
     * @param start Starting Point object
     * @param end Ending Point object
     * @return Returns the newly calculated point.
     */
    private Point nextPoint(Point start, Point end) {
        /*
        peakX = x1 + (x2 - x1) * 0.5 - (y1 - y2) * 0.5
        peakY = y1 - (x2 - x1) * 0.5 - (y1 - y2) * 0.5
        number of segments in an iteration is 2^n, where n is iterations
        thus number of points is (2^n)+1
         */
        double peakX = (start.x + (end.x - start.x) * 0.5) - ((start.y - end.y) * 0.5);
        double peakY = (start.y - (end.x - start.x) * 0.5) - ((start.y - end.y) * 0.5);
        Point nextPoint = new Point();          //For some reason, points can't be constructed with double values
        nextPoint.setLocation(peakX, peakY);    //Those double values must be assigned.
        return nextPoint;
    }
}