import ExpressionTree.*;
import javafx.geometry.Point3D;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author ADKN.
 * @version 04 Jan 2016.
 */
public class Workshop {

    public static void main(String[] args) throws InvalidExpressionException {
        //WORKSHOP
        /*
        double[] a1 = {5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        StatOps.statSum(a1);

        double[] a2 = {31.6,29.94,28.28,28.92,28.76,24.6,23.44,21.68,19.32,20.96,19.1,17.14,14.58,12.62,14.16,11.2};
        StatOps.statSum(a2);

        double r = 0;
        for (int i = 0; i < a1.length; i++) {
            double zX = StatOps.zScore(a1[i],12.5,4.7610);
            double zY = StatOps.zScore(a2[i],21.643,6.616);
            r += (zX * zY);
        }
        System.out.println(r/a1.length-1);
        */

        Point3D first = new Point3D(2,4,4);
        Point3D second = new Point3D(6,8,8);
        System.out.println(pointDistance3D(second,first));
        double x = second.distance(first);
        System.out.println(x*x);


    }

    public static double pointDistance(Point first, Point second) {
        double result = Math.sqrt( (Math.pow(second.getX() - first.getX(),2)) + (Math.pow(second.getY() - first.getY(), 2)) );
        return result;
    }

    public static double pointDistance3D(Point3D first, Point3D second) {
        double result = Math.sqrt( (Math.pow(second.getX() - first.getX(),2)) + (Math.pow(second.getY() - first.getY(), 2)) + (Math.pow(second.getZ() - first.getZ(), 2)) );
        return result;
    }



}

class StatOps {
    public static double sumDataSet(double[] source) {
        double total = 0;
        for (double d : source) {
            total += d;
        }
        return total;
    }

    public static double zScore(double value, double mean, double stdev) {
        double z = (value - mean)/stdev;
        return z;
    }

    public static void statSum(double[] source) {
        double[] temp = source.clone();
        StatOps.shellSort(temp);

        DecimalFormat formatter = new DecimalFormat("#0.0000");
        String mean = formatter.format((StatOps.sumDataSet(temp)/temp.length));
        String sdx = formatter.format(stdev(source));

        String min = formatter.format(temp[0]);
        String max = formatter.format(temp[temp.length-1]);

        String q1;
        String q3;

        String median;

        double x1 = temp[temp.length/4];
        double x2 = temp[temp.length/4-1];
        q1 = formatter.format((x1 + x2)/2);

        x1 = temp[temp.length/4*3];
        x2 = temp[(temp.length)/4*3+1];
        q3 = formatter.format((x1 + x2)/2);

        if (temp.length%2 == 0) {
            x1 = temp[(temp.length/2) - 1];
            x2 = temp[(temp.length/2)];
            median = formatter.format((x1 + x2)/2);
            q1 = formatter.format(temp[temp.length/4]);
            q3 = formatter.format(temp[temp.length/4*3+1]);

        } else {
            median = formatter.format(temp[(temp.length/2)]);
        }
        String iqr = formatter.format(Double.parseDouble(q3) - Double.parseDouble(q1));
        String range = formatter.format(Double.parseDouble(max) - Double.parseDouble(min));

        System.out.println("Min: " + min + "\nQ1: " + q1 + "\nMedian: " + median + "\nQ3: " + q3
                + "\nMax: " + max + "\nMean: " + mean + "\nSdx: " + sdx + "\nIQR: " + iqr + "\nRange: " + range);
        System.out.println();
    }

    /**
     * Method to calculate standard deviation of an integer array.
     * @param array an int[]
     * @return returns a double value; the standard deviation of the array.
     */
    public static double stdev(double[] array) {
        double stDeviation;
        double sum = 0;
        double avg = 0;
        //steps: take sum of (all differences of values from the total average). get avg first.
        for (int i = 0; i < array.length; i++) {
            avg += array[i];
        } avg /= array.length;

        for (int i = 0; i < array.length; i++) {
            sum += Math.pow((array[i] - avg),2);
        }
        stDeviation = Math.sqrt(sum/(array.length-1));

        return stDeviation;
    }
    public static double stdev(ArrayList<Double> list) {
        double stDeviation;
        double sum = 0;
        double avg = 0;
        //steps: take sum of (all differences of values from the total average). get avg first.
        for (int i = 0; i < list.size(); i++) {
            avg += list.get(i);
        } avg /= list.size();

        for (int i = 0; i < list.size(); i++) {
            sum += Math.pow((list.get(i) - avg),2);
        }
        stDeviation = Math.sqrt(sum/(list.size()-1));

        return stDeviation;
    }

    public static double[] shellSort(double[] a) {
        int increment = a.length / 2;
        while (increment > 0) {
            for (int i = increment; i < a.length; i++) {
                int j = i;
                double temp = a[i];
                while (j >= increment && a[j - increment] > temp) {
                    a[j] = a[j - increment];
                    j = j - increment;
                }
                a[j] = temp;
            }
            if (increment == 2) {
                increment = 1;
            } else {
                increment *= (5.0 / 11);
            }
        }
        return a;
    }
}