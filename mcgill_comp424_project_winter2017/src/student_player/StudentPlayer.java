package student_player;

import java.util.ArrayList;

import boardgame.Move;
import bohnenspiel.BohnenspielBoard;
import bohnenspiel.BohnenspielBoardState;
import bohnenspiel.BohnenspielMove;
import bohnenspiel.BohnenspielPlayer;
import bohnenspiel.BohnenspielMove.MoveType;
import student_player.mytools.Minimax;
import student_player.mytools.MyTools;
import student_player.mytools.Solver;

/** A Hus player submitted by a student. */
public class StudentPlayer extends BohnenspielPlayer {
	/** You must modify this constructor to return your student number.
	 * This is important, because this is what the code that runs the
	 * competition uses to associate you with your agent.
	 * The constructor should do nothing else. */
	public StudentPlayer() { super("260617865"); }

	/** This is the primary method that you need to implement.
	 * The ``board_state`` object contains the current state of the game,
	 * which your agent can use to make decisions. See the class 
	 * bohnenspiel.RandomPlayer
	 * for another example agent.
	 **/
	
	private Minimax solver;
	
	public BohnenspielMove chooseMove(BohnenspielBoardState board_state)
	{
		if (solver == null) {
			solver = new Minimax();
			solver.findAndSet(board_state, null, depth);
		};
		
		
		
		// Get the contents of the pits so we can use it to make decisions.
		int[][] pits = board_state.getPits();
		
		// Use ``player_id`` and ``opponent_id`` to get my pits and opponent pits.
		int[] my_pits = pits[player_id];
		int[] op_pits = pits[opponent_id];
		
		
	}
}