package ExpressionTree;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

public class TreeArea extends JPanel {
    private NodeLevel[] nodes;
    private int x;

    public TreeArea() {
    }

    public void drawTree(ExpressionTree tree) {
        if(tree == null) {
            this.nodes = null;
        } else {
            this.x = 0;
            this.nodes = new NodeLevel[(int)Math.pow(2.0D, (double)ViewTree.WINDOWSIZE)];
            this.drawInOrder(tree.root(), 0);
        }

        this.repaint();
    }

    private void drawInOrder(Position position, int level) {
        if(position.leftChild() != null) {
            this.drawInOrder(position.leftChild(), level + 1);
        }

        this.nodes[this.x] = new NodeLevel(position, level);
        ++this.x;
        if(position.rightChild() != null) {
            this.drawInOrder(position.rightChild(), level + 1);
        }

    }

    public void paintComponent(Graphics g) {
        Object obj = null;
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        if(g instanceof Graphics2D) {
            Graphics2D l = (Graphics2D)g;
            l.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        }

        super.paintComponent(g);
        if(this.nodes != null) {
            g.setColor(Color.red);

            int var10;
            for(var10 = 0; var10 < this.nodes.length; ++var10) {
                Position rectangle2d;
                if(this.nodes[var10] != null && (rectangle2d = this.nodes[var10].node.parent()) != null) {
                    boolean k = false;

                    for(int parentIdx = 0; parentIdx < this.nodes.length && !k; ++parentIdx) {
                        if(this.nodes[parentIdx] != null && this.nodes[parentIdx].node == rectangle2d) {
                            k = true;
                            g.drawLine(25 * var10 + 12, 30 * this.nodes[var10].level + 12, 25 * parentIdx + 12, 30 * this.nodes[parentIdx].level + 12);
                        }
                    }
                }
            }

            for(var10 = 0; var10 < this.nodes.length; ++var10) {
                if(this.nodes[var10] != null) {
                    g.setColor(Color.cyan);
                    g.fillOval(25 * var10, 30 * this.nodes[var10].level, 24, 24);
                    g.setColor(Color.black);
                    Rectangle2D var11 = g.getFontMetrics().getStringBounds(this.nodes[var10].node.element().toString(), g);
                    int var12 = (int)var11.getWidth() / 2;
                    g.drawString(this.nodes[var10].node.element().toString(), 25 * var10 + 12 - var12, 30 * this.nodes[var10].level + 17);
                }
            }
        }

    }
}
