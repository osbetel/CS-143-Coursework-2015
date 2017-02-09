package ExpressionTree;

import java.util.EmptyStackException;
import java.util.Stack;
/**
 * @author ADKN
 * @version 06 Mar 2016, 10:17 PM
 */

/**
 * ExpressionTree Class. Takes a String of an artihmetic expression and produces a binary tree, each Node
 * holding a single piece of that expression. Follows the Order of Operations (PEMDAS) with or without
 * parenthesis separation; supports integers but not floating point numbers, and accepts any of the five
 * operations, + - / * ^, for addition, subtraction, division, multiplication, and exponentiation.
 */
public class ExpressionTree {
    private Node root;
    //These next 3 variables are used in producing a String[] of a postfix version of the input String.
    //Used to create the tree itself.
    private Stack<String> operators;
    private String[] input;
    private String output = "";

    /**
     * Constructor for an ExpressionTree. Takes an arithmetic expression as a parameter. Checks for expression
     * validity; eg: "(2+4)" is a legal expression. The tree is constructed using a Stack, with nodes & links.
     *
     * @param s An arithmetic expression as a String.
     * @throws InvalidExpressionException Whenever an unacceptable character is found, or when there is an issue
     *                                    with the Stack being empty (if there are two operators in a row, eg "1++2").
     *                                    Also throws an InvalidExpressionException if there are an uneven number of
     *                                    parentheses.
     */
    public ExpressionTree(String s) throws InvalidExpressionException {
        String preTest = "+-/*^0123456789 ()";

        //Testing for legitimate characters. ie: no letters
        for (String str : s.split("")) {
            if (!preTest.contains(str)) {
                throw new InvalidExpressionException("The input String has an invalid character: " + str);
            }
        }

        int countParen = 0;
        for (String str : s.split("")) {
            if (str.equals("(")) {countParen++;}
            if (str.equals(")")) {countParen--;}
        }
        if (countParen != 0) {throw new InvalidExpressionException("There are an uneven number of parentheses.");}

        operators = new Stack<>();
        inputStringToArray(s);
        output = "";
        output = convert();

        Stack<Node> tree = new Stack<>();   //The last node in the stack will be the root of the tree
        for (String str : output.split(" ")) {            //This is why we needed the postfix expression as an array
            if (!"+-/*^".contains(str)) {     //If not an op; if it is a number
                tree.push(new Node(str));
            } else {

                try {
                    //Popping nodes and making a new on for the current op
                    Node rootNode = new Node(str);      //If it is an operator, we pop the last two nodes
                    Node rightNode = tree.pop();        //And join them with the current op as the parent
                    Node leftNode = tree.pop();         //If you have "1++2" the Stack comes up empty here. Throws Exception below.

                    //Joining nodes here
                    rightNode.setParent(rootNode);
                    leftNode.setParent(rootNode);
                    rootNode.setRightChild(rightNode);
                    rootNode.setLeftChild(leftNode);

                    //Place the current node back on the stack with the other two joined to it
                    tree.push(rootNode);
                } catch (EmptyStackException err) {
                    throw new InvalidExpressionException("The input String is incorrect and may have too many operators.");
                }
                //Set the root as the node on top of the stack
                root = tree.peek();
            }
            if (!tree.isEmpty()) {
                root = tree.peek();
            }
        }
    }

    /**
     * Method to retrieve the root node of the tree.
     *
     * @return Returns the root as a node object.
     */
    public Node root() {
        return root;
    }

    /**
     * Calculate the value of the expression. Calculation is performed by retrieving the root,
     * and generating a postfix expression from that root. THe final value is then calculated using a stack.
     *
     * @return Returns the integer value of the tree.
     */
    public int value() {
        Stack<String> nums = new Stack<>();     //Numbers will be pushed here
        String[] postfix = getPostfix(root).split(" "); //Gets the postfix of the tree's expression
        //Then splits it into an array at every whitespace
        for (String s : postfix) {
            if (!"+-/*^".contains(s)) {     //If it is not an operator, ie: it's a number, push to the stack
                nums.push(s);
            } else {                                        //We've hit an operator
                int left = Integer.parseInt(nums.pop());    //Pop the last two numbers
                int right = Integer.parseInt(nums.pop());
                switch (s) {                                //Perform operation and put result back on stack
                    case "-":
                        //The "" empty String is needed so the integer is pushed as a String.
                        nums.push((right - left) + "");
                        break;
                    case "+":
                        nums.push((right + left) + "");
                        break;
                    case "*":
                        nums.push((right * left) + "");
                        break;
                    case "/":
                        nums.push((right / left) + "");
                        break;
                    case "^":
                        nums.push((int) Math.pow(right, left) + "");
                }
            }
        }
        return Integer.parseInt(nums.pop());        //Return last num on stack; will be the result.
    }

    /**
     * Method to retrieve the tree's expression in prefix notation.
     *
     * @return Returns the expression in prefix notation as a String
     */
    public String prefix() {
        return getPrefix(root).trim();
    }

    /**
     * Method to retrieve the tree's expression in infix notation.
     *
     * @return Returns the expression in infix notation as a String
     */
    public String infix() {
        return getInfix(root).trim();
    }

    /**
     * Method to retrieve the tree's expression in postfix notation.
     *
     * @return Returns the expression in postfix notation as a String
     */
    public String postfix() {
        return getPostfix(root).trim();
    }

    /**
     * Helper method to prefix(). Recursively assembles the expression in prefix notation.
     *
     * @param r The root of the tree.
     * @return Returns the fully assembled prefix String.
     */
    private String getPrefix(Node r) {
        String output = "";
        if (r == null) {        //Base case, can't return a null String or it'll have a NullPointerEx
            return "";          //So we return blank String
        } else {
            output += r.element() + " ";            //Root comes first in prefix
            output += getPrefix(r.leftChild());     //Followed by the left subtree
            output += getPrefix(r.rightChild());    //And finally the right subtree
        }
        return output;
    }

    /**
     * Helper method to infix(). Recursively assembles the expression in infix notation.
     *
     * @param r The root of the tree.
     * @return Returns the fully assembled infix String.
     */
    private String getInfix(Node r) {
        String output = "";
        if (r == null) {
            return "";
        } else {
            output += getInfix(r.leftChild());      //Left subtree comes first in infix
            output += r.element() + " ";            //Followed by the root
            output += getInfix(r.rightChild());     //And finally the right subtree
        }
        return output;
    }

    /**
     * Helper method to postfix(). Recursively assembles the expression in postfix notation.
     *
     * @param r The root of the tree.
     * @return Returns the fully assembled postfix String.
     */
    private String getPostfix(Node r) {
        String output = "";
        if (r == null) {
            return "";
        } else {
            output += getPostfix(r.leftChild());    //Left subtree comes first in postfix
            output += getPostfix(r.rightChild());   //followed by the right subtree
            output += r.element() + " ";            //And finally the root.
        }
        return output;
    }

    /**
     * toString() Override. Returns the output of the postfix String.
     *
     * @return The postfix String from the input string.
     */
    @Override
    public String toString() {
        return output;
    }

    /**
     * Primary method for converting an infix expression to a postfix expression. Calls other helper methods.
     * @return Returns an output string from an arithmetic expression as an input string. "(2+4)" ==> "2 4 +"
     */
    private String convert() {
        for (String s : input) {
            switch (s) {
                case "^":       //Yes it supports exponents! And the PEMDAS rules too, even without parentheses.
                    foundOperator(s, 3);
                    break;
                case "/":
                case "*":
                    foundOperator(s, 2);
                    break;

                case "+":
                case "-":
                    foundOperator(s, 1);
                    break;

                case "(":
                    operators.push(s);  //Pushes the open parenthesis onto the stack.
                    break;

                case ")":   //If a closing parenthesis is found, will pop operators until open parenthesis is found or stack empty
                    closeParen();
                    break;

                default:    //Number; adds to output string
                    output = output + s + " ";
                    break;
            }
        }
        //When we've come to the end of the input String[], clear out the operator stack by popping
        while (!operators.isEmpty()) {
            output = output + operators.pop() + " ";
        }
        return output;
    }

    /**
     * Helper method. Called whenever an operator is found while reading the input. Manages the operator
     * stack and adds to the output string accordingly.
     *
     * @param op         The operator, + - / *, as a String.
     * @param precedence An integer representing the precedence of the operator using PEMDAS.
     */
    private void foundOperator(String op, int precedence) {
        while (!operators.isEmpty()) {
            String popped = operators.pop();
            if (popped.equals("(")) {   //If it's an open paren, put it back.
                operators.push(popped);
                break;
            } else {  //Otherwise, determine precedence of input op and popped op.
                int stackOpPrecedence;

                if (popped.equals("+") || popped.equals("-")) {
                    stackOpPrecedence = 1;
                } else if (popped.equals("*") || popped.equals("/")) {
                    stackOpPrecedence = 2;
                } else {
                    stackOpPrecedence = 3;
                }

                if (stackOpPrecedence < precedence) {
                    operators.push(popped);
                    break;
                } else {
                    output = output + popped + " ";
                }
            }
        }
        operators.push(op);
    }

    /**
     * Helper method. Called whenever a closing parenthesis is found while reading the input.
     * Modifies output and operator stack accordingly.
     */
    private void closeParen() {
        //Continues to pop and add operators to the output string until another open paren is reached or the stack is empty.
        while (!operators.isEmpty()) {
            String nextOp = operators.pop();
            if (nextOp.equals("(")) {
                break;
            } else {
                output = output + nextOp + " ";
            }
        }
    }

    /**
     * Turns the input String to a String[]. Preserves numbers with 2+ digits. Called by constructor.
     * This method was required otherwise input would be read one character at a time.
     * Eg: "23+4" would become "2 3 4 +" instead of the proper "23 4 +"
     * @param data Input the arithmetic expression as a String.
     * @return Returns an array of the arithmetic expression.
     */
    private void inputStringToArray(String data) throws InvalidExpressionException {

        //This is just a prior check for a whitespace between numbers, eg: "12 34"
        input = data.split("");
//        int idx = 0;
        for (int i = 0; i < input.length - 2; i++) {
            if ("0123456789".contains(input[i])) {
                //This would check for multiple whitespace, "1   2+4" but it doesn't seem to work.
//                idx = i;
//                while (input[idx].equals(" ") && idx < input.length - 2) {
//                    if ("0123456789".contains(input[idx])) {
//                        throw new InvalidExpressionException("There is whitespace in the middle of a number.");
//                    }
//                    idx++;
//                }
                if (input[i+1].equals(" ")) {
                    if ("0123456789".contains(input[i+2])) {
                        throw new InvalidExpressionException("There is whitespace in the middle of a number.");
                    }
                }
            }
        }

        data = data.replaceAll(" ", "");    //Remove all whitespace from input string
        input = data.split("");             //Creates a string array split at every char; eg "(2+4)" ==> [(, 2, +, 4, )]
        removeExtraParen();
        //This block changes the created array, [(, 2, 2, +, 4, )] into [(, 22, , +, 4]. It joins adjacent numbers into
        //their proper multi digit values and places them in a single array slot.
        String[] array = new String[input.length*2];
        String temp = "";
        int index = -1; //Start at an index of -1 because...*
        for (String s : input) {
            if (s != null && "0123456789".contains(s)) {
                temp += s;      //temp gets numbers added onto it until String s is not a number. temp is the multi digit number.
            } else if (s == null) {
                continue;       //Skip any null values left from removeExtraParen()
            } else {
                index++;    //*...This is required so that multi digit numbers do not "overwrite" things placed in prior loops.
                array[index] = temp;
                temp = "";
                index++;    //Simply shifting up by one to assign the current String which must be an operator or parenthesis.
                array[index] = s;
                /*
                We can't do array[index] and array[index+1].
                input: [2, 2, +, 5]
                array: [22, +, , 5]
                We run into index bounds issues with larger expressions. So,
                 */
            }
        }
        index++;
        if (!temp.equals(""))
            array[index] = temp;

        //This block simply counts all the filled values that are not "" or null. So that we can change the input array
        //to the exact size we want. Dynamic allocation.
        index = 0;
        for (String s : array) {
            if (s != null) {
                if (!s.equalsIgnoreCase("")) {
                    index++;
                }
            }
        }

        //This last block dynamically allocates to the input array, conserving any multi digit numbers.
        input = new String[index];
        index = 0;
        for (String s : array) {
            if (s != null) {
                if (!s.equalsIgnoreCase("")) {
                    input[index] = s;
                    index++;
                }
            }
        }
    }

    /**
     * Helper method. Called in inputStringToArray(), it removes any extraneous parentheses around the outsides
     * of the expression. Eg: "((22-8) + (5*4))" ==> "(22-8) + (5*4)"
     * This was required because the value() method computed incorrectly when there were unnecessary outer parentheses
     * since they would instantly be added to the stack and mess up computation.
     * Necessary outer parentheses like "(5*2) - (3/1)" are still kept though, as the algorithm checks for "((" + "))"
     */
    private void removeExtraParen() {
        //Start and end indexes of the input String, after it has been split into an array.
        int start = 0;
        int end = input.length - 1;

        while (input[start].equals("(") && input[end].equals(")")) {
            if (input[start + 1].equals("(") && input[end - 1].equals(")")) {   //Checks for "((" + "))" which are unnecessary
                input[start] = null;        //Sets those parentheses to null, null values are ignored in the calling method
                input[end] = null;
                start++;
                end--;
            } else {
                break;
            }
        }
    }
}
/**
 * ===================================
 * END
 * OF
 * EXPRESSION
 * TREE
 * CLASS
 * ===================================
 */

/**
 * Node Class; used to construct the individual nodes of the Expression Tree.
 * Implements the Position interface.
 */
class Node implements Position {
    private Node leftChild;
    private Node rightChild;
    private Node parent;
    private String item;        //item can either be a number (as a String) or an operator.

    /**
     * Constructor for a single Node.
     * @param s A String; either a number or an operator. Does not support negative numbers.
     */
    public Node(String s) {
        item = s;
        //The parent and child nodes are all left as null since the ExpressionTree
        //constructor takes care of that.
        parent = null;
        leftChild = null;
        rightChild = null;
    }

    /**
     * Fetches the item that this node is holding. Part of the Position interface.
     * @return Returns the item as an Object type. We know it is a String though.
     */
    public Object element() {
        return item;
    }

    /**
     * Fetches the parent node. Part of the Position interface.
     * @return Returns the parent node.
     */
    public Node parent() {
        return parent;
    }

    /**
     * Fetches the left child node. Part of the Position interface.
     * @return Returns the left child node.
     */
    public Node leftChild() {
        return leftChild;
    }

    /**
     * Fetches the right child node. Part of the Position interface.
     * @return Returns the right child node.
     */
    public Node rightChild() {
        return rightChild;
    }

    /**
     * Protected method. Allows the ExpressionTree constructor to set the parent node.
     * @param parent The node to be set to this node's parent.
     */
    protected void setParent(Node parent) {this.parent = parent;}

    /**
     * Protected method. Allows the ExpressionTree constructor to set the left child node.
     * @param left The node to be set to this node's left child.
     */
    protected void setLeftChild(Node left) {leftChild = left;}

    /**
     * Protected method. Allows the ExpressionTree constructor to set the right child node.
     * @param right The node to be set to this node's right child.
     */
    protected void setRightChild(Node right) {rightChild = right;}
}

/**
 * Position interface. The Node class implements this.
 */
interface Position {
    Object element();

    Position parent();

    Position leftChild();

    Position rightChild();
}
