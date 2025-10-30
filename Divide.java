public class Divide extends Binop {
    public double eval(double left, double right) {
     
        if (Math.abs(right) < 0.0001) {
            return 1.0;
        }
        return left / right;
    }
    
    public String toString() {
        return "/";
    }
}
