package jp.ac.uryukyu.ie.e235720;

import java.util.ArrayList;
import java.util.List;

/*ゲームの盤面を表す2次元配列。"●" は黒石、"○" は白石を表し、"・" は空きマスを表す */

public class Board {

    private String[][] board = {
            {"　", "ａ", "ｂ", "ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "　"},
            {"１", "・", "・", "・", "・", "・", "・", "・", "・", "　"},
            {"２", "・", "・", "・", "・", "・", "・", "・", "・", "　"},
            {"３", "・", "・", "・", "・", "・", "・", "・", "・", "　"},
            {"４", "・", "・", "・", "○", "●", "・", "・", "・", "　"},
            {"５", "・", "・", "・", "●", "○", "・", "・", "・", "　"},
            {"６", "・", "・", "・", "・", "・", "・", "・", "・", "　"},
            {"７", "・", "・", "・", "・", "・", "・", "・", "・", "　"},
            {"８", "・", "・", "・", "・", "・", "・", "・", "・", "　"},
            {"　", "　", "　", "　", "　", "　", "　", "　", "　", "　"}
    };

    private Point[] D8 = {
            new Point(-1, -1), new Point(-1, 0), new Point(-1, 1),
            new Point(0, -1), new Point(0, 1),
            new Point(1, -1), new Point(1, 0), new Point(1, 1)
    };

    private ArrayList<RevList> whiteList = new ArrayList<>();
    private ArrayList<RevList> blackList = new ArrayList<>();

    private Stone stoneManager;

    public Board() {
        this.stoneManager = new Stone(this);
    }

    public String getEnemyStone(String stone) {
        return (stone.equals("○")) ? "●" : "○";
    }

	/*指定された石の裏返し可能な場所を探し、whiteList または blackList に追加する */

    public void findSetPoints(String stone) {
        if (stone.equals("●")) {
            blackList.clear();
        } else {
            whiteList.clear();
        }

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (!board[i][j].equals("・")) continue;

                boolean around = false;
                for (int k = 0; k < 8; k++) {
                    if (board[i + D8[k].getY()][j + D8[k].getX()].equals(getEnemyStone(stone))) {
                        around = true;
                    }
                }
                if (!around) continue;

                RevList revList = new RevList(new Point(i, j));

                for (int k = 0; k < 8; k++) {
                    Point nowP = revList.getCenterPoint();
                    ArrayList<Point> tempPoints = new ArrayList<>();
                    boolean canCapture = false;

                    while (true) {
                        nowP.move(D8[k]);
                        if (board[nowP.getY()][nowP.getX()].equals(getEnemyStone(stone))) {
                            tempPoints.add(new Point(nowP.getY(), nowP.getX()));
                        } else if (board[nowP.getY()][nowP.getX()].equals(stone)) {
                            if (tempPoints.size() > 0) {
                                canCapture = true;
                                break;
                            } else {
                                canCapture = false;
                                break;
                            }
                        } else {
                            canCapture = false;
                            break;
                        }
                    }

                    if (!canCapture) continue;

                    revList.addRevPoint(tempPoints);
                }

                if (revList.getRevNum() > 0) {
                    if (stone.equals("●")) {
                        blackList.add(revList);
                    } else {
                        whiteList.add(revList);
                    }
                }
            }
        }
    }

    public boolean containSetPoints(int y, int x) {
        for (RevList revList : (blackList.isEmpty()) ? whiteList : blackList) {
            if (revList.getCenterY() == y && revList.getCenterX() == x) {
                return true;
            }
        }
        return false;
    }

	/*指定された座標を登録する */

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

    public void revStone(String stone, int y, int x) {
        stoneManager.revStone(stone, y, x);
    }

    public void setStone(String stone, Point p) {
        board[p.getY()][p.getX()] = stone;
    }

    public void print() {
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

	/*打つ場所がなくなった時に結果を出力する */

    public void result() {
        System.out.println();
        System.out.println("ゲームが終了しました。結果は……");
        print();

        int blackNum = 0, whiteNum = 0;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (board[i][j].equals("○")) whiteNum++;
                else if (board[i][j].equals("●")) blackNum++;
            }
        }

        if (blackNum == whiteNum) {
            System.out.println(blackNum + "対" + whiteNum + "で引き分けです！");
        } else if (blackNum > whiteNum) {
            System.out.println(blackNum + "対" + whiteNum + "でプレイヤーの勝利です！");
        } else {
            System.out.println(blackNum + "対" + whiteNum + "でAIの勝利です！");
        }
    }

    public ArrayList<RevList> getBlackList() {
        return blackList;
    }

    public ArrayList<RevList> getWhiteList() {
        return whiteList;
    }

    public String[][] getBoard() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getBoard'");
    }
}