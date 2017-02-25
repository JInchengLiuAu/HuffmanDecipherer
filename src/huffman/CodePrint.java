package huffman;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.swing.*;

public class CodePrint extends JFrame 
{
    private static JTextArea jtaNumbers = new JTextArea();
    private static LinkedList list = new LinkedList();
    private JButton jtaReturn = new JButton("Back");
    private static CodePrint frame = new CodePrint();

    public CodePrint() 
    {
        JScrollPane jscrollPane = new JScrollPane(jtaNumbers);
        JPanel panel = new JPanel();
        panel.add(jtaReturn);
        add(jscrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        jtaReturn.addActionListener(new ReturnListener());
    }

    public static void main(String[] args) 
    {
        try 
        {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("codefile.dat"));
            String bits = input.readUTF();
            input.close();
            list.clear();
            for (int i = 0; i < bits.length(); i++) 
            {
                list.add(bits.charAt(i));
            }
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("codeprint.dat"));
            output.writeObject(list);
            output.close();
            display();
            frame.setTitle("Print code file");
            frame.setSize(375, 300);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } 
        catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, "Can not find code file, please code it");
        } 
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null, "error!");
        }
    }


    private static void display() 
    {
        int i = 1;
        jtaNumbers.setText(null);
        ListIterator iterator = list.listIterator();
        while (iterator.hasNext()) 
        {
            if (i == 50) 
            {
                jtaNumbers.append(iterator.next() + "\n");
                i = 1;
            } 
            else 
            {
                jtaNumbers.append(iterator.next() + "");
                i++;
            }

        }
    }

    class ReturnListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e)
        {
            frame.dispose();
        }
    }
}
