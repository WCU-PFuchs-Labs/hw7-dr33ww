
// unary operation (leaf); subclasses like Const and Variable override eval(values)
public class Unop extends Op {
    public double eval(double[] values) {
        return 0.0;
    }
}
