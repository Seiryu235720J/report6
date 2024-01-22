package jp.ac.uryukyu.ie.e235720;

import java.util.ArrayList;

public class RevList {

    private Point center;
    private ArrayList<Point> revPoints = new ArrayList<>();

    RevList(Point p) {
        center = new Point(p.getY(), p.getX());
    }

    public Point getCenterPoint() {
        return new Point(center.getY(), center.getX());
    }

    public void addRevPoint(ArrayList<Point> list) {
        revPoints.addAll(list);
    }

    public ArrayList<Point> getRevPoints() {
        return revPoints;
    }

    public int getCenterY() {
        return center.getY();
    }

    public int getCenterX() {
        return center.getX();
    }

    public int getRevNum() {
        return revPoints.size();
    }
}