import java.util.ArrayList;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
    private int numberOfSegments = 0;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Argument is null");
        if (points.length < 4) {
            for (int i = 0; i < points.length; i++) {
                if (points[i] == null) throw new IllegalArgumentException("Point is null");
                for (int j = 0; j < points.length; j++) {
                    if (points[j] == null) throw new IllegalArgumentException("Point is null");
                    if (i != j && points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException("Similar!");
                }
            }
            return;
        }
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[m];

                        if (p == null || q == null || r == null || s == null) {
                            throw new IllegalArgumentException("Point is null");
                        } else if (p.compareTo(q) == 0 || p.compareTo(r) == 0 || p.compareTo(s) == 0 ||
                                q.compareTo(r) == 0 || q.compareTo(s) == 0 || r.compareTo(s) == 0) {
                            throw new IllegalArgumentException("Argument to the constructor contains a repeated point");
                        } else {
                            double firstSlope = p.slopeTo(q);
                            double secondSlope = p.slopeTo(r);
                            double thirdSlope = p.slopeTo(s);

                            if (firstSlope == secondSlope && firstSlope == thirdSlope) {
                                Point[] linePoints = new Point[4];
                                linePoints[0] = p;
                                linePoints[1] = q;
                                linePoints[2] = r;
                                linePoints[3] = s;

                                Point minPoint = new Point(0, 0), maxPoint = new Point(0, 0);
                                for (int x = 0; x < 4; x++) {
                                    int counterMax = 0;
                                    int counterMin = 0;
                                    for (int y = 0; y < 4; y++) {
                                        if (linePoints[x].compareTo(linePoints[y]) < 0) {
                                            counterMin += 1;
                                        } else if (linePoints[x].compareTo(linePoints[y]) > 0) {
                                            counterMax += 1;
                                        }
                                    }
                                    if (counterMin == 3) minPoint = linePoints[x];
                                    if (counterMax == 3) maxPoint = linePoints[x];
                                }
                                LineSegment segment = new LineSegment(minPoint, maxPoint);
                                segments.add(segment);
                                this.numberOfSegments += 1;
                            }
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return this.numberOfSegments;
    }

    public LineSegment[] segments() {
        LineSegment[] returnLineSegment = segments.toArray(new LineSegment[segments.size()]);
        return returnLineSegment;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.03);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
