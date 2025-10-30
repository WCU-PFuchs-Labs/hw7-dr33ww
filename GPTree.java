import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GPTree {
    private final NodeFactory factory;
    private final Random rand;
    private Node root;

    
    private String crossNodes = "";

    public GPTree(NodeFactory factory, int maxDepth, Random rand) {
        this.factory = factory;
        this.rand = rand;
        this.root = factory.getOperator(rand);
        int depth = Math.max(1, maxDepth);
        root.addRandomKids(factory, depth, rand);
    }

    public double eval(double[] data) {
        return (root == null) ? 0.0 : root.eval(data);
    }

    public String toString() {
        return (root == null) ? "" : root.toString();
    }

    
    public void traverse() {
        final StringBuilder sb = new StringBuilder();
        if (root != null) {
            root.traverse(new Collector() {
                public void collect(Node n) {
                   sb.append(n.toString()).append("\n")
                }
            });
        }
        crossNodes = sb.toString();
    }

    
    public String getCrossNodes() {
        return crossNodes;
    }

  
    public void crossover(GPTree other, Random rand) {
        List<Node> mine = new ArrayList<>();
        List<Node> theirs = new ArrayList<>();
        collectAllNodes(this.root, mine);
        collectAllNodes(other.root, theirs);
        if (mine.isEmpty() || theirs.isEmpty()) return;
        Node a = mine.get(rand.nextInt(mine.size()));
        Node b = theirs.get(rand.nextInt(theirs.size()));
        swapChildren(a, b);
    }

    private void swapChildren(Node a, Node b) {
        Node aL = a.getLeft(), aR = a.getRight();
        a.setLeft(b.getLeft()); a.setRight(b.getRight());
        b.setLeft(aL);          b.setRight(aR);
    }

    private void collectAllNodes(Node n, List<Node> out) {
        if (n == null) return;
        out.add(n);
        collectAllNodes(n.getLeft(), out);
        collectAllNodes(n.getRight(), out);
    }
}
