package ExpressionTree;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ViewTree extends JFrame {
    public static int WINDOWSIZE = 5;
    private NodePositions[] nodes;
    private ExpressionTree tree;
    private TreeArea displayPanel;
    private JTextField expressionField;
    private JLabel evaluation;

    public ViewTree(String s) {
        this();
        this.expressionField.setText(s);
        this.makeTree();
    }

    public ViewTree() {
        super("Tree Display Window");
        this.displayPanel = new TreeArea();
        this.expressionField = new JTextField(25);
        JPanel jpanel = new JPanel(new FlowLayout());
        jpanel.setSize(new Dimension(25 * (int)Math.pow(2.0D, (double)WINDOWSIZE), 10 * (int)Math.pow(2.0D, (double)WINDOWSIZE)));
        JButton jbutton = new JButton("Make Tree");
        JPanel jpanel1 = new JPanel(new FlowLayout());
        jpanel1.setSize(new Dimension(25 * (int)Math.pow(2.0D, (double)WINDOWSIZE), 120));
        this.evaluation = new JLabel("Your methods\' results will appear here");
        this.setDefaultCloseOperation(3);
        jpanel.add(this.expressionField);
        jpanel.add(jbutton);
        jpanel1.add(this.evaluation);
        this.expressionField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent keyevent) {
                if(keyevent.getKeyCode() == 10) {
                    ViewTree.this.makeTree();
                }

            }
        });
        jbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionevent) {
                ViewTree.this.makeTree();
            }
        });
        this.setResizable(true);
        this.getContentPane().add(jpanel, "North");
        this.getContentPane().add(this.displayPanel, "Center");
        this.getContentPane().add(jpanel1, "South");
        this.setSize(new Dimension(20 * (int)Math.pow(2.0D, (double)WINDOWSIZE), 10 * (int)Math.pow(2.0D, (double)WINDOWSIZE) + 120));
        this.setVisible(true);
    }

    private void makeTree() {
        try {
            this.tree = new ExpressionTree(this.expressionField.getText());
            this.displayPanel.drawTree(this.tree);
            this.evaluation.setText("<html><table><tr><td align=right><i><u>Value</u>: </i></td><td>" + this.tree.value() + "</td></tr>" + "<tr><td align=right><i><u>Prefix</u>: </i></td><td>" + this.tree.prefix() + "</td></tr>" + "<tr><td align=right><i><u>Infix</u>: </i></td><td>" + this.tree.infix() + "</td></tr>" + "<tr><td align=right><i><u>Postfix</u>: </i></td><td>" + this.tree.postfix() + "</td></tr>" + "</table></html>");
        } catch (Exception var2) {
            this.tree = null;
            this.nodes = null;
            this.displayPanel.drawTree((ExpressionTree)null);
            this.displayPanel.repaint();
            this.evaluation.setText("<html>" + var2 + "</html>");
        }

    }

    public static void main(String[] args) {
        if(args.length == 1) {
            int x = Integer.parseInt(args[0]);
            if(x <= 0) {
                throw new IllegalArgumentException("cannot start program with negative value");
            }

            WINDOWSIZE = x;
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ViewTree();
            }
        });
    }
}
