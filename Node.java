



import java.util.Random;

public class Node {
    Op operation;
    Node lChild;
    Node rChild;

    // node holding operator
    public Node(Op o) {
        this.operation = o;
        this.lChild = null;
        this.rChild = null;
    }

   
    public void addRandomKids(NodeFactory factory, int maxDepth, Random rand) {
        if (maxDepth <= 0) {
            return; // stop growing
        }

        if (operation instanceof Binop) {
            // need two children
            lChild = factory.getOperator(rand);
            if (lChild != null) {
                lChild.addRandomKids(factory, maxDepth - 1, rand);
            }

            rChild = factory.getOperator(rand);
            if (rChild != null) {
                rChild.addRandomKids(factory, maxDepth - 1, rand);
            }

        } else if (operation instanceof Unop) {
            // need one child 
            lChild = factory.getOperator(rand);
            if (lChild != null) {
                lChild.addRandomKids(factory, maxDepth - 1, rand);
            }
            // rChild stays null

        } else {
           
        }
    }

    // the operator will evaluate the nodes
    public double eval(double[] data) {
        
        return operation.eval(this, data);
    }

    // delegate printing to the operator
    public String toString() {
        
        return operation.toString(this);
    }

  

   
    public void traverse(Collector c) {
        c.collect(this);
        if (this.lChild != null) this.lChild.traverse(c);
        if (this.rChild != null) this.rChild.traverse(c);
    }

  //true if no children
    public boolean isLeaf() {
        return this.lChild == null && this.rChild == null;
    }

    // swap this node's left child with trunk's left child, gohann
    public void swapLeft(Node trunk) {
        Node temp = this.lChild;
        this.lChild = trunk.lChild;
        trunk.lChild = temp;
    }

    // swap this node's right child with trunk's right child, what did they do to u gohan
    public void swapRight(Node trunk) {
        Node temp = this.rChild;
        this.rChild = trunk.rChild;
        trunk.rChild = temp;
    }


}

