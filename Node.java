import java.lang.reflect.Method;
import java.util.Random;

public class Node {

    private Object operation;
    private Node lChild;
    private Node rChild;

    public Node(Object op) {
        this.operation = op;
    }

    public Object getOperation() { return operation; }
    public Node getLeft() { return lChild; }
    public Node getRight() { return rChild; }
    public void setLeft(Node n) { this.lChild = n; }
    public void setRight(Node n) { this.rChild = n; }
    public void setOperation(Object op) { this.operation = op; }

    public boolean isLeaf() {
        return !(operation instanceof Binop) && !(operation instanceof Unop);
    }

    public void addRandomKids(NodeFactory factory, int maxDepth, Random rand) {
        if (maxDepth <= 0) return;

        if (operation instanceof Binop) {
            lChild = factory.getOperator(rand);
            rChild = factory.getOperator(rand);
            if (lChild != null) lChild.addRandomKids(factory, maxDepth - 1, rand);
            if (rChild != null) rChild.addRandomKids(factory, maxDepth - 1, rand);
        } else if (operation instanceof Unop) {
            lChild = factory.getOperator(rand);
            if (lChild != null) lChild.addRandomKids(factory, maxDepth - 1, rand);
        } // leaves: nothing to add
    }

    public double eval(double[] data) {
        if (operation instanceof Binop) {
            if (lChild == null || rChild == null)
                throw new NullPointerException("Binop node missing child(ren)");
            double a = lChild.eval(data);
            double b = rChild.eval(data);
            return ((Binop) operation).eval(a, b);
        }

        if (operation instanceof Unop) {
            if (lChild == null)
                throw new NullPointerException("Unop node missing child");
            double v = lChild.eval(data);

            // Try Unop.eval(double)
            try {
                Method m = operation.getClass().getMethod("eval", double.class);
                Object out = m.invoke(operation, v);
                return ((Number) out).doubleValue();
            } catch (Throwable ignore) {}

          
            try {
                Method m = operation.getClass().getMethod("eval", double[].class);
                Object out = m.invoke(operation, (Object) new double[]{v});
                return ((Number) out).doubleValue();
            } catch (Throwable ignore) {}

            throw new IllegalStateException("Unop has no compatible eval(double) or eval(double[])");
        }

       
        try {
            
            Method m = operation.getClass().getMethod("eval", double[].class);
            Object out = m.invoke(operation, (Object) data);
            return ((Number) out).doubleValue();
        } catch (Throwable ignore) {}

        try {
           
            Method m = operation.getClass().getMethod("eval");
            Object out = m.invoke(operation);
            return ((Number) out).doubleValue();
        } catch (Throwable ignore) {}

        try {
            
            Method m = operation.getClass().getMethod("getValue");
            Object out = m.invoke(operation);
            return ((Number) out).doubleValue();
        } catch (Throwable ignore) {}

        try {
            
            Method m = operation.getClass().getMethod("getVal");
            Object out = m.invoke(operation);
            return ((Number) out).doubleValue();
        } catch (Throwable ignore) {}

        throw new IllegalStateException("Cannot evaluate leaf operation of type: " + operation.getClass().getName());
    }

    public void swapWith(Node other) {
        Object tmpOp = other.operation;
        Node tmpL = other.lChild;
        Node tmpR = other.rChild;

        other.operation = this.operation;
        other.lChild = this.lChild;
        other.rChild = this.rChild;

        this.operation = tmpOp;
        this.lChild = tmpL;
        this.rChild = tmpR;
    }

    public String toString() {
        if (operation instanceof Binop) {
            return "(" + lChild + " " + operation + " " + rChild + ")";
        } else if (operation instanceof Unop) {
            return operation + "(" + lChild + ")";
        } else {
            return String.valueOf(operation);
        }
    }
}
