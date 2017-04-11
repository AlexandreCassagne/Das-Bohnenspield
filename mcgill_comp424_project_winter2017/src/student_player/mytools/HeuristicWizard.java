package student_player.mytools;

import boardgame.BoardState;
import bohnenspiel.BohnenspielBoardState;

public final class HeuristicWizard {
	
	/**
	 * this is the global maximising player.
	 * Must be set before starting the game.
	 */
	private static int maxPlayer = 0;
	private static int minPlayer = 1;
	
	
	
	public static void setPlayers(int maxPlayer, int minPlayer) {
		HeuristicWizard.maxPlayer = maxPlayer;
		HeuristicWizard.minPlayer = minPlayer;
	}
	
	/* **** Weights **** */
	private static int WIN_ADJUSTMENT = Integer.MAX_VALUE / 2 - 1; // to avoid overflows
	private static int DELTA_SCORE_POWER = 3;
	private static int DELTA_MOVES_POWER = 2;
	/**
	 * @param state The state to calibrateDepth a heuristic for
	 * @return The heuristic evaluated by the function
	 */
	public static int heuristic(SearchNode state) {
		
		if (state.postState.getWinner() == maxPlayer)
			return WIN_ADJUSTMENT;
		else if (state.postState.getWinner() == minPlayer)
			return -WIN_ADJUSTMENT;
		
		int playableMovesMax = playableMoves(state.postState, maxPlayer);
		int playableMovesMin = playableMoves(state.postState, minPlayer);
		
		int scoreAdjustment = 0;
		if (playableMovesMax == 0)
			scoreAdjustment -= sumScoreLeft(state.postState, minPlayer);
		else if (playableMovesMin == 0)
			scoreAdjustment -= sumScoreLeft(state.postState, maxPlayer);
		
		int delta = playableMovesMax - playableMovesMin;
		
		return (int) (Math.pow( (state.postState.getScore(maxPlayer) - state.postState.getScore(maxPlayer == 1 ? 0 : 1)),
				DELTA_SCORE_POWER)
						+ ((delta >= 0) ? 1 : -1) * Math.pow(delta, DELTA_MOVES_POWER));
	}
	
	
	public static int sumScoreLeft(BohnenspielBoardState state, int player) {
		int acc = 0;
		int [][] pits =  state.getPits();
		for (int i = 0; i < state.BOARD_WIDTH * 2; i++)
				acc += pits[player][i];
		return acc;
		
	}
	
	/**
	 *
	 * @param state the board to calibrateDepth
	 * @param player the player to calibrateDepth
	 * @return The number of moves available
	 */
	public static int playableMoves(BohnenspielBoardState state, int player) {
		int moves = 0;
		int [][] pits =  state.getPits();
		for (int i = 0; i < state.BOARD_WIDTH * 2; i++)
			if (pits[player][i] != 0)
				moves ++;
		return moves;
	}
	
	
	/**
	 * @param state
	 * @return The difference in number of moves playable between maxPlayer and minPlayer
	 */
	public static int deltaPlayableMoves(BohnenspielBoardState state) {
		int max = 0, min = 0;
		int [][] pits =  state.getPits();
		
		return playableMoves(state, maxPlayer) - playableMoves(state, minPlayer);
//
//		for (int i = 0; i < pits[0].length; i++) {
//			if (pits[maxPlayer][i] != 0)
//				max ++;
//			if (pits[minPlayer][i] != 0)
//				min ++;
//		}
//
//		return max - min;
	}
}
