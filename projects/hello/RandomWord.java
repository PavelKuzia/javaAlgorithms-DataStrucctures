/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        while (!StdIn.isEmpty()) {
            double index = 1.0;
            String champ = StdIn.readString();
            while (!StdIn.isEmpty()) {
                String input = StdIn.readString();
                index = index + 1;
                if (StdRandom.bernoulli(1 / index)) {
                    champ = input;
                }
            }
            StdOut.println(champ);
        }
    }
}


