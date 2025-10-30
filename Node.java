import java.util.Random;

public class Node {
    private final Op operation;
    private Node lChild;
    private Node rChild;

 
    public Node(Op op) {
        this.operation = op;
    }

    public Node(Op op, Node left) {
        this.operation = op;
        this.lChild = left;
    }

    public Node(Op op, Node left, Node right) {
        this.operation = op;
        this.lChild = left;
        this.rChild = right;
    }

    public Op getOperation() { return operation; }
    public Node getLeft()     { return lChild; }
    public Node getRight()    { return rChild; }
    public void setLeft(Node n)  { this.lChild = n; }
    public void setRight(Node n) { this.rChild = n; }


    public double eval(double[] data) {
        if (operation instanceof Const) {
            return ((Const) operation).eval(data);
        }
        if (operation instanceof Variable) {
            return ((Variable) operation).eval(data);
        }
        if (operation instanceof Unop) {
            double v = (lChild == null ? 0.0 : lChild.eval(data));
            return ((Unop) operation).eval(v);
        }
        if (operation instanceof Binop) {
            double a = (lChild == null ? 0.0 : lChild.eval(data));
            double b = (rChild == null ? 0.0 : rChild.eval(data));
            return ((Binop) operation).eval(a, b);
        }
 
        return 0.0;
    }



 
    @Override
    public String toString() {
        return toPlaceholderString();
    }

    public String toConcreteString() {
        if (operation instanceof Binop) {
            String leftS = (lChild == null) ? "null" : lChild.toConcreteString();
            String rightS = (rChild == null) ? "null" : rChild.toConcreteString();
            String sym = binopSymbol((Binop) operation);
            return "(" + leftS + " " + sym + " " + rightS + ")";
        } else if (operation instanceof Unop) {
            String inner = (lChild == null) ? "null" : lChild.toConcreteString();
            return operation.toString() + "(" + inner + ")";
        } else {
          
            return operation.toString();
        }
    }


    public String toPlaceholderString() {
        if (operation instanceof Binop) {
            String leftS = (lChild == null) ? "?" : lChild.toPlaceholderString();
            String rightS = (rChild == null) ? "?" : rChild.toPlaceholderString();
            String sym = binopSymbol((Binop) operation);
            return "(" + leftS + " " + sym + " " + rightS + ")";
        } else if (operation instanceof Unop) {
            String inner = (lChild == null) ? "?" : lChild.toPlaceholderString();
            return operation.toString() + "(" + inner + ")";
        } else {
         
            return operation.toString();
        }
    }

    private String binopSymbol(Binop bop) {
        if (bop instanceof Plus)   return "+";
        if (bop instanceof Minus)  return "-";
        if (bop instanceof Mult)   return "*";
        if (bop instanceof Divide) return "/";
        return "?";
    }


    public void growIfNeeded(NodeFactory factory, int maxDepth, Random rand) {
        if (!(operation instanceof Binop)) {
            if (operation instanceof Unop && lChild == null && maxDepth > 0) {
                lChild = factory.randomNode(maxDepth - 1, rand);
            }
            return;
        }
  
        if (lChild == null && maxDepth > 0) lChild = factory.randomNode(maxDepth - 1, rand);
        if (rChild == null && maxDepth > 0) rChild = factory.randomNode(maxDepth - 1, rand);
    }


    public void collect(Collector c) {
        c.collect(this);
        if (lChild != null) lChild.collect(c);
        if (rChild != null) rChild.collect(c);
    }
}
