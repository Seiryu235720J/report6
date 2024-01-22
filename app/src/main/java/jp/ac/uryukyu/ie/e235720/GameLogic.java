package jp.ac.uryukyu.ie.e235720;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        System.out.println("Player's turn: " + (symbol.equals("●") ? "Black" : "White"));
        board.findSetPoints(symbol);

        List<Point> setPoints = board.getSetPoints(symbol);
        if (!setPoints.isEmpty()) {
            Point set = getUserInput(setPoints);
            placeStone(symbol, set);
            return true;
        } else {
            System.out.println("No valid moves for " + (symbol.equals("●") ? "Black" : "White"));
            return false;
        }
    }

    private Point getUserInput(List<Point> setPoints) {
        Point set = null;
        while (set == null) {
            System.out.print("Enter your move (e.g., 4d): ");
            String input = sc.next();
            if (isValidInput(input)) {
                int y = input.charAt(0) - '0';
                int x = input.charAt(1) - 'a' + 1;
                Point userInput = new Point(y, x);
                if (setPoints.contains(userInput)) {
                    set = userInput;
                } else {
                    System.out.println("You cannot place a stone there.");
                }
            } else {
                System.out.println("Invalid input.");
            }
        }
        return set;
    }

    private boolean isValidInput(String input) {
        return (input.length() == 2) &&
                (input.charAt(0) >= '1' && input.charAt(0) <= '8') &&
                (input.charAt(1) >= 'a' && input.charAt(1) <= 'h');
    }

    private void placeStone(String symbol, Point set) {
        board.revStone(symbol, set.getY(), set.getX());
        System.out.println("-----------------------------------------------");
        System.out.println("Placed a stone at " + set);
        board.setStone(symbol, set);
    }
}