package Stats;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author ADKN
 * @version 06 Mar 2016, 10:16 PM
 */
public class StatSort {

    public static void main(String[] args) throws FileNotFoundException {
        statsClass();

    }

    public static void statsClass() throws FileNotFoundException {
        /**
         * Original list = 10,061 data points.
         * Modified list = 9956 data points.
         * Q1: 3.625
         * Median: 4.400
         * Q3: 5.200
         * IQR: 1.575
         * Low Bound: 1.263
         * High Bound: 7.563
         *
         * Mean = 4.394
         * Sdx = 1.243
         */

        class colData {
            public double item;
            public String date;

            public colData(String date, double item) {
                this.date = date;
                this.item = item;
            }
            public String toString() {
                return date + " | " +  item;
            }
        }

        Scanner dataPoints = new Scanner(new File("src/Stats/AlgaeData.txt"));
        ArrayList<Double> dataList = readNumData(dataPoints);

        ArrayList<Double> temp = new ArrayList<>();
        for (double d : dataList) {
            temp.add(d);
        }
        temp.sort(Comparator.naturalOrder());

        Scanner timestamps = new Scanner(new File("src/Stats/AlgaeDates.txt"));
        ArrayList<String> timeList = readTimestamp(timestamps);

        double min = temp.get(0);
        double q1 = temp.get(temp.size()/4);
        double median = temp.get(temp.size()/2);
        double q3 = temp.get((temp.size()/4)*3);
        double max = temp.get(temp.size()-1);

        double stdev = stdev(temp);
        double mean = average(temp);

        double IQR = round(q3-q1,3);
        double lowBound = round(q1 - (1.5*IQR),3);
        double hiBound = round(q3 + (1.5*IQR),3);

        ArrayList<colData> modList = new ArrayList<>();
        int index = 0;
        for (double d : dataList) {
            if (d >= lowBound && d <= hiBound) {
                modList.add(new colData(timeList.get(index),d));
            }
            index+=1;
        }

//        int k = 0;
//        PrintStream dataStream = new PrintStream(new File("src/Stats/OutputData.txt"));
//        while (k < 20) {
//            ArrayList<colData> samples = new ArrayList<>();
//            Random rand = new Random();
//            for (int i = 0; i < 20; i++) {
//                samples.add(modList.get(rand.nextInt(modList.size())));
//            }
//            PrintStream dataStream = new PrintStream(new File("src/Stats/OutputData.txt"));
//            for (colData d : samples) {
//                dataStream.println(d.item);
//            }
//            dataStream.println();
//            k += 1;
//        }

        /*
        //Prints dates
        PrintStream dateStream = new PrintStream(new File("src/Stats/OutputDates.txt"));
        for (colData d : modList) {
            dateStream.println(d.date);
        }

        //Prints data
        PrintStream dataStream = new PrintStream(new File("src/Stats/OutputData.txt"));
        for (colData d : modList) {
            dataStream.println(d.item);
        }
        */
    }

    public static ArrayList<Double> readNumData(Scanner sc) {
        ArrayList<Double> list = new ArrayList<>();
        sc.nextLine();
        while (sc.hasNextDouble()) {
            list.add(sc.nextDouble());
            sc.nextLine();
        }
        return list;
    }

    public static ArrayList<String> readTimestamp(Scanner sc) {
        ArrayList<String> list = new ArrayList<>();
        sc.nextLine();
        while (sc.hasNextLine()) {
            list.add(sc.nextLine());
        }
        return list;
    }

    public static double average(ArrayList<Double> list) {
        double sum = 0;
        for (double d : list) {
            sum+=d;
        }
        sum = sum/list.size();
        return sum;
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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
