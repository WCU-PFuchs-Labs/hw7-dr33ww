import java.util.Random;

public class Node {
    Op operation;
    Node lChild;
    Node rChild;

    public Node(Op op) {
        this.operation = op;
    }

    public boolean isLeaf() {
        return !(operation instanceof Binop);
    }

    // evaluate the tree recursively
    public double eval(double[] data) {
        if (operation instanceof Binop) {
            if (lChild == null || rChild == null) {
                throw new NullPointerException("Binop node missing child(ren)");
            }
            double leftVal = lChild.eval(data);
            double rightVal = rChild.eval(data);
            return ((Binop) operation).eval(leftVal, rightVal);
        } else if (operation instanceof Unop) {
            return ((Unop) operation).eval(data);
        } else if (operation instanceof Variable) {
            return ((Variable) operation).eval(data);
        } else if (operation instanceof Const) {
            return ((Const) operation).eval();
        } else {
            throw new IllegalStateException("Unknown operation type: " + operation.getClass());
        }
    }

    // add children recursively
    public void addRandomKids(NodeFactory factory, int maxDepth, Random rand) {
        if (maxDepth <= 0) return;

        if (operation instanceof Binop) {
            lChild = factory.getOperator(rand);
            rChild = factory.getOperator(rand);
            lChild.addRandomKids(factory, maxDepth - 1, rand);
            rChild.addRandomKids(factory, maxDepth - 1, rand);
        } else if (operation instanceof Unop) {
            lChild = factory.getOperator(rand);
            lChild.addRandomKids(factory, maxDepth - 1, rand);
        } else {
            // terminal node — no children
        }
    }

    // collect all Binop nodes for the Collector
    public void traverse(Collector c) {
        if (operation instanceof Binop) {
            c.collect(this);
        }
        if (lChild != null) lChild.traverse(c);
        if (rChild != null) rChild.traverse(c);
    }

    // swap this node entire subtree with another
    public void swapWith(Node other) {
        Op tempOp = this.operation;
        Node tempL = this.lChild;
        Node tempR = this.rChild;

        this.operation = other.operation;
        this.lChild = other.lChild;
        this.rChild = other.rChild;

        other.operation = tempOp;
        other.lChild = tempL;
        other.rChild = tempR;
    }

    // convert node to readable infix string
    public String toString() {
        if (operation instanceof Binop) {
            String left = (lChild != null) ? lChild.toString() : "?";
            String right = (rChild != null) ? rChild.toString() : "?";
            return "(" + left + " " + operation.toString() + " " + right + ")";
        } else if (operation instanceof Unop) {
            String inner = (lChild != null) ? lChild.toString() : "?";
            return operation.toString() + "(" + inner + ")";
        } else {
            return operation.toString();
        }
    }

    // string used for Collector test pattern
    public String patternString() {
        if (operation instanceof Binop) {
            String left = (lChild != null) ? lChild.patternString() : "?";
            String right = (rChild != null) ? rChild.patternString() : "?";
            return "(" + left + " " + operation.toString() + " " + right + ")";
        } else {
            return "?";
        }
    }
