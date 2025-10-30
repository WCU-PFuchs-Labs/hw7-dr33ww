import java.util.Random;

public class Node {
    Op operation;
    Node lChild;
    Node rChild;

    
    public Node(Op o) {
        this.operation = o;
        this.lChild = null;
        this.rChild = null;
    }

    // grow children up to maxDepth using a NodeFactory
    public void addRandomKids(NodeFactory factory, int maxDepth, Random rand) {
        if (maxDepth <= 0) return;

        if (operation instanceof Binop) {
            // two children
            lChild = factory.getOperator(rand);
            if (lChild != null) lChild.addRandomKids(factory, maxDepth - 1, rand);

            rChild = factory.getOperator(rand);
            if (rChild != null) rChild.addRandomKids(factory, maxDepth - 1, rand);

        } else if (operation instanceof Unop) {
            
            lChild = factory.getOperator(rand);
            if (lChild != null) lChild.addRandomKids(factory, maxDepth - 1, rand);
            

        } else {

        }
    }

    // evaluate this node on the given data
    public double eval(double[] data) {
        if (operation instanceof Binop) {
            double a = lChild.eval(data);
            double b = rChild.eval(data);
            return ((Binop) operation).eval(a, b);
        } else if (operation instanceof Unop) {
            // fix: let Unop evaluate using the data array (it handles its child internally)
            return ((Unop) operation).eval(data);
        } else if (operation instanceof Const) {
            return ((Const) operation).eval(data);
        } else if (operation instanceof Variable) {
            return ((Variable) operation).eval(data);
        } else {
            
            throw new IllegalStateException("unknown Op type: " + operation.getClass().getName());
        }
    }

    // convert node to readable str
    public String toString() {
        if (operation instanceof Binop) {
            
            return "(" + lChild.toString() + " " + operation.toString() + " " + rChild.toString() + ")";
        } else if (operation instanceof Unop) {
           
            return operation.toString() + "(" + lChild.toString() + ")";
        } else {
            // terminals print themselves
            return operation.toString();
        }
    }

   

    //  visit self, then left, then right
    public void traverse(Collector c) {
        c.collect(this);
        if (this.lChild != null) this.lChild.traverse(c);
        if (this.rChild != null) this.rChild.traverse(c);
    }

    // true if no children
    public boolean isLeaf() {
        return this.lChild == null && this.rChild == null;
    }

    // swap this node's left child with trunk's left child, gohan
    public void swapLeft(Node trunk) {
        Node temp = this.lChild;
        this.lChild = trunk.lChild;
        trunk.lChild = temp;
    }

    // swap this node's right child with trunk's right child, what did they do to u gohan
    public void swapRight(Node trunk) {
        Node temp = this.rChild;
        this.rChild = trunk.rChild;
        trunk.rChild = temp;
    }

   
}
