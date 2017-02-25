package huffman;

public class BinaryTree<E extends Comparable<E>>
        extends AbstractTree<E> 
{
    protected TreeNode<E> root;
    protected int size = 0;

    public BinaryTree() 
    {
    }

    public BinaryTree(E[] objects) 
    {
        for (int i = 0; i < objects.length; i++)
        {
            insert(objects[i]);
        }
    }

    @Override
    public boolean search(E e)
    {
        TreeNode<E> current = root; // Start from the root

        while (current != null) 
        {
            if (e.compareTo(current.element) < 0)
            {
                current = current.left;
            } 
            else if (e.compareTo(current.element) > 0) 
            {
                current = current.right;
            } 
            else // element matches current.element
            {
                return true; // Element is found
            }
        }

        return false;
    }

    @Override
    public boolean insert(E e) 
    {
        if (root == null) 
        {
            root = createNewNode(e); // Create a new root
        } 
        else 
        {
        	// Locate the parent node
        	TreeNode<E> parent = null;
            TreeNode<E> current = root;
            while (current != null) 
            {
                if (e.compareTo(current.element) < 0) 
                {
                    parent = current;
                    current = current.left;
                } 
                else if (e.compareTo(current.element) > 0) 
                {
                    parent = current;
                    current = current.right;
                } 
                else 
                {
                    return false; // Duplicate node not inserted
                }
            }
            
            // Create the new node and attach it to the parent node
            if (e.compareTo(parent.element) < 0)
            {
                parent.left = createNewNode(e);
            } 
            else
            {
                parent.right = createNewNode(e);
            }
            
        }

        size++;
        return true; // Element inserted
    }

    protected TreeNode<E> createNewNode(E e)
    {
        return new TreeNode<E>(e);
    }

    @Override
    public void inorder() 
    {
        inorder(root);
    }

    protected void inorder(TreeNode<E> root)
    {
        if (root == null) 
        {
            return;
        }
        inorder(root.left);
        System.out.print(root.element + " ");
        inorder(root.right);
    }

    @Override
    public void postorder() 
    {
        postorder(root);
    }

    protected void postorder(TreeNode<E> root)
    {
        if (root == null) 
        {
            return;
        }
        postorder(root.left);
        postorder(root.right);
        System.out.print(root.element + " ");
    }

    @Override
    public void preorder() 
    {
        preorder(root);
    }

    protected void preorder(TreeNode<E> root) 
    {
        if (root == null)
        {
            return;
        }
        System.out.print(root.element + " ");
        preorder(root.left);
        preorder(root.right);
    }

    @Override
    public int getSize() 
    {
        return size;
    }

    public TreeNode getRoot()
    {
        return root;
    }

    public java.util.ArrayList<TreeNode<E>> path(E e) 
    {
        java.util.ArrayList<TreeNode<E>> list =
                new java.util.ArrayList<TreeNode<E>>();
        TreeNode<E> current = root; // Start from the root

        while (current != null) 
        {
            list.add(current); // Add the node to the list
            if (e.compareTo(current.element) < 0) 
            {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) 
            {
                current = current.right;
            } 
            else 
            {
                break;
            }
        }

        return list; // Return an array of nodes
    }

    @Override
    public boolean delete(E e) 
    {
        // Locate the node to be deleted and also locate its parent node
        TreeNode<E> parent = null;
        TreeNode<E> current = root;
        while (current != null)
        {
            if (e.compareTo(current.element) < 0)
            {
                parent = current;
                current = current.left;
            } 
            else if (e.compareTo(current.element) > 0)
            {
                parent = current;
                current = current.right;
            }
            else 
            {
                break; // Element is in the tree pointed by current
            }
        }

        if (current == null)
        {
            return false; // Element is not in the tree
        }
        // Case 1: current has no left children
        if (current.left == null) 
        {
            // Connect the parent with the right child of the current node
            if (parent == null)
            {
                root = current.right;
            }
            else 
            {
                if (e.compareTo(parent.element) < 0) 
                {
                    parent.left = current.right;
                }
                else 
                {
                    parent.right = current.right;
                }
            }
        } 
        else
        {
            // Case 2: The current node has a left child
            // Locate the rightmost node in the left subtree of
            // the current node and also its parent
            TreeNode<E> parentOfRightMost = current;
            TreeNode<E> rightMost = current.left;

            while (rightMost.right != null) 
            {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right; // Keep going to the right
            }

            // Replace the element in current by the element in rightMost
            current.element = rightMost.element;

            // Eliminate rightmost node
            if (parentOfRightMost.right == rightMost) 
            {
                parentOfRightMost.right = rightMost.left;
            } 
            else // Special case: parentOfRightMost == current
            {
                parentOfRightMost.left = rightMost.left;
            }
        }

        size--;
        return true; // Element inserted
    }

    @Override
    public java.util.Iterator iterator() 
    {
        return inorderIterator();
    }

    public java.util.Iterator inorderIterator() 
    {
        return new InorderIterator();
    }

    // Inner class InorderIterator
    class InorderIterator implements java.util.Iterator
    {
        // Store the elements in a list

        private java.util.ArrayList<E> list =
                new java.util.ArrayList<E>();
        private int current = 0; // Point to the current element in list

        public InorderIterator()
        {
            inorder(); // Traverse binary tree and store elements in list
        }

        private void inorder() 
        {
            inorder(root);
        }

        private void inorder(TreeNode<E> root)
        {
            if (root == null)
            {
                return;
            }
            inorder(root.left);
            list.add(root.element);
            inorder(root.right);
        }

        @Override
        public boolean hasNext()
        {
            if (current < list.size()) 
            {
                return true;
            }

            return false;
        }

        @Override
        public Object next() 
        {
            return list.get(current++);
        }

        @Override
        public void remove() 
        {
            delete(list.get(current)); // Delete the current element
            list.clear(); // Clear the list
            inorder(); // Rebuild the list
        }
    }

    public void clear() 
    {
        root = null;
        size = 0;
    }
}
