package BigInt;
/**
 * @author ADKN.
 * @version 19 Feb 2016.
 */
public class BigInt implements Comparable<BigInt> {
    //These 4 instance variables only matter for the "front" node.
    private boolean isFront;        //If true, prev is null, this is the first node. F
    private boolean isNegative;     //Whether the number as a whole is +/-. F
    private int size;               //# of digits in the entire "list" of BigInt nodes. F
    private int value;              //Only a single digit. Eg, if # = 1000, 4 nodes are required.

    BigInt next;                    //Next node
    BigInt prev;                    //Previous node

    /**
     * Constructor for a BigInt which takes a String as a parameter
     * @param val A number in String form. Trailing/leading whitespace is ignored, and +/-
     *            signs are accounted for.
     */
    public BigInt(String val) {
        isFront = true;
        prev = null;
        val = val.trim();   //Removes all trailing and leading white spaces,
        if (val.contains(" ")) {throw new BigIntFormatException("String input is invalid.");} //If there are whitespaces in the middle


        if (val.length() == 1) {  //If it's a single digit
            value = Integer.parseInt(val.substring(0, 1));
            isNegative = false;
            size = val.length();
        } else if (val.length() >= 2) {   //For cases where the String is "-1" or "+89"; will remove the +/- sign
            if (val.substring(0, 1).equals("-")) {
                isNegative = true;
                val = val.substring(1); //Removes the "-" so it can be ignored and numbers assigned to nodes
            } else if (val.substring(0, 1).equals("+")) {
                isNegative = false;
                val = val.substring(1); //Removes any potential "+" sign.
            }
        }
        size = val.length();            //Size assigned after any possible +/- sign is removed
        value = Integer.parseInt(val.substring(0, 1));   //This is the value of the first node

        //All following nodes are created and assigned values by this loop. Does not activate when
        //String val = "-1" or anything similar, since i will == val.length().
        try {
            BigInt b = this;
            for (int i = 1; i < val.length(); i++) {
                b.next = new BigInt(b, Integer.parseInt(val.substring(i, i + 1)));
                b = b.next;
            }
        } catch (Exception e) {
            throw new BigIntFormatException("String input is invalid.");
        }
    }
    /**
     * Private constructor created to aid in the creation of full BigInts.
     * @param prev The previous node is supplied
     * @param integer The value to assign to this node
     */
    private BigInt(BigInt prev, int integer) {      //Nodes that are not first or last
        isFront = false;    //Any node after the first node cannot be the front
        isNegative = prev.isNegative;   //sign of the BigInt is the same across the board
        /*
        Note that only the front node's isNegative matters. But I set all of them the same in the event that
        the front node must be erased. Eg: -100 + 1 = -99. verifyListData() ensures that the signs across nodes
         are always persistent.
         */

        size = prev.size;
        value = integer;

        this.prev = prev;
        this.next = null;   //Must be assigned
    }

    /**
     * Copy constructor. Clones a BigInt object with another BigInt as a parameter. Creates a depp copy.
     * @param bigInt A BigInt object to copy data from.
     */
    public BigInt(BigInt bigInt) {
        //Assumes the BigInt val passed in is the "front" node.

        this.isFront = true;
        this.isNegative = bigInt.isNegative;
        this.size = bigInt.size;
        this.value = bigInt.value;

        this.prev = null;
        //END CREATION OF FIRST NODE HERE

        //Creates all further nodes if there are more required.
        if (bigInt.size > 1) {
            BigInt copy = this;
            BigInt source = bigInt;
            for (int i = 1; i < bigInt.size; i++) {
                source = source.next;
                copy.next = new BigInt(copy, source.value);
                copy = copy.next;
            }
        }
    }

    /**
     * Constructor for a BigInt object that takes a long as a parameter.
     * @param longInt A long value.
     */
    public BigInt(long longInt) {
        isFront = true;
        prev = null;
        size = 19;  //19 digits is the maximum size of a long.
        value = 0;  //Set to a default 0 for now.

        //This structure sets the isNegative boolean.
        if (longInt < 0) {
            isNegative = true;
            longInt *= -1;
        } else {
            isNegative = false;
        }

        //This loop creates all 19 nodes for the maximum possible size of a long.
        BigInt b = this;
        for (int i = 0; i < 18; i++) {
            b.next = new BigInt(b, 0);
            b = b.next;
        }   //b is now the last node
        /*
        There are now 19 empty nodes with value 0; a long can only be 19 digits long.
        Using the %10 method of getting digits, we get the digits in reverse order.
        Eg: 12345 would give the digits as 54321. So we start assigning values from the last node.
         */
        while (longInt > 0) {
            b.value = (int) (longInt % 10); //Needs to be casted as an int; it's ok, no errors with modulus method.
            longInt /= 10;

            //Keep jumping back by one node each loop as long as we aren't at the front.
            if (b.prev != null) {
                b = b.prev;
            }
        }
        //Nodes will be something like 0000000000123456789. This doesn't matter as verifyListData() is run
        //Every time an action will be taken on a BigInt. It'll print out or add just fine.
    }

    /**
     * Method to get the integer value of the current node.
     * @return returns that node's integer value.
     */
    private int getValue() {
        return value;
    }

    /**
     * Method to get the number of digits/nodes in the BigInt.
     * @return Returns the number of digits/nodes in the big int.
     */
    private int getSize() {
        return size;
    }

    /**
     * Method to find out if the current reference var points to the front node in the BigInt.
     * @return Returns true/false
     */
    private boolean isFront() {
        return isFront;
    }

    /**
     * Method to find if the BigInt is negative or not.
     * @return Returns true/false
     */
    private boolean isNegative() {
        return isNegative;
    }

    /**
     * Determines whether this is > or == or < bigInt supplied in the parameter.
     * @param bigInt Another BigInt object to compare this to.
     * @return Returns 1 if this > bigInt, 0 if they are equal, and -1 if this < bigInt.
     */
    public int compareTo(BigInt bigInt) {
        boolean plusSign = (!this.isNegative && !bigInt.isNegative);    //Both are positive
        boolean minusSign = (this.isNegative && bigInt.isNegative);     //Both are negative

        if (this.equals(bigInt)) {  //If equal
            return 0;
        } else if (plusSign) {  //Both are +
            if (this.size > bigInt.size) {  //aaa > bb
                return 1;
            } else if (this.size < bigInt.size) { //aa < bbb
                return -1;
            } else {
                //loop through values, sizes the same. Determines which one is bigger if all the easy ways failed.
                BigInt a = this; BigInt b = bigInt;
                while (a != null || b != null) {
                    if (a.value > b.value) {
                        return 1;
                    }
                    if (a.value < b.value) {
                        return -1;
                    }
                    if (a.next == null && a.value == b.value) {
                        return 0;
                    }
                    a = a.next;
                    b = b.next;
                }
            }
        } else if (minusSign) { //Both are -
            if (this.size > bigInt.size) {  //-aaa < -bb
                return -1;
            } else if (this.size < bigInt.size) {   //-aa < -bbb
                return 1;
            } else {
                //loop through values, sizes the same. Determines which one is smaller if all the easy ways failed.
                BigInt a = this; BigInt b = bigInt;
                while (a != null || b != null) {
                    if (a.value > b.value) {
                        return -1;
                    }
                    if (a.value < b.value) {
                        return 1;
                    }
                    if (a.next == null && a.value == b.value) {
                        return 0;
                    }
                    a = a.next;
                    b = b.next;
                }
            }
        } else if (this.isNegative && !bigInt.isNegative) { //-a < b
            return -1;
        } else if (!this.isNegative && bigInt.isNegative) {//a > -b
            return 1;
        }
        return 1000;    //Final case. It should never ever reach this point anyway as I've covered all scenarios.
    }

    /**
     * Determines equality of two BigInts by testing to see if their final output
     * strings are equal.
     * @param o An object to test for equality. A BigInt in this case.
     * @return Returns true or false.
     */
    @Override
    public boolean equals(Object o) {
        return ((o instanceof BigInt) && (this.toString().equals(o.toString())));
    }

    /**
     * Takes the data of the BigInt and outputs the number it represents as a String.
     * @return Returns a String representing the BigInt's full integer value.
     */
    public String toString() {
        BigInt b = verifyListData(this);    //Verifies data consistency and also removes all extraneous 0's.
        String output = "";                 //Eg: an integer "00000123" would become "123."
        if (b.isNegative) {
            output = "-";
        }

        int loopSize = b.size;  //Can't use b.size in for loop because b is reassigned each loop. It causes issues.
        for (int i = 0; i < loopSize; i++) {
            output += b.value;      //Simply adding all the values to the end of the output string.
            b = b.next;
        }
        return output;
    }

    /**
     * Takes the data of the BigInt and outputs the number it represents as a String.
     * Reverses that String and makes it output backwards. Eg: "123" would be "321."
     * @return Returns a reversed String representing the BigInt's full integer value.
     */
    private String reverseToString() {
        BigInt b = verifyListData(this);    //Verifies data consistency and also removes all extraneous 0's.
        String output = "";
        if (b.isNegative) {
            output = "-";
        }

        //Only difference from toString() is that it loops to the last node before creating the final output string.
        int loopSize = b.size;
        for (int k = 0; k < loopSize - 1; k++) {
            b = b.next;
        }   //b is now the last node

        //Starting from the last node, adds all values to a string; comes out reversed.
        for (int i = 0; i < loopSize; i++) {
            output += b.value;
            b = b.prev;
        }
        return output;
    }

    /**
     * Multiplies the whole number by -1
     * @return Returns a new BigInt object equivalent to this * -1
     */
    public BigInt negate() {
        BigInt copy = new BigInt(this); //Deep copies the current BigInt
        BigInt firstNode = copy;        //Saves a reference to the front node

        if (copy.isNegative) {
            //Loops through every node and assigns their isNegative value to false.
            //This is done just for data consistency.
            for (int i = 0; i < firstNode.size; i++) {
                copy.isNegative = false;
                copy = copy.next;
            }
        } else {
            //Loops through every node and assigns their isNegative value to true.
            for (int i = 0; i < firstNode.size; i++) {
                copy.isNegative = true;
                copy = copy.next;
            }
        }
        return firstNode;
    }

    /**
     * Helper method to add();
     * Takes two input BigInts, one larger in size and another smaller in size, and adds them
     * recursively. Does account for scenarios where they are the same size and "carry-over" addition
     * @param bigNode The larger BigInt
     * @param lilNode The smaller BigInt
     * @return Returns an integer, offset, which represents the "carry-over" value from adding two numbers
     * that sum to >= 10.
     */
    private int recursiveAdd(BigInt bigNode, BigInt lilNode) {
        int offset;

        if (lilNode != null || bigNode != null) {  //As long as both BigInt lists have more nodes.
//            System.out.println("bigNode Value: " + bigNode.value);
//            System.out.println("lilNode Value: " + lilNode.value);
//            System.out.println();
            offset = recursiveAdd(bigNode.next, lilNode.next);  //Recursive call; the "carry-over" value is returned.
//            System.out.println("Returned Offset: " + offset);
//            System.out.println();

            //The digit after adding = the sum of both node values + any prior carry over value.
            offset = offset + (bigNode.value + lilNode.value);

            /*This decision structure defines the carry over value. 0 if the two node values sum to <10,
            Or 1 if the two node values sum to >=10.*/
            if (offset >= 10 && bigNode.isFront) {
                bigNode.value = offset;
                /*Can possibly set the front node to 10. eg 999 -> 1000. Would still be three nodes with values
                10, 0, 0. That's ok; that's what the verifyListData() is for. It will create the new front node.*/
            } else if (offset >= 10) {
                /*If it is not the front node that adds up to >=10,
                then we continue as normal with the carry-over rule.*/
                offset -= 10;
                bigNode.value = offset;
                offset = 1;
            } else {
                //When the nodes add up to <10, we simply set it as the new value. Carry-over = 0.
                bigNode.value = offset;
                offset = 0;
            }
        } else {
            return 0;
        }
        return offset;
    }

    /**
     * Helper method to add();
     * Takes two input BigInts, one larger in size and another smaller in size, and subtracts them
     * recursively. Does account for scenarios where they are the same size and "borrowing" subtraction.
     * @param bigNode The larger BigInt
     * @param lilNode The smaller BigInt
     * @return Returns an integer, offset, which represents the "borrowed" value from subtracting two numbers
     * that sum to < 0.
     */
    private int recursiveSubtract(BigInt bigNode, BigInt lilNode) {
        int offset;

        if (lilNode != null || bigNode != null) {  //As long as both BigInt lists have more nodes.
//            System.out.println("bigNode Value: " + bigNode.value);
//            System.out.println("lilNode Value: " + lilNode.value);
//            System.out.println();
            offset = recursiveSubtract(bigNode.next, lilNode.next); //Recursive call; the "borrowed" value is returned.
//            System.out.println("Returned Offset: " + offset);
//            System.out.println();
            //The digit after subtracting = the sum of the bigNode and "borrowed" value - lilNode value.
            offset = (offset + bigNode.value) - (lilNode.value);

            /*This decision structure defines the borrowed value. 0 if the two numbers subtract and are
            still above 0, or -1 if below 0.*/
//            if (offset < 0 && bigNode.isFront) {
//                 bigNode.value = offset;
//            } else
            if (offset < 0) {
                offset += 10;           //Returns the value after "borrowing" 10 from the prior node
                bigNode.value = offset; //Eg: 1 - 9 = -8, the value for that spot is 2 after borrowing 10.
                offset = -1;            //Will be returned recursively, accounts for borrowing.
            } else {
                bigNode.value = offset;
                offset = 0;
            }
        } else {
            return 0;
        }
        return offset;
    }

    /**
     * Primary add/subtract method (by adding negative numbers). Takes a single BigInt as a parameter
     * and adds the two BigInt linked lists.
     * @param bigInt The BigInt to be added to this BigInt
     * @return Returns a new BigInt of the sum of the two BigInts. Thus, needs to be captured
     * in a variable.
     */
    public BigInt add(BigInt bigInt) {
        BigInt verify1 = verifyListData(this);  //Serves to verify the data of the lists first
        BigInt verify2 = verifyListData(bigInt);

        BigInt copy;        //Because we can't modify the original BigInt
        BigInt addend1 = null;
        BigInt addend2 = null;
        int idx;            //idx serves as a count of the difference in sizes between two BigInts.
                            //Eg: 1234 and 22; the idx will be 2. More on this in a moment.

        if (verify2.size > verify1.size) {
            addend1 = new BigInt(verify2);
            addend2 = new BigInt(verify1);
            copy = addend1;
            idx = verify2.size - verify1.size;
        } else if (verify2.size < verify1.size) {
            addend1 = new BigInt(verify1);
            addend2 = new BigInt(verify2);
            copy = addend1;
            idx = verify1.size - verify2.size;
        }
        /* The two above decision structures will always assign addend1 to the number with more digits in it.
        Whichever one has a larger size will be addend1. copy is to save a reference to the first node of
        addend1. idx is the difference in sizes. */

        //If the two sizes are the same, the situation is trickier.
        else {
            BigInt a = verify1;
            BigInt b = verify2;
          /*
          When the sizes are the same, I assign addend1 to the BigInt with larger absolute value,
          ignoring negativity. If the numbers are mostly the same, eg: 12357 and 12347, it will loop through
          until one value is greater than another.
           */
            while (a != null || b != null) {
                if (a.value > b.value) {
                    //If it finds that a's value is higher, then addend1 gets this
                    //And addend2 gets bigInt.
                    addend1 = new BigInt(verify1);
                    addend2 = new BigInt(verify2);
                    break;
                }
                if (a.value < b.value) {
                    //If it finds that a's value is higher, then addend1 gets bigInt
                    //And addend2 gets this.
                    addend1 = new BigInt(verify2);
                    addend2 = new BigInt(verify1);
                    break;
                }
                if (a.next == null && a.value == b.value) {
                    //At this point, all the digits are the same, so which
                    //variable we assign to which object doesn't matter.
                    addend1 = new BigInt(verify1);
                    addend2 = new BigInt(verify2);
                    break;
                }
                a = a.next;
                b = b.next;
            }
            copy = addend1; //saving the first node to addend1, which will be returned at the end.
            idx = 0;    //Because the sizes are the same.
        }

        //This loop will take addend1 to the same "index" as addend2. Remember addend1 is the number with more digits.
        //Eg: if addend1 starts out as 1234 and addend2 is 12, then addend1 loops to the tens place for addition.
        for (int i = 0; i < idx; i++) {
            addend1 = addend1.next;
        }

        //If both BigInts are + or both are -, then the recursiveAdd() is used.
        if (verify2.isNegative && verify1.isNegative || !verify2.isNegative && !verify1.isNegative) {
//            System.out.println("recursiveAdd() Called.");
            int offset = recursiveAdd(addend1, addend2);    //RecursiveAdd returns the "carry-over" value.
//            System.out.println("Offset = " + offset);
            if (offset == 1) {                              //So that value needs to be accounted for here.
                addend1.prev.value += offset;
            }
            copy = verifyListData(copy);                    //Verifies validity of the BigInt.
            return copy;
        }
        //If only one BigInt is negative, then recursiveSubtract() is used.
        else {
//            System.out.println("recursiveSubtract() Called.");
            int offset = recursiveSubtract(addend1, addend2);   //Returns the "borrowed" value.
//            System.out.println("Offset = " + offset);
            if (offset == -1 && idx != 0) {                     //Accounts for borrowed value.
                /*
                If the numbers are the same size, ie: idx = 0, then no borrowed value needs to be accounted for.
                This is because up above, in a one negative, one positive situation, addend1 is assigned to the one
                with greater absolute value. The only error case is when the numbers are inverses like 100, -100.
                But then, this algorithm is never reached since we know their sum is 0.
                 */
                addend1.prev.value += offset;
            }
            copy = verifyListData(copy);    //Verifies data before returning.
            return copy;
        }
    }

    /**
     * Helper method to verifyListData()
     * Removes any "empty" nodes leading in the list. Eg: 0004428 -> 4428. Only used in verifyListData()
     * @return Returns a BigInt object with all the leading 0s removed.
     */
    private BigInt removeFrontZeros() {
        BigInt b = this;
        int breakLocation = 0;
        //Loops to a location until the leading 0s stop.
        while (b.value == 0 && b.next != null) {
            breakLocation += 1;
            b = b.next;
        }

        //b is now at the link in the "chain" we need to break to remove leading zeros
        b.prev = null;              //Only the front node will have a null node.prev
        b.isFront = true;           //Sets the new front node.
        b.size -= breakLocation;    //Subtracts the number of nodes that were broken off from the size.
        //The reason it must return is because we can't do, this = b at the end. Not even in a void method.
        return b;
    }

    /**
     * Helper method to verifyListData()
     * Verifies the length of the BigInt by counting through all the nodes.
     * Only used in verifyListData().
     * @param b A BigInt to very the size of.
     * @return Returns an integer of the number of nodes.
     */
    private int verifyLength(BigInt b) {
        if (b == null)
            return 0;
        else
            return 1 + verifyLength(b.next);
    }

    /**
     * Method for verifying the validity of all data in the list.
     * @param b A BigInt object to verify.
     * @return Returns a verified and modified for validity BigInt object.
     */
    private BigInt verifyListData(BigInt b) {
        BigInt cursor = b;
        b = b.removeFrontZeros();   //Removes any leading 0s

        //If any node has a value of 10, it is cut down to 0 and the carry-over addition rule is applied.
        while (cursor != null) {
            if (cursor.value >= 10 && !cursor.isFront){
                cursor.value -= 10;
                cursor.prev.value += 1;
            }
            cursor = cursor.next;
        }   //Now cursor == null

        cursor = b;
        //Brings cursor to the last node.
        while (cursor.next != null) {
            cursor = cursor.next;
        } //Now cursor == last node

        //If any node has a value of -1, then the subtraction borrow rule is applied.
        while (cursor != null) {
            if (cursor.value <= -1 && !cursor.isFront){
                cursor.value += 10;
                cursor.prev.value -= 1;
            }
            cursor = cursor.prev;
        }
        b = b.removeFrontZeros();   //In the event that the borrow subtraction changes the front node to 0.

        cursor = b;
        //If the front node == 10, this will create a new node and assign proper values.
        //Usually this happens when adding something like 999 and 1, which goes to 1000.
        //Will change three nodes to four. Eg: (10,0,0) -> (1,0,0,0)
        if (cursor.value >= 10 && cursor.isFront) {
            cursor.isFront = false;
            cursor.value -= 10;
            cursor.prev = new BigInt("1");
            cursor.prev.size = b.size + 1;
            cursor.prev.isNegative = cursor.isNegative;
            cursor.prev.next = cursor;
            b = cursor.prev;
        }

        cursor = b;
        //Sets the verified size to all of the nodes. Sets isNegative across all nodes.
        int verifiedSize = verifyLength(b);
        while (cursor != null) {
            cursor.size = verifiedSize;
            cursor.isNegative = b.isNegative;
            cursor.isFront = false;
            cursor = cursor.next;
        }
        b.isFront = true;

        if (b.size == 1 && b.value == 0) {b.isNegative = false;}    //So that 0 never prints as -0.
        return b;
    }

    public void print(BigInt b) {
        if (b == null)
            return;
        else
            System.out.print(b.value);
            print(b.next);
    }
}