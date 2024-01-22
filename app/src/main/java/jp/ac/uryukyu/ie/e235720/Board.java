package jp.ac.uryukyu.ie.e235720;

import java.util.ArrayList;

import java.util.List;

public class Board {

	private String[][] board = {

			{"　","ａ","ｂ","ｃ","ｄ","ｅ","ｆ","ｇ","ｈ","　"},
			{"１","・","・","・","・","・","・","・","・","　"},
			{"２","・","・","・","・","・","・","・","・","　"},
			{"３","・","・","・","・","・","・","・","・","　"},
			{"４","・","・","・","○" ,"●" ,"・","・","・","　"},
			{"５","・","・","・","●" ,"○" ,"・","・","・","　"},
			{"６","・","・","・","・","・","・","・","・","　"},
			{"７","・","・","・","・","・","・","・","・","　"},
			{"８","・","・","・","・","・","・","・","・","　"},
			{"　","　","　","　","　","　","　","　","　","　"}
	};

	private Point[] D8 = {
			new Point(-1, -1), new Point(-1, 0), new Point(-1, 1),
			new Point( 0, -1),                   new Point( 0, 1),
			new Point( 1, -1), new Point( 1, 0), new Point( 1, 1)
	};

	ArrayList<RevList> whiteList = new ArrayList<>();
	ArrayList<RevList> blackList = new ArrayList<>();

	//相手の石の色を取得するメソッド
	public String getEnemyStone (String stone) {
		if (stone.equals("○")) return "●";
		else return "○";
	}

	public void findSetPoints (String stone) {
		if (stone.equals("●")) {
			blackList.clear();
		}
		else {
			whiteList.clear();
		}

		for (int i=1; i<=8; i++) {
			for (int j=1; j<=8; j++) {

				//石がすでに置いてある場合はcontinue
				if (board[i][j].equals("・") == false) continue;

				//まず周囲八方向を見る。相手の石が1個もなければcontinue
				boolean around = false;
				for (int k=0; k<8; k++) {
					if (board[i+D8[k].getY()][j+D8[k].getX()].equals(getEnemyStone(stone))) {
						around = true;
					}
				}
				if (around == false) continue;

				//1個以上相手の石があるので、探索を開始する
				RevList revList = new RevList(new Point(i, j));

				for (int k=0; k<8; k++) {

					Point nowP = revList.getCenterPoint();
					ArrayList<Point> tempPoints = new ArrayList<>();
					boolean canCapture = false;

					//八方向に伸ばしていって、相手の石を挟めるかどうか探索する
					while (true) {
						nowP.move(D8[k]);
						if (board[nowP.getY()][nowP.getX()].equals(getEnemyStone(stone)) == true) {
							tempPoints.add(new Point(nowP.getY(), nowP.getX()));
						}
						else if (board[nowP.getY()][nowP.getX()].equals(stone) == true) {
							if (tempPoints.size() > 0) {
								canCapture = true;
								break;
							}
							else {
								canCapture = false;
								break;
							}
						}
						else {
							canCapture = false;
							break;
						}
					}

					//自分の石がなければcontinue
					if (canCapture == false) continue;

					revList.addRevPoint(tempPoints);

				}

				//1個以上の相手の石を裏返せる場合のみ、リストに追加
				if (revList.getRevNum() > 0) {
					if (stone.equals("●")) {
						blackList.add(revList);
					}
					else {
						whiteList.add(revList);
					}
				}

			}
		}
	}

	//プレイヤーが選んだ位置がリストに含まれているかを判定するメソッド
	public boolean containSetPoints (int y, int x) {
		for (int i=0; i<blackList.size(); i++) {
			if (blackList.get(i).getCenterY()==y && blackList.get(i).getCenterX()==x) {
				return true;
			}
		}
		return false;
	}

	//石を裏返すメソッド

	public List<Point> getSetPoints(String symbol) {
        List<Point> setPoints = new ArrayList<>();
        if (symbol.equals("●")) {
            for (RevList revList : blackList) {
                setPoints.add(revList.getCenterPoint());
            }
        } else if (symbol.equals("○")) {
            for (RevList revList : whiteList) {
                setPoints.add(revList.getCenterPoint());
            }
        }
        return setPoints;
    }
	

	public void revStone (String stone, int y, int x) {
		if (stone.equals("○")) {
			for (int i=0; i<whiteList.size(); i++) {
				if (whiteList.get(i).getCenterY()==y && whiteList.get(i).getCenterX()==x) {
					ArrayList<Point> list = whiteList.get(i).getRevPoints();
					for (int j=0; j<list.size(); j++) {
						board[list.get(j).getY()][list.get(j).getX()] = stone;
					}
					return;
				}
			}
		}
		else {
			for (int i=0; i<blackList.size(); i++) {
				if (blackList.get(i).getCenterY()==y && blackList.get(i).getCenterX()==x) {
					ArrayList<Point> list = blackList.get(i).getRevPoints();
					for (int j=0; j<list.size(); j++) {
						board[list.get(j).getY()][list.get(j).getX()] = stone;
					}
					return;
				}
			}
		}
	}

	//石を置くメソッド
	public void setStone (String stone, Point p) {
		board[p.getY()][p.getX()] = stone;
	}

	public void print () {
		for (int i=0; i<=9; i++) {
			for (int j=0; j<=9; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}

	public void result () {
		System.out.println();
		System.out.println("ゲームが終了しました。結果は……");
		print();

		int blackNum = 0, whiteNum = 0;
		for (int i=1; i<=8; i++) {
			for (int j=1; j<=8; j++) {
				if (board[i][j].equals("○")) whiteNum++;
				else if (board[i][j].equals("●")) blackNum++;
			}
		}

		if (blackNum == whiteNum) {
			System.out.println(blackNum+"対"+whiteNum+"で引き分けです！");
		}
		else if (blackNum > whiteNum) {
			System.out.println(blackNum+"対"+whiteNum+"でプレイヤーの勝利です！");
		}
		else {
			System.out.println(blackNum+"対"+whiteNum+"でAIの勝利です！");
		}

	}

}
