/ node class for algebraic trees

public class Node {
    Op op;
    Node lChild;
    Node rChild;

    // constructor
    public Node(Op o) {
        op = o;
        lChild = null;
        rChild = null;
    }

    // add random kids using the factory
    public void addRandomKids(NodeFactory n, int maxDepth, java.util.Random rand) {
        op.addRandomKids(this, n, maxDepth, rand);
    }

    // evaluate this node on given data
    public double eval(double[] data) {
        return op.eval(this, data);
    }

    // convert this node into a readable string
    public String toString() {
        return op.toString(this);
    }

   
    public void traverse(Collector c) {
        c.collect(this);
        if (this.lChild != null) this.lChild.traverse(c);
        if (this.rChild != null) this.rChild.traverse(c);
    }

    // leaf check: true if this node has no children
    public boolean isLeaf() {
        return this.lChild == null && this.rChild == null;
    }

    // swap this node's left child with trunk's left child , i want to call node gohan
    public void swapLeft(Node trunk) {
        Node temp = this.lChild;
        this.lChild = trunk.lChild;
        trunk.lChild = temp;
    }

    // swap this node's right child with trunk's right child, shout out dbz rip toriyam
    public void swapRight(Node trunk) {
        Node temp = this.rChild;
        this.rChild = trunk.rChild;
        trunk.rChild = temp;
    }

