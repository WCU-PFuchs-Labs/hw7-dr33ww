public class Divide extends Binop {
    public Divide() {}
    public Divide(Node l, Node r) {
        super(l, r);
    }
    public double eval(double[] data) {
        double divisor = rChild.eval(data);
   
        if (Math.abs(divisor) < 0.0001) {
            return 1.0;
        }
        return lChild.eval(data) / divisor;
    }
    public String toString() {
        return "(" + lChild.toString() + " / " + rChild.toString() + ")";
    }
}
