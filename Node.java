import java.util.Random;

public class Node {
    private Op operation;
    private Node lChild;
    private Node rChild;

    public Node(Op op) {
        this.operation = op;
    }

    public Op getOperation() {
        return operation;
    }

    public Node getLeft() {
        return lChild;
    }

    public Node getRight() {
        return rChild;
    }

    public void setLeft(Node n) {
        this.lChild = n;
    }

    public void setRight(Node n) {
        this.rChild = n;
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
            return operation.eval(new double[]{ a, b });
        }

        
        if (lChild == null && rChild == null) {
            return operation.eval(data);
        }

        if (lChild != null && rChild == null) {
            double v = lChild.eval(data);
            return operation.eval(new double[]{ v });
        }

       
        if (lChild == null && rChild != null) {
            double v = rChild.eval(data);
            return operation.eval(new double[]{ v });
        }

        double v = lChild.eval(data);
        return operation.eval(new double[]{ v });
    }

   
    public void addRandomKids(NodeFactory factory, int maxDepth, Random rand) {
        if (maxDepth <= 0) {
            
            lChild = null;
            rChild = null;
            return;
        }

        if (operation instanceof Binop) {
            if (lChild == null) {
                lChild = factory.getOperator(rand);
            }
            if (rChild == null) {
                rChild = factory.getOperator(rand);
            }
            lChild.addRandomKids(factory, maxDepth - 1, rand);
            rChild.addRandomKids(factory, maxDepth - 1, rand);
        } else {
           
            if (lChild == null) {
                lChild = factory.getOperator(rand);
            }
            rChild = null;
            lChild.addRandomKids(factory, maxDepth - 1, rand);
        }
    }

    
    public void traverse(Collector c) {
        if (operation instanceof Binop) {
            
            c.collect(((Binop) operation).toString(
                (lChild == null ? "null" : lChild.toString()),
                (rChild == null ? "null" : rChild.toString())
            ));
        }
        if (lChild != null) lChild.traverse(c);
        if (rChild != null) rChild.traverse(c);
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

   
    public String toString() {
        if (operation instanceof Binop) {
            String leftS = (lChild == null ? "null" : lChild.toString());
            String rightS = (rChild == null ? "null" : rChild.toString());
            return ((Binop) operation).toString(leftS, rightS);
        }

       
        if (lChild != null && rChild == null) {
            return operation.toString() + "(" + lChild.toString() + ")";
        }

        
        if (lChild == null && rChild == null) {
            return operation.toString();
        }

        
        String inner = (lChild != null ? lChild.toString()
                    : (rChild != null ? rChild.toString() : ""));
        return operation.toString() + "(" + inner + ")";
    }
}
