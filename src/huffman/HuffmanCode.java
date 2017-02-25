package huffman;
import java.io.*;
import javax.swing.JOptionPane;

public class HuffmanCode implements Serializable 
{
    public static void main(String[] args) throws IOException 
    {    	
        try       
        {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("hfmtree.dat"));    
            Tree tree = (Tree) input.readObject();
            input.close();
            ObjectInputStream inputText = new ObjectInputStream(new FileInputStream("tobetrans.dat"));
            String text = inputText.readUTF();                   
            inputText.close();
            String[] codes = getCode(tree.root);
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("codefile.dat"));
            output.writeUTF(encode(text, codes));
            output.close();
            JOptionPane.showMessageDialog(null, "Code Finished");
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


    public static String[] getCode(Tree.Node root) 
    {
        if (root == null)        
        {
            return null;
        }
        
        String[] codes = new String[2 * 128];
        assignCode(root, codes);
        return codes;
    }

    private static void assignCode(Tree.Node root, String[] codes) 
    {
        if (root.left != null)
        {
            root.left.code = root.code + "0";
            assignCode(root.left, codes);

            root.right.code = root.code + "1";
            assignCode(root.right, codes);
        } 
        else 
        {
            codes[(int) root.element] = root.code;
        }
    }


    public static Tree getHuffmanTree(int[] counts)
    {
        Heap<Tree> heap = new Heap<Tree>();
        for (int i = 0; i < counts.length; i++) 
        {
            if (counts[i] > 0) 
            {
                heap.add(new Tree(counts[i], (char) i));        
            }
        }
        while (heap.getSize() > 1) 
        {
            Tree t1 = heap.remove();             
            Tree t2 = heap.remove();            
            heap.add(new Tree(t1, t2));
        }
        return heap.remove();                   
    }

  
    public static int[] getCharacterFrequency(String text) 
    {
        int[] counts = new int[256];
        for (int i = 0; i < text.length(); i++)
        {
            counts[(int) text.charAt(i)]++;
        }
        return counts;
    }


    public static String encode(String text, String codes[])
    {
        String result = "";
        for (int i = 0; i < text.length(); i++)
        {
            result = (new StringBuilder(String.valueOf(result))).append(codes[text.charAt(i)]).toString();
        }

        return result;
    }

  
    public static class Tree implements Comparable<Tree>, Serializable 
    {

        Node root;

        public Tree(Tree t1, Tree t2) 
        {
            root = new Node();
            root.left = t1.root;
            root.right = t2.root;
            root.weight = t1.root.weight + t2.root.weight;
        }

        public Tree(int weight, char element) 
        {
            root = new Node(weight, element);
        }

        @Override
        public int compareTo(Tree o) 
        {
            if (root.weight < o.root.weight) 
            {
                return 1;
            } 
            else if (root.weight == o.root.weight) 
            {
                return 0;
            } 
            else 
            {
                return -1;
            }
        }

        public class Node implements Serializable 
        {

            char element;                  
            int weight;                   
            Node left;                     
            Node right;                   
            String code = "";              

            public Node() 
            {
            }

            public Node(int weight, char element)
            {
                this.weight = weight;
                this.element = element;
            }
        }
    }
}
