import java.util.Random;

public class Node {
    private Op operation;
    private Node lChild;
    private Node rChild;

    public Node(Op operation) {
        this.operation = operation;
    }

   

    public boolean isLeaf() {
        return !(operation instanceof Unop) && !(operation instanceof Binop);
    }

    public void swapLeft(Node other) {
        Node tmp = this.lChild;
        this.lChild = other.lChild;
        other.lChild = tmp;
    }

    public void swapRight(Node other) {
        Node tmp = this.rChild;
        this.rChild = other.rChild;
        other.rChild = tmp;
    }

    public Node getLeft() { return lChild; }
    public Node getRight() { return rChild; }
    public void setLeft(Node n) { lChild = n; }
    public void setRight(Node n) { rChild = n; }
    public Op getOperation() { return operation; }
    public void setOperation(Op op) { this.operation = op; }

   

    public double eval(double[] data) {
        if (operation == null) {
            throw new IllegalStateException("Node has no operation");
        }

        
        if (!(operation instanceof Unop) && !(operation instanceof Binop)) {
            return operation.eval(data);
        }

        if (operation instanceof Unop) {
            if (lChild == null) {
                throw new NullPointerException("Unop node missing child");
            }
            double v = lChild.eval(data);
            
            return operation.eval(new double[]{ v });
        }

        // Binop
        if (lChild == null || rChild == null) {
            throw new NullPointerException("Binop node missing child(ren)");
        }
        double a = lChild.eval(data);
        double b = rChild.eval(data);
        
        return operation.eval(new double[]{ a, b });
    }

   

    public void addRandomKids(NodeFactory factory, int maxDepth, Random rand) {
        
        if (maxDepth <= 0 || operation == null) return;

        if (operation instanceof Unop) {
            // exactly one child on the left
            if (lChild == null) {
                lChild = factory.randomNode(maxDepth - 1, rand);
            } else {
                lChild.addRandomKids(factory, maxDepth - 1, rand);
            }
            // guarantee unary shape
            rChild = null;
            return;
        }

        if (operation instanceof Binop) {
            // must have both children
            if (lChild == null) lChild = factory.randomNode(maxDepth - 1, rand);
            if (rChild == null) rChild = factory.randomNode(maxDepth - 1, rand);
            lChild.addRandomKids(factory, maxDepth - 1, rand);
            rChild.addRandomKids(factory, maxDepth - 1, rand);
            return;
        }

       
    }
}
