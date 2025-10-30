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
        if (root == null) throw new NullPointerException("Empty tree");
        return root.eval(data);
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
        aPick.swapWith(bPick);
    }

    private void collectAllNodes(Node n, List<Node> out) {
        if (n == null) return;
        out.add(n);
        collectAllNodes(n.getLeft(), out);
        collectAllNodes(n.getRight(), out);
    }

    
    public void traverse() {
        collectedBinops.clear();
        traverseCollect(root);
    }

    private void traverseCollect(Node n) {
        if (n == null) return;

        if (n.getOperation() instanceof Binop) {
            collectedBinops.add(n.toString());
        }
        traverseCollect(n.getLeft());
        traverseCollect(n.getRight());
    }

    public String getCrossNodes() {
       
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < collectedBinops.size(); i++) {
            if (i > 0) sb.append("\n");
            sb.append(collectedBinops.get(i));
        }
        return sb.toString();
    }

    public String toString() {
        return root == null ? "<empty>" : root.toString();
    }
}

