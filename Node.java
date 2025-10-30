import java.util.Random;

public class Node {
    private Op operation;
    private Node lChild;
    private Node rChild;

    public Node(Op operation) {
        this.operation = operation;
    }

    
    public Node getLeft() { return lChild; }
    public Node getRight() { return rChild; }
    public Op getOperation() { return operation; }


    public double eval(double[] data) {
        if (operation instanceof Binop) {
            double a = lChild.eval(data);
            double b = rChild.eval(data);
            return ((Binop) operation).eval(a, b); 
        } else if (operation instanceof Unop) {
            double v = lChild.eval(data);
            return ((Unop) operation).eval(v); 
        } else {
            return operation.eval(data); 
        }
    }

   
    public void addRandomKids(NodeFactory factory, int maxDepth, Random rand) {
        if (maxDepth <= 0) return;

        if (operation instanceof Binop) {
            if (lChild == null) lChild = factory.getOperator(rand);
            if (rChild == null) rChild = factory.getOperator(rand);
            lChild.addRandomKids(factory, maxDepth - 1, rand);
            rChild.addRandomKids(factory, maxDepth - 1, rand);
        } else if (operation instanceof Unop) {
            if (lChild == null) lChild = factory.getOperator(rand);
            lChild.addRandomKids(factory, maxDepth - 1, rand);
        }
    }

    
    public void swapWith(Node other) {
        Op tmpOp = this.operation;
        Node tmpL = this.lChild;
        Node tmpR = this.rChild;

        this.operation = other.operation;
        this.lChild = other.lChild;
        this.rChild = other.rChild;

        other.operation = tmpOp;
        other.lChild = tmpL;
        other.rChild = tmpR;
    }

   
    public void collect(Collector c) {
        if (operation instanceof Binop) {
            c.collect(this);
        }
        if (lChild != null) lChild.collect(c);
        if (rChild != null) rChild.collect(c);
    }

    
    public String toString() {
        if (operation instanceof Binop) {
            String leftS = (lChild == null) ? "?" : lChild.toString();
            String rightS = (rChild == null) ? "?" : rChild.toString();
            return "(" + leftS + " " + operation.toString() + " " + rightS + ")";
        } else if (operation instanceof Unop) {
            return operation.toString() + "(" + (lChild == null ? "?" : lChild.toString()) + ")";
        } else {
            return operation.toString();
        }
    }
}
