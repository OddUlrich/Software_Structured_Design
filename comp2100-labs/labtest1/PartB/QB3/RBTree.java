public class RBTree<T extends Comparable<T>> {
    public Node<T> root; // The root node of the tree


    // Check property 1
    private boolean leafChildRecurse(Node<T> cur) {
        if (cur.value != null) {
            return leafChildRecurse(cur.left) & leafChildRecurse(cur.right);
        } else {
            if (cur.colour == Colour.BLACK) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean testProp1() {
        // TODO: Implement this method
        // You are allowed to use any standard library
        // You may define additional helper methods to complete the task

        if (root != null && root.colour != Colour.BLACK) return false;

        return leafChildRecurse(root);
    }

    // Check property 2
    private boolean childRecurseForParentColour(Node<T> cur) {
        if (cur.value != null) {
            if (cur.colour == Colour.RED) {
                if (cur.parent == null || cur.parent.colour != Colour.BLACK) {
                    return false;
                }
            }

            return childRecurseForParentColour(cur.left) & childRecurseForParentColour(cur.right);

        } else {
            return true;
        }
    }

    public boolean testProp2() {
        // TODO: Implement this method
        // You are allowed to use any standard library
        // You may define additional helper methods to complete the task
        return childRecurseForParentColour(root);
    }
    
    /** 
     * Base class for node with link to the parent
     *
     * @param <T> data type
     */
    public class Node<T extends Comparable<T>> {
        Colour colour;       // Node colour
        T value;             // Node value
        Node<T> parent;      // Parent node
        Node<T> left, right;        // Children nodes
        
        public Node(T value) {
            this.value = value;
            this.colour = Colour.BLACK;
            this.parent = null;

            // Initialise children leaf nodes
            this.left = new Node<T>();
            this.right = new Node<T>();
            this.left.parent = this;
            this.right.parent = this;
        }

        // Leaf node
        public Node() {
            this.value = null;
            this.colour = Colour.BLACK;
        }

        public void setColour(boolean red) {
        	this.colour = red ? Colour.RED : Colour.BLACK;
        }
    }

    /**
     *  Initialize empty RBTree
     */
    public RBTree() {
        root = null;
    }

    /**
     * Add a new node into the tree with {@code root} node.
     * @param root Node<T> The root node of the tree where x is being inserted.
     * @param x Node<T> New node being inserted. 
     */
    private void insertRecurse(Node<T> root, Node<T> x){
            if (root.value.compareTo(x.value) > 0) {
                if (root.left.value == null) {
                    root.left = x;
                    x.parent = root;
                } else {
                    insertRecurse(root.left, x);
                }
            } else if (root.value.compareTo(x.value) < 0) {
                if (root.right.value == null) {
                    root.right = x;
                    x.parent = root;
                } else {
                    insertRecurse(root.right, x);
                }
            }
            // Do nothing if the tree already has a node with the same value.
    }

    /**
     * Insert node into RBTree. 
     * Note that for checking properties, we do not need to have a complete RB tree.
     * Here the insertion is the same as the binary tree.
     * 
     * @param x Node<T> The new node being inserted into the tree.
     */
    private void insert(Node<T> x) {
        // Insert node into tree
        if (root == null) {
            root = x;
        } else {
            insertRecurse(root, x);
        }
    }
    
    /**
     * Demo functions 
     * (Safely) insert a value into the tree
     * @param value T The value of the new node being inserted.
     */
    public void insert(T value) {
        Node<T> node = new Node<T>(value);
        if (node != null)
            insert(node);
    }

    /**
     *  Return the corresponding node of a value, if it exists in the tree
     * @param x Node<T> The root node of the tree we search for the value {@code v}
     * @param v Node<T> The node that we are looking for
     * @return
     */
    private Node<T> find(Node<T> x, T v) {
        if (x.value == null)
            return null;

        int cmp = v.compareTo(x.value);
        if (cmp < 0)
            return find(x.left, v);
        else if (cmp > 0)
            return find(x.right, v);
        else
            return x;
    }

    /**
     * Returns a node if the value of the node is {@code key}.
     * 
     * @param key T The value we are looking for
     * @return
     */
    public Node<T> search(T key) {
        return find(root, key);
    }

    public enum Colour {
        RED,
        BLACK;
    }

}