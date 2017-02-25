package GUI;
import huffman.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainWindow extends JFrame 
{

    private JButton jbtInitialization = new JButton("I:Init");
    private JButton jbtCoding = new JButton("C:Code");
    private JButton jbtDecoding = new JButton("D:Decode");
    private JButton jbtPrint = new JButton("P:Print code file");
    private JButton jbtTreePrinting = new JButton("T:Print Huffman Tree");

    public MainWindow() 
    {
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(5, 1));
        panel1.add(jbtInitialization);
        panel1.add(jbtCoding);
        panel1.add(jbtDecoding);
        panel1.add(jbtPrint);
        panel1.add(jbtTreePrinting);
        jbtInitialization.addActionListener(new InitializationListener());
        jbtCoding.addActionListener(new CodingListener());
        jbtDecoding.addActionListener(new DecodingListener());
        jbtPrint.addActionListener(new PrintListener());
        jbtTreePrinting.addActionListener(new TreePrintingListener());
        add(panel1, BorderLayout.WEST);
    }

    class InitializationListener implements ActionListener, Serializable 
    {

        @Override
        public void actionPerformed(ActionEvent e) throws NullPointerException 
        {
            String text = JOptionPane.showInputDialog(null, "Enter a text:", "Initialization", JOptionPane.QUESTION_MESSAGE);
            if (text == null) 
            {
                return;
            }
            while (text.isEmpty()) 
            {
                JOptionPane.showMessageDialog(null, "Content is empty，please input.");
                text = JOptionPane.showInputDialog(null, "Enter a text:", "Initialization", JOptionPane.QUESTION_MESSAGE);
                if (text == null)
                {
                    return;
                }
            }
            try 
            {
                int[] counts = HuffmanCode.getCharacterFrequency(text);
                HuffmanCode.Tree tree = HuffmanCode.getHuffmanTree(counts);
                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("hfmtree.dat"));
                output.writeObject(tree);
                output.close();
                ObjectOutputStream outputText = new ObjectOutputStream(new FileOutputStream("tobetrans.dat"));
                outputText.writeUTF(text);
                outputText.close();
            } 
            catch (IOException ex)
            {
                JOptionPane.showMessageDialog(null, "error!");
            }
        }
    }


    class CodingListener implements ActionListener 
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            try 
            {
                HuffmanCode.main(null);
            } 
            catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(null, "error!");
            }
        }
    }

    class DecodingListener implements ActionListener 
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            try
            {
                Decoding.main(null);
            } 
            catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(null, "error!");
            } 
            catch (ClassNotFoundException ex)
            {
                JOptionPane.showMessageDialog(null, "Can not find Huffman Tree.");
            }
        }
    }

    class PrintListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            CodePrint.main(null);
        }
    }

    class TreePrintingListener implements ActionListener 
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                DisplayHuffmanTree.main(null);
            } 
            catch (IOException ex)
            {
                JOptionPane.showMessageDialog(null, "error!");
            }
        }
    }

    public static void main(String[] args) 
    {
        MainWindow frame = new MainWindow();
        frame.setTitle("Huffman Code Machine");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
