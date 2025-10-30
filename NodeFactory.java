import java.util.Random;

public class NodeFactory {
    private final int numIndepVars;
    private final Node[] currentOps;

    public NodeFactory(Binop[] binops, int numVars) {
        this.numIndepVars = numVars;
        this.currentOps = new Node[binops.length];
        for (int i = 0; i < binops.length; i++) {
            this.currentOps[i] = new Node(binops[i]);
        }
    }

    public Node getOperator(Random rand) {
        int index = rand.nextInt(currentOps.length);
        Binop cloned = (Binop) currentOps[index].operation.clone();
        return new Node(cloned);
    }

    public int getNumOps() {
        return currentOps.length;
    }

    public Node getTerminal(Random rand) {
        int pick = rand.nextInt(numIndepVars + 1);
        if (pick < numIndepVars) {
            return new Node(new Variable(pick));
        } else {
            return new Node(new Const(rand.nextDouble()));
        }
    }

    public int getNumIndepVars() {
        return numIndepVars;
    }
}
