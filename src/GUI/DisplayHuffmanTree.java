package GUI;
import huffman.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DisplayHuffmanTree extends JFrame 
{

    private static JButton jtaReturn = new JButton("Back");
    static DisplayHuffmanTree frame = new DisplayHuffmanTree();

 
    public DisplayHuffmanTree()
    {
        jtaReturn.addActionListener(new ReturnListener());       
    }

    public static void main(String[] args) throws IOException 
    {
        try 
        {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("hfmtree.dat"));
            HuffmanCode.Tree tree = (HuffmanCode.Tree) input.readObject();
            input.close();
            JPanel panel = new JPanel();
            panel.add(jtaReturn);
            frame.setTitle("Print Huffman Tree");
            frame.add(new TreeControl(tree), BorderLayout.CENTER);
            frame.add(panel, BorderLayout.SOUTH);
            frame.setSize(500, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("treeprint.dat"));
            output.writeObject(new TreeControl(tree));
            output.close();
        }
        catch (ClassNotFoundException ex) 
        {
            JOptionPane.showMessageDialog(null, "Can not find Huffman Tree, Please init it.");
        } 
        catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "Can not find init file, Please init it.");
        }
    }

    class ReturnListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            frame.dispose();
        }
    }
}
