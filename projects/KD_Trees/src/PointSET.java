import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private final SET<Point2D> points;

    public PointSET() {
        points = new SET<Point2D>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Illegal argument in insert");
        else if (points.contains(p)) return;
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Illegal argument in contains");
        return points.contains(p);
    }

    public void draw() {
        if (points.isEmpty()) return;
        // StdDraw.setPenRadius(0.02);
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Illegal argument in range");
        ArrayList<Point2D> res = new ArrayList<>();
        for (Point2D p : points) {
            if (rect.contains(p)) res.add(p);
        }
        return res;
    }

    public Point2D nearest(Point2D p) {
        if (points.isEmpty()) return null;
        else if (p == null) throw new IllegalArgumentException("Illegal argument in nearest");
        double distance = Double.POSITIVE_INFINITY;
        Point2D resPoint = null;

        for (Point2D myP : points) {
            double newDistance = myP.distanceSquaredTo(p);
            if (newDistance < distance) {
                resPoint = new Point2D(myP.x(), myP.y());
                distance = newDistance;
            }
        }

        return resPoint;
    }

    public static void main(String[] args) {
        /*
        PointSET myPointSET = new PointSET();
        In in = new In(args[0]);
        while (!in.isEmpty()) {
            String[] line = in.readLine().split(" ");
            myPointSET.insert(new Point2D(Double.parseDouble(line[0]), Double.parseDouble(line[1])));
        }

        myPointSET.draw();
         */
    }

}
