package student_player.mytools;

import bohnenspiel.BohnenspielBoard;
import bohnenspiel.BohnenspielBoardState;

import static bohnenspiel.BohnenspielBoardState.BOARD_WIDTH;

public class MyTools {
	
	private static Runtime rt = Runtime.getRuntime();
	public static long usedMemory() {
		return (rt.totalMemory() - rt.freeMemory());
	}
	
	public static boolean boardStatesEqual(BohnenspielBoardState a, BohnenspielBoardState b) {
		int [][] pitsA = a.getPits();
		int [][] pitsB = b.getPits();
		
		if (a.getTurnPlayer() != b.getTurnPlayer()) return false;
		if (a.getTurnNumber() != b.getTurnNumber()) return false;
		if (a.firstPlayer() != b.firstPlayer()) return false;
		if (a.getWinner() != b.getWinner()) return false;
		if (a.gameOver() != b.gameOver()) return false;
		
		
		for(int i = 0; i < 2 * BohnenspielBoardState.BOARD_WIDTH; i++){
			if (pitsA[0][i] != pitsB[0][i]) return false;
			if (pitsA[1][i] != pitsB[1][i]) return false;
		}
		
		return true;
	}
	/**
	 *         StringBuilder sb = new StringBuilder();
	 sb.append("Omweso board:\n");
	 sb.append("Player 0: \n");
	 for(int i = 0; i < 2 * BOARD_WIDTH; i++){
	 if(i > 0)
	 sb.append(",");
	 
	 sb.append(Integer.toString(board[0][i]));
	 }
	 
	 sb.append("\nPlayer 1: \n");
	 for(int i = 0; i < 2 * BOARD_WIDTH; i++){
	 if(i > 0)
	 sb.append(",");
	 
	 sb.append(Integer.toString(board[1][i]));
	 }
	 
	 sb.append("\nNext to play: " + turn_player);
	 sb.append("\nPlays first: " + first_player);
	 sb.append("\nWinner: " + winner);
	 sb.append("\nTurns played: " + turn_number);
	 
	 return sb.toString();
	 */
	
	
}
