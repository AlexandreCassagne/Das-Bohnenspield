package student_player.mytools;

public final class HeuristicWizard {
	
	/**
	 * this is the global maximising player.
	 * Must be set before starting the game.
	 */
	public static int maxPlayer = 0;
	
	/**
	 * @param state The state to evaluate a heuristic for
	 * @return
	 */
	public static int heuristic(SearchNode state) {
		return (	state.postState.getScore(maxPlayer)
				- 	state.postState.getScore(maxPlayer == 1 ? 0 : 1)
				);
	}
}
