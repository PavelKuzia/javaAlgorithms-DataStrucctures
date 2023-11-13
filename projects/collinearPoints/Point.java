import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) return -1;
        else if (this.y == that.y && this.x == that.x) return 0;
        else return 1;
    }

    public double slopeTo(Point that) {
        if (this.y == that.y && this.x == that.x) return Double.NEGATIVE_INFINITY;
        else if (this.y == that.y) return +0.0;
        else if (this.x == that.x) return Double.POSITIVE_INFINITY;
        else return (double) (that.y - this.y) / (double) (that.x - this.x);
    }

    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return Double.compare(slopeTo(p1), slopeTo(p2));
            }
        };
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 5);
        StdDraw.setYscale(0, 5);
        StdDraw.setPenRadius(0.05);
        Point first = new Point(2, 3);
        Point second = new Point(2, 1);
        StdOut.println(first.slopeTo(second));
        first.drawTo(second);
    }
}
