import java.util.Random;

public class Node {
    Op operation;
    Node lChild;
    Node rChild;

    public Node(Op op) {
        this.operation = op;
    }

    public boolean isLeaf() {
        return lChild == null && rChild == null;
    }

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
            return ((Const) operation).eval();
        } else {
            return 0.0;
        }
    }

    public void collect(Collector c) {
        if (operation instanceof Binop) {
            String leftS = (lChild != null) ? lChild.toString() : "null";
            String rightS = (rChild != null) ? rChild.toString() : "null";
            String expr = "(" + leftS + " " + operation.toString() + " " + rightS + ")";
            c.collect(expr);
            if (lChild != null) lChild.collect(c);
            if (rChild != null) rChild.collect(c);
        } else {
            if (lChild != null) lChild.collect(c);
            if (rChild != null) rChild.collect(c);
        }
    }

    public String toString() {
        if (operation instanceof Binop) {
            String leftS = (lChild != null) ? lChild.toString() : "null";
            String rightS = (rChild != null) ? rChild.toString() : "null";
            return "(" + leftS + " " + operation.toString() + " " + rightS + ")";
        } else if (operation instanceof Unop) {
            String inner = (lChild != null) ? lChild.toString() : "?";
            return operation.toString() + "(" + inner + ")";
        } else if (operation instanceof Variable) {
            return operation.toString();
        } else if (operation instanceof Const) {
            return operation.toString();
        } else {
            return operation.toString();
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
}
