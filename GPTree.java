import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GPTree {
    private Node root;
    private final NodeFactory factory;
    private final StringBuilder crossNodes = new StringBuilder();

    public GPTree(NodeFactory factory) {
        this.factory = factory;
    }

   
    public GPTree(NodeFactory factory, int maxDepth, Random rand) {
        this.factory = factory;
        growRandom(maxDepth, rand);
    }

    public void growRandom(int maxDepth, Random rand) {
        root = factory.getOperator(rand);
        if (root != null) {
            root.addRandomKids(factory, maxDepth, rand);
        }
    }

    public double eval(double[] data) {
        return (root == null) ? 0.0 : root.eval(data);
    }

    public Node getRoot() { return root; }

    
    public void traverse() {
        crossNodes.setLength(0);
        if (root != null) {
            Collector c = new Collector() {
                public void collect(Node n) {
                    crossNodes.append(n.toString()).append("\n");
                }
            };
            root.collect(c);
        }
    }

    public String getCrossNodes() {
        return crossNodes.toString();
    }

    public void crossover(GPTree other, Random rand) {
        if (root == null || other.root == null) return;

        List<Node> a = new ArrayList<>();
        List<Node> b = new ArrayList<>();
        collectAllNodes(root, a);
        collectAllNodes(other.root, b);

        if (a.isEmpty() || b.isEmpty()) return;

        Node aPick = a.get(rand.nextInt(a.size()));
        Node bPick = b.get(rand.nextInt(b.size()));
        aPick.swapWith(bPick);
    }

    private void collectAllNodes(Node n, List<Node> out) {
        if (n == null) return;
        out.add(n);
        collectAllNodes(n.getLeft(), out);
        collectAllNodes(n.getRight(), out);
    }

    public String toString() {
        return (root == null) ? "" : root.toString();
    }
}

