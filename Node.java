import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;

public class Node {

    private Object operation;
    private Node lChild;
    private Node rChild;

    public Node(Object op) {
        this.operation = op;
    }

    public Object getOperation() {
        return operation;
    }

    public Node getLeft() {
        return lChild;
    }

    public Node getRight() {
        return rChild;
    }

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
        } else {
   
        }
    }

 
    public double eval(double[] data) {
     
        if (operation instanceof Binop) {
            if (lChild == null || rChild == null) {
                throw new NullPointerException("Binop node missing child(ren)");
            }
            double left = lChild.eval(data);
            double right = rChild.eval(data);
            return ((Binop) operation).eval(left, right);
        }

     
        if (operation instanceof Unop) {
            if (lChild == null) {
                throw new NullPointerException("Unop node missing left child");
            }
            double v = lChild.eval(data);
            return ((Unop) operation).eval(v);
        }

        Object op = operation;


        if (op instanceof Variable) {
            try {
                return ((Variable) op).eval(data);
            } catch (Throwable t) {
               
            }
        }

        
        if (op instanceof Const) {
        
            try {
                Method m = op.getClass().getMethod("eval");
                Object out = m.invoke(op);
                if (out instanceof Number) return ((Number) out).doubleValue();
            } catch (Throwable ignore) {}

            // Try getValue()/getVal()
            try {
                Method m = op.getClass().getMethod("getValue");
                Object out = m.invoke(op);
                if (out instanceof Number) return ((Number) out).doubleValue();
            } catch (Throwable ignore) {}

            try {
                Method m = op.getClass().getMethod("getVal");
                Object out = m.invoke(op);
                if (out instanceof Number) return ((Number) out).doubleValue();
            } catch (Throwable ignore) {}

        
            try {
                for (Field f : op.getClass().getDeclaredFields()) {
                    if (f.getType() == double.class) {
                        f.setAccessible(true);
                        return f.getDouble(op);
                    }
                }
            } catch (Throwable ignore) {}
        }

       
        try {
            Method m = op.getClass().getMethod("eval", double[].class);
            Object out = m.invoke(op, (Object) data);
            if (out instanceof Number) return ((Number) out).doubleValue();
        } catch (Throwable ignore) {}

       
        try {
            Method m = op.getClass().getMethod("eval");
            Object out = m.invoke(op);
            if (out instanceof Number) return ((Number) out).doubleValue();
        } catch (Throwable ignore) {}

        throw new IllegalStateException("Cannot evaluate leaf operation of type: " + op.getClass().getName());
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
