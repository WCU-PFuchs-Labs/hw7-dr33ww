

import java.util.Random;

public class Node {
    Op operation;
    Node lChild;
    Node rChild;

    
    public Node(Op o) {
        this.operation = o;
        this.lChild = null;
        this.rChild = null;
    }

    // build children maxdepth
    
    public void addRandomKids(NodeFactory factory, int maxDepth, Random rand) {
        if (maxDepth <= 0) {
            return; // stop growing
        }

        if (operation instanceof Binop) {
            // binop needs two children
            lChild = new Node(factory.getOperator(rand));
            lChild.addRandomKids(factory, maxDepth - 1, rand);

            rChild = new Node(factory.getOperator(rand));
            rChild.addRandomKids(factory, maxDepth - 1, rand);

        } else if (operation instanceof Unop) {
            // unop needs one child 
            lChild = new Node(factory.getOperator(rand));
            lChild.addRandomKids(factory, maxDepth - 1, rand);
            // right remains null

        } else {
            // terminal stay leaves
        }
    }

    // evaluate this node on the given data
    public double eval(double[] data) {
        if (operation instanceof Binop) {
            double a = lChild.eval(data);
            double b = rChild.eval(data);
            return ((Binop) operation).eval(a, b);
        } else if (operation instanceof Unop) {
            double a = lChild.eval(data);
            return ((Unop) operation).eval(a);
        } else {
            
            return operation.eval(data);
        }
    }

    // conver node readable str
    public String toString() {
        if (operation instanceof Binop) {
            
            return "(" + lChild.toString() + " " + operation.toString() + " " + rChild.toString() + ")";
        } else if (operation instanceof Unop) {
            
            return operation.toString() + "(" + lChild.toString() + ")";
        } else {
            
            return operation.toString();
        }
    }



    // preorder traversal
    public void traverse(Collector c) {
        c.collect(this);
        if (this.lChild != null) this.lChild.traverse(c);
        if (this.rChild != null) this.rChild.traverse(c);
    }

    // check: if no children
    public boolean isLeaf() {
        return this.lChild == null && this.rChild == null;
    }

    // swap this node's left child with trunk's left child, what did they do to u gohan
    public void swapLeft(Node trunk) {
        Node temp = this.lChild;
        this.lChild = trunk.lChild;
        trunk.lChild = temp;
    }

    // swap this node's right child with trunk's right child, gohannn
    public void swapRight(Node trunk) {
        Node temp = this.rChild;
        this.rChild = trunk.rChild;
        trunk.rChild = temp;
    }

  
}

