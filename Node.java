import java.util.Random;

public class Node {

    private Op operation;      
    private Node lChild;      
    private Node rChild;      

   
    public Node(Op op) {
        this.operation = op;
    }

    public Node(Op op, Node left) {
        this.operation = op;
        this.lChild = left;
    }

    public Node(Op op, Node left, Node right) {
        this.operation = op;
        this.lChild = left;
        this.rChild = right;
    }

  
    public Op getOperation() { return operation; }
    public Node getLeft()     { return lChild; }
    public Node getRight()    { return rChild; }
    public void setLeft(Node n)  { lChild = n; }
    public void setRight(Node n) { rChild = n; }

    public boolean isLeaf() {
        return lChild == null && rChild == null;
    }

    
    public void swapWith(Node other) {
        Op tmpOp = this.operation;
        Node tmpL = this.lChild;
        Node tmpR = this.rChild;

        this.operation = other.operation;
        this.lChild    = other.lChild;
        this.rChild    = other.rChild;

        other.operation = tmpOp;
        other.lChild    = tmpL;
        other.rChild    = tmpR;
    }

    
    public double eval(double[] data) {
        if (operation == null) throw new NullPointerException("Node has null operation");

        
        if (operation instanceof Const) {
            
            return ((Const) operation).getVal();
        }

        
        if (operation instanceof Variable) {
            
            int idx = ((Variable) operation).getIndex();
            if (data == null) throw new NullPointerException("data is null");
            if (idx < 0 || idx >= data.length)
                throw new ArrayIndexOutOfBoundsException("Variable index " + idx + " out of bounds for data length " + (data == null ? -1 : data.length));
            return data[idx];
        }

        
        if (operation instanceof Unop) {
            if (lChild == null) throw new NullPointerException("Unop node missing child");
            double v = lChild.eval(data);
            return ((Unop) operation).eval(v);
        }

        
        if (operation instanceof Binop) {
            if (lChild == null || rChild == null) throw new NullPointerException("Binop node missing child(ren)");
            double a = lChild.eval(data);
            double b = rChild.eval(data);
            return ((Binop) operation).eval(a, b);
        }

       
        throw new IllegalStateException("Unknown Op type: " + operation.getClass().getName());
    }

    
    public void addRandomKids(NodeFactory factory, int remainingDepth, Random rand) {
        if (factory == null) return;

        
        if (remainingDepth <= 0) {
           
            if (operation instanceof Binop || operation instanceof Unop) {
               
                Node leaf = factory.getOperator(rand);
                while (leaf != null && (leaf.operation instanceof Binop || leaf.operation instanceof Unop)) {
                    leaf = factory.getOperator(rand);
                }
                if (leaf != null) {
                    this.operation = leaf.operation;
                    this.lChild = null;
                    this.rChild = null;
                } else {
                   
                    this.lChild = null;
                    this.rChild = null;
                }
            } else {
                
                this.lChild = null;
                this.rChild = null;
            }
            return;
        }

        
        if (operation instanceof Unop) {
            if (lChild == null) {
                Node child = factory.getOperator(rand);
                if (child == null) return;
                lChild = child;
            }
            
            lChild.addRandomKids(factory, remainingDepth - 1, rand);
            
            rChild = null;
            return;
        }

        if (operation instanceof Binop) {
            if (lChild == null) {
                Node left = factory.getOperator(rand);
                if (left != null) lChild = left;
            }
            if (rChild == null) {
                Node right = factory.getOperator(rand);
                if (right != null) rChild = right;
            }
            if (lChild != null) lChild.addRandomKids(factory, remainingDepth - 1, rand);
            if (rChild != null) rChild.addRandomKids(factory, remainingDepth - 1, rand);
            return;
        }

       
        lChild = null;
        rChild = null;
    }

    
    public String toString() {
        if (operation == null) return "null";
        
        if (operation instanceof Const || operation instanceof Variable) {
            return operation.toString();
        }
        if (operation instanceof Unop) {
            String inner = (lChild == null) ? "?" : lChild.toString();
            return operation.toString() + "(" + inner + ")";
        }
        if (operation instanceof Binop) {
            String a = (lChild == null) ? "?" : lChild.toString();
            String b = (rChild == null) ? "?" : rChild.toString();
            return operation.toString() + "(" + a + ", " + b + ")";
        }
        return operation.toString();
    }
}
