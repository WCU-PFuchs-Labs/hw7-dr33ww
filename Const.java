
// constant numeric value
public class Const extends Unop {
    private double value;

    public Const(double value) {
        this.value = value;
    }

    public double eval(double[] values) {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
