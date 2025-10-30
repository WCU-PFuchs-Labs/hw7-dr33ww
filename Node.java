import java.util.Random;

package binary;

import java.util.ArrayList;
import java.util.Random;

public class Node {

    Op operation;
    Node lChild;
    Node rChild;

    // empty constructor
    public Node() {
        operation = null;
        lChild = null;
        rChild = null;
    }

    // create node with an operation
    public Node(Op o) {
        operation = o;
        lChild = null;
        rChild = null;
    }

    // set left child
    public void setLeft(Node n) {
        lChild = n;
    }

    // set right child
    public void setRight(Node n) {
        rChild = n;
    }

    // return left child
    public Node getLeft() {
        return lChild;
    }

    // return right child
    public Node getRight() {
        return rChild;
    }

    // get operation
    public Op getOperation() {
        return operation;
    }

    // set operation
    public void setOperation(Op o) {
        operation = o;
    }

    // evaluate recursively
    public double eval(double[] data) {
        if (operation instanceof Const) {
            return ((Const) operation).getVal();
        } 
        else if (operation instanceof Variable) {
            return ((Variable) operation).eval(data);
        } 
        else if (operation instanceof Unop) {
            return ((Unop) operation).eval(lChild.eval(data));
        } 
        else if (operation instanceof Binop) {
            double leftVal = lChild.eval(data);
            double rightVal = rChild.eval(data);
            return ((Binop) operation).eval(leftVal, rightVal);
        } 
        else {
            return 0.0;
        }
    }

    // print the expression
    public String toString() {
        if (operation == null) {
            return "?";
        }
        if (operation instanceof Const || operation instanceof Variable) {
            return operation.toString();
        } 
        else if (operation instanceof Unop) {
            return operation.toString() + "(" + lChild.toString() + ")";
        } 
        else if (operation instanceof Binop) {
            return "(" + lChild.toString() + " " + operation.toString() + " " + rChild.toString() + ")";
        }
        return "?";
    }

    // traverse and collect binops
    public void traverse(Collector c) {
        if (operation instanceof Binop) {
            c.collect(this);
        }
        if (lChild != null) {
            lChild.traverse(c);
        }
        if (rChild != null) {
            rChild.traverse(c);
        }
    }

    // used by nodefactory to add random kids
    public void addRandomKids(NodeFactory factory, int depth, Random rand) {
        if (depth > 0) {
            if (operation instanceof Binop) {
                lChild = factory.randomNode(depth - 1, rand);
                rChild = factory.randomNode(depth - 1, rand);
            } 
            else if (operation instanceof Unop) {
                lChild = factory.randomNode(depth - 1, rand);
            }
        }
    }
}
