import java.util.Random;

public class TestCollect {
    static int numIndepVars = 3;
    static int maxDepth = 5;
    static Random rand = new Random();

    public static void main(String[] args) {
        double[] data = { 3.14, 2.78, 1.0 };
        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        NodeFactory o = new NodeFactory(ops, numIndepVars);

        int n = 10;
        System.out.println("Testing " + n + " trees for collect");
        boolean correctBinopCount = true;
        boolean mismatchedBinop = false;

        for (int j = 1; j <= n; ++j) {
            GPTree gpt = new GPTree(o, maxDepth, rand);
            System.out.println("----------");
            System.out.print("Tree " + j + ":");
            System.out.println(gpt.toString());
            gpt.traverse();
            String crossNodes = gpt.getCrossNodes();
            
  
            String[] binops;
            if (crossNodes.isEmpty()) {
                binops = new String[0]; 
            } else {
                binops = crossNodes.split(";");
            }
            
            int numBinops = 0;
            String fullString = gpt.toString();
            for (int i = 0; i < fullString.length(); ++i) {
                if ('(' == fullString.charAt(i)) {
                    ++numBinops;
                }
            }
            if (numBinops != binops.length) {
                System.out.print("Wrong number of binops:");
                System.out.println("have " + binops.length + ", should have " + numBinops);
                correctBinopCount = false;
            }
            for (String binop : binops) {
                System.out.print("Testing " + binop + ": ");
                if (!fullString.contains(binop)) {
                    System.out.println(binop + " is not part of " + fullString);
                    mismatchedBinop = true;
                } else {
                    System.out.println("okay!");
                }
            }
        }
        if (correctBinopCount && !mismatchedBinop) {
            System.out.println("Test Passes: Collect has all binops!");
        } else {
            System.out.println("ERROR: Not all binops were collected by traverse.");
        }
    }
}
