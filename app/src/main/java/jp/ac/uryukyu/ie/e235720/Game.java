package jp.ac.uryukyu.ie.e235720;

import java.util.Scanner;





public class Game {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Board board = new Board();
        GameLogic gameLogic = new GameLogic(board);

        gameLogic.runGame();
    }
}