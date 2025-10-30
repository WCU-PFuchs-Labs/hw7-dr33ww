import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GPTree implements Collector {
    private Node root;
    private ArrayList<Node> crossNodes;
    
   
    public void collect(Node node) {
     
        if (node.getOperation() instanceof Binop) {
            crossNodes.add(node);
        }
    }
    
  
    public void traverse() {
        crossNodes = new ArrayList<Node>();
        if (root != null) {
            root.traverse(this);
        }
    }
    
  
    public String getCrossNodes() {
        if (crossNodes == null || crossNodes.isEmpty()) {
            return "";
        }
        StringBuilder string = new StringBuilder();
        int lastIndex = crossNodes.size() - 1;
        for(int i = 0; i < lastIndex; ++i) {
            Node node = crossNodes.get(i);
            string.append(node.toString());
            string.append(";");
        }
        string.append(crossNodes.get(lastIndex));
        return string.toString();
    }
   

    public void crossover(GPTree tree, Random rand) {
       
        this.traverse();
        tree.traverse();
        if (this.crossNodes.isEmpty() || tree.crossNodes.isEmpty()) {
            return;
        }
        int thisPoint = rand.nextInt(this.crossNodes.size());
        int treePoint = rand.nextInt(tree.crossNodes.size());
        boolean left = rand.nextBoolean();

        Node thisTrunk = crossNodes.get(thisPoint);
        Node treeTrunk = tree.crossNodes.get(treePoint);

        if(left) {
            thisTrunk.swapLeft(treeTrunk);
        } else {
            thisTrunk.swapRight(treeTrunk);
        }
    }

    GPTree() { 
        root = null; 
    }    
    
    public GPTree(NodeFactory n, int maxDepth, Random rand) {
        root = n.getOperator(rand);
        root.addRandomKids(n, maxDepth, rand);
    }
    
    public String toString() { 
        return (root == null) ? "" : root.toString(); 
    }
    
    public double eval(double[] data) { 
        return (root == null) ? 0.0 : root.eval(data); 
    }
}
