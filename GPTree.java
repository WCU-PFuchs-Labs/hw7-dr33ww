import java.util.ArrayList;
import java.util.Random;

// tree wrapper that encapsulates a root node and supports crossover when we blow
public class GPTree implements Collector {
    private Node root;
    private ArrayList<Node> crossNodes;

    // collect nonleaf nodes for crossover
    public void collect(Node node) {
        if (node != null && !node.isLeaf()) {
            crossNodes.add(node);
        }
    }

    // traverse the tree to build list of cross candids
    public void traverse() {
        crossNodes = new ArrayList<Node>();
        if (root != null) {
            root.traverse(this);
        }
    }

    // return all collected node strings separated by semicolons, if it don't work call David
    public String getCrossNodes() {
        if (crossNodes == null || crossNodes.isEmpty()) return "";
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < crossNodes.size(); i++) {
            s.append(crossNodes.get(i).toString());
            if (i != crossNodes.size() - 1) s.append(";");
        }
        return s.toString();
    }

    // basic left-left / right-right crossover shoutout uzi nd Carti
   
    public void crossover(GPTree tree, Random rand) {
        // build candidate lists
        this.traverse();
        tree.traverse();
        if (this.crossNodes.isEmpty() || tree.crossNodes.isEmpty()) return;

        int thisPoint = rand.nextInt(this.crossNodes.size());
        int treePoint = rand.nextInt(tree.crossNodes.size());
        boolean left = rand.nextBoolean();

        Node thisTrunk = this.crossNodes.get(thisPoint);
        Node treeTrunk = tree.crossNodes.get(treePoint);

        if (left) {
            thisTrunk.swapLeft(treeTrunk);
        } else {
            thisTrunk.swapRight(treeTrunk);
        }
    }

    // default constructor
    GPTree() {
        root = null;
    }

    // build tree node factory
    public GPTree(NodeFactory n, int maxDepth, Random rand) {
        root = n.getOperator(rand);
        root.addRandomKids(n, maxDepth, rand);
    }

    // eval data given
    public double eval(double[] data) {
        return root.eval(data);
    }

    public String toString() {
        return root.toString();
    }
}
