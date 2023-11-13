import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
    private ArrayList<Point> begins = new ArrayList<Point>();
    private ArrayList<Point> ends = new ArrayList<Point>();
    private int numberOfSegments = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Argument is null");
        if (points.length < 4) return;
        Point[] pointsCopy = points.clone();

        for (int i = 0; i < points.length; i++) {
            // choose a point
            Point p = points[i];

            if (p == null) throw new IllegalArgumentException("Element is null");
            // copy original array and sort it based on slope with this point

            Arrays.sort(pointsCopy, p.slopeOrder());
            int counter = 1;

            // take a first slope as a reference (zero is reference Point)
            double slope = p.slopeTo(pointsCopy[1]);

            // declare crucial Points
            Point minPoint = pointsCopy[0], maxPoint = pointsCopy[0];

            // find any 3 or more points having same slope with point p
            for (int j = 2; j < pointsCopy.length; j++) {
                Point currentPoint = pointsCopy[j];

                // get a new slope
                double newSlope = p.slopeTo(currentPoint);

                // if we have equal slopes
                if (newSlope == slope) {
                    counter += 1;

                    // it means previous point has same slope
                    // check if previous point can be min / max
                    if (pointsCopy[j - 1].compareTo(minPoint) < 0) {
                        minPoint = pointsCopy[j - 1];
                    } else if (pointsCopy[j - 1].compareTo(maxPoint) > 0) {
                        maxPoint = pointsCopy[j - 1];
                    }

                    // check current point to be min max
                    if (currentPoint.compareTo(minPoint) < 0) {
                        minPoint = currentPoint;
                    } else if (currentPoint.compareTo(maxPoint) > 0) {
                        maxPoint = currentPoint;
                    }

                    // if current element is last
                    if (j == pointsCopy.length - 1 && counter >= 3) {
                        int present = 0;
                        for (int c = 0; c < begins.size(); c++) {
                            if (begins.get(c).compareTo(minPoint) == 0 && ends.get(c).compareTo(maxPoint) == 0) {
                                present = 1;
                            }
                        }

                        if (present == 0) {
                            LineSegment newLineSegent = new LineSegment(minPoint, maxPoint);
                            segments.add(newLineSegent);
                            numberOfSegments += 1;
                            begins.add(minPoint);
                            ends.add(maxPoint);
                        }
                    }

                } else {
                    if (counter >= 3) {
                        int present = 0;
                        for (int c = 0; c < begins.size(); c++) {
                            if (begins.get(c).compareTo(minPoint) == 0 && ends.get(c).compareTo(maxPoint) == 0) {
                                present = 1;
                            }
                        }

                        if (present == 0) {
                            LineSegment newLineSegent = new LineSegment(minPoint, maxPoint);
                            segments.add(newLineSegent);
                            numberOfSegments += 1;
                            begins.add(minPoint);
                            ends.add(maxPoint);
                        }
                    }
                    counter = 1;
                    slope = newSlope;
                    minPoint = pointsCopy[0];
                    maxPoint = pointsCopy[0];
                }
            }
        }
    }

    public int numberOfSegments() {
        return this.numberOfSegments;
    }

    public LineSegment[] segments() {
        LineSegment[] lineSegments = this.segments.toArray(new LineSegment[segments.size()]);
        return lineSegments;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

