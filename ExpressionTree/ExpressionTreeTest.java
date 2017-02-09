package ExpressionTree;

import junit.framework.TestCase;

/**
 * @author ADKN
 * @version 13 Mar 2016, 10:41 PM
 */
public class ExpressionTreeTest extends TestCase {


    public void testTreeConstructorAndPreInPostFix() throws InvalidExpressionException {
        String[] input = {
                "1     + 8  - (4      /2)",
                "((22+  18) - (92 /8     ))",
                "(71+((3-43*6)+145)-5+(22 / (4-1)) + 14)^2",
                "     92 + 6-    14* 2 + 523   ",
                "(  (  5+(12 / 4)  )  - (3671+539))",
                "( (5+ 8)  *  ( 332 + 5 )    )",
                "((5   +  8)*3)",
                "(((7-(5*2)-6*(4+2))))"};

        String[] prefix = {
                "- + 1 8 / 4 2",
                "- + 22 18 / 92 8",
                "^ + + - + 71 + - 3 * 43 6 145 5 / 22 - 4 1 14 2",
                "+ - + 92 6 * 14 2 523",
                "- + 5 / 12 4 + 3671 539",
                "* + 5 8 + 332 5",
                "* + 5 8 3",
                "- - 7 * 5 2 * 6 + 4 2"};

        String[] infix = {
                "1 + 8 - 4 / 2",
                "22 + 18 - 92 / 8",
                "71 + 3 - 43 * 6 + 145 - 5 + 22 / 4 - 1 + 14 ^ 2",
                "92 + 6 - 14 * 2 + 523",
                "5 + 12 / 4 - 3671 + 539",
                "5 + 8 * 332 + 5",
                "5 + 8 * 3",
                "7 - 5 * 2 - 6 * 4 + 2"};

        String[] postfix = {
                "1 8 + 4 2 / -",
                "22 18 + 92 8 / -",
                "71 3 43 6 * - 145 + + 5 - 22 4 1 - / + 14 + 2 ^",
                "92 6 + 14 2 * - 523 +",
                "5 12 4 / + 3671 539 + -",
                "5 8 + 332 5 + *",
                "5 8 + 3 *",
                "7 5 2 * - 6 4 2 + * -"};

        for (int i = 0; i < input.length; i++) {
            ExpressionTree tree = new ExpressionTree(input[i]);
            assertTrue("Prefix; Problem with the input: " + input[i], tree.prefix().equals(prefix[i]));
            assertTrue("Infix; Problem with the input: " + input[i], tree.infix().equals(infix[i]));
            assertTrue("Postfix; Problem with the input: " + input[i], tree.postfix().equals(postfix[i]));
        }
    }

    public void testValueOutput() throws InvalidExpressionException {
        String[] input = {
                "1     + 8  - (4      /2)",
                "((22+  18) - (92 /8     ))",
                "(71+((3-43*6)+145)-5+(22 / (4-1)) + 14)^2",
                "     92 + 6-    14* 2 + 523   ",
                "(  (  5+(12 / 4)  )  - (3671+539))",
                "( (5+ 8)  *  ( 332 + 5 )    )",
                "((5   +  8)*3)",
                "(((7-(5*2)-6*(4+2))))"};

        String[] result = {
                "7",
                "29",
                "529",
                "593",
                "-4202",
                "4381",
                "39",
                "-39"};

        for (int i = 0; i < input.length; i++) {
            ExpressionTree tree = new ExpressionTree(input[i]);
            assertTrue("Postfix; Problem with the input: " + input[i], tree.value() == Integer.parseInt(result[i]));
        }
    }

    public void testRootIsCorrect() throws InvalidExpressionException {
        String[] input = {
                "1     + 8  - (4      /2)",
                "((22+  18) - (92 /8     ))",
                "(71+((3-43*6)+145)-5+(22 / (4-1)) + 14)^2",
                "     92 + 6-    14* 2 + 523   ",
                "(  (  5+(12 / 4)  )  - (3671+539))",
                "( (5+ 8)  *  ( 332 + 5 )    )",
                "((5   +  8)*3)",
                "(((7-(5*2)-6*(4+2))))"};

        String[] result = {
                "-",
                "-",
                "^",
                "+",
                "-",
                "*",
                "*",
                "-"};

        for (int i = 0; i < input.length; i++) {
            ExpressionTree tree = new ExpressionTree(input[i]);
            assertTrue("Root incorrect; Problem with the input: " + input[i], tree.root().element().equals(result[i]));
        }
    }

    public void testInvalidInputThrowsException() throws InvalidExpressionException {
        String[] input = {
                "1    K + 8  - (4      /2)",
                "((2K2+  18) - (92 /8     ))",
                "(71+((3-43*6)+145)-5+^(22 / (4-1)) + 14)^2",
                "     92 + 6-    14*+ 2 + 523   ",
                "(  (  5+(1j2 / 4)  )--  - (3671+539))",
                "( (5+ 8)  *  ( 332 + 5( )    )",
                "((5   +  8)*3P)",
                "(((7-(5*2)-6*(L4+2))))"};

        for (int i = 0; i < input.length; i++) {
            try {
                ExpressionTree tree = new ExpressionTree(input[i]);
            } catch (Throwable err) {
                assertTrue("Input is correct at: " + input[i],err instanceof InvalidExpressionException);
            }
        }
    }
}
