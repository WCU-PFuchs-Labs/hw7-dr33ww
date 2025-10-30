import java.util.*;

public class GPTree {
    private Node root;
    private final NodeFactory factory;

    public GPTree(NodeFactory factory) {
        this.factory = factory;
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

    public Node getRoot() {
        return root;
    }

    
    public void traverse(Collector c) {
        if (c == null) return;
        if (root != null) root.collect(c);
    }

    
    private void collectAllNodes(Node n, List<Node> out) {
        if (n == null) return;
        out.add(n);
        collectAllNodes(n.getLeft(), out);
        collectAllNodes(n.getRight(), out);
    }


    public void crossover(GPTree other, Random rand) {
        if (this.root == null || other.root == null) return;

        List<Node> a = new ArrayList<>();
        List<Node> b = new ArrayList<>();
        collectAllNodes(this.root, a);
        collectAllNodes(other.root, b);
        if (a.isEmpty() || b.isEmpty()) return;

        Node aPick = a.get(rand.nextInt(a.size()));
        Node bPick = b.get(rand.nextInt(b.size()));
        aPick.swapWith(bPick);
    }

    public String toString() {
        return (root == null) ? "" : root.toString();
    }

