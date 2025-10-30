
// represents a variable like X0, X1, X2
public class Variable extends Unop {
    private int index;

    public Variable(int index) {
        this.index = index;
    }

    public double eval(double[] values) {
        if (values == null || index < 0 || index >= values.length) {
            System.out.println("index out of range for variable X" + index);
            return 0.0;
        }
        return values[index];
    }

    public String toString() {
        return "X" + index;
    }
}
