import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> myQueue = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            myQueue.enqueue(input);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(myQueue.dequeue());
        }
    }
}
