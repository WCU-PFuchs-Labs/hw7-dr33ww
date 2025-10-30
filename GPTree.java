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

    @Override
    public String toString() {
        return (root == null) ? "" : root.toString();
    }

    public void traverse() {
        if (root == null) {
            crossNodes = "";
            return;
        }

        String treeString = root.toString();
        List<String> binops = collectBinopsFromString(treeString);
        
        if (binops.isEmpty()) {
            crossNodes = "";
        } else {
            crossNodes = String.join(";", binops);  
        }
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

    private List<String> collectBinopsFromString(String s) {
        List<String> out = new ArrayList<>();
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
              
                int depth = 1;
                int j = i + 1;
                while (j < s.length() && depth > 0) {
                    if (s.charAt(j) == '(') depth++;
                    else if (s.charAt(j) == ')') depth--;
                    j++;
                }
                
                if (depth == 0) {
                 
                    String sub = s.substring(i, j);
                    if (isTopLevelBinop(sub)) {
                        out.add(sub);
                    }
                }
            }
        }
        
        return out;
    }

    private boolean isTopLevelBinop(String parened) {
     
        if (parened.length() < 3 || parened.charAt(0) != '(' || parened.charAt(parened.length() - 1) != ')') {
            return false;
        }
        
    
        String content = parened.substring(1, parened.length() - 1);
        
   
        int depth = 0;
        int operatorCount = 0;
        
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c == '(') depth++;
            else if (c == ')') depth--;
            else if (depth == 0) {
                if (c == '+' || c == '-' || c == '*' || c == '/') {
                    operatorCount++;
                    if (operatorCount > 1) return false; 
                }
            }
        }
        
        return operatorCount == 1; 
    }
}
