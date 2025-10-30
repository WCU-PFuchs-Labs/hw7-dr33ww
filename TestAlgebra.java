
public class TestAlgebra {
    public static void main(String[] args) {
        double[] vals1 = {1.0, 2.0, 3.0};
        double[] vals2 = {2.0, 1.0, 0.0};

        // ((16.0 / X0) - (7.0 * X1))
        Node tree1 = new Node(
            new Minus(),
            new Node(new Divide(), new Node(new Const(16.0)), new Node(new Variable(0))),
            new Node(new Mult(), new Node(new Const(7.0)), new Node(new Variable(1)))
        );

        System.out.println("When {X0,X1,X2} = {1.0,2.0,3.0}:");
        System.out.println(tree1.toString() + " = " + tree1.eval(vals1));

        System.out.println();
        System.out.println("When {X0,X1,X2} = {2.0,1.0,0.0}:");
        System.out.println(tree1.toString() + " = " + tree1.eval(vals2));
    }
}
