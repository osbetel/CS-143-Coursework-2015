package BigInt;
/**
 * @author
 * @version 24 Feb 2016.
 */
import junit.framework.TestCase;

/** Class to test BigInt.java
 *
 * SOME NOTES:
 * You need to have toString() implemented with the constructors in order to test them with this file
 *
 *
 * At a minimum you should have a test method for each public method. You may decide to decompose these even more
 * Think about testing if a method works correctly and also if it fails correctly
 */


public class BigIntTest extends TestCase {

    /**
     * Testing String constructor and toString
     */
    public void testStringConstructorAndToString() {

        // String arguments for the constructor
        String[] s = new String[]{
                "-1424",
                "  +14324",
                "    142432",
                "\n\t2402\n\t\r",
                "   0   ",
                "   28402437802743027340273402734027340273402734027340732407204702740274027430732073027430730"};

        // expected results from toString()
        String[] trim = {
                "-1424",
                "14324",
                "142432",
                "2402",
                "0",
                "28402437802743027340273402734027340273402734027340732407204702740274027430732073027430730"};

        for (int i = 0; i < s.length; i++) {
            BigInt b = new BigInt(s[i]);
            assertTrue("Problem with the string: " + s[i], b.toString().equals(trim[i]));
        }
    }

    /**
     * Testing copy constructor and equality
     */
    public void testCopyConstructorAndEquals() {
        String[] s = new String[]{
                "-1424",
                "14324",
                "142432",
                "2402",
                "0",
                "28402437802743027340273402734027340273402734027340732407204702740274027430732073027430730"};

        for (int i = 0; i < s.length; i++) {
            BigInt b = new BigInt(s[i]);
            BigInt c = new BigInt(b);
            BigInt offset = new BigInt("200");
            c = c.add(offset);  //This way we know that changing one does not change the other
                                //ie: it's a deep copy
            assertFalse("Copy constructor failed: " + s[i], (c.equals(b)));
        }
    }

    /**
     * Test long constructor
     */
    public void testLongConstructor() {
        Long[] l = new Long[]{
                637485958475364L,
                92387592L,
                -9283492837L,
                +93825729834L};

        for (int i = 0; i < l.length; i++) {
            BigInt b = new BigInt(l[i]);
            assertTrue("Long constructor failed: "+ l[i], Long.parseLong(b.toString()) == l[i]);
        }
    }

    /*
    Test the compare to method
     */
    public void testCompareTo() {
        String[] s = new String[]{
                "-1424",
                "14324",
                "142432",
                "2402",
                "263739648936"};

        Long[] l = new Long[]{
                637485958475364L,
                92387592L,
                -9283492837L,
                +93825729834L,
                263739648936L};

        Integer[] results = new Integer[] {-1,-1,1,-1,0};   //Results for the compareTo() method

        for (int i = 0; i < s.length; i++) {
            BigInt a = new BigInt(s[i]);
            BigInt b = new BigInt(l[i]);
            assertTrue("Issue with: " + s[i] + ", " + l[i] , a.compareTo(b) == results[i]);
        }
    }

    /*
    Tests the add method
     */
    public void testAdd() {
        Long[] l = new Long[]{  //First number
                637485958475364L,
                92387592L,
                -9283492837L,
                +9382L,
                263739648936L};
        String[] s = new String[] {     //Added to second number
                "412567",
                "+1110832",
                "9283492837",
                "-5",
                "-263738936"};

        String[] results = new String[] {   //The results for the add method
                "637485958887931",
                "93498424",
                "0",
                "9377",
                "263475910000"};

        for (int i = 0; i < s.length; i++) {
            BigInt a = new BigInt(s[i]);
            BigInt b = new BigInt(l[i]);
            a = a.add(b);
            System.out.println(a);
            assertTrue("Result incorrect: " + results[i], a.toString().equals(results[i]));
        }
    }
}
