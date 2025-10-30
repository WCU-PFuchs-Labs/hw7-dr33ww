import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//im on the very of edge of losing my mind i know wat a node is in its natural state, it speaks to me
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
        String tree = root.toString();         
        List<String> binops = collectBinopsFromString(tree);
        if (binops.isEmpty()) {
            crossNodes = "";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < binops.size(); i++) {
                if (i > 0) sb.append('\n');
                sb.append(binops.get(i));
            }
            crossNodes = sb.toString();
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
        List<Integer> stack = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                stack.add(i);
            } else if (ch == ')') {
                if (!stack.isEmpty()) {
                    int start = stack.remove(stack.size() - 1);
                    String sub = s.substring(start, i + 1);
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
        int depth = 0;
        int topOps = 0;
        for (int i = 0; i < parened.length(); i++) {
            char c = parened.charAt(i);
            if (c == '(') depth++;
            else if (c == ')') depth--;
            else if (depth == 1) {
                if (c == '+' || c == '-' || c == '*' || c == '/') {
                    topOps++;
                    if (topOps > 1) return false;
                }
            }
        }
        return topOps == 1;
    }
}
