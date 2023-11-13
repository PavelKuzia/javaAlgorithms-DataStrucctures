import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int size;

    private static class Node {
        private final Point2D key;
        private Node left, right;

        public Node(Point2D key) {
            this.key = key;
        }
    }

    public KdTree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public int size() {
        return this.size;
    }

    private Node newPoint(Node n, Point2D k, boolean h) {
        if (n == null) {
            this.size += 1;
            return new Node(k);
        } else {
            int comparison;
            if (h) comparison = Point2D.X_ORDER.compare(k, n.key);
            else comparison = Point2D.Y_ORDER.compare(k, n.key);
            if (comparison < 0) n.left = newPoint(n.left, k, !h);
            else n.right = newPoint(n.right, k, !h);
        }
        return n;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (this.contains(p)) return;
        this.root = newPoint(this.root, p, true);
    }

    private boolean searchPoint(Node n, Point2D p, boolean h) {
        if (n == null) return false;
        else if (n.key.equals(p)) return true;
        int comparison;
        boolean result;
        if (h) comparison = Point2D.X_ORDER.compare(p, n.key);
        else comparison = Point2D.Y_ORDER.compare(p, n.key);
        if (comparison < 0) result = searchPoint(n.left, p, !h);
        else result = searchPoint(n.right, p, !h);
        return result;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return searchPoint(this.root, p, true);
    }

    private void drawPoint(Node n, double xmin, double xmax, double ymin, double ymax, boolean d) {
        if (n == null) return;
        // make sure that all points are painted in black
        StdDraw.setPenColor(StdDraw.BLACK);
        // StdDraw.setPenRadius(0.015);
        n.key.draw();
        // draw division lines
        // StdDraw.setPenRadius(0.01);
        if (d) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.key.x(), ymin, n.key.x(), ymax);
            // draw children points
            drawPoint(n.left, xmin, n.key.x(), ymin, ymax, !d);
            drawPoint(n.right, n.key.x(), xmax, ymin, ymax, !d);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xmin, n.key.y(), xmax, n.key.y());
            // draw children points
            drawPoint(n.left, xmin, xmax, ymin, n.key.y(), !d);
            drawPoint(n.right, xmin, xmax, n.key.y(), ymax, !d);
        }
    }

    public void draw() {
        if (this.root == null) return;
        drawPoint(this.root, 0, 1, 0, 1, true);
    }

    // d true corresponds to horizontal direction
    private void searchRange(Node n, RectHV rect, ArrayList<Point2D> res, boolean d) {
        if (n == null) return;
        if (rect.contains(n.key)) res.add(n.key);

        if (d) {
            if (rect.xmin() <= n.key.x()) searchRange(n.left, rect, res, !d);
            if (rect.xmax() >= n.key.x()) searchRange(n.right, rect, res, !d);
        } else {
            if (rect.ymin() <= n.key.y()) searchRange(n.left, rect, res, !d);
            if (rect.ymax() >= n.key.y()) searchRange(n.right, rect, res, !d);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> points = new ArrayList<>();
        searchRange(this.root, rect, points, true);
        return points;
    }

    private Point2D searchNearestPoint(Node n, Point2D p, Point2D champ, boolean d) {
        if (n == null) return champ;
        else if (champ == null) champ = n.key;
        else if (n.key.distanceSquaredTo(p) < champ.distanceSquaredTo(p)) champ = n.key;

        if (d) {
            if (p.x() < n.key.x()) {
                champ = searchNearestPoint(n.left, p, champ, !d);
                if (Math.abs(p.x() - n.key.x()) < champ.distanceSquaredTo(p))
                    champ = searchNearestPoint(n.right, p, champ, !d);
            } else {
                champ = searchNearestPoint(n.right, p, champ, !d);
                if (Math.abs(p.x() - n.key.x()) < champ.distanceSquaredTo(p))
                    champ = searchNearestPoint(n.left, p, champ, !d);
            }
        } else {
            if (p.y() < n.key.y()) {
                champ = searchNearestPoint(n.left, p, champ, !d);
                if (Math.abs(p.y() - n.key.y()) < champ.distanceSquaredTo(p))
                    champ = searchNearestPoint(n.right, p, champ, !d);
            } else {
                champ = searchNearestPoint(n.right, p, champ, !d);
                if (Math.abs(p.y() - n.key.y()) < champ.distanceSquaredTo(p))
                    champ = searchNearestPoint(n.left, p, champ, !d);
            }
        }
        return champ;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return searchNearestPoint(this.root, p, null, true);
    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();

        tree.insert(new Point2D(0, 1));
        tree.insert(new Point2D(0.5, 0.25));
        tree.insert(new Point2D(1, 0.75));
        tree.insert(new Point2D(1, 0.25));
        tree.insert(new Point2D(0.25, 1));
        tree.insert(new Point2D(0.75, 1));
        tree.insert(new Point2D(0.25, 0));
        tree.insert(new Point2D(0, 0.75));
        tree.insert(new Point2D(0.25, 0.25));
        tree.insert(new Point2D(1, 1));

        RectHV rect = new RectHV(0, 0, 0.75, 0.25);
        tree.draw();
        rect.draw();
        for (Point2D p : tree.range(rect)) System.out.printf("(%f, %f)\n", p.x(), p.y());

    }
}
