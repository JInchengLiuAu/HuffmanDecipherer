package huffman;
import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.JPanel;

public class TreeControl extends JPanel 
{

    private HuffmanCode.Tree tree;
    private TreeView View = new TreeView();

    public TreeControl(HuffmanCode.Tree tree) 
    {
        this.tree = tree;
        setUI();
    }

    private void setUI() 
    {
        this.setLayout(new BorderLayout());
        add(View, BorderLayout.CENTER);
    }

    class TreeView extends JPanel 
    {

        private int radius = 20;
        private int vGap = 50;

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            if (tree == null) 
            {
                return;
            }
            if (tree.root != null)
            {
                displayTree(g, tree.root, getWidth() / 2, 30, getWidth() / 4);
            }
        }

        private void displayTree(Graphics g, HuffmanCode.Tree.Node root, int x, int y, int hGap) 
        {
            g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
            g.drawString((new StringBuilder(String.valueOf(root.weight))).toString(), x - 6, y + 4);
            if (root.left == null) 
            {
                g.drawString((new StringBuilder(String.valueOf(root.element))).toString(), x - 6, y + 34);
            }
            if (root.left != null) 
            {
                connectLeftChild(g, x - hGap, y + vGap, x, y);
                displayTree(g, root.left, x - hGap, y + vGap, hGap / 2);
            }
            if (root.right != null) 
            {
                connectRightChild(g, x + hGap, y + vGap, x, y);
                displayTree(g, root.right, x + hGap, y + vGap, hGap / 2);
            }
        }

        private void connectLeftChild(Graphics g, int x1, int y1, int x2, int y2) 
        {
            double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
            int x11 = (int) (x1 + radius * (x2 - x1) / d);
            int y11 = (int) (y1 - radius * vGap / d);
            int x21 = (int) (x2 - radius * (x2 - x1) / d);
            int y21 = (int) (y2 + radius * vGap / d);
            g.drawLine(x11, y11, x21, y21);
            g.drawString("0", (x11 + x21) / 2 - 5, (y11 + y21) / 2);
        }

        private void connectRightChild(Graphics g, int x1, int y1, int x2, int y2)
        {
            double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
            int x11 = (int) (x1 - radius * (x1 - x2) / d);
            int y11 = (int) (y1 - radius * vGap / d);
            int x21 = (int) (x2 + radius * (x1 - x2) / d);
            int y21 = (int) (y2 + radius * vGap / d);
            g.drawLine(x11, y11, x21, y21);
            g.drawString("1", (x11 + x21) / 2 + 5, (y11 + y21) / 2);
        }
    }
}
