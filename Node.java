


public class Node {
    Op op;
    Node lChild;
    Node rChild;

    
    public Node(Op o) {
        op = o;
        lChild = null;
        rChild = null;
    }

    // add random kids using the factory
    public void addRandomKids(NodeFactory n, int maxDepth, java.util.Random rand) {
        op.addRandomKids(this, n, maxDepth, rand);
    }

    // eval node given data
    public double eval(double[] data) {
       
        return op.eval(this, data);
    }

    // turn node into readable str
    public String toString() {
        
        return op.toString(this);
    }



   
    public void traverse(Collector c) {
        c.collect(this);
        if (this.lChild != null) this.lChild.traverse(c);
        if (this.rChild != null) this.rChild.traverse(c);
    }

    // true if node has no children, who is gohan
    public boolean isLeaf() {
        return this.lChild == null && this.rChild == null;
    }

    // swap this node's left child with trunk's left child, what did they do to u gohan
    public void swapLeft(Node trunk) {
        Node temp = this.lChild;
        this.lChild = trunk.lChild;
        trunk.lChild = temp;
    }

    // swap this node's right child with trunk's right child, gohannnn
    public void swapRight(Node trunk) {
        Node temp = this.rChild;
        this.rChild = trunk.rChild;
        trunk.rChild = temp;
    }

    
}
