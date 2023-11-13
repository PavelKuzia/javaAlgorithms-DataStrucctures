import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {
    private boolean solvable = false;
    private final ArrayList<Board> reversedSolution = new ArrayList<>();

    // an inner class to represent SearchNode
    private static class SearchNode implements Comparable<SearchNode> {
        public final Board board;
        public final SearchNode previousSearchNode;
        public final int priority;
        public final int moves;

        public SearchNode(Board board, int moves, SearchNode previousSearchNode) {
            this.board = board;
            this.previousSearchNode = previousSearchNode;
            this.moves = moves;
            this.priority = board.manhattan() + this.moves;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Input is null");
        MinPQ<SearchNode> PQO = new MinPQ<>();
        MinPQ<SearchNode> PQT = new MinPQ<>();

        SearchNode finalSN = null;

        // insert initial SearchNode
        SearchNode initialSearchNode = new SearchNode(initial, 0, null);
        PQO.insert(initialSearchNode);

        // insert twin SearchNode
        Board twin = initial.twin();
        SearchNode twinSearchNode = new SearchNode(twin, 0, null);
        PQT.insert(twinSearchNode);

        while (true) {
            // take a SearchNode with lowest priority (initial and twin)
            SearchNode SNO = PQO.delMin();
            SearchNode SNT = PQT.delMin();

            if (SNO.board.isGoal()) {
                this.solvable = true;
                finalSN = SNO;
                break;
            }

            // if twin board is a goal we leave the loop - the board is unsolvable
            if (SNT.board.isGoal()) break;

            // add neighbours to minPQ for initial board
            for (Board neighbour : SNO.board.neighbors()) {
                if (SNO.previousSearchNode == null || !SNO.previousSearchNode.board.equals(neighbour)) {
                    PQO.insert(new SearchNode(neighbour, SNO.moves + 1, SNO));
                }
            }

            // add neighbours to minPQ for twin board
            for (Board neighbour : SNT.board.neighbors()) {
                if (SNT.previousSearchNode == null || !SNT.previousSearchNode.board.equals(neighbour)) {
                    PQT.insert(new SearchNode(neighbour, SNT.moves + 1, SNT));
                }
            }
        }

        if (this.solvable) {
            SearchNode current = finalSN;
            while (current != null) {
                this.reversedSolution.add(current.board);
                current = current.previousSearchNode;
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!this.solvable) return -1;
        else return this.reversedSolution.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!this.solvable) return null;
        ArrayList<Board> solution = new ArrayList<>();

        for (int i = this.reversedSolution.size() - 1; i >= 0; i--) {
            solution.add(this.reversedSolution.get(i));
        }

        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
