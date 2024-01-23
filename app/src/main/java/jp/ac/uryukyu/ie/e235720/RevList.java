package jp.ac.uryukyu.ie.e235720;

import java.util.ArrayList;

/*オセロの盤面上で石を裏返す際の情報を管理するためのクラス */

public class RevList {

    private Point center;
    private ArrayList<Point> revPoints = new ArrayList<>();

    RevList(Point p) {
        center = new Point(p.getY(), p.getX());
    }

    public Point getCenterPoint() {
        return new Point(center.getY(), center.getX());
    }

	/*裏返す対象の座標をリストに追加するメソッド */

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