import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GPTree {

    private Node root;
    private final List<String> collectedBinops = new ArrayList<String>();

    public GPTree(Node root) {
        this.root = root;
    }

    public GPTree(NodeFactory factory, int maxDepth, Random rand) {
        root = factory.getOperator(rand);
        if (root != null) root.addRandomKids(factory, maxDepth - 1, rand);
    }

    public Node getRoot() { return root; }

    public double eval(double[] data) {
        if (root == null) throw new NullPointerException("Tree has no root");
        return root.eval(data);
    }

  
    public void traverse() {
        collectedBinops.clear();
        collectBinops(root);
    }

    private void collectBinops(Node n) {
        if (n == null) return;
        Object op = n.getOperation();
        if (op instanceof Binop) {
            collectedBinops.add(n.toString());
        }
        collectBinops(n.getLeft());
        collectBinops(n.getRight());
    }

    public String getCrossNodes() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < collectedBinops.size(); i++) {
            if (i > 0) sb.append("\n");
            sb.append(collectedBinops.get(i));
        }
        return sb.toString();
    }

  
    public void crossover(GPTree other, Random rand) {
        if (this.root == null || other == null || other.root == null) return;

        List<Node> aNodes = new ArrayList<Node>();
        List<Node> bNodes = new ArrayList<Node>();
        collectAllNodes(this.root, aNodes);
        collectAllNodes(other.root, bNodes);
        if (aNodes.isEmpty() || bNodes.isEmpty()) return;

        Node aPick = aNodes.get(rand.nextInt(aNodes.size()));
        Node bPick = bNodes.get(rand.nextInt(bNodes.size()));

        // swap the *contents* of the chosen nodes (operation + children)
        aPick.swapWith(bPick);
    }

    private void collectAllNodes(Node n, List<Node> out) {
        if (n == null) return;
        out.add(n);
        collectAllNodes(n.getLeft(), out);
        collectAllNodes(n.getRight(), out);
    }

    public String toString() {
        return root == null ? "<empty>" : root.toString();
    }
}
