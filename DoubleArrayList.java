import java.util.List;

/**
 * @author ADKN.
 * @version 04 Feb 2016.
 */
public class DoubleArrayList {
    private double[] elements;  //The double[] itself
    private int size;       //# of elements stored, not including null values.

    private static final int START_LIMIT = 10;

    public DoubleArrayList() {
        elements = new double[START_LIMIT];
        size = 0;

    }

    public DoubleArrayList(int capacity) {
        if (capacity < 1) {throw new IllegalArgumentException();}
        elements = new double[capacity];
        size = 0;
    }

    public DoubleArrayList(List<Double> src) {
        elements = new double[src.size()];
        for (double x : src) {

        }
    }

    private void checkValidIndex(int index) {
        if (index < 0 || index > size) {throw new IllegalArgumentException();}
    }
    public double get(int index) {
        checkValidIndex(index);
        return elements[index];
    }

    public double set(int index, double value) {
        checkValidIndex(index);
        double oldVal = elements[index];
        elements[index] = value;
        return oldVal;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        /* Do I need to empty the array? No, because client can no longer access data in a legitimate index
        since data is stored from 0 to size-1. Array MUST be emptied if it's a list of OBJECTS
         */
//        elements =  new double[START_LIMIT];
    }

    public void add(int index, double input) {

    }
}