import java.util.Random;

public class Node {

    Op operation;
    Node lChild;
    Node rChild;

    public Node() {
        this.operation = null;
        this.lChild = null;
        this.rChild = null;
    }

    public Node(Op operation) {
        this.operation = operation;
        this.lChild = null;
        this.rChild = null;
    }

    public void setLeft(Node n) { this.lChild = n; }
    public void setRight(Node n) { this.rChild = n; }
    public Node getLeft() { return lChild; }
    public Node getRight() { return rChild; }
    public Op getOperation() { return operation; }
    public void setOperation(Op o) { this.operation = o; }

    // check if this node is a leaf
    public boolean isLeaf() {
        return lChild == null && rChild == null;
    }

    // swap this node's left child with another node's left child
    public void swapLeft(Node other) {
        Node temp = this.lChild;
        this.lChild = other.lChild;
        other.lChild = temp;
    }

    // swap this node's right child with another node's right child
    public void swapRight(Node other) {
        Node temp = this.rChild;
        this.rChild = other.rChild;
        other.rChild = temp;
    }

    // visit all nodes and collect only binops
    public void traverse(Collector c) {
        if (operation instanceof Binop) {
            c.collect(this);
        }
        if (lChild != null) lChild.traverse(c);
        if (rChild != null) rChild.traverse(c);
    }

    // evaluate the node recursively
    public double eval(double[] data) {
        if (operation == null) return 0.0;

        if (operation instanceof Const) {
            return ((Const) operation).eval(data);
        } else if (operation instanceof Variable) {
            return ((Variable) operation).eval(data);
        } else if (operation instanceof Unop) {
            return ((Unop) operation).eval(lChild.eval(data));
        } else if (operation instanceof Binop) {
            double leftVal = lChild.eval(data);
            double rightVal = rChild.eval(data);
            return ((Binop) operation).eval(leftVal, rightVal);
        } else {
            return 0.0;
        }
    }

    // build a readable string of the tree
    public String toString() {
        if (operation == null) return "?";
        if (operation instanceof Const || operation instanceof Variable) {
            return operation.toString();
        } else if (operation instanceof Unop) {
            return operation.toString() + "(" + lChild.toString() + ")";
        } else if (operation instanceof Binop) {
            return "(" + lChild.toString() + " " + operation.toString() + " " + rChild.toString() + ")";
        }
        return "?";
    }
}
