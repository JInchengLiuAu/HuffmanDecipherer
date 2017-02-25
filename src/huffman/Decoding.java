package huffman;
import java.io.*;
import javax.swing.JOptionPane;

public class Decoding
{

    public static void main(String[] args) throws IOException, ClassNotFoundException 
    {
        try 
        {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("codefile.dat"));
            String bits = input.readUTF();
            input.close();
            String text = decode(bits);
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("textfile.dat"));
            output.writeUTF(text);
            output.close();
            JOptionPane.showMessageDialog(null, "Decode finished");
        } 
        catch (FileNotFoundException ex) 
        {
            JOptionPane.showMessageDialog(null, "Can not find code file, please init it");
        }
    }

    public static String decode(String bits) throws IOException, ClassNotFoundException 
    {
        String result = "";
        ObjectInputStream input = new ObjectInputStream(new FileInputStream("hfmtree.dat"));
        HuffmanCode.Tree tree = (HuffmanCode.Tree) input.readObject();
        input.close();
        HuffmanCode.Tree.Node p = tree.root;
        for (int i = 0; i < bits.length(); i++) 
        {
            if (bits.charAt(i) == '0') 
            {
                p = p.left;
            } 
            else if (bits.charAt(i) == '1') 
            {
                p = p.right;
            } 
            else
            {
                return null;
            }
            if (p.left == null) 
            {
                result = (new StringBuilder(String.valueOf(result))).append(p.element).toString();
                p = tree.root;
            }
        }

        return result;
    }
}
