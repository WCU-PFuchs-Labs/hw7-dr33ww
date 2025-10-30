public class Node {
    Op op;
    Node lChild;
    Node rChild;

    public Node(Op o) {
        op = o;
        lChild = null;
        rChild = null;
    }

    public void addRandomKids(NodeFactory n, int maxDepth, java.util.Random rand) {
        op.addRandomKids(this, n, maxDepth, rand);
    }

    public double eval(double[] data) {
        return op.eval(this, data);
    }

    public String toString() {
        return op.toString(this);
    }

   

    // vis all nodes in preorder
    public void traverse(Collector c) {
        c.collect(this);
        if (this.lChild != null) this.lChild.traverse(c);
        if (this.rChild != null) this.rChild.traverse(c);
    }

    // return true if this node has no children (a leaf)
    public boolean isLeaf() {
        boolean hasLeft = (this.lChild != null);
        boolean hasRight = (this.rChild != null);
        return !(hasLeft || hasRight);
    }

    // swap this nodes left child with trunks left child shout out dragon ball z
    public void swapLeft(Node trunk) {
        Node temp = this.lChild;
        this.lChild = trunk.lChild;
        trunk.lChild = temp;
    }

    // swap this node's right child with trunk's right child, i was so tempted to call this node gohan but
    public void swapRight(Node trunk) {
        Node temp = this.rChild;
        this.rChild = trunk.rChild;
        trunk.rChild = temp;
    }
}
