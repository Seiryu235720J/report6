package jp.ac.uryukyu.ie.e235720;

/*二次元平面上の点を表現するためのシンプルなクラス
 * y は縦軸の座標を、x は横軸の座標を表す
 */

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