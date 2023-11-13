import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] fractionArray;
    private final int trials;

    private final double confidence95;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Value should be bigger than 0");
        }

        confidence95 = 1.96;
        this.fractionArray = new double[trials];
        this.trials = trials;
        Percolation percolation = new Percolation(n);

        for (int i = 0; i < trials; i++) {

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            int openSites = percolation.numberOfOpenSites();
            double threshold = openSites * 1.0 / (n * n);
            this.fractionArray[i] = threshold;
        }
    }

    public double mean() {
        return StdStats.mean(fractionArray);
    }

    public double stddev() {
        return StdStats.stddev(fractionArray);
    }

    public double confidenceLo() {
        double rightTerm = confidence95 * Math.sqrt(stddev()) / Math.sqrt(this.trials);
        return mean() - rightTerm;
    }

    public double confidenceHi() {
        double rightTerm = confidence95 * Math.sqrt(stddev()) / Math.sqrt(this.trials);
        return mean() + rightTerm;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats PS = new PercolationStats(n, trials);
        StdOut.println("Mean = " + PS.mean());
        StdOut.println("Stddev = " + PS.stddev());
        StdOut.println("95% confidence interval = [" + PS.confidenceLo() +
                " , " + PS.confidenceHi() + "]");
    }
}
