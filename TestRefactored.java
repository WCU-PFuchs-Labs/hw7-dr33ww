import java.util.Random;

public class TestRefactored {
    static int numIndepVars = 3;
    static int maxDepth = 5;
    static Random rand = new Random();
    public static void main(String[] args) {
        double[] data = new double[3];
        data[0] = 3.14;
        data[1] = 2.78;
        data[2] = 1.0;
        Binop[] ops = {new Plus(), new Minus(), new Mult(), new Divide()};
        NodeFactory n = new NodeFactory(ops, numIndepVars);
        Node root = n.getOperator(rand);
        root.addRandomKids(n, maxDepth, rand);
        String s = root.toString();
        System.out.println(s + " = " + root.eval(data));
    }
}