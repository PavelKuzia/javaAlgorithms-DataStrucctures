import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;

public final class Board {
    private final int n;
    private final int[][] tiles;
    private int row;
    private int col;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        // determine position of an empty tile
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    this.row = i;
                    this.col = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder boardStr = new StringBuilder();

        boardStr.append(this.n).append("\n");

        for (int[] tile : this.tiles) {
            for (int t : tile) {
                boardStr.append(t).append("\t");
            }
            boardStr.append("\n");
        }
        return boardStr.toString();
    }

    // board dimension n
    public int dimension() {
        return this.tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hammingSum = 0;
        int targetValue = 1;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                int val = this.tiles[i][j];
                if (val != targetValue && val != 0) hammingSum += 1;
                targetValue += 1;
            }
        }
        return hammingSum;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanSum = 0;
        int rowGoal, colGoal;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                int val = this.tiles[i][j];
                if (val == 0) continue;
                rowGoal = (val - 1) / this.n;
                colGoal = (val - 1) % this.n;
                manhattanSum += Math.abs(rowGoal - i) + Math.abs(colGoal - j);
            }
        }
        return manhattanSum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (y == null) return false;
        else if (y == this) return true;
        else if (y.getClass() != this.getClass()) {
            return false;
        }
        Board b = (Board) y;
        if (this.n != b.n) return false;
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles.length; j++) {
                if (this.tiles[i][j] != b.tiles[i][j]) return false;
            }
        }
        return true;
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        // determine positions of possible next moves
        List<int[]> nextMoves = new ArrayList<>();
        if (row - 1 >= 0) nextMoves.add(new int[]{row - 1, col});
        if (row + 1 < this.n) nextMoves.add(new int[]{row + 1, col});
        if (col - 1 >= 0) nextMoves.add(new int[]{row, col - 1});
        if (col + 1 < this.n) nextMoves.add(new int[]{row, col + 1});

        ArrayList<Board> neighbours = new ArrayList<>();
        // empty array to hold copies of tiles array (saved at different addresses)
        int[][] copyTiles = null;
        for (int[] move : nextMoves) {
            copyTiles = cloneTiles();
            copyTiles[row][col] = copyTiles[move[0]][move[1]];
            copyTiles[move[0]][move[1]] = 0;
            neighbours.add(new Board(copyTiles));
        }
        // List is also iterable - hence can be returned alternatively to implementing next / hasNext methods
        return neighbours;
    }

    // auxiliary method to create a new copy of tiles array
    private int[][] cloneTiles() {
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = tiles[i][j];
            }
        }
        return a;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newTiles = cloneTiles();

        int x0 = 0, y0 = 0, x1 = 0, y1 = 0;
        boolean firstCoordinateIsSet = false;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (newTiles[i][j] != 0 && !firstCoordinateIsSet) {
                    x0 = i;
                    y0 = j;
                    firstCoordinateIsSet = true;
                } else if (newTiles[i][j] != 0 && firstCoordinateIsSet) {
                    x1 = i;
                    y1 = j;
                    break;
                }
            }
        }
        int temp = newTiles[x0][y0];
        newTiles[x0][y0] = newTiles[x1][y1];
        newTiles[x1][y1] = temp;

        return new Board(newTiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // print out a board
        System.out.println(initial.toString());

        // print out its dimension
        System.out.print("Dimension: ");
        System.out.println(initial.dimension());

        // print out its hamming sum
        System.out.print("Hamming sum: ");
        System.out.println(initial.hamming());

        // print out its manhattan sum
        System.out.print("Manhattan sum: ");
        System.out.println(initial.manhattan());

        // print out is it goal board
        System.out.print("Is goal: ");
        System.out.println(initial.isGoal());

        // print out neighbouring boards
        for (Board neighbour : initial.neighbors()) {
            System.out.println(neighbour.toString());
        }

        // print out twin board
        System.out.println(initial.twin().toString());
    }
}
