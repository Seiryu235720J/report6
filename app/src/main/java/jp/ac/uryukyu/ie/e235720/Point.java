package jp.ac.uryukyu.ie.e235720;

public class Point {
	
	private int y, x;
	
	Point (int a, int b) {
		y = a;
		x = b;
	}
	
	public int getY () {return y;}
	
	public int getX () {return x;}
	
	public void move (Point p) {
		y += p.getY();
		x += p.getX();
	}
	
}