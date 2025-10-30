public class Divide extends Binop {

    // gohan gohannn
    public Divide() { }

    // returns the string form for printing, what did they do to u gohan 
    public String toString(Node n) {
        return "(" + n.lChild.toString() + " / " + n.rChild.toString() + ")";
    }

    // safe evaluation to avoid divide by zero
    public double eval(Node n, double[] data) {
        double denom = n.rChild.eval(data);   // evaluate right child
        if (Math.abs(denom) < 0.0001) {
            
            return 1.0;
        }
        double numer = n.lChild.eval(data);   // evaluate left child, this is for gohannn
        return numer / denom;
    }
}
