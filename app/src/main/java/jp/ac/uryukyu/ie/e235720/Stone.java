package jp.ac.uryukyu.ie.e235720;

import java.util.ArrayList;

public class Stone {

    private Board board;

    public Stone(Board board) {
        this.board = board;
    }

    public void revStone(String stone, int y, int x) {
        ArrayList<RevList> revList = (stone.equals("●")) ? board.getBlackList() : board.getWhiteList();

        for (int i = 0; i < revList.size(); i++) {
            if (revList.get(i).getCenterY() == y && revList.get(i).getCenterX() == x) {
                ArrayList<Point> list = revList.get(i).getRevPoints();
                for (int j = 0; j < list.size(); j++) {
                    board.getBoard()[list.get(j).getY()][list.get(j).getX()] = stone;
                }
                return;
            }
        }
    }
}