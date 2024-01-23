package jp.ac.uryukyu.ie.e235720;

import java.util.List;
import java.util.Scanner;

public class GameLogic {

    private static final Scanner sc = new Scanner(System.in);

    private Board board;

    public GameLogic(Board board) {
        this.board = board;
    }

    /*ゲームを開始するメソッド
     * 自分と敵の手番を交互に処理する
     */

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

    /*石のおける場所を探すメソッド 
     * 石を置く場所がない場合は false を返し、続行可能な場合は true を返す
    */

    public List<Point> getSetPoints(String symbol) {
        return board.getSetPoints(symbol);
    }

    boolean processPlayerTurn(String symbol) {
        board.print();
        System.out.println("あなたの番です");
        board.findSetPoints(symbol);

        List<Point> setPoints = board.getSetPoints(symbol);
        if (!setPoints.isEmpty()) {
            Point set = getUserInput(setPoints);
            placeStone(symbol, set);
            return true;
        } else {
            System.out.println("あなたは石を置く場所がありませんでした");
            return false;
        }
    }

        private Point getUserInput(List<Point> setPoints) {
            Point set = null;
            while (set == null) {
                System.out.print("石を置く場所を選んでください（例: 4d）: ");
                String input = sc.next();
                if (isValidInput(input)) {
                    int y = input.charAt(0) - '0';
                    int x = input.charAt(1) - 'a' + 1;
                    Point userInput = new Point(y, x);
                    if (setPoints.contains(userInput)) {
                        set = userInput;
                    } else {
                        System.out.println("その場所には石を置くことができません");
                    }
                } else {
                    System.out.println("無効な入力です");
                }
            }
            return set;
        }

        boolean isValidInput(String input) {
            return (input.length() == 2) &&
                    (input.charAt(0) >= '1' && input.charAt(0) <= '8') &&
                    (input.charAt(1) >= 'a' && input.charAt(1) <= 'h');
        }

        private void placeStone(String symbol, Point set) {
            board.revStone(symbol, set.getY(), set.getX());
            System.out.println("---------------------------------------------------");
            System.out.println("あなたは " + set + " に石を置きました");
            board.setStone(symbol, set);
        }
    }