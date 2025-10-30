import java.util.Random;

public class Node {
    private Op operation;
    private Node lChild;
    private Node rChild;

    public Node(Op op) {
        this.operation = op;
    }

   
    public Node(Node n) {
        this.operation = n.operation;
        this.lChild = n.lChild;
        this.rChild = n.rChild;
    }

   
    public Op getOperation() { return operation; }
    public Node getLeft()     { return lChild;    }
    public Node getRight()    { return rChild;    }
    public void setLeft(Node n)  { this.lChild = n; }
    public void setRight(Node n) { this.rChild = n; }

    
    public double eval(double[] data) {
        if (operation instanceof Binop) {
            double a = (lChild != null) ? lChild.eval(data) : 0.0;
            double b = (rChild != null) ? rChild.eval(data) : 0.0;
            return ((Binop) operation).eval(new double[]{ a, b });
        } else if (operation instanceof Unop) {
            double v = (lChild != null) ? lChild.eval(data) : 0.0;
            return ((Unop) operation).eval(new double[]{ v });
        } else if (operation instanceof Const) {
            return ((Const) operation).eval(new double[0]);
        } else if (operation instanceof Variable) {
            return ((Variable) operation).eval(data);
        }
       
        return 0.0;
    }

   
    public void addRandomKids(NodeFactory factory, int maxDepth, Random rand) {
        if (maxDepth <= 0) return;

        if (operation instanceof Binop) {
            if (lChild == null) lChild = factory.getOperator(rand);
            if (rChild == null) rChild = factory.getOperator(rand);
            if (lChild != null) lChild.addRandomKids(factory, maxDepth - 1, rand);
            if (rChild != null) rChild.addRandomKids(factory, maxDepth - 1, rand);
        } else if (operation instanceof Unop) {
            if (lChild == null) lChild = factory.getOperator(rand);
            if (lChild != null) lChild.addRandomKids(factory, maxDepth - 1, rand);
        } else {
          
        }
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
            String leftS  = (lChild != null ? lChild.toString() : "null");
            String rightS = (rChild != null ? rChild.toString() : "null");
            return ((Binop) operation).toString(leftS, rightS);
        } else if (operation instanceof Unop) {
            String arg = (lChild != null ? lChild.toString() : "null");
            
            return operation.toString() + "(" + arg + ")";
        } else {
            return operation.toString();
        }
    }

    public Node deepCopy() {
        Node n = new Node(operation);
        if (lChild != null) n.lChild = lChild.deepCopy();
        if (rChild != null) n.rChild = rChild.deepCopy();
        return n;
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

