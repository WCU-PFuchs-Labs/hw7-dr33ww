public class Divide extends Binop {

    // text form of this operator
    public String toString() {
        return "/";
    }

    // evaluate a / b with guard against tiny denominators
    public double eval(double a, double b) {
        if (Math.abs(b) < 0.0001) {
            return 1.0;
        }
        return a / b;
    }
}
