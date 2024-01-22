package jp.ac.uryukyu.ie.e235720;

import java.util.Scanner;



public class Game {
	public static Scanner sc = new Scanner(System.in);
	public static void main (String[] args) {

		//盤の生成
		Board board = new Board();

		
		//メインループ
		while (true) {

			boolean playerCanSet = true;
			boolean enemyCanSet = true;


			//プレイヤーの処理----------
			board.print();
			System.out.println("あなたの番です");
			board.findSetPoints("●");
			if (board.blackList.size() != 0) {
				Point set = null;
				while (true) {
					String temp = sc.next();
					char c0 = temp.charAt(0), c1 = temp.charAt(1);
					if ((c1<'a'&&'h'<c1)) {
						System.out.println("入力が正しくありません");
					}
					if  (temp.length()!=2 || (c0<'1'||'8'<c0) || (c1<'a'||'h'<c1)){
						System.out.println("入力が正しくありません");
					}
					else {
						int y = temp.charAt(0) - '0';
						int x = temp.charAt(1) - 'a' + 1;
						if (board.containSetPoints(y, x) == true) {
							set = new Point(y, x);
							board.revStone("●", y, x);
							System.out.println("---------------------------------------------------");
							System.out.println("あなたは"+temp+"に石を置きました");
							break;
						}
						else {
							System.out.println("その場所には石を置くことができません");
						}
					}
				}
				board.setStone("●", set);
			}
			else {
				System.out.println("あなたは石を置く場所がありませんでした");
				playerCanSet = false;
			}

			//白の石
			board.print();
			System.out.println("あなたの番です");
			board.findSetPoints("○");
			if (board.whiteList.size() != 0) {
				Point set = null;
				while (true) {
					String temp = sc.next();
					char c0 = temp.charAt(0), c1 = temp.charAt(1);
					if ((c1<'a'&&'h'<c1)) {
						System.out.println("入力が正しくありません");
					}
					if  (temp.length()!=2 || (c0<'1'||'8'<c0) || (c1<'a'||'h'<c1)){
						System.out.println("入力が正しくありません");
					}
					else {
						int y = temp.charAt(0) - '0';
						int x = temp.charAt(1) - 'a' + 1;
						if (board.containSetPoints(y, x) == true) {
							set = new Point(y, x);
							board.revStone("○", y, x);
							System.out.println("---------------------------------------------------");
							System.out.println("あなたは"+temp+"に石を置きました");
							break;
						}
						else {
							System.out.println("その場所には石を置くことができません");
						}
					}
				}
				board.setStone("○", set);
			}
			else {
				System.out.println("あなたは石を置く場所がありませんでした");
				playerCanSet = false;
			}


			

			//ゲーム終了判定
			if (playerCanSet==false && enemyCanSet==false) {
				break;
			}


		}

		//対戦結果表示
		board.result();

	}
}
