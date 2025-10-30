import java.util.Random;

public class GPTree {

    private Node root;

    public GPTree(Node root) {
        this.root = root;
    }

    public GPTree(NodeFactory factory, int maxDepth, Random rand) {
      
        root = factory.getOperator(rand);
        if (root != null) {
            root.addRandomKids(factory, maxDepth - 1, rand);
        }
    }

    public Node getRoot() {
        return root;
    }

    public double eval(double[] data) {
        if (root == null) throw new NullPointerException("Tree has no root");
        return root.eval(data);
    }

    public String toString() {
        return root == null ? "<empty>" : root.toString();
    }
}
