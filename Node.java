import java.util.Random;

public class Node {
    Op operation;
    Node lChild;
    Node rChild;

    public Node(Op op) {
        this.operation = op;
    }

 
    public Node getLeft()  { return lChild; }
    public Node getRight() { return rChild; }
    public Op getOperation() { return operation; }

    public boolean isLeaf() {
        return !(operation instanceof Binop);
    }

    // evaluate the tree recursively
    public double eval(double[] data) {
        if (operation instanceof Binop) {
            if (lChild == null || rChild == null) {
                throw new NullPointerException("Binop node missing child(ren)");
            }
            double a = lChild.eval(data);
            double b = rChild.eval(data);
            return ((Binop) operation).eval(a, b);
        } else if (operation instanceof Unop) {
            return ((Unop) operation).eval(data);
        } else if (operation instanceof Variable) {
            return ((Variable) operation).eval(data);
        } else if (operation instanceof Const) {
            return ((Const) operation).eval(data);
        } else {
            throw new IllegalStateException("Unknown operation type: " + operation.getClass());
        }
    }

    // build random children where appropriate
    public void addRandomKids(NodeFactory factory, int maxDepth, Random rand) {
        if (maxDepth <= 0) return;

        if (operation instanceof Binop) {
            // both children
            if (lChild == null) lChild = new Node(factory.getOperator(rand));
            if (rChild == null) rChild = new Node(factory.getOperator(rand));
            lChild.addRandomKids(factory, maxDepth - 1, rand);
            rChild.addRandomKids(factory, maxDepth - 1, rand);
        } else if (operation instanceof Unop) {
            // unary gets a single left child
            if (lChild == null) lChild = new Node(factory.getOperator(rand));
            lChild.addRandomKids(factory, maxDepth - 1, rand);
        } else {
            // terminal node - no children
        }
    }

    // traverse and let collector see binops
    public void traverse(Collector c) {
        if (operation instanceof Binop) {
            c.collect(this); 
        }
        if (lChild != null) lChild.traverse(c);
        if (rChild != null) rChild.traverse(c);
    }

    // Swap entire subtrees with another node
    public void swapWith(Node other) {
        Op opTmp = this.operation;
        Node lTmp = this.lChild;
        Node rTmp = this.rChild;

        this.operation = other.operation;
        this.lChild = other.lChild;
        this.rChild = other.rChild;

        other.operation = opTmp;
        other.lChild = lTmp;
        other.rChild = rTmp;
    }

    // readable infix
    public String toString() {
        if (operation instanceof Binop) {
            String left = (lChild != null) ? lChild.toString() : "?";
            String right = (rChild != null) ? rChild.toString() : "?";
            return "(" + left + " " + operation.toString() + " " + right + ")";
        } else if (operation instanceof Unop) {
            String inner = (lChild != null) ? lChild.toString() : "?";
            return operation.toString() + "(" + inner + ")";
        } else {
            // const or variable prints via its own toString
            return operation.toString();
        }
    }

    // pattern string used by collector tests for binops
    public String patternString() {
        if (operation instanceof Binop) {
            String left = (lChild != null) ? lChild.patternString() : "?";
            String right = (rChild != null) ? rChild.patternString() : "?";
            return "(" + left + " " + operation.toString() + " " + right + ")";
        } else {
            return "?";
        }
    }
}
