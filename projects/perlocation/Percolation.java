import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] model;
    private final int n;
    private final WeightedQuickUnionUF uf;
    private int numberOfOpenSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Value should be bigger than 0");
        }
        this.n = n;
        this.numberOfOpenSites = 0;
        // initializing an UF object
        // two elements correspond to virtual sites
        this.uf = new WeightedQuickUnionUF(n * n + 2);

        // a percolation model - all sites are initially blocked
        this.model = new boolean[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                this.model[i][j] = false;
            }
        }
    }

    public void open(int row, int col) {
        if (!this.inRange(row, col)) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        if (this.isOpen(row, col)) return;

        this.model[row][col] = true;
        this.numberOfOpenSites += 1;

        int siteToOpen = this.convertInt(row, col);

        // if it's first or last row we need to connect sites with virtual sites
        if (row == 1) {
            this.uf.union(0, siteToOpen);
        } else if (row == this.n) {
            this.uf.union(n * n + 1, siteToOpen);
        }

        if (this.toConnect(row + 1, col)) {
            int neighbour = this.convertInt(row + 1, col);
            if (this.uf.find(siteToOpen) != this.uf.find(neighbour)) this.uf.union(siteToOpen, neighbour);
        }
        if (this.toConnect(row - 1, col)) {
            int neighbour = this.convertInt(row - 1, col);
            if (this.uf.find(siteToOpen) != this.uf.find(neighbour)) this.uf.union(siteToOpen, neighbour);
        }
        if (this.toConnect(row, col + 1)) {
            int neighbour = this.convertInt(row, col + 1);
            if (this.uf.find(siteToOpen) != this.uf.find(neighbour)) this.uf.union(siteToOpen, neighbour);
        }
        if (this.toConnect(row, col - 1)) {
            int neighbour = this.convertInt(row, col - 1);
            if (this.uf.find(siteToOpen) != this.uf.find(neighbour)) this.uf.union(siteToOpen, neighbour);
        }
    }

    public boolean isOpen(int row, int col) {
        if (!this.inRange(row, col)) {
            throw new IllegalArgumentException("Arguments are unvalid");
        }

        if (this.model[row][col]) return true;
        return false;
    }

    public boolean isFull(int row, int col) {
        if (!this.inRange(row, col)) {
            throw new IllegalArgumentException("Arguments are unvalid");
        }
        int site = this.convertInt(row, col);

        if (this.uf.find(site) == this.uf.find(0)) return true;
        return false;
    }

    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    public boolean percolates() {
        if (this.uf.find(0) == this.uf.find(n * n + 1)) return true;
        return false;
    }

    private boolean toConnect(int row, int col) {
        if (this.inRange(row, col) && this.isOpen(row, col)) return true;
        return false;
    }

    private int convertInt(int row, int col) {
        return (row - 1) * this.n + col;
    }

    private boolean inRange(int row, int col) {
        if (row > n || row < 1 || col > n || col < 1) return false;
        return true;
    }

    public static void main(String[] args) {
        StdOut.println("Working");
    }
}
