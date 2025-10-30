import java.util.Random;

public class Node implements Cloneable {
    protected Node left, right;
    protected Op operation;
    protected int depth = 0;

    public Node(Binop op) {
        this.operation = op;
    }

    public Node(Unop op) {
        this.operation = op;
    }

    // add random children to the node recursively
    public void addRandomKids(NodeFactory nf, int maxDepth, Random rand) {
        if (operation instanceof Unop) return;

        if (depth >= maxDepth) {
            left = nf.getTerminal(rand);
            left.depth = this.depth + 1;
            right = nf.getTerminal(rand);
            right.depth = this.depth + 1;
            return;
        }

        java.util.function.Consumer<Boolean> makeChild = isLeft -> {
            int span = nf.getNumOps() + nf.getNumIndepVars();
            int r = rand.nextInt(span + 1);

            Node child;
            if (r < nf.getNumOps()) {
                child = nf.getOperator(rand);
                child.depth = this.depth + 1;
                if (isLeft) this.left = child;
                else this.right = child;
                child.addRandomKids(nf, maxDepth, rand);
            } else {
                child = nf.getTerminal(rand);
                child.depth = this.depth + 1;
                if (isLeft) this.left = child;
                else this.right = child;
            }
        };

        makeChild.accept(true);
        makeChild.accept(false);
    }

    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Node can't clone.");
        }
        Node b = (Node) o;

        if (left != null) b.left = (Node) left.clone();
        if (right != null) b.right = (Node) right.clone();
        if (operation != null) b.operation = (Op) operation.clone();

        return b;
    }

    // recursively evaluate the expression tree
    public double eval(double[] data) {
        if (operation instanceof Binop) {
            Binop bop = (Binop) operation;
            double leftVal = left.eval(data);
            double rightVal = right.eval(data);
            return bop.eval(leftVal, rightVal);
        } else if (operation instanceof Unop) {
            Unop uop = (Unop) operation;
            return uop.eval(data);
        } else {
            throw new RuntimeException("Unknown operation type in Node");
        }
    }

    public String toString() {
        if (operation instanceof Binop) {
            return "(" + left.toString() + " " + operation.toString() + " " + right.toString() + ")";
        } else {
            return operation.toString();
        }
    }
}
