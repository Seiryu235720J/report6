package jp.ac.uryukyu.ie.e235720;

import java.util.Scanner;
import java.util.List;


public class GameLogic {
    
    private static final Scanner sc = new Scanner(System.in);

    private Board board;

    public GameLogic(Board board) {
        this.board = board;
    }

    public void runGame() {
        while (true) {
            boolean playerCanSet = processPlayerTurn("●");
            boolean enemyCanSet = processPlayerTurn("○");

            if (!playerCanSet && !enemyCanSet) {
                break;
            }
        }

        board.result();
    }

    public List<Point> getSetPoints(String symbol) {
        return board.getSetPoints(symbol);
    }

    private boolean processPlayerTurn(String symbol) {
        board.print();
        System.out.println("あなたの番です");
        board.findSetPoints(symbol);

        if (board.getSetPoints(symbol).size() != 0) {
            Point set = getUserInput(symbol);
            placeStone(symbol, set);
            return true;
        } else {
            System.out.println("あなたは石を置く場所がありませんでした");
            return false;
        }
    }

    private Point getUserInput(String symbol) {
        Point set = null;
        while (true) {
            String temp = sc.next();
            if (isValidInput(temp)) {
                int y = temp.charAt(0) - '0';
                int x = temp.charAt(1) - 'a' + 1;
                if (board.containSetPoints(y, x)) {
                    set = new Point(y, x);
                    break;
                } else {
                    System.out.println("その場所には石を置くことができません");
                }
            }
        }
        return set;
    }

    private boolean isValidInput(String input) {
        char c0 = input.charAt(0), c1 = input.charAt(1);
        return (c1 >= 'a' && c1 <= 'h') &&
                (input.length() == 2) &&
                (c0 >= '1' && c0 <= '8') &&
                (c1 >= 'a' && c1 <= 'h');
    }

    private void placeStone(String symbol, Point set) {
        board.revStone(symbol, set.getY(), set.getX());
        System.out.println("---------------------------------------------------");
        System.out.println("あなたは " + set + " に石を置きました");
        board.setStone(symbol, set);
    }
}