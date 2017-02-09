/**
 * @author ADKN
 * @version 02 Mar 2016, 10:05 PM
 */
import java.util.*;

public class HorseRace {
    final static int DATASIZE = 400000;
    static int[] hold;  // holds the random data; used in all DS
    static int[] ar;
    static ArrayList<Integer> al;
    static LinkedList<Integer> ll;
    static TreeSet<Integer> ts;
    static TreeMap<Integer, Integer> tm;


    public static void main(String[] args) {
        buildDS();
//        searchDS(hold[DATASIZE- 1]);  // search for last value
         Random r = new Random();
         searchDS(hold[r.nextInt(DATASIZE)]);  // search for random value
        System.out.print("\n\nTime to fail:");
        searchDS(DATASIZE);
    }

    public static void generateData(){
        hold = new int[DATASIZE];
        for (int i = 0; i < DATASIZE; i++) {
            hold[i] = i;

        }
    }

    public static void buildDS(){
        System.out.print("Data size is: ");
        System.out.format("%,d", DATASIZE);
        System.out.println();
        System.out.println();
        generateData();
        System.out.println("Builds ------");

        // build array
        long st = System.nanoTime();
        ar = new int[DATASIZE];
        for (int i = 0; i < DATASIZE; i++) {
            ar[i] = hold[i];
        }
        System.out.println("Build Array: " + (System.nanoTime() - st));

        // build ArrayList
        st = System.nanoTime();
        al = new ArrayList<Integer>();
        for (int i = 0; i < DATASIZE; i++) {
            al.add(hold[i]);
        }
        System.out.println("Build ArrayList: " + (System.nanoTime() - st));

        // build LinkedList
        st = System.nanoTime();
        ll = new LinkedList<Integer>();
        for (int i = 0; i < DATASIZE; i++) {
            ll.add(hold[i]);
        }
        System.out.println("Build LinkedList: " + (System.nanoTime() - st));

        // build treeset
        st = System.nanoTime();
        ts = new TreeSet<Integer>();
        for (int i = 0; i < DATASIZE; i++) {
            ts.add(hold[i]);
        }
        System.out.println("Build TreeSet: " + (System.nanoTime() - st));

        // build treemap
        st = System.nanoTime();
        tm = new TreeMap<Integer, Integer>();
        for (int i = 0; i < DATASIZE; i++) {
            tm.put(hold[i], hold[i]);
        }
        System.out.println("Build TreeMap: " + (System.nanoTime() - st));
    }

    public static void searchDS(int find){
        System.out.println();
        System.out.println("Searches ------");

        // search array
        long st = System.nanoTime();
        for (int i = 0; i < DATASIZE; i++) {
            if (ar[i] == find) {
                System.out.println(find + " found  at "  + i);
                break;
            }
        }
        System.out.println("\tSearch time Array: "  + (System.nanoTime() - st));

        // search ArrayList
        st = System.nanoTime();
        System.out.println( find + " found at " + al.indexOf(find));
        System.out.println("\tSearch time ArrayList: " + (System.nanoTime() - st));

        // search LinkedList
        st = System.nanoTime();
        System.out.println( find + " found at " + ll.indexOf(find));
        System.out.println("\tSearch time LinkedList: " + (System.nanoTime() - st));

        // search TreeSet
        st = System.nanoTime();
        System.out.println( find + " found? " + ts.contains(find));
        System.out.println("\tSearch time TreeSet: " + (System.nanoTime() - st));

        // search TreeMap
        st = System.nanoTime();
        System.out.println( find + " found? " + tm.containsKey(find));
        System.out.println("\tSearch time TreeMap: " + (System.nanoTime() - st));
    }
}
